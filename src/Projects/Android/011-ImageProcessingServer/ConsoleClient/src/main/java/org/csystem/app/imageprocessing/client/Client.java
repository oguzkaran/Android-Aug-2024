package org.csystem.app.imageprocessing.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Component
@Slf4j
public class Client {
    @Value("${server.imageprocessing.host}")
    private String m_host;

    @Value("${server.imageprocessing.port}")
    private int m_port;

    public void run()
    {
        try (var socket = new Socket(m_host, m_port)) {
            log.info("Connected to {}:{}", m_host, m_port);
            var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            var str = "Bugün hava çok güzel. Çok çok güzel";
            var s = "çok";

            bw.write("%s\r\n".formatted(str));
            bw.flush();
            var upperStr = br.readLine();

            log.info("Upper: {}", upperStr);
        }
        catch (IOException ex) {
            log.error("IO Problem occurred:{}",  ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Problem occurred:{}",  ex.getMessage());
        }
    }
}
