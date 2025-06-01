package com.karandev.util.net;

import com.karandev.util.net.exception.NetworkException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Immutable TCP class for TCP socket operations.
 * <p>Copyleft (c) 1993 by C and System Programmers Association (CSD) All Rights Free</p>
 *
 * @see TcpUtil
 * @author JavaApp2-Jan-2024 Group
 * @version 1.0.0
 */
public class TCPClient implements Closeable {
    private final Socket m_socket;

    /**
     * <p>Resolves the specified {@code host} to an {@link InetAddress}.</p>
     *
     * @param host the hostname to resolve
     * @return the resolved {@code InetAddress}
     * @throws NetworkException if the hostname cannot be resolved
     */
    private static InetAddress getInetAddress(String host)
    {
        try {
            return InetAddress.getByName(host);
        }
        catch (Throwable ex) {
            throw new NetworkException("InetAddress:", ex);
        }
    }

    /**
     * <p>Creates a new TCP client and connects to the specified {@code host} on {@code port}.</p>
     *
     * @param host the hostname of the server
     * @param port the port number of the server
     * @throws NetworkException if a connection cannot be established
     */
    public TCPClient(String host, int port)
    {
        this(getInetAddress(host), port);
    }

    /**
     * <p>Creates a new TCP client and connects to the specified {@code inetAddress} on {@code port}.</p>
     *
     * @param inetAddress the {@code InetAddress} of the server
     * @param port        the port number of the server
     * @throws NetworkException if a connection cannot be established
     */
    public TCPClient(InetAddress inetAddress, int port)
    {
        try {
            m_socket = new Socket(inetAddress, port);
        }
        catch (IOException ex) {
            throw new NetworkException("IO Problem", ex);
        }
        catch (Throwable ex) {
            throw new NetworkException("Internal Problem", ex);
        }
    }

    /**
     * <p>Checks whether the TCP connection is open.</p>
     *
     * @return true if the socket is open, false otherwise
     */
    public boolean isOpen()
    {
        return !m_socket.isClosed();
    }

    /**
     * <p>Returns the underlying {@link Socket} instance.</p>
     *
     * @return the underlying {@code Socket}
     */
    public Socket getSocket()
    {
        return m_socket;
    }

    /**
     * <p>Receives data with specified {@code length} from the server and stores it into the specified byte array.</p>
     * {@code offset} parameter can be used for offsetting the start index of the byte array.
     * @param data   the byte array to store the received data
     * @param offset the start offset in the array at which the data is written
     * @param length number of bytes to read
     * @return the number of bytes read, or -1 if the end of the stream is reached before any data is read, or
     * 0 when no bytes are read
     * @throws NetworkException – if any problem occurs while receiving from the socket
     * @see TcpUtil#receive(Socket, byte[], int, int)
     */
    public int receive(byte [] data, int offset, int length)
    {
        return TcpUtil.receive(m_socket, data, offset, length);
    }

    /**
     * <p>Receives data with specified {@code length} from the server and stores it into the specified byte array.
     * This method uses default index offset as 0 and default length to receive is as big as incoming data.</p>
     * @param data the byte array to store the received data
     * @return the number of bytes read
     * @throws NetworkException – if any problem occurs while receiving from the socket
     * @see TcpUtil#receive(Socket, byte[]) 
     */
    public int receive(byte [] data)
    {
        return TcpUtil.receive(m_socket, data);
    }

    /**
     * <p>Sends {@code data} with specified {@code length} to the server.
     * {@code offset} parameter can be used for offsetting the start index of the byte array.</p>
     * @param data   the byte array containing the data to send
     * @param offset the start offset in the array from which the data is sent
     * @param length the number of bytes to send
     * @return the number of bytes sent
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#send(Socket, byte[], int, int) 
     */
    public int send(byte [] data, int offset, int length)
    {
        return TcpUtil.send(m_socket, data, offset, length);
    }

    /**
     * <p>Sends {@code data} with specified {@code length} to the server.
     * This method uses default index offset as 0 and default length to send is as big as incoming data.</p>
     * @param data the byte array containing the data to send
     * @return the number of bytes sent
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#send(Socket, byte[]) 
     */
    public int send(byte [] data)
    {
        return TcpUtil.send(m_socket, data);
    }

    /**
     * <p>Receives a single byte from the server.</p>
     *
     * @return the received byte
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveByte(Socket) 
     */
    public byte receiveByte()
    {
        return TcpUtil.receiveByte(m_socket);
    }

    /**
     * <p>Receives a short value from the server.</p>
     *
     * @return the received short value
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveShort(Socket) 
     */
    public short receiveShort()
    {
        return TcpUtil.receiveShort(m_socket);
    }

    /**
     * <p>Receives an integer value from the server.</p>
     * @return the received integer value
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveInt(Socket)
     */
    public int receiveInt()
    {
        return TcpUtil.receiveInt(m_socket);
    }

