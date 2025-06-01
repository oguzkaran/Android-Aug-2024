package com.karandev.util.net;

import com.karandev.util.net.exception.NetworkException;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Utility class with usage of datagram packets and datagram sockets for UDP operations,
 * includes sending and receiving primitive type values and texts.
 * <p>Copyleft (c) 1993 by C and System Programmers Association (CSD) All Rights Free</p>
 *
 * @see DatagramPacket
 * @see DatagramSocket
 * @author JavaApp2-Jan-2024 Group
 * @version 1.0.0
 */

public final class UdpUtil {
    /**
     * Creates a new {@link DatagramPacket} object for sending data through
     * a given specified ip address and port number.
     * @param data the byte array which will be transferred in a single fetch
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @return a new {@link DatagramPacket} for sending data over the datagram socket
     * @throws UnknownHostException if the destination ip address {@code host} is invalid
     * @throws IllegalArgumentException if the {@code data} is null
     */
    private static DatagramPacket createDatagramPacket(byte [] data, String host, int port) throws UnknownHostException
    {
        return new DatagramPacket(data, 0, data.length, InetAddress.getByName(host), port);
    }
    /**
     * Creates a new {@link DatagramPacket} object
     * for receiving data through from a specified {@link DatagramSocket}.
     * The created datagram packet uses a byte array for fetching the data
     * which size is given {@code length}
     * @param length the size of the received data in a single fetch
     * @return a new DataGramPacket Object for receive data over the datagram socket
     * @throws IllegalArgumentException if given length value is negative
     */
    private static DatagramPacket createDatagramPacket(int length)
    {
        return new DatagramPacket(new byte[length], length);
    }

