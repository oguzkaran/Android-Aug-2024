package org.csystem.app.imageprocessing.server;

import lombok.extern.slf4j.Slf4j;
import org.csystem.image.OpenCVUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

@Component
@Slf4j
public class Server {
    private final ExecutorService m_executorService;
    private final DateTimeFormatter m_formatter;

    @Value("${app.server.port}")
    private int m_port;

    @Value("${app.image.transmission.bufsize}")
    private int m_bufferSize;

    @Value("${app.image.transmission.maxbufcount}")
    private int m_maxBufferCount;

    @Value("${app.image.directory}")
    private String m_imagesPath;

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

    private void readAndSaveImage(Socket socket, byte [] buffer) throws IOException
    {
        var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var filename = br.readLine();
        var extension = filename.substring(filename.lastIndexOf('.') + 1);

        var path = "%s/%s-%s-%s.%s".formatted(m_imagesPath, filename,
                socket.getInetAddress().getHostAddress(), m_formatter.format(LocalDateTime.now()), extension);

        try (var fos = new FileOutputStream(path)) {
            IntStream.generate(() -> readDataCallback(socket, buffer))
                    .takeWhile(len -> len != -1)
                    .forEach(len -> saveImageData(fos, buffer, len));
        }

        OpenCVUtil.grayScale(path, path + "gs.jpeg");
    }

    private int readInt(InputStream is) throws IOException
    {
        byte [] bytes = new byte[Integer.BYTES];

        if (is.read(bytes) != Integer.BYTES)
            throw new IOException("Invalid data length");

        return ByteBuffer.wrap(bytes).getInt(0);
    }

    private void handleClient(Socket socket)
    {
        try (socket) {
            log.info("Client connected from {}:{}", socket.getInetAddress().getHostAddress(), socket.getPort());
            var os = socket.getOutputStream();
            var bufSizeData = ByteBuffer.allocate(Integer.BYTES).putInt(m_bufferSize).array();
            os.write(bufSizeData);
            readAndSaveImage(socket, new byte[m_bufferSize]);
        }
        catch (IOException ex) {
            log.error("IO Problem occurred while client connected:{}",  ex.getMessage());
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
        log.info("Starting server on port {}", m_port);

        try (var serverSocket = new ServerSocket(m_port)) {
            while (true) {
                var socket = serverSocket.accept();

                m_executorService.execute(() -> handleClient(socket));
            }
        }
        catch (IOException ex) {
            log.error("IO Problem occurred while server is waiting:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred while server is waiting:{}",  ex.getMessage());
        }
    }
}