    /**
     * <p>Receives a long value from the server.</p>
     * @return the received long value
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveLong(Socket)
     */
    public long receiveLong()
    {
        return TcpUtil.receiveLong(m_socket);
    }

    /**
     * <p>Receives a float value from the server.</p>
     * @return the received float value
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveFloat(Socket)
     */
    public float receiveFloat()
    {
        return TcpUtil.receiveFloat(m_socket);
    }

    /**
     * <p>Receives a double value from the server.</p>
     * @return the received double value
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveDouble(Socket)
     */
    public double receiveDouble()
    {
        return TcpUtil.receiveDouble(m_socket);
    }

    /**
     * <p>Receives a character from the server.</p>
     * @return the received character
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveChar(Socket)
     */
    public char receiveChar()
    {
        return TcpUtil.receiveChar(m_socket);
    }

    /**
     * <p>Receives a length-prefixed string from the server using default charset {@link StandardCharsets#UTF_8}</p>
     * <p>Sender must use matching {@link #sendStringViaLength(String)} method for successful transfer. </p>
     * @return the received string
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveStringViaLength(Socket) 
     */
    public String receiveStringViaLength()
    {
        return TcpUtil.receiveStringViaLength(m_socket);
    }

    /**
     * <p>Receives a length-prefixed string from the server using specified {@code charset}.</p>
     * <p>Sender must use matching {@link #sendStringViaLength(String, Charset)} method for successful transfer. </p>
     * @param charset the charset to use for decoding the string
     * @return the received string
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveStringViaLength(Socket, Charset) 
     */
    public String receiveStringViaLength(Charset charset)
    {
        return TcpUtil.receiveStringViaLength(m_socket, charset);
    }

    /**
     * <p>Receives a string of a specified {@code length} using default charset {@link StandardCharsets#UTF_8} from the server.</p>
     *
     * @param length the number of bytes to read as a string
     * @return the received string
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveString(Socket, int) 
     */
    public String receiveString(int length)
    {
        return TcpUtil.receiveString(m_socket, length);
    }

    /**
     * <p>Receives a string of a specified {@code length}, using a specified {@code charset} from the server.</p>
     *
     * @param length  the number of bytes to read as a string
     * @param charset the charset to use for decoding the string
     * @return the received string
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see com.karandev.util.net.TcpUtil#receiveString(Socket, int, Charset) 
     */
    public String receiveString(int length, Charset charset)
    {
        return TcpUtil.receiveString(m_socket, length, charset);
    }

    /**
     * <p>Receives a `\n\r` terminated string from the server.</p>
     * @return the received line of text
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveLine(Socket) 
     */
    public String receiveLine()
    {
        return TcpUtil.receiveLine(m_socket);
    }

    /**
     * <p>Receives a `\n\r` terminated string from the server using specified {@code charset}</p>
     * @param charset the charset to use for decoding the line
     * @return the received line of text
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveLine(Socket, Charset) 
     */
    public String receiveLine(Charset charset)
    {
        return TcpUtil.receiveLine(m_socket, charset);
    }

    /**
     * <p>Receives a `\n\r` terminated string from the server using specified {@code blockSize}</p>
     * @param blockSize the block size to use for reading the line
     * @return the received line of text
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveLine(Socket, int)  
     */
    public String receiveLine(int blockSize)
    {
        return TcpUtil.receiveLine(m_socket, blockSize);
    }

    /**
     * <p>Receives a `\n\r` terminated string from the server using specified {@code charset}
     * and {@code blockSize}</p>
     * @param charset   the charset to use for decoding the line
     * @param blockSize the block size to use for reading the line
     * @return the received line of text
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveLine(Socket, Charset, int) 
     */
    public String receiveLine(Charset charset, int blockSize)
    {
        return TcpUtil.receiveLine(m_socket, charset, blockSize);
    }

    /**
     * <p>Receives a {@code file} from the server and saves it to the absolute path obtained from {@code file} object.</p>
     *
     * @param file reference to a file object for obtaining path
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveFile(Socket, File)
     */
    public void receiveFile(File file)
    {
        TcpUtil.receiveFile(m_socket, file);
    }

    /**
     * <p>Receives a file from the server and saves it to the specified file system {@code path}.</p>
     *
     * @param path the path to save the received data to
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#receiveFile(Socket, String)
     */
    public void receiveFile(String path)
    {
        TcpUtil.receiveFile(m_socket, path);
    }

    /**
     * <p>Sends a single byte to the server.</p>
     * @param val the byte to send
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#sendByte(Socket, byte)              
     */
    public void sendByte(byte val)
    {
        TcpUtil.sendByte(m_socket, val);
    }

    /**
     * <p>Sends a short value to the server.</p>
     * @param val the short value to send
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#sendShort(Socket, short) 
     */
    public void sendShort(short val)
    {
        TcpUtil.sendShort(m_socket, val);
    }

    /**
     * <p>Sends an integer value to the server.</p>
     * @param val the integer value to send
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#sendInt(Socket, int) 
     */
    public void sendInt(int val)
    {
        TcpUtil.sendInt(m_socket, val);
    }

