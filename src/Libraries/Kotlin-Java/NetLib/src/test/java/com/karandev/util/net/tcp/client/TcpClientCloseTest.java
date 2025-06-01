package com.karandev.util.net.tcp.client;

import com.karandev.util.net.TCPClient;
import com.karandev.util.net.exception.NetworkException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClientCloseTest {
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
    public void closeTest() throws InterruptedException
    {
        Thread.sleep(100);
        try (var tcpClient = new TCPClient(HOST, PORT)) {
            tcpClient.close();

            Assertions.assertTrue(tcpClient.getSocket().isClosed());
            Assertions.assertThrows(NetworkException.class, tcpClient::receiveInt);
        }
    }

    @AfterEach
    public void tearDown() throws IOException
    {
        m_serverSocket.close();
        m_threadPool.shutdown();
    }
}
