package org.csystem.app.imageprocessing.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.random.RandomGenerator;

@Component
@Slf4j
public class Client {
    private final RandomGenerator m_randomGenerator;
    @Value("${server.imageprocessing.host}")
    private String m_host;

    @Value("${server.imageprocessing.port}")
    private int m_port;

    private int readInt(InputStream is) throws IOException
    {
        byte [] bytes = new byte[Integer.BYTES];

        if (is.read(bytes) != Integer.BYTES)
            throw new IOException("Invalid data length");

        return ByteBuffer.wrap(bytes).getInt(0);
    }

    private void writeInt(OutputStream os, int val) throws IOException
    {
        var bytes = ByteBuffer.allocate(Integer.BYTES).putInt(val).array();

        os.write(bytes);
    }

    private int sendFilename(Socket socket, String filename) throws IOException
    {
        var os = socket.getOutputStream();
        var is = socket.getInputStream();
        var filenameBytes = filename.getBytes(StandardCharsets.UTF_8);

        writeInt(os, filenameBytes.length);

        var status = readInt(is);

        log.info("Filename length status: {}", status);

        if (status != 0) {
            log.error("Filename length error: {}", status);
            return status;
        }

        os.write(filenameBytes);

        status = readInt(is);

        log.info("Filename status: {}", status);

        if (status != 0) {
            log.error("Filename error: {}", status);
            return status;
        }

        log.info("Send filename Status: {}", status);
        return status;
    }

    private int sendBuffCount(Socket socket, long length, int bufSize) throws IOException
    {
        var os = socket.getOutputStream();
        var is = socket.getInputStream();
        var bufCount = (int)(length / bufSize) + (length % bufSize != 0 ? 1 : 0);

        log.info("Sending buffer count: {}", bufCount);
        writeInt(os, bufCount);
        var status = readInt(is);

        if (status != 0) {
            log.error("Sending buffer count error: {}", status);
            return status;
        }

        log.info("Send buffer count status: {}", status);
        return status;
    }

    private void sendImage(Socket socket, int bufSize) throws IOException
    {
        var os = socket.getOutputStream();
        var path = "images/red-kit.jpeg";

        if (sendFilename(socket, "red-kit.jpeg") != 0)
            return;

        var buffer = new byte[bufSize];

        try (var raf = new RandomAccessFile(path, "r")) {
            if (sendBuffCount(socket, raf.length(), bufSize) != 0)
                return;

            int len;
            int total = 0;

            while ((len = raf.read(buffer)) != -1) {
                log.info("Len:{}", len);
                total += len;
                os.write(buffer, 0, len);
            }

            os.flush();
            log.info("Total len:{}", total);
        }
    }

    public Client(RandomGenerator randomGenerator)
    {
        m_randomGenerator = randomGenerator;
    }

    public void run()
    {
        try (var socket = new Socket(m_host, m_port)) {
            log.info("Console client connected to {}:{}", m_host, m_port);
            var is = socket.getInputStream();
            var os = socket.getOutputStream();
            var bufSize = readInt(is);
            var maxBufCount = readInt(is);
            var maxFilenameDataLength = readInt(is);

            log.info("Buffer size: {}, Maximum buffer count: {}, Maximum filename data length:{}", bufSize, maxBufCount, maxFilenameDataLength);

            sendImage(socket, bufSize);
            var operationCode = m_randomGenerator.nextInt(1, 3);

            log.info("Operation code: {}", operationCode);

            writeInt(os, operationCode);

            switch (operationCode) {
                case 1 -> {}
                case 2 -> {writeInt(os, 128);}
            }
        }
        catch (IOException ex) {
            log.error("IO Problem occurred:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred:{}",  ex.getMessage());
        }
    }
}
