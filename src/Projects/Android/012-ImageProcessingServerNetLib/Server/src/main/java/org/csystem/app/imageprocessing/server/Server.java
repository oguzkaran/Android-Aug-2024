package org.csystem.app.imageprocessing.server;

import com.karandev.util.net.TcpUtil;
import com.karandev.util.net.exception.NetworkException;
import lombok.extern.slf4j.Slf4j;
import org.csystem.app.imageprocessing.server.constant.ImageProcessingCode;
import org.csystem.app.imageprocessing.server.constant.StatusCode;
import org.csystem.image.OpenCVUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

import static org.csystem.app.imageprocessing.server.constant.ImageProcessingCode.BINARY;
import static org.csystem.app.imageprocessing.server.constant.ImageProcessingCode.GRAY_SCALE;

@Component
@Slf4j
public class Server {
    private final ExecutorService m_executorService;
    private final DateTimeFormatter m_formatter;

    @Value("${app.server.port}")
    private int m_port;

    @Value("${app.client.socket.timeout}")
    private int m_socketTimeout;

    @Value("${app.image.transmission.bufsize}")
    private int m_bufferSize;

    @Value("${app.image.transmission.maxbufcount}")
    private int m_maxBufferCount;

    @Value("${app.image.transmission.maxfilenamedatalength}")
    private int m_maxFilenameDataLength;

    @Value("${app.image.directory}")
    private String m_imagesPath;

    private void doGrayScale(Socket socket, String path)
    {
        OpenCVUtil.grayScale(path, path + "gs.jpeg");
        //...

        //writeInt(socket.getOutputStream(), StatusCode.STATUS_SUCCESS);
    }

    private void doBinary(Socket socket, String path)
    {
        var threshold = TcpUtil.receiveInt(socket);

        OpenCVUtil.binary(path, path + "bin.jpeg", threshold); //redkit-xxxxxx-gs.jpeg
        //...

        //writeInt(socket.getOutputStream(), StatusCode.STATUS_SUCCESS);
    }

    private void doUnsupported(Socket socket)
    {
        TcpUtil.sendInt(socket, ImageProcessingCode.UNSUPPORTED);
    }

    private void doImageProcessing(Socket socket, String path) throws IOException
    {
        switch (TcpUtil.receiveInt(socket)) {
            case GRAY_SCALE -> doGrayScale(socket, path);
            case BINARY ->  doBinary(socket, path);
            //...
            default -> doUnsupported(socket);
        }
    }

    private void saveImageData(FileOutputStream fos, byte [] buffer, int len)
    {
        try {
            fos.write(buffer, 0, len);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int readDataCallback(Socket socket, byte[] buffer)
    {
        try {
            return TcpUtil.receive(socket, buffer);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String readFilename(Socket socket)
    {
        var filenameDataLength = TcpUtil.receiveInt(socket);

        if (filenameDataLength <= 0 || filenameDataLength > m_maxFilenameDataLength) {
            log.info("File name data length must be positive and less than {}", m_maxFilenameDataLength);
            TcpUtil.sendInt(socket, StatusCode.STATUS_FILENAME_DATA_LENGTH_ERROR);
            return "";
        }

        log.info("File name length: {}",  filenameDataLength);
        TcpUtil.sendInt(socket, StatusCode.STATUS_SUCCESS);

        var bytes = new byte[filenameDataLength];

        if (TcpUtil.receive(socket, bytes) != filenameDataLength) {
            log.info("File name receive length error:{}", filenameDataLength);
            TcpUtil.sendInt(socket, StatusCode.STATUS_FILENAME_LENGTH_RECEIVE_ERROR);
            return "";
        }

        TcpUtil.sendInt(socket, StatusCode.STATUS_SUCCESS);

        var filename = new String(bytes, StandardCharsets.UTF_8);

        log.info("Filename received successfully:{}", filename);

        return filename;
    }

    private String getImagePath(Socket socket,String filename)
    {
        var extension = filename.substring(filename.lastIndexOf('.') + 1);
        var name = filename.substring(0, filename.lastIndexOf('.'));

        return "%s/%s-%s-%s.%s".formatted(m_imagesPath, name,
                socket.getInetAddress().getHostAddress(), m_formatter.format(LocalDateTime.now()), extension);
    }

    private String readAndSaveImage(Socket socket, byte [] buffer) throws IOException
    {
        var filename = readFilename(socket);
        var path = getImagePath(socket, filename);
        var bufCount = TcpUtil.receiveInt(socket);

        if (bufCount <= 0 || bufCount > m_maxBufferCount) {
            log.info("Buffer count must be positive and less than {}",  m_maxBufferCount);
            TcpUtil.sendInt(socket, StatusCode.STATUS_BUFFER_COUNT_LIMIT_RECEIVE_ERROR);
            return "";
        }

        log.info("Buffer count:{}", bufCount);

        TcpUtil.sendInt(socket, StatusCode.STATUS_SUCCESS);

        try (var fos = new FileOutputStream(path)) {
            IntStream.generate(() -> readDataCallback(socket, buffer))
                     //.takeWhile(len -> len != -1)
                    .limit(bufCount)
                    .forEach(len -> saveImageData(fos, buffer, len));
        }

        return path;
    }


    private void sendInitialInfo(Socket socket) throws IOException
    {
        TcpUtil.sendInt(socket, m_bufferSize);
        TcpUtil.sendInt(socket, m_maxBufferCount);
        TcpUtil.sendInt(socket, m_maxFilenameDataLength);
    }

    private void handleClient(Socket socket)
    {
        try (socket) {
            socket.setSoTimeout(m_socketTimeout);
            log.info("Client connected from {}:{}", socket.getInetAddress().getHostAddress(), socket.getPort());

            sendInitialInfo(socket);
            var path = readAndSaveImage(socket, new byte[m_bufferSize]);

            doImageProcessing(socket, path);
        }
        catch (IOException ex) {
            log.error("IO Problem occurred:{}",  ex.getMessage());
        }
        catch (NetworkException ex) {
            log.error("Network Problem occurred:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred while client connected:{}",  ex.getMessage());
        }
    }

    public Server(ExecutorService executorService, DateTimeFormatter formatter)
    {
        m_executorService = executorService;
        m_formatter = formatter;
    }

    public void start()
    {
        log.info("Image processing server started on port {}", m_port);

        try (var serverSocket = new ServerSocket(m_port)) {
            while (true) {
                var socket = serverSocket.accept();

                m_executorService.execute(() -> handleClient(socket));
            }
        }
        catch (IOException ex) {
            log.error("IO Problem occurred while server is waiting: {}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred while server is waiting: {}",  ex.getMessage());
        }
    }
}
