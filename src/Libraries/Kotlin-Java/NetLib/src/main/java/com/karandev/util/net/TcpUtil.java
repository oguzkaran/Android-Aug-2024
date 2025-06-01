package com.karandev.util.net;

import com.karandev.util.net.exception.NetworkException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Utility class for TCP socket operations, including sending and receiving primitive type values, texts and files.
 * <p>Copyleft (c) 1993 by C and System Programmers Association (CSD) All Rights Free</p>
 *
 * @author JavaApp2-Jan-2024 Group
 * @version 1.0.0
 */
public final class TcpUtil {
	private static final int DEFAULT_LINE_BLOCK_SIZE = 2048;

	/**
	 * <p>Receives a specified number of bytes from the input stream and stores them into the given byte array.</p>
	 *
	 * <p>This method is called by {@link #receive(Socket,byte[],int,int) receive(Socket,byte[],int,int)} and
	 * {@link #receive(Socket, byte[]) receive(Socket,byte[])} for handling byte-by-byte transfer from a socket.</p>
	 *
	 * <p>It is possible to handle data transfer when incoming bytes are not available in a single read operation.</p>
	 *
	 * @param dis the DataInputStream to read from
	 * @param data the byte array to store the read data from the DataInputStream
	 * @param offset the starting index offset of the byte array
	 * @param length the number of the bytes to be read
	 * @return the number of bytes read, or -1 if the end of the stream is reached before any data is read, or
	 * 0 when no bytes are read
	 * @throws IOException if an I/O error occurs while reading from the input stream
	 */
	private static int receive(DataInputStream dis, byte [] data, int offset, int length) throws IOException
	{
		int result;
		int left = length, index = offset;

		while (left > 0) {
			if ((result = dis.read(data, index, left)) == -1)
				return -1;

			if (result == 0)
				break;

			index += result;
			left -= result;
		}

		return index;
	}

	/**
	 * <p>Receives {@code data.length} number of bytes from the input stream and stores them into the given byte array.</p>
	 *
	 * <p>This method is called by {@link #receive(Socket, byte[], int, int) receive(Socket,byte[],int,int)} and
	 * {@link #receive(Socket, byte[]) receive(Socket,byte[])} for handling data transfer from a socket.</p>
	 *
	 * <p>It uses a fixed offset value of 0 and receives up to the length of byte array.</p>
	 *
	 * @param dis the DataInputStream to read from
	 * @param data the byte array to store the read data from the DataInputStream
	 * @return the number of bytes read, or -1 if the end of the stream is reached before any data is read, or
	 * 0 when no bytes are read
	 * @throws IOException if an I/O error occurs while reading from the input stream
	 */
	private static int receive(DataInputStream dis, byte [] data) throws IOException
	{
		return receive(dis, data, 0, data.length);
	}

	/**
	 * <p>Sends specified {@code data} to an output stream {@code dos}</p>
	 *
	 * <p>This method first determines initial stages of transmission by instantiating with given {@code offset} and {@code length}.
	 * Upon entering the loop a complete transmission attempt is made, followed by a flush. After the flush, size of successfully
	 * written data is calculated by subtracting the initially written byte count from current written byte count. Based on number of
	 * successful writes, total bytes written, the number of bytes remaining and current offset is updated and method goes on
	 * looping until there are no more bytes remaining.</p>
	 *
	 * @param dos the DataOutputStream to write to
	 * @param data the byte array used for writing to the data output stream
	 * @param offset the starting index offset of the byte array
	 * @param length the maximum length of data from the byte array to be written
	 * @return the total number of bytes written to the data output stream
	 * @throws IOException if an I/O error occurs while writing to the data output stream
	 */
	private static int send(DataOutputStream dos, byte [] data, int offset, int length) throws IOException
	{
		int curOffset = offset;
		int left = length;
		int total = 0;
		int written;
		int initWritten = dos.size();

		while (total < length) {
			dos.write(data, curOffset, left);
			dos.flush();
			written = dos.size() - initWritten;
			total += written;
			left -= written;
			curOffset += written;
		}

		dos.flush();

		return total;
	}

