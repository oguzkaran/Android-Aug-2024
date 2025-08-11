package org.csystem.app.imageprocessing.client;

import com.karandev.util.net.TcpUtil;
import com.karandev.util.net.exception.NetworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    private int sendFilename(Socket socket, String filename)
    {
        var filenameBytes = filename.getBytes(StandardCharsets.UTF_8);

        TcpUtil.sendInt(socket, filenameBytes.length);

        var status = TcpUtil.receiveInt(socket);

        log.info("Filename length status: {}", status);

        if (status != 0) {
            log.error("Filename length error: {}", status);
            return status;
        }

        TcpUtil.send(socket, filenameBytes);

        status = TcpUtil.receiveInt(socket);

        log.info("Filename status: {}", status);

        if (status != 0) {
            log.error("Filename error: {}", status);
            return status;
        }

        log.info("Filename sent successfully");
        return status;
    }

    private int sendBuffCount(Socket socket, long length, int bufSize)
    {
        var bufCount = (int)(length / bufSize) + (length % bufSize != 0 ? 1 : 0);

        log.info("Sending buffer count: {}", bufCount);
        TcpUtil.sendInt(socket, bufCount);
        var status = TcpUtil.receiveInt(socket);

        if (status != 0) {
            log.error("Sending buffer count error: {}", status);
            return status;
        }

        log.info("Send buffer count status: {}", status);


        return status;
    }

    private void sendImage(Socket socket, int bufSize) throws IOException
    {
        var path = "images/red-kit.jpeg";

        if (sendFilename(socket, "red-kit.jpeg") != 0)
            return;

        try (var raf = new RandomAccessFile(path, "r")) {
            if (sendBuffCount(socket, raf.length(), bufSize) != 0)
                return;

            var buffer = new byte[bufSize];
            int len;
            int total = 0;

            while ((len = raf.read(buffer)) != -1) {
                log.info("Len:{}", len);
                total += len;
                TcpUtil.send(socket, buffer, 0, len);
            }

            log.info("Total len:{}", total);
        }
    }

    private void grayScaleProc(Socket socket)
    {
        //...
    }

    private void binaryProc(Socket socket)
    {
        TcpUtil.sendInt(socket, 128);
        //...
    }

    private void unsupportedOperationProc(Socket socket)
    {
        var bytes = new byte[Integer.BYTES];

        if (TcpUtil.receive(socket, bytes) != Integer.BYTES) {
            log.error("Invalid data length");
            return;
        }

        var code = ByteBuffer.wrap(bytes).getInt();

        log.error("Unsupported operation code: {}", code);
    }

    private void doOperation(Socket socket) throws IOException
    {
        var operationCode = m_randomGenerator.nextInt(0, 3);

        log.info("Operation code: {}", operationCode);

        TcpUtil.sendInt(socket, operationCode);

        switch (operationCode) {
            case 1 -> grayScaleProc(socket);
            case 2 -> binaryProc(socket);
            default -> unsupportedOperationProc(socket);
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
            var bufSize = TcpUtil.receiveInt(socket);
            var maxBufCount = TcpUtil.receiveInt(socket);
            var maxFilenameDataLength = TcpUtil.receiveInt(socket);

            log.info("Buffer size: {}, Maximum buffer count: {}, Maximum filename data length:{}", bufSize, maxBufCount, maxFilenameDataLength);

            sendImage(socket, bufSize);
            doOperation(socket);
        }
        catch (IOException ex) {
            log.error("IO Problem occurred:{}",  ex.getMessage());
        }
        catch (NetworkException ex) {
            log.error("Network Problem occurred:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred:{}",  ex.getMessage());
        }
    }
}
