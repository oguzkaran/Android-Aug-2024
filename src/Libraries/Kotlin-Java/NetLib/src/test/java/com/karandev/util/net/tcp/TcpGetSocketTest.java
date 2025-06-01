package com.karandev.util.net.tcp;

import com.karandev.util.net.TCP;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpGetSocketTest {
    private static final String HOST = "localhost";
    private static final int PORT = 50500;
    private ExecutorService m_threadPool;
    private ServerSocket m_serverSocket;

    private void serverCallback()
    {
        try {
            m_serverSocket = new ServerSocket(PORT, 1024);
            m_serverSocket.accept();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp()
    {
        m_threadPool = Executors.newSingleThreadExecutor();
        m_threadPool.execute(this::serverCallback);
    }

    @Test
    public void getSocketTest() throws IOException, InterruptedException
    {
        Thread.sleep(100);
        try (var socket = new Socket(HOST, PORT)) {
            var tcp = new TCP(socket);

            Assertions.assertNotNull(tcp.getSocket());
            Assertions.assertEquals(HOST, tcp.getSocket().getInetAddress().getHostName());
            Assertions.assertEquals(PORT, tcp.getSocket().getPort());
        }
    }

    @AfterEach
    public void tearDown() throws IOException
    {
        m_serverSocket.close();
        m_threadPool.shutdown();
    }
}
