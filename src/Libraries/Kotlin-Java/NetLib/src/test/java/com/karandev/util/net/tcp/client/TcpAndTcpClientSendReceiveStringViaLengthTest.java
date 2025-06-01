package com.karandev.util.net.tcp.client;

import com.karandev.util.net.TCP;
import com.karandev.util.net.TCPClient;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpAndTcpClientSendReceiveStringViaLengthTest {
    private static final String HOST = "localhost";
    private static final int PORT = 50500;
    private static final String SEND_TEXT = "Oguz Karan";
    private ServerSocket m_serverSocket;
    private ExecutorService m_threadPool;

    private void serverCallback()
    {
        try {
            m_serverSocket = new ServerSocket(PORT, 1024);
            var clientSocket = m_serverSocket.accept();
            var tcp = new TCP(clientSocket);
            var text = tcp.receiveStringViaLength();

            Assertions.assertEquals(SEND_TEXT, text);

            text = tcp.receiveStringViaLength();
            Assertions.assertEquals(SEND_TEXT.toUpperCase(), text);

            tcp.sendStringViaLength(SEND_TEXT);
        }
        catch (Throwable ex) {
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
            tcpClient.sendStringViaLength(SEND_TEXT);
            tcpClient.sendStringViaLength(SEND_TEXT.toUpperCase());

            Assertions.assertEquals(SEND_TEXT, tcpClient.receiveStringViaLength());
        }
    }

    @AfterEach
    public void tearDown() throws IOException
    {
        m_serverSocket.close();
        m_threadPool.shutdown();
    }
}
