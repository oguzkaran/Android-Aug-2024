package org.csystem.app.imageprocessing.server;

import lombok.extern.slf4j.Slf4j;
import org.csystem.app.imageprocessing.server.constant.ImageProcessingCode;
import org.csystem.app.imageprocessing.server.constant.StatusCode;
import org.csystem.image.OpenCVUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
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
    private final ApplicationContext m_applicationContext;
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

    private void doGrayScale(Socket socket, String path) throws IOException
    {
        OpenCVUtil.grayScale(path, path + "gs.jpeg");
        //...

        //writeInt(socket.getOutputStream(), StatusCode.STATUS_SUCCESS);
    }

    private void doBinary(Socket socket, String path) throws IOException
    {
        var threshold = readInt(socket.getInputStream());

        OpenCVUtil.binary(path, path + "bin.jpeg", threshold); //redkit-xxxxxx-gs.jpeg
        //...

        //writeInt(socket.getOutputStream(), StatusCode.STATUS_SUCCESS);
    }

    private void doUnsupported(Socket socket) throws IOException
    {
        socket.getOutputStream().write(ByteBuffer.allocate(Integer.BYTES).putInt(ImageProcessingCode.UNSUPPORTED).array());
    }

    private void doImageProcessing(Socket socket, String path) throws IOException
    {
        switch (readInt(socket.getInputStream())) {
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
            return socket.getInputStream().read(buffer);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String readFilename(Socket socket) throws IOException
    {
        var is = socket.getInputStream();
        var os = socket.getOutputStream();
        var filenameDataLength = readInt(is);

        if (filenameDataLength <= 0 || filenameDataLength > m_maxFilenameDataLength) {
            log.info("File name data length must be positive and less than {}", m_maxFilenameDataLength);
            writeInt(os, StatusCode.STATUS_FILENAME_DATA_LENGTH_ERROR);
            return "";
        }

        log.info("File name length: {}",  filenameDataLength);
        writeInt(os, StatusCode.STATUS_SUCCESS);

        var bytes = new byte[filenameDataLength];

        if (is.read(bytes) != filenameDataLength) {
            log.info("File name receive length error:{}", filenameDataLength);
            writeInt(os, StatusCode.STATUS_FILENAME_LENGTH_RECEIVE_ERROR);
            return "";
        }

        var filename = new String(bytes, StandardCharsets.UTF_8);

        log.info("Receive file name successfully completed:{}", filename);
        writeInt(os, StatusCode.STATUS_SUCCESS);

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
        var bufCount = readInt(socket.getInputStream());

        if (bufCount <= 0 || bufCount > m_maxBufferCount) {
            log.info("Buffer count must be positive and less than {}",  m_maxBufferCount);
            writeInt(socket.getOutputStream(), StatusCode.STATUS_BUFFER_COUNT_LIMIT_RECEIVE_ERROR);
            return "";
        }

        writeInt(socket.getOutputStream(), StatusCode.STATUS_SUCCESS);

        try (var fos = new FileOutputStream(path)) {
            IntStream.generate(() -> readDataCallback(socket, buffer))
                    .takeWhile(len -> len != -1)
                    .limit(bufCount)
                    .forEach(len -> saveImageData(fos, buffer, len));
        }

        return path;
    }

    private int readInt(InputStream is) throws IOException
    {
        var bytes = m_applicationContext.getBean(byte[].class);

        if (is.read(bytes) != Integer.BYTES)
            throw new IOException("Invalid data length");

        return ByteBuffer.wrap(bytes).getInt(0);
    }

    private void writeInt(OutputStream os, int val) throws IOException
    {
        var bytes = ByteBuffer.allocate(Integer.BYTES).putInt(val).array();

        os.write(bytes);
    }

    private void sendInitialInfo(Socket socket) throws IOException
    {
        var os = socket.getOutputStream();

        writeInt(os, m_bufferSize);
        writeInt(os, m_maxBufferCount);
        writeInt(os, m_maxFilenameDataLength);
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
            log.error("IO Problem occurred while client connected:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred while client connected:{}",  ex.getMessage());
        }
    }

    public Server(ExecutorService executorService, DateTimeFormatter formatter, ApplicationContext applicationContext)
    {
        m_executorService = executorService;
        m_formatter = formatter;
        m_applicationContext = applicationContext;
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