	/**
	 * <p>Sends specified {@code data} to an output stream {@code dos}</p>
	 *
	 * <p>This method works the same way as {@link #send(DataOutputStream, byte[], int, int)}, but instead
	 * uses fixed starting offset of 0 and length determined by {@code data.length}</p>
	 *
	 * @param dos the DataOutputStream to write to
	 * @param data the byte array used for writing to the data output stream
	 * @return the total number of bytes written to the data output stream
	 * @throws IOException if an I/O error occurs while writing to the data output stream
	 */
	private static int send(DataOutputStream dos, byte [] data) throws IOException
	{
		return send(dos, data, 0, data.length);
	}

	private TcpUtil() {}

	/**
	 * <p>Returns an Optional SocketServer with the first available port number in the given
	 * {@code minPort}, {@code maxPort} (inclusive)
	 * range, having the specified {@code backlog} value for maximum number of pending connections on the server socket.
	 * Other-wise returns an empty Optional.</p>
	 *
	 * @param backlog requested maximum length of the queue of incoming connections
	 * @param minPort minimum value for the port number
	 * @param maxPort maximum value for the port number
	 * @return an Optional SocketServer with given backlog value,
	 * or empty optional if all the ports are busy and cannot be assigned
	 * @throws IllegalArgumentException if the provided port numbers are outside the valid range
	 */
	public static Optional<ServerSocket> getFirstAvailableSocket(int backlog, int minPort, int maxPort)
	{
		Optional<ServerSocket> result = Optional.empty();

		for (int port = minPort; port <= maxPort; ++port)
			try {
				result = Optional.of(new ServerSocket(port, backlog));
			}
			catch (IOException ignore) {
			}

		return result;
	}

	/**
	 * <p>Returns an Optional SocketServer with the first available port number in the given
	 * {@code minPort}, {@code maxPort} (inclusive)
	 * range. Other-wise returns an empty Optional.</p>
	 *
	 * @param minPort minimum value for the port number
	 * @param maxPort maximum value for the port number
	 * @return an Optional SocketServer, or empty optional if all the ports are busy and cannot be assigned
	 * @throws IllegalArgumentException if the provided port numbers are outside the valid range
	 */
	public static Optional<ServerSocket> getFirstAvailablePort(int minPort, int maxPort)
	{
		Optional<ServerSocket> result = Optional.empty();

		for (int port = minPort; port <= maxPort; ++port)
			try {
				result = Optional.of(new ServerSocket(port));
			}
			catch (IOException ignore) {
			}

		return result;
	}

	/**
	 * <p>Returns an Optional SocketServer with the first available port number in the given vararg parameter {@code ports},
	 * and sets the maximum number of pending connections on the server socket using the {@code backlog}.
	 * Other-wise returns an empty Optional.</p>
	 *
	 * @param backlog requested maximum length of the queue of incoming connections
	 * @param ports vararg parameter for the port number values
	 * @return an Optional SocketServer, or empty optional if all the {@code ports} are busy and cannot be assigned
	 * @throws IllegalArgumentException if the provided port numbers are outside the valid range
	 */
	public static Optional<ServerSocket> getFirstAvailableSocket(int backlog, int...ports)
	{
		Optional<ServerSocket> result = Optional.empty();

		for (var port : ports)
			try {
				result = Optional.of(new ServerSocket(port, backlog));
			}
			catch (IOException ignore) {
			}

		return result;
	}

	/**
	 * <p>Returns an Optional SocketServer with the first available port number in the given vararg parameter {@code ports}.
	 * Other-wise returns an empty Optional.</p>
	 *
	 * @param ports vararg parameter for the port number values
	 * @return an Optional SocketServer, or empty optional if all the ports are busy and cannot be assigned
	 * @throws IllegalArgumentException if the provided port numbers are outside the valid range
	 */
	public static Optional<ServerSocket> getFirstAvailableSocket(int...ports)
	{
		Optional<ServerSocket> result = Optional.empty();

		for (var port : ports)
			try {
				result = Optional.of(new ServerSocket(port));
			}
			catch (IOException ignore) {
			}

		return result;
	}

