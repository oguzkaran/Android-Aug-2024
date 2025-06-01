package com.karandev.util.net;

import com.karandev.util.net.exception.NetworkException;

import java.io.File;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Immutable TCP class for TCP socket operations
 * <p>Copyleft (c) 1993 by C and System Programmers Association (CSD) All Rights Free</p>
 *
 * @author JavaApp2-Jan-2024 Group
 */

public class TCP {
    private final Socket m_socket;

    /**
     * <p>Constructor to initialize a TCP instance with a provided socket.</p>
     *
     * @param socket the {@link Socket} instance to be used for TCP communication
     */
    public TCP(Socket socket)
    {
        m_socket = socket;
    }

    /**
     * <p>Constructor to initialize a TCP instance with a provided socket and set a socket timeout.</p>
     *
     * @param socket the {@link Socket} instance to be used for TCP communication
     * @param timeout the timeout duration in milliseconds for socket operations
     * @throws NetworkException if an error occurs when setting the timeout
     */
    public TCP(Socket socket, int timeout)
    {
        try {
            m_socket = socket;
            socket.setSoTimeout(timeout);
        }
        catch (Throwable ex) {
            throw new NetworkException("TCP(socket, timeout)", ex);
        }
    }

    /**
     * <p> Checks if the socket is open and ready for communication.</p>
     *
     * @return {@code true} if the socket is open, otherwise {@code false}
     */
    public boolean isOpen()
    {
        return !m_socket.isClosed();
    }

    /**
     * <p>Retrieves the underlying socket used by this TCP instance.</p>
     *
     * @return the {@link Socket} used for communication
     */
    public Socket getSocket()
    {
        return m_socket;
    }

    /**
     * <p>Receives data from the socket.</p>
     *
     * @param data the buffer to store received data
     * @param offset the offset into the buffer to start storing data
     * @param length the maximum number of bytes to read
     * @return the number of bytes actually read
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receive(Socket, byte[], int, int)
     */
    public int receive(byte [] data, int offset, int length)
    {
        return TcpUtil.receive(m_socket, data, offset, length);
    }

    /**
     * <p>Receives data from the socket.</p>
     *
     * @param data the buffer to store received data
     * @return the number of bytes actually read
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receive(Socket, byte[]) 
     */
    public int receive(byte [] data)
    {
        return TcpUtil.receive(m_socket, data);
    }

    /**
     * <p>Sends data to the socket.</p>
     *
     * @param data the data to send
     * @param offset the offset into the buffer to start sending from
     * @param length the number of bytes to send
     * @return the number of bytes actually sent
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#send(Socket, byte[], int, int) 
     */
    public int send(byte [] data, int offset, int length)
    {
        return TcpUtil.send(m_socket, data, offset, length);
    }

    /**
     * <p>Sends data to the socket.</p>
     *
     * @param data the data to send
     * @return the number of bytes actually sent
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#send(Socket, byte[]) 
     */
    public int send(byte [] data)
    {
        return TcpUtil.send(m_socket, data);
    }

    /**
     * <p>Receives a single byte from the socket.</p>
     *
     * @return the byte received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveByte(Socket) 
     */
    public byte receiveByte()
    {
        return TcpUtil.receiveByte(m_socket);
    }

    /**
     * Receives a short value from the socket.
     *
     * @return the short value received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#
     */
    public short receiveShort()
    {
        return TcpUtil.receiveShort(m_socket);
    }

    /**
     * Receives an integer from the socket.
     *
     * @return the integer received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#
     */
    public int receiveInt()
    {
        return TcpUtil.receiveInt(m_socket);
    }

    /**
     * Receives a long value from the socket.
     *
     * @return the long value received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveLong(Socket)
     */
    public long receiveLong()
    {
        return TcpUtil.receiveLong(m_socket);
    }

    /**
     * Receives a float value from the socket.
     *
     * @return the float value received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveFloat(Socket)
     */
    public float receiveFloat()
    {
        return TcpUtil.receiveFloat(m_socket);
    }

    /**
     * Receives a double value from the socket.
     *
     * @return the double value received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveDouble(Socket)
     */
    public double receiveDouble()
    {
        return TcpUtil.receiveDouble(m_socket);
    }

    /**
     * Receives a char value from the socket.
     *
     * @return the char value received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveChar(Socket)
     */
    public char receiveChar()
    {
        return TcpUtil.receiveChar(m_socket);
    }

