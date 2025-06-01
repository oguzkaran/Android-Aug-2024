package com.karandev.util.net.tcp.client;

import com.karandev.util.net.TCP;
import com.karandev.util.net.TCPClient;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpAndTcpClientSendReceiveLongTest {
    private static final String HOST = "localhost";
    private static final int PORT = 50500;
    private static final int SOCKET_TIMEOUT = 1000;
    private static final long SEND_LONG = 34L;
    private ServerSocket m_serverSocket;
    private ExecutorService m_threadPool;

    private void serverCallback()
    {
        try {
            m_serverSocket = new ServerSocket(PORT, 1024);
            var clientSocket = m_serverSocket.accept();
            clientSocket.setSoTimeout(SOCKET_TIMEOUT);

            var tcp = new TCP(clientSocket);
            var val = tcp.receiveLong();

            Assertions.assertEquals(SEND_LONG, val);

            tcp.sendLong(SEND_LONG);
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
    public void test() throws IOException, InterruptedException
    {
        Thread.sleep(100);
        try (var tcpClient = new TCPClient(HOST, PORT)) {
            tcpClient.sendLong(SEND_LONG);

            Assertions.assertEquals(SEND_LONG, tcpClient.receiveLong());
        }
    }

    @AfterEach
    public void tearDown() throws IOException
    {
        m_serverSocket.close();
        m_threadPool.shutdown();
    }
}