    /**
     * <p>Sends a long value to the server.</p>
     * @param val the long value to send
     * @throws NetworkException -  if any problem occurs while sending through the socket         
     * @see TcpUtil#sendLong(Socket, long) 
     */
    public void sendLong(long val)
    {
        TcpUtil.sendLong(m_socket, val);
    }

    /**
     * <p>Sends a float value to the server.</p>
     * @param val the float value to send
     * @throws NetworkException -  if any problem occurs while sending through the socket   
     * @see TcpUtil#sendFloat(Socket, float) 
     */
    public void sendFloat(float val)
    {
        TcpUtil.sendFloat(m_socket, val);
    }

    /**
     * <p>Sends a double value to the server.</p>
     * @param val the double value to send
     * @throws NetworkException -  if any problem occurs while sending through the socket   
     * @see TcpUtil#sendDouble(Socket, double) 
     */
    public void sendDouble(double val)
    {
        TcpUtil.sendDouble(m_socket, val);
    }

    /**
     * <p>Sends a character to the server.</p>
     * @param val the character to send
     * @throws NetworkException -  if any problem occurs while sending through the socket
     * @see TcpUtil#sendChar(Socket, char) 
     */
    public void sendChar(char val)
    {
        TcpUtil.sendChar(m_socket, val);
    }

    /**
     * <p>Sends a string {@code str} to the server using a length-prefixed protocol with default charset {@link StandardCharsets#UTF_8}</p>
     * @param str     the string to send
     * @throws NetworkException if any problem occurs while sending through the socket
     * @see TcpUtil#sendString(Socket, String)
     */
    public void sendStringViaLength(String str)
    {
        TcpUtil.sendStringViaLength(m_socket, str);
    }

    /**
     * <p>Sends a string {@code str} to the server using a length-prefixed protocol with a specified {@code charset}.</p>
     * @param str     the string to send
     * @param charset the charset to use for encoding the string
     * @throws NetworkException if any problem occurs while sending through the socket
     * @see TcpUtil#sendStringViaLength(Socket, String, Charset)
     */
    public void sendStringViaLength(String str, Charset charset)
    {
        TcpUtil.sendStringViaLength(m_socket, str, charset);
    }

    /**
     * <p>Sends the specified {@code str} to the server using the default charset {@link StandardCharsets#UTF_8}.</p>
     *
     * @param str the string to send
     * @throws NetworkException if any problem occurs while sending through the socket
     * @see TcpUtil#sendString(Socket, String)  
     */
    public void sendString(String str)
    {
        TcpUtil.sendString(m_socket, str);
    }

    /**
     * <p>Sends the specified {@code str} to the server using the specified {@code charset}.</p>
     *
     * @param str     the string to send
     * @param charset the charset to use for encoding the string
     * @throws NetworkException if any problem occurs while sending through the socket
     * @see TcpUtil#sendString(Socket, String, Charset) 
     */
    public void sendString(String str, Charset charset)
    {
        TcpUtil.sendString(m_socket, str, charset);
    }

    /**
     * <p>Sends a `\n\r` terminated {@code str} to the server using default charset {@link StandardCharsets#UTF_8}.</p>
     *
     * @param str the line of text to send
     * @throws NetworkException if any problem occurs while sending through the socket
     * @see TcpUtil#sendLine(Socket, String) 
     */
    public void sendLine(String str)
    {
        TcpUtil.sendLine(m_socket, str);
    }

    /**
     * <p>Sends a `\n\r` terminated {@code str} to the server using specified {@code charset}.</p>
     *
     * @param str     the line of text to send
     * @param charset the charset to use for encoding the text
     * @throws NetworkException if any problem occurs while sending through the socket    
     * @see TcpUtil#sendLine(Socket, String, Charset) 
     */
    public void sendLine(String str, Charset charset)
    {
        TcpUtil.sendLine(m_socket, str, charset);
    }

    /**
     * <p>Sends a file specified as {@code file} object to the server via specified {@code blockSize}.</p>
     *
     * @param file      reference to file object
     * @param blockSize the block size to use for sending the file
     * @throws NetworkException if any problem occurs while sending through the socket 
     * @see TcpUtil#sendFile(Socket, File, int) 
     */
    public void sendFile(File file, int blockSize)
    {
        TcpUtil.sendFile(m_socket, file, blockSize);
    }

    /**
     * <p>Sends a file at system {@code path} to the server via specified {@code blockSize}.</p>
     *
     * @param path      the path of the file to send
     * @param blockSize the block size to use for sending the file
     * @throws NetworkException if any problem occurs while sending through the socket 
     * @see TcpUtil#sendFile(Socket, String, int) 
     */
    public void sendFile(String path, int blockSize)
    {
        TcpUtil.sendFile(m_socket, path, blockSize);
    }

    @Override
    public void close()
    {
        try {
            m_socket.close();
        }
        catch (IOException ex) {
            throw new NetworkException("close", ex);
        }
    }
}