	/**
	 * <p>Receives {@code length} number of bytes from a socket, starting with index specified by {@code offset} and
	 * stores them into the specified byte array.</p>
	 *
	 * <p>This method relies on {@link #receive(DataInputStream, byte[], int, int)} for handling byte-by-byte transfer and
	 * is called by receiveXXX methods for retrieving different types of data including primitive types.</p>
	 *
	 * @param socket any valid and open socket
	 * @param data the byte array to store the read data from the socket
	 * @param offset the starting index offset of the byte array
	 * @param length the number of the bytes to be read
	 * @return the number of bytes read, or -1 if the end of the stream is reached before any data is read,
	 * or 0 when no bytes are read
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static int receive(Socket socket, byte [] data, int offset, int length)
	{
		try {
			return receive(new DataInputStream(socket.getInputStream()), data, offset, length);
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receive", ex);
		}
	}

	/**
	 * <p>Receives {@code data.length} number of bytes from a socket, starting from index number 0 and stores them into
	 * the specified byte array.</p>
	 *
	 * <p>This method relies on {@link #receive(Socket, byte[], int, int)} for handling byte-by-byte transfer and
	 * is called by receiveXXX methods for retrieving different types of data including primitive types.</p>
	 *
	 * @param socket any valid and open socket
	 * @param data the byte array to store the read data from the socket
	 * @return the number of bytes read, or -1 if the end of the stream is reached before any data is read,
	 * or 0 when no bytes are read
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static int receive(Socket socket, byte [] data)
	{
		try {
			return receive(socket, data, 0, data.length);
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receive", ex.getCause());
		}
	}

	/**
	 * <p>Sends {@code length} number of bytes over a socket, starting with index specified by {@code offset}.</p>
	 *
	 * <p>This method relies on {@link #send(DataOutputStream, byte[], int, int)} for handling byte-by-byte transfer and
	 * is called by sendXXX methods for sending different types of data including primitive types.</p>
	 *
	 * @param socket any valid and open socket
	 * @param data the byte array used for sending the data through the socket
	 * @param offset the starting index offset of the byte array to be sent
	 * @param length the maximum length of data from the byte array to be sent
	 * @return the total number of bytes sent through the socket
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static int send(Socket socket, byte [] data, int offset, int length)
	{
		try {
			return send(new DataOutputStream(socket.getOutputStream()), data, offset, length);
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.send", ex);
		}
	}

	/**
	 * <p>Sends {@code data.length} number of bytes over a socket, starting from index number 0.</p>
	 *
	 * <p>Number of bytes to read is as long as the incoming data and starting offset is 0.</p>
	 *
	 * <p>This method relies on {@link #send(Socket, byte[], int, int)} for handling byte-by-byte transfer and
	 * is called by sendXXX methods for sending different types of data including primitive types.</p>
	 *
	 * @param socket any valid and open socket
	 * @param data the byte array used for sending the data through the socket
	 * @return the total number of bytes sent through the socket
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static int send(Socket socket, byte [] data)
	{
		try {
			return send(socket, data, 0, data.length);
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.send", ex.getCause());
		}
	}

	/**
	 * <p>Receives a single byte from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for a byte and calls {@link #receive(Socket, byte[])}.</p>
	 * <p>Received byte is returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received byte value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static byte receiveByte(Socket socket)
	{
		try {
			byte [] data = new byte[Byte.BYTES];

			receive(socket, data);

			return data[0];
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveByte", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveByte", ex);
		}
	}

	/**
	 * <p>Receives a short value from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for 2 bytes and calls {@link #receive(Socket, byte[])}.
	 * Byte array filled by incoming data is converted to short by {@link BitConverter#toShort(byte[])}
	 * and returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received short value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static short receiveShort(Socket socket)
	{
		try {
			byte[] data = new byte[Short.BYTES];

			receive(socket, data);

			return BitConverter.toShort(data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveShort", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveShort", ex);
		}
	}

	/**
	 * <p>Receives an integer value from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for 4 bytes and calls {@link #receive(Socket, byte[])}.
	 * Byte array filled by incoming data is converted to Int by {@link BitConverter#toInt(byte[])}
	 * and returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received int value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static int receiveInt(Socket socket)
	{
		try {
			byte[] data = new byte[Integer.BYTES];

			receive(socket, data);

			return BitConverter.toInt(data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveInt", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveInt", ex);
		}
	}

	/**
	 * <p>Receives an integer value from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for 8 bytes and calls {@link #receive(Socket, byte[])}.
	 * Byte array filled by incoming data is converted to long by {@link BitConverter#toLong(byte[])}
	 * and returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received long value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static long receiveLong(Socket socket)
	{
		try {
			byte[] data = new byte[Long.BYTES];

			receive(socket, data);

			return BitConverter.toLong(data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveLong", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveLong", ex);
		}
	}

	/**
	 * <p>Receives a float value from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for 4 bytes and calls {@link #receive(Socket, byte[])}.
	 * Byte array filled by incoming data is converted to float by {@link BitConverter#toFloat(byte[])}
	 * and returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received float value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static float receiveFloat(Socket socket)
	{
		try {
			byte[] data = new byte[Float.BYTES];

			receive(socket, data);

			return BitConverter.toFloat(data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveFloat", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveFloat", ex);
		}
	}

	/**
	 * <p>Receives a double value from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for 8 bytes and calls {@link #receive(Socket, byte[])}.
	 * Byte array filled by incoming data is converted to double by {@link BitConverter#toDouble(byte[])}
	 * and returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received double value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static double receiveDouble(Socket socket)
	{
		try {
			byte[] data = new byte[Double.BYTES];

			receive(socket, data);

			return BitConverter.toDouble(data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveDouble", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveDouble", ex);
		}
	}

	/**
	 * <p>Receives a char value from the {@code socket}</p>
	 *
	 * <p>This method allocates a buffer for 2 bytes and calls {@link #receive(Socket, byte[])}.
	 * Byte array filled by incoming data is converted to char by {@link BitConverter#toChar(byte[])}
	 * and returned.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received char value
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static char receiveChar(Socket socket)
	{
		try {
			byte[] data = new byte[Character.BYTES];

			receive(socket, data);

			return BitConverter.toChar(data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveChar", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveChar", ex);
		}
	}

	/**
	 * <p>Receives a length-prefixed string {@code str} from a socket using default charset {@link StandardCharsets#UTF_8}</p>
	 *
	 * <p>This method consecutively receives specified {@code str}'s length and then the string itself from
	 * the specified {@code socket}.</p>
	 *
	 * <p>In order to successfully receive length-prefixed strings, sender must use matching
	 * {@link #sendStringViaLength(Socket, String, Charset)} method. </p>
	 *
	 * @param socket any valid and open socket
	 * @return the received string
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static String receiveStringViaLength(Socket socket)
	{
		return receiveStringViaLength(socket, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Receives a length-prefixed string {@code str} from a socket using specified {@code charset}</p>
	 *
	 * <p>This method consecutively receives specified {@code str}'s length and then the string itself
	 * from the specified {@code socket}.</p>
	 *
	 * <p>In order to successfully receive length-prefixed strings, sender must use matching
	 * {@link #sendStringViaLength(Socket, String, Charset)} method. </p>
	 *
	 * @param socket any valid and open socket
	 * @param charset the charset of the text
	 * @return the received string
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @see TcpUtil#sendStringViaLength(Socket, String, Charset)
	 */
	public static String receiveStringViaLength(Socket socket, Charset charset)
	{
		try {
			byte[] data = new byte[receiveInt(socket)];

			receive(socket, data);

			return BitConverter.toString(data, charset);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveStringViaLength", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveStringViaLength", ex);
		}
	}

	/**
	 * <p>Receives a string having {@code length} number of bytes from the {@code socket}, using
	 * the default charset {@link StandardCharsets#UTF_8}.</p>
	 *
	 * @param socket any valid and open socket
	 * @param length the number of bytes to read
	 * @return the received string
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static String receiveString(Socket socket, int length)
	{
		return receiveString(socket, length, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Receives a string having {@code length} number of bytes from the {@code socket}, using
	 * the specified {@code charset}.</p>
	 *
	 * @param socket any valid and open socket
	 * @param length the number of the bytes to be read
	 * @param charset the charset of the text
	 * @return the received string
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static String receiveString(Socket socket, int length, Charset charset)
	{
		try {
			byte[] data = new byte[length];

			if (receive(socket, data) == -1)
				return null;

			return BitConverter.toString(data, charset);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveString", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveString", ex);
		}
	}

	/**
	 * <p>Receives a line of text from the specified {@code socket}, using default charset {@link StandardCharsets#UTF_8}
	 * for decoding the received data and reading it in blocks of default block size {@link TcpUtil#DEFAULT_LINE_BLOCK_SIZE}.
	 * The method stops reading when a newline character `\n` is detected and returns the assembled line of text.</p>
	 *
	 * <p>This method will work properly if the sender closes the socket after the send process.</p>
	 *
	 * @param socket any valid and open socket
	 * @return the received text
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @throws NullPointerException if socket is null or charset is null
	 * @throws NegativeArraySizeException blockSize is less than zero
	 * @see TcpUtil#receiveLine(Socket, Charset, int)
	 */
	public static String receiveLine(Socket socket)
	{
		return receiveLine(socket, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Receives a line of text from the specified {@code socket}, using the specified {@code charset}
	 * for decoding the received data and reading it in blocks of default block size {@link TcpUtil#DEFAULT_LINE_BLOCK_SIZE}.
	 * The method stops reading when a newline character `\n` is detected and returns the assembled line of text.</p>
	 *
	 * <p>This method will work properly if the sender closes the socket after the send process.</p>
	 *
	 * @param socket any valid and open socket
	 * @param charset the charset of the text
	 * @return the received text
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @throws NullPointerException if socket is null or charset is null
	 * @throws NegativeArraySizeException blockSize is less than zero
	 * @see TcpUtil#receiveLine(Socket, Charset, int)
	 */
	public static String receiveLine(Socket socket, Charset charset)
	{
		return receiveLine(socket, charset, DEFAULT_LINE_BLOCK_SIZE);
	}

	/**
	 * <p>Receives a line of text from the specified {@code socket}, using default charset {@link StandardCharsets#UTF_8}
	 * for decoding the received data and reading it in blocks of the specified {@code blockSize}. The method stops reading
	 * when a newline character `\n` is detected and returns the assembled line of text.</p>
	 *
	 * <p>This method will work properly if the sender closes the socket after the send process.</p>
	 *
	 * @param socket any valid and open socket
	 * @param blockSize block size of the internal buffer. If zero, no data is read
	 * @return the received text
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @throws NullPointerException if socket is null or charset is null
	 * @throws NegativeArraySizeException blockSize is less than zero
	 * @see TcpUtil#receiveLine(Socket, Charset, int)
	 */
	public static String receiveLine(Socket socket, int blockSize)
	{
		return receiveLine(socket, StandardCharsets.UTF_8, blockSize);
	}

	/**
	 * <p>Receives a line of text from the specified {@code socket}, using the provided {@code charset} for decoding
	 * the received data and reading it in blocks of the specified {@code blockSize}. The method stops reading
	 * when a newline character `\n` is detected and returns the assembled line of text.</p>
	 *
	 * <p>This method reads data from the socket in chunks of the given block size, converting each chunk
	 * from byte data into a string using the specified charset. It appends the received data to a
	 * {@link StringBuilder} until it encounters a newline character. If the newline is found in the
	 * middle of a chunk, only the portion up to the newline is appended, and reading stops. Otherwise,
	 * the entire chunk is appended, and the method continues reading more data.</p>
	 *
	 * <p>This method will work properly if the sender closes the socket after the send process.</p>
	 *
	 * @param socket any valid and open socket
	 * @param charset the charset of the text
	 * @param blockSize block size of the internal buffer. If zero, no data is read
	 * @return the received text
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @throws NullPointerException if socket is null or charset is null
	 * @throws NegativeArraySizeException blockSize is less than zero
	 */
	public static String receiveLine(Socket socket, Charset charset, int blockSize)
	{
		var sb = new StringBuilder();
		var buf = new byte[blockSize];

		try (socket) {
			while (true) {
				receive(socket, buf);
				var str = BitConverter.toString(buf, charset);
				var index = str.lastIndexOf('\n');

				if (index != -1) {
					sb.append(str, 0, index);
					break;
				}

				sb.append(str);
			}
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveLine", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveLine", ex);
		}

		return sb.toString();
	}

	/**
	 * <p>Receives multiple lines of text from the specified {@code socket}, using default charset {@link StandardCharsets#UTF_8}
	 * for decoding and reading data in blocks of default block size {@link TcpUtil#DEFAULT_LINE_BLOCK_SIZE}.
	 * The method returns an array of strings, where each element represents a separate line of text. It reads the
	 * full content from the socket and splits it into individual lines based on newline characters.</p>
	 *
	 * <p>This method functions the same way as {@link #receiveLines(Socket, Charset, int)}, except that
	 * it uses default block size{@link TcpUtil#DEFAULT_LINE_BLOCK_SIZE} and default charset {@link StandardCharsets#UTF_8}</p>
	 *
	 * @param socket any valid and open socket
	 * @return all received lines from the socket in a string array
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @see TcpUtil#receiveLines(Socket, Charset, int)
	 */
	public static String [] receiveLines(Socket socket)
	{
		return receiveLines(socket, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Receives multiple lines of text from the specified {@code socket}, using the provided {@code charset}
	 * for decoding and reading data in blocks of default block size {@link TcpUtil#DEFAULT_LINE_BLOCK_SIZE}.
	 * The method returns an array of strings, where each element represents a separate line of text. It reads the
	 * full content from the socket and splits it into individual lines based on newline characters.</p>
	 *
	 * <p>This method functions the same way as {@link #receiveLines(Socket, Charset, int)}, except that
	 * it uses default block size{@link TcpUtil#DEFAULT_LINE_BLOCK_SIZE}</p>
	 *
	 * @param socket any valid and open socket
	 * @param charset the charset of the text
	 * @return all received lines from the socket in a string array
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @see TcpUtil#receiveLines(Socket, Charset, int)
	 */
	public static String [] receiveLines(Socket socket, Charset charset)
	{
		return receiveLines(socket, charset, DEFAULT_LINE_BLOCK_SIZE);
	}

	/**
	 * <p>Receives multiple lines of text from the specified {@code socket}, using default charset {@link StandardCharsets#UTF_8}
	 * for decoding and reading data in blocks of the specified {@code blockSize}. The method returns an
	 * array of strings, where each element represents a separate line of text. It reads the
	 * full content from the socket and splits it into individual lines based on newline characters.</p>
	 *
	 * <p>This method functions the same way as {@link #receiveLines(Socket, Charset, int)}, except that
	 * it uses default charset {@link StandardCharsets#UTF_8}</p>
	 *
	 * @param socket any valid and open socket
	 * @param blockSize block size of the internal buffer. If zero, no data is read
	 * @return all received lines from the socket in a string array
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 * @see TcpUtil#receiveLines(Socket, Charset, int)
	 */
	public static String [] receiveLines(Socket socket, int blockSize)
	{
		return receiveLines(socket, StandardCharsets.UTF_8, blockSize);
	}

	/**
	 * <p>Receives multiple lines of text from the specified {@code socket}, using the provided {@code charset}
	 * for decoding and reading data in blocks of the specified {@code blockSize}. The method returns an
	 * array of strings, where each element represents a separate line of text. It reads the
	 * full content from the socket and splits it into individual lines based on newline characters.</p>
	 *
	 * <p>This method relies on the {@link #receiveLine(Socket, Charset, int)} method to receive
	 * a single line of text. Once a full block of data is read, it splits the result using a
	 * regular expression to handle both Unix-style `\n` and Windows-style `\r\n` line endings.</p>
	 *
	 * <p>This method will work properly if sender close socket after the send process</p>
	 *
	 * @param socket any valid and open socket
	 * @param charset the charset of the text
	 * @param blockSize block size of the internal buffer
	 * @return all received lines from the socket in a string array
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static String [] receiveLines(Socket socket, Charset charset, int blockSize)
	{
		try {
			var lines = receiveLine(socket, charset, blockSize);

			return lines.split("[\r\n]+");
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveLines", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveLines", ex);
		}
	}

	/**
	 * <p>Receives a file from the socket and writes it to the path contained in {@link File} object.</p>
	 *
	 * <p>This method functions the same way as {@link #receiveFile(Socket, String)}, except that it uses the
	 * absolute path obtained from {@link File} object, instead of using string for path argument.</p>
	 *
	 * @param socket any valid and open socket
	 * @param file {@link File} object for saving the received file
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static void receiveFile(Socket socket, File file)
	{
		receiveFile(socket, file.getAbsolutePath());
	}

	/**
	 * <p>Receives a file from the {@code socket} and writes it to specified {@code path}.</p>
	 *
	 * <p>This method is to be used with {@link #sendFile(Socket, String, int)} and expects to receive the remaining length
	 * of the transfer in order to properly receive file.</p>
	 *
	 * <p>This method instantiates a {@link FileOutputStream} object with specified {@code path},
	 * allocates a buffer area of file size via calling {@link #receiveInt(Socket)}, then loops until no file chunks remain.
	 * Finally the resulting file data is written via calling {@link FileOutputStream}'s write method
	 * with the resulting byte array and with a starting offset of 0.</p>
	 *
	 * @param socket any valid and open socket
	 * @param path the path for saving the received file
	 * @throws NetworkException if any problem occurs while receiving from the socket
	 */
	public static void receiveFile(Socket socket, String path)
	{
		try (FileOutputStream fos = new FileOutputStream(path)) {
			int result;

			for (;;) {
				var size = receiveInt(socket);

				if (size <= 0)
					break;

				var data = new byte[size];

				result = receive(socket, data);
				fos.write(data, 0, result);
			}
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.receiveFile", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.receiveFile", ex);
		}
	}

	/**
	 * <p>Sends a single byte value to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(byte)} and calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the byte value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendByte(Socket socket, byte val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendByte", ex);
		}
	}

	/**
	 * <p>Sends a single byte value to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(short)} and calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the short value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendShort(Socket socket, short val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendShort", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendShort", ex);
		}
	}

	/**
	 * <p>Sends an integer value to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(int)} and then calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the int value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendInt(Socket socket, int val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendInt", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendInt", ex);
		}
	}

	/**
	 * <p>Sends a long value to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(long)} and then calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the long value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendLong(Socket socket, long val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendLong", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendLong", ex);
		}
	}

	/**
	 * <p>Sends a float value to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(float)} and then calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the float value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendFloat(Socket socket, float val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendFloat", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendFloat", ex);
		}
	}

	/**
	 * <p>Sends a double value to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(double)} and then calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the double value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendDouble(Socket socket, double val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendDouble", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendDouble", ex);
		}
	}

	/**
	 * <p>Sends a char to the {@code socket}</p>
	 *
	 * <p>This method allocates a new byte buffer via {@link BitConverter#getBytes(char)} and then calls {@link #send(Socket, byte[])}
	 * with the return value. </p>
	 *
	 * @param socket any valid and open socket
	 * @param val the char value to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendChar(Socket socket, char val)
	{
		try {
			send(socket, BitConverter.getBytes(val));
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendChar", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendChar", ex);
		}
	}

	/**
	 * <p>Sends a length-prefixed string {@code str} to a socket using default charset {@link StandardCharsets#UTF_8}</p>
	 *
	 * <p>This method consecutively sends specified {@code str}'s length and then the string itself using the specified {@code socket}.</p>
	 *
	 * <p>In order to successfully receive length-prefixed strings, receiver must use matching {@link #receiveStringViaLength(Socket)}
	 * method</p>
	 *
	 * @param socket any valid and open socket
	 * @param str the string to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 * @see TcpUtil#receiveStringViaLength(Socket)
	 */
	public static void sendStringViaLength(Socket socket, String str)
	{
		sendStringViaLength(socket, str, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Sends a length-prefixed string {@code str} to a socket using specified {@code charset}</p>
	 *
	 * <p>This method consecutively sends specified {@code str}'s length and then the string itself using the specified {@code socket}.</p>
	 *
	 * <p>In order to successfully receive length-prefixed strings, receiver must use matching {@link #receiveStringViaLength(Socket, Charset)}
	 * method</p>
	 *
 	 * @param socket any valid and open socket
	 * @param str the string to send
	 * @param charset the charset of the text
	 * @throws NetworkException if any problem occurs while sending through the socket
	 * @see TcpUtil#receiveStringViaLength(Socket, Charset)
	 */
	public static void sendStringViaLength(Socket socket, String str, Charset charset)
	{
		try {
			byte[] data = BitConverter.getBytes(str, charset);
			byte[] dataLen = BitConverter.getBytes(data.length);

			send(socket, dataLen);
			send(socket, data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendStringViaLength", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendStringViaLength", ex);
		}
	}

	/**
	 * <p>Sends the {@code str} to the {@code socket} using the default charset {@link StandardCharsets#UTF_8}.</p>
	 *
	 * @param socket any valid and open socket
	 * @param str the string to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendString(Socket socket, String str)
	{
		sendString(socket, str, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Sends the {@code str} to the {@code socket} using the specified {@code charset}.</p>
	 *
	 * @param socket any valid and open socket
	 * @param str the string to send
	 * @param charset the charset of the text
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendString(Socket socket, String str, Charset charset)
	{
		try {
			byte[] data = BitConverter.getBytes(str, charset);

			send(socket, data);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendString", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendString", ex);
		}
	}

	/**
	 * <p>Takes the text {@code str}, terminates it by line break and sends it to the {@code socket},
	 * using the default charset {@link StandardCharsets#UTF_8}</p>
	 *
	 * @param socket any valid and open socket
	 * @param str the text to send
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendLine(Socket socket, String str)
	{
		sendLine(socket, str, StandardCharsets.UTF_8);
	}

	/**
	 * <p>Takes the text {@code str}, terminates it by line break and sends it to {@code socket},
	 * using the specified {@code charset}.</p>
	 *
	 * @param socket any valid and open socket
	 * @param str the text to send
	 * @param charset the charset of the text
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendLine(Socket socket, String str, Charset charset)
	{
		try {
			sendString(socket, String.format("%s\r\n", str), charset);
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendLine", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendLine", ex);
		}
	}

	/**
	 * <p>Sends a {@code file} over the socket with specified block size.</p>
	 *
	 * <p>This method functions the same way as {@link #sendFile(Socket, String, int)}, except that it
	 * requires a {@link File} object.</p>
	 *
	 * @param socket any valid and open socket
	 * @param file the file to send
	 * @param blockSize block size of the internal buffer
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendFile(Socket socket, File file, int blockSize)
	{
		sendFile(socket, file.getAbsolutePath(), blockSize);
	}

	/**
	 * <p>Sends a file on given {@code path} over the socket with specified {@code blockSize}.</p>
	 *
	 * <p>This method allocates a byte array of {@code blockSize} and pushes the file in {@code path} into a
	 * {@link FileInputStream}. Then loops by sending transferred byte amount and the actual data until EOF condition is met.</p>
	 *
	 * @param socket any valid and open socket
	 * @param path the path to the file to send
	 * @param blockSize block size of the internal buffer
	 * @throws NetworkException if any problem occurs while sending through the socket
	 */
	public static void sendFile(Socket socket, String path, int blockSize)
	{
		byte [] data = new byte[blockSize];

		try (FileInputStream fis = new FileInputStream(path)) {
			int result;

			for (;;) {
				result = fis.read(data);
				sendInt(socket, result);
				if (result <= 0)
					break;
				send(socket, data, 0, result);
			}
		}
		catch (NetworkException ex) {
			throw new NetworkException("TcpUtil.sendFile", ex.getCause());
		}
		catch (Throwable ex) {
			throw new NetworkException("TcpUtil.sendFile", ex);
		}
	}
}
