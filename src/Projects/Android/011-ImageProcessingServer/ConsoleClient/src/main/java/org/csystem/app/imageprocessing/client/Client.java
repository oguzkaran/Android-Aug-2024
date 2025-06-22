package org.csystem.app.imageprocessing.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

@Component
@Slf4j
public class Client {
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

    private void sendImage(Socket socket, int bufSize) throws IOException
    {
        var os = socket.getOutputStream();
        var bw = new BufferedWriter(new OutputStreamWriter(os));
        var path = "images/x.jpeg";

        //bw.write("red-kit.jpeg\r\n");
        //bw.flush();
        byte [] buffer = new byte[bufSize];

        try (var fis = new FileInputStream(path)) {
            int len;

            int total = 0;

            while ((len = fis.read(buffer)) != -1) {
                log.info("Len:{}", len);
                total += len;
                os.write(buffer, 0, len);
            }

            os.flush();
            log.info("Total len:{}", total);
        }
    }

    public void run()
    {
        try (var socket = new Socket(m_host, m_port)) {
            log.info("Connected to {}:{}", m_host, m_port);
            var is = socket.getInputStream();
            var bufSize = readInt(is);

            log.info("Buffer size: {}", bufSize);

            sendImage(socket, bufSize);
        }
        catch (IOException ex) {
            log.error("IO Problem occurred:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred:{}",  ex.getMessage());
        }
    }
}
