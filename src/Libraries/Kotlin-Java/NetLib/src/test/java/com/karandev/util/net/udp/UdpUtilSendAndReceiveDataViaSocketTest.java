package com.karandev.util.net.udp;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.karandev.util.net.UdpUtil.*;

public class UdpUtilSendAndReceiveDataViaSocketTest {
    private static final String HOST = "localhost";
    private static final int PORT = 50500;
    private DatagramSocket m_dataGramSocket;
    private ExecutorService m_threadPool;

    public static byte [] getBytes(int...ints)
    {
        ByteBuffer bb = ByteBuffer.allocate(ints.length * Integer.BYTES);

        for (int val : ints)
            bb.putInt(val);

        return bb.array();
    }

    private <T> void sendAndReceiveValue_ThenCompare(T value, Runnable runnable, Supplier<T> supplier) {
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
        catch (Throwable ignored) {}
    }

    @BeforeEach
    public void setUp()
    {
        try {
            m_threadPool = Executors.newSingleThreadExecutor();
            m_dataGramSocket = new DatagramSocket(PORT);
        } catch (SocketException ignored) {}
    }

    @Test
    public void sendByteViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = (byte)1;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendByte(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveByte(m_dataGramSocket));
    }

    @Test
    public void sendShortViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = (short)1;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendShort(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveShort(m_dataGramSocket));
    }

    @Test
    public void sendIntViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendInt(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveInt(m_dataGramSocket));
    }

    @Test
    public void sendLongViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1L;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendLong(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveLong(m_dataGramSocket));
    }

    @Test
    public void sendFloatViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1F;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendFloat(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveFloat(m_dataGramSocket));
    }

    @Test
    public void sendDoubleViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = 1D;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendDouble(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveDouble(m_dataGramSocket));
    }

    @Test
    public void sendCharViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = '1';

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendChar(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveChar(m_dataGramSocket));
    }

    @Test
    public void sendBooleanViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = true;

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendBoolean(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveBoolean(m_dataGramSocket));
    }

    @Test
    public void sendStringViaDataGramSocket_ThenReceive_CompareEquals()
    {
        var senderValue = "TEST";

        sendAndReceiveValue_ThenCompare(senderValue,
                () -> sendString(m_dataGramSocket, HOST, PORT, senderValue),
                () -> receiveString(m_dataGramSocket, senderValue.length()));
    }


    @Test
    public void sendStringViaDataGramSocket_ThenReceiveAsDataGramPacket_CompareEquals()
    {
        var senderValue = "TEST";

        var future = m_threadPool.submit(() -> senderCallback(
                () -> sendString(m_dataGramSocket, HOST, PORT, senderValue)));

        var receiverValue = new String (receiveDatagramPacket(m_dataGramSocket, senderValue.length()).getData());
        future.cancel(true);

        Assertions.assertEquals(senderValue, receiverValue);
    }

    @Test
    public void sendIntArrayViaDataGramSocket_ThenReceiveAsDataGramPacket_CompareEquals()
    {
        var senderArray = new int[] {0, 1, 2, 3, 4};
        var expectedByteArray = getBytes(senderArray);

        var future = m_threadPool.submit(() -> senderCallback(
                () -> sendIntArray(m_dataGramSocket, HOST, PORT, senderArray)));

        var receiverByteArray = receiveDatagramPacket(m_dataGramSocket,
                senderArray.length * Integer.BYTES).getData();

        future.cancel(true);

        Assertions.assertArrayEquals(expectedByteArray, receiverByteArray);
    }

    @AfterEach
    public void tearDown() throws IOException
    {
        m_dataGramSocket.close();
        m_threadPool.shutdownNow();
    }
}