    /**
     * <p>Receives a length-prefixed string from the socket using default charset {@link StandardCharsets#UTF_8}</p>
     * <p>Sender must use matching {@link #sendStringViaLength(String)} method for successful transfer. </p>
     * @return the string received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveStringViaLength(Socket)
     */
    public String receiveStringViaLength()
    {
        return TcpUtil.receiveStringViaLength(m_socket);
    }

    /**
     * <p>Receives a length-prefixed string from the socket using specified {@code charset}.</p>
     * <p>Sender must use matching {@link #sendStringViaLength(String, Charset)} method for successful transfer. </p>
     * @param charset the {@link Charset} to use for decoding the string
     * @return the string received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveString(Socket, int, Charset)
     */
    public String receiveStringViaLength(Charset charset)
    {
        return TcpUtil.receiveStringViaLength(m_socket, charset);
    }

    /**
     * <p>Receives a string of a specified {@code length} using default charset {@link StandardCharsets#UTF_8}.</p>
     *
     * @param length the number of characters to read
     * @return the string received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveString(Socket, int)
     */
    public String receiveString(int length)
    {
        return TcpUtil.receiveString(m_socket, length);
    }

    /**
     * <p>Receives a string of a specified {@code length}, using a specified {@code charset}.</p>
     *
     * @param length the number of characters to read
     * @param charset the {@link Charset} to use for decoding the string
     * @return the string received
     * @throws NetworkException if an error occurs while receiving data
     * @see TcpUtil#receiveString(Socket, int, Charset)
     */
    public String receiveString(int length, Charset charset)
    {
        return TcpUtil.receiveString(m_socket, length, charset);
    }

    /**
     * <p>Receives a line of text.</p>
     * <p>Uses default charset {@link StandardCharsets#UTF_8}</p>
     * @return the line received
     * @throws NetworkException if an error occurs while receiving data
     * @throws NullPointerException if an unexpected null value is encountered
     * @throws NegativeArraySizeException if an invalid size for data buffer is specified
     * @see TcpUtil#receiveLine(Socket)
     */
    public String receiveLine()
    {
        return TcpUtil.receiveLine(m_socket);
    }

    /**
     * <p>Receives a line of text using specified {@code charset}</p>
     * @param charset the {@link Charset} to use for decoding the string
     * @return the line received
     * @throws NetworkException if an error occurs while receiving data
     * @throws NullPointerException if an unexpected null value is encountered
     * @throws NegativeArraySizeException if an invalid size for data buffer is specified
     * @see TcpUtil#receiveLine(Socket, Charset)
     */
    public String receiveLine(Charset charset)
    {
        return TcpUtil.receiveLine(m_socket, charset);
    }

    /**
     * <p>Receives a line of text with a specified {@code blockSize}.</p>
     * @param blockSize the number of bytes to read in each block
     * @return the line received
     * @throws NetworkException if an error occurs while receiving data
     * @throws NullPointerException if an unexpected null value is encountered
     * @throws NegativeArraySizeException if an invalid size for data buffer is specified
     * @see TcpUtil#receiveLine(Socket, int)
     */
    public String receiveLine(int blockSize)
    {
        return TcpUtil.receiveLine(m_socket, blockSize);
    }

    /**
     * <p>Receives a line of text using a specified {@code charset} and {@code blockSize}</p>

     * @param charset the {@link Charset} to use for decoding the string
     * @param blockSize the number of bytes to read in each block
     * @return the line received
     * @throws NetworkException if an error occurs while receiving data
     * @throws NullPointerException if an unexpected null value is encountered
     * @throws NegativeArraySizeException if an invalid size for data buffer is specified
     * @see TcpUtil#receiveLine(Socket, Charset, int)
     */
    public String receiveLine(Charset charset, int blockSize)
    {
        return TcpUtil.receiveLine(m_socket, charset, blockSize);
    }

    /**
     * <p>Receives a file from the socket and saves it to a specified {@code file} object's path.</p>
     *
     * @param file the {@link File} to save the received data to
     * @throws NetworkException if an error occurs while receiving the file
     * @see TcpUtil#receiveFile(Socket, File)
     */
    public void receiveFile(File file)
    {
        TcpUtil.receiveFile(m_socket, file);
    }

    /**
     * <p>Receives a file from the socket and saves it to a specified file {@code path}.</p>
     *
     * @param path the path where the file should be saved
     * @throws NetworkException if an error occurs while receiving the file
     * @see TcpUtil#receiveFile(Socket, String)
     */
    public void receiveFile(String path)
    {
        TcpUtil.receiveFile(m_socket, path);
    }

