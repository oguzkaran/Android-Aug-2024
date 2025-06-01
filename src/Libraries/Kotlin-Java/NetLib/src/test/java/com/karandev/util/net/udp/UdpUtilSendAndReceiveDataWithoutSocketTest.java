package com.karandev.util.net.udp;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.karandev.util.net.UdpUtil.*;

public class UdpUtilSendAndReceiveDataWithoutSocketTest {
    private static final String HOST = "localhost";
    private static final int PORT = 50500;
    private ExecutorService m_threadPool;

    private <T> void sendAndReceiveValue_ThenCompare(T value, Runnable runnable, Supplier<T> supplier)
    {
        try {
            var m_future = m_threadPool.submit(() -> senderCallback(runnable));

            var receiverValue = supplier.get();
            Thread.sleep(100);
            m_future.cancel(true);

            Assertions.assertEquals(value, receiverValue);
        } catch (InterruptedException ignored) {}
    }
    private void senderCallback(Runnable runnable)
    {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                runnable.run();
            }
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp()
    {
        m_threadPool = Executors.newSingleThreadExecutor();
    }

    @Test
    public void sendByteWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = (byte)1;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendByte(HOST, PORT, senderValue),
                () -> receiveByte(PORT));
    }

    @Test
    public void sendShortWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = (short)1;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendShort(HOST, PORT, senderValue),
                () -> receiveShort(PORT));
    }

    @Test
    public void sendIntWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendInt(HOST, PORT, senderValue),
                () -> receiveInt(PORT));
    }

    @Test
    public void sendLongWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1L;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendLong(HOST, PORT, senderValue),
                () -> receiveLong(PORT));
    }

    @Test
    public void sendFloatWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1F;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendFloat(HOST, PORT, senderValue),
                () -> receiveFloat(PORT));
    }

    @Test
    public void sendDoubleWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1D;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendDouble(HOST, PORT, senderValue),
                () -> receiveDouble(PORT));
    }

    @Test
    public void sendCharWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = '1';

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendChar(HOST, PORT, senderValue),
                () -> receiveChar(PORT));
    }

    @Test
    public void sendBooleanWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = true;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendBoolean(HOST, PORT, senderValue),
                () -> receiveBoolean(PORT));
    }

    @Test
    public void sendStringWithoutDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = "TEST";

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendString(HOST, PORT, senderValue),
                () -> receiveString(PORT, senderValue.length()));
    }

    @Test
    public void sendStringWithoutDataGramSocket_ThenReceiveAsDataGramPacket_CompareEquals()
    {
        var senderValue = "TEST";

        var future = m_threadPool.submit(() -> senderCallback(
                () -> sendString(HOST, PORT, senderValue)));

        var receiverValue = new String (receiveDatagramPacket(PORT, senderValue.length()).getData());
        future.cancel(true);

        Assertions.assertEquals(senderValue, receiverValue);
    }

    @AfterEach
    public void tearDown() throws IOException
    {
        m_threadPool.shutdownNow();
    }
}