    private UdpUtil()
    {
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single byte value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the byte value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendByte(String host, int port, byte val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendByte(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendByte", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single byte value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the byte value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendByte(DatagramSocket datagramSocket, String host, int port, byte val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendByte", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single short value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the short value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendShort(String host, int port, short val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendShort(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendShort", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single short value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the short value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendShort(DatagramSocket datagramSocket, String host, int port, short val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendShort", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single int value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the int value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendInt(String host, int port, int val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendInt(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendInt", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single int value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the int value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendInt(DatagramSocket datagramSocket, String host, int port, int val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendInt", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single long value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the long value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendLong(String host, int port, long val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendLong(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendLong", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single long value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the long value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendLong(DatagramSocket datagramSocket, String host, int port, long val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendLong", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single float value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the float value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendFloat(String host, int port, float val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendFloat(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendFloat", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single float value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the float value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendFloat(DatagramSocket datagramSocket, String host, int port, float val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendFloat", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single double value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the double value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendDouble(String host, int port, double val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendDouble(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendDouble", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single double value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the double value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendDouble(DatagramSocket datagramSocket, String host, int port, double val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendDouble", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single char value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param ch the char value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendChar(String host, int port, char ch)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendChar(datagramSocket, host, port, ch);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendChar", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single char value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param ch the char value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendChar(DatagramSocket datagramSocket, String host, int port, char ch)
    {
        try {
            byte [] data = BitConverter.getBytes(ch);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendChar", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single boolean value, regardless of the bit sequence,
     * through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the boolean value which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendBoolean(String host, int port, boolean val)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendBoolean(datagramSocket, host, port, val);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendBoolean", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single boolean value, regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param val the boolean value which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendBoolean(DatagramSocket datagramSocket, String host, int port, boolean val)
    {
        try {
            byte [] data = BitConverter.getBytes(val);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendBoolean", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single String object in default charset {@linkplain StandardCharsets#UTF_8},
     * regardless of the bit sequence, through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param str the String object which will be sent
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendString(String host, int port, String str)
    {
        sendString(host, port, str, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single String object in default charset {@linkplain StandardCharsets#UTF_8},
     * regardless of the bit sequence, through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param str the String object which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendString(DatagramSocket datagramSocket, String host, int port, String str)
    {
        sendString(datagramSocket, host, port, str, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single String object in given {@code charset},
     * regardless of the bit sequence, through of a new {@link DatagramSocket} object
     *
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param str the String object which will be sent
     * @param charset the specified charset of the given String object {@code str}
     * @throws NetworkException if any problem occurs while constructing the objects or sending data through the socket
     */
    public static void sendString(String host, int port, String str, Charset charset)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            sendString(datagramSocket, host, port, str, charset);
        }
        catch (SocketException ex) {
            throw new NetworkException("UdpUtil.sendString", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends a single String object in given {@code charset},
     * regardless of the bit sequence, through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param str the String object which will be sent
     * @param charset the specified charset of the given String object {@code str}
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    public static void sendString(DatagramSocket datagramSocket, String host, int port, String str, Charset charset)
    {
        try {
            byte [] data = BitConverter.getBytes(str, charset);

            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendString", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object which is bind to the given ip address
     * {@code host} and {@code port} number
     * then sends multiple int values, all regardless of the bit sequence,
     * through of given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for transfer data
     * @param host the ip address of the transfer destination
     * @param port the port number of transfer destination
     * @param ints the int values which will be sent
     * @throws NetworkException if any problem occurs while sending data through the socket or creating the datagram packet
     */
    ////////////////////
    public static void sendIntArray(DatagramSocket datagramSocket, String host, int port, int...ints)
    {
        try {
            var data = BitConverter.getBytes(ints);
            datagramSocket.send(createDatagramPacket(data, host, port));
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.sendIntArray", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single byte value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received byte value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Byte}
     */
    public static byte receiveByte(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Byte.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Byte.BYTES)
                throw new NetworkException("Invalid data length");

            return datagramPacket.getData()[0];
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveByte", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single byte value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received byte value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Byte}
     */
    public static byte receiveByte(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveByte(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveByte", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single short value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received short value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Short}
     */
    public static short receiveShort(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Short.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Short.BYTES)
                throw new NetworkException("Invalid data length");

            return BitConverter.toShort(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveShort", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single short value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received short value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Short}
     */
    public static short receiveShort(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveShort(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveShort", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single int value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received int value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Integer}
     */
    public static int receiveInt(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Integer.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Integer.BYTES)
                throw new NetworkException("Invalid data length");

            return BitConverter.toInt(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveInt", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single int value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received int value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Integer}
     */
    public static int receiveInt(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveInt(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveInt", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single long value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received long value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Long}
     */
    public static long receiveLong(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Long.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Long.BYTES)
                throw new NetworkException("Invalid data length");

            return BitConverter.toLong(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveInt", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single long value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received long value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Long}
     */
    public static long receiveLong(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveLong(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveInt", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single float value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received float value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Float}
     */
    public static float receiveFloat(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Float.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Float.BYTES)
                throw new NetworkException("Invalid data length");

            return BitConverter.toFloat(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveFloat", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single float value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received float value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Float}
     */
    public static float receiveFloat(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveFloat(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveFloat", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single double value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received double value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Double}
     */
    public static double receiveDouble(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Double.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Double.BYTES)
                throw new NetworkException("Invalid data length");

            return BitConverter.toDouble(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveDouble", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single double value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received double value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Double}
     */
    public static double receiveDouble(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveDouble(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveDouble", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single char value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received char value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Character}
     */
    public static char receiveChar(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(Character.BYTES);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != Character.BYTES)
                throw new NetworkException("Invalid data length");

            return BitConverter.toChar(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveDouble", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single char value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received char value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Character}
     */
    public static char receiveChar(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveChar(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveDouble", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single boolean value,
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @return the received boolean value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Boolean}
     */
    public static boolean receiveBoolean(DatagramSocket datagramSocket)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(1);

            datagramSocket.receive(datagramPacket);
            int length = datagramPacket.getLength();
            if (length != 1)
                throw new NetworkException("Invalid data length");

            return BitConverter.toBoolean(datagramPacket.getData());
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveBoolean", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single boolean value,
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @return the received boolean value
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is not equals to length of the type {@link Boolean}
     */
    public static boolean receiveBoolean(int port)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveBoolean(datagramSocket);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveBoolean", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single String object
     * in default charset {@linkplain StandardCharsets#UTF_8},
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @param maxLength the max length of the string object which will be received in a datagram packet on a single fetch
     * @return the received String object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static String receiveString(DatagramSocket datagramSocket, int maxLength)
    {
        return receiveString(datagramSocket, maxLength, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single String object
     * in default charset {@linkplain StandardCharsets#UTF_8},
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @param maxLength the max length of the string object which will be received in a datagram packet on a single fetch
     * @return the received String object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static String receiveString(int port, int maxLength)
    {
        return receiveString(port, maxLength, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single String object
     * in given {@code charset},
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @param maxLength the max length of the string object which will be received in a datagram packet on a single fetch
     * @param charset the specified charset of the String value to be received
     * @return the received String object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static String receiveString(DatagramSocket datagramSocket, int maxLength, Charset charset)
    {
        try {
            DatagramPacket datagramPacket = receiveDatagramPacket(datagramSocket, maxLength, charset);

            return BitConverter.toString(datagramPacket.getData(), 0, datagramPacket.getLength(), charset);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveString", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to receive a single String object
     * in given {@code charset},
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @param maxLength the max length of the string object which will be received in a datagram packet on a single fetch
     * @param charset the specified charset of the String value to be received
     * @return the received String object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static String receiveString(int port, int maxLength, Charset charset)
    {
        try {
            DatagramPacket datagramPacket = receiveDatagramPacket(port, maxLength, charset);

            return BitConverter.toString(datagramPacket.getData(), 0, datagramPacket.getLength(), charset);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveString", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to fill it
     * in default charset {@linkplain StandardCharsets#UTF_8},
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @param maxLength the max length of the received data that fills the datagram packet on a single fetch
     * @return the received DatagramPacket object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static DatagramPacket receiveDatagramPacket(DatagramSocket datagramSocket, int maxLength)
    {
        return receiveDatagramPacket(datagramSocket, maxLength, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to fill it,
     * in default charset {@linkplain StandardCharsets#UTF_8},
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @param maxLength the max length of the received data that fills the datagram packet on a single fetch
     * @return the received DatagramPacket object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static DatagramPacket receiveDatagramPacket(int port, int maxLength)
    {
        return receiveDatagramPacket(port, maxLength, StandardCharsets.UTF_8);
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to fill it,
     * in given {@code charset},
     * regardless of the bit sequence, through from the given {@code datagramSocket} object
     *
     * @param datagramSocket the socket which will be used for receiving data
     * @param maxLength the max size of data which will be received in a datagram packet on a single fetch
     * @param charset the specified charset of the Datagram Packet object to be received
     * @return the received DatagramPacket object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static DatagramPacket receiveDatagramPacket(DatagramSocket datagramSocket, int maxLength, Charset charset)
    {
        try {
            DatagramPacket datagramPacket = createDatagramPacket(maxLength);

            datagramSocket.receive(datagramPacket);

            return datagramPacket;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveStringPacket", ex);
        }
    }

    /**
     * Creates a new {@link DatagramPacket} object that uses a byte array
     * for fetching the data, then waits for to fill it,
     * in given {@code charset},
     * regardless of the bit sequence,
     * through from a new {@link DatagramSocket} object that bind to {@code localhost}
     *
     * @param port the port number of {@link DatagramSocket} object that will be used for communication
     * @param maxLength the max length of the string object which will be received in a datagram packet on a single fetch
     * @param charset the specified charset of the String value to be received
     * @return the received DatagramPacket object
     * @throws NetworkException if any problem occurs while creating the datagram packet,
     * receiving data through from the socket or received packet length is greater than {@code maxLength}
     */
    public static DatagramPacket receiveDatagramPacket(int port, int maxLength, Charset charset)
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            return receiveDatagramPacket(datagramSocket, maxLength, charset);
        }
        catch (NetworkException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new NetworkException("UdpUtil.receiveStringPacket", ex);
        }
    }
}