    /**
     * <p>Sends a single byte to the socket.</p>
     *
     * @param val the byte value to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendByte(Socket, byte)
     */
    public void sendByte(byte val)
    {
        TcpUtil.sendByte(m_socket, val);
    }

    /**
     * <p>Sends a short value to the socket.</p>
     *
     * @param val the short value to send
     * @throws NetworkException if an error occurs while sending data
     *  @see TcpUtil#sendShort(Socket, short)
     */
    public void sendShort(short val)
    {
        TcpUtil.sendShort(m_socket, val);
    }

    /**
     * <p>Sends an integer to the socket.</p>
     *
     * @param val the integer value to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendInt(Socket, int)
     */
    public void sendInt(int val)
    {
        TcpUtil.sendInt(m_socket, val);
    }

    /**
     * <p>Sends a long value to the socket.</p>
     *
     * @param val the long value to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendLong(Socket, long)
     */
    public void sendLong(long val)
    {
        TcpUtil.sendLong(m_socket, val);
    }

    /**
     * <p>Sends a float value to the socket.</p>
     *
     * @param val the float value to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendFloat(Socket, float)
     */
    public void sendFloat(float val)
    {
        TcpUtil.sendFloat(m_socket, val);
    }

    /**
     * <p>Sends a double value to the socket.</p>
     *
     * @param val the double value to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendDouble(Socket, double)
     */
    public void sendDouble(double val)
    {
        TcpUtil.sendDouble(m_socket, val);
    }

    /**
     * <p>Sends a char value to the socket.</p>
     *
     * @param val the char value to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendChar(Socket, char)
     */
    public void sendChar(char val)
    {
        TcpUtil.sendChar(m_socket, val);
    }

    /**
     * <p>Sends a string {@code str} using a length-prefixed protocol with default charset {@link StandardCharsets#UTF_8}</p>
     *
     * @param str the string to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendStringViaLength(Socket, String)
     */
    public void sendStringViaLength(String str)
    {
        TcpUtil.sendStringViaLength(m_socket, str);
    }

    /**
     * <p>Sends a string {@code str} using a length-prefixed protocol with a specified {@code charset}.</p>
     *
     * @param str the string to send
     * @param charset the {@link Charset} to use for encoding the string
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendStringViaLength(Socket, String, Charset)
     */
    public void sendStringViaLength(String str, Charset charset)
    {
        TcpUtil.sendStringViaLength(m_socket, str, charset);
    }

    /**
     * <p>Sends a string {@code str} to the socket.</p>
     *
     * @param str the string to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendString(Socket, String)
     */
    public void sendString(String str)
    {
        TcpUtil.sendString(m_socket, str);
    }

    /**
     * <p>Sends a string to the socket, using a specified {@code charset}</p>
     *
     * @param str the string to send
     * @param charset the {@link Charset} to use for encoding the string
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendString(Socket, String, Charset)
     */
    public void sendString(String str, Charset charset)
    {
        TcpUtil.sendString(m_socket, str, charset);
    }

    /**
     * <p>Sends a `\n\r` terminated {@code str} to the socket.</p>
     *
     * @param str the line of text to send
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendLine(Socket, String)
     */
    public void sendLine(String str)
    {
        TcpUtil.sendLine(m_socket, str);
    }

    /**
     * <p>Sends a `\n\r` terminated {@code str} to the socket using specified {@code charset}.</p>
     *
     * @param str the line of text to send
     * @param charset the {@link Charset} to use for encoding the text
     * @throws NetworkException if an error occurs while sending data
     * @see TcpUtil#sendLine(Socket, String, Charset)
     */
    public void sendLine(String str, Charset charset)
    {
        TcpUtil.sendLine(m_socket, str, charset);
    }

    /**
     * <p>Sends a {@code file} to the socket, with a specified {@code blockSize}.</p>
     *
     * @param file the {@link File} to send
     * @param blockSize the size of blocks to send the file in
     * @throws NetworkException if an error occurs while sending the file
     * @see TcpUtil#sendFile(Socket, File, int)
     */
    public void sendFile(File file, int blockSize)
    {
        TcpUtil.sendFile(m_socket, file, blockSize);
    }

    /**
     * <p>Sends a file at {@code path} to the socket, with a specified {@code blockSize}.</p>
     *
     * @param path the path of the file to send
     * @param blockSize the size of blocks to send the file in
     * @throws NetworkException if an error occurs while sending the file
     * @see TcpUtil#sendFile(Socket, String, int)
     */
    public void sendFile(String path, int blockSize)
    {
        TcpUtil.sendFile(m_socket, path, blockSize);
    }
}
