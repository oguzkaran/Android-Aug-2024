package org.csystem.util.datetime.random;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.*;
import java.util.Random;

/**
 * Utility class for generating random dates.
 */
public final class DateTimeUtil {
    private static final String NTP_HOST = "pool.ntp.org";
    private static final int NTP_PORT = 123;
    private static final long SECONDS_FROM_1900_TO_1970 = 2208988800L;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_SOCKET_TIMEOUT = 1000;
    private static final int [] DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private DateTimeUtil()
    {}

    private static Instant getInstant(DatagramPacket response)
    {
        var data = response.getData();
        var seconds = ((data[40] & 0xFFL) << 24) | ((data[41] & 0xFFL) << 16) | ((data[42] & 0xFFL) << 8) | (data[43] & 0xFFL);
        var fraction = ((data[44] & 0xFFL) << 24) | ((data[45] & 0xFFL) << 16) | ((data[46] & 0xFFL) << 8) | (data[47] & 0xFFL);
        var epochSeconds = seconds - SECONDS_FROM_1900_TO_1970;
        var nanos = (fraction * 1_000_000_000L) >>> 32;

        return Instant.ofEpochSecond(epochSeconds, nanos);
    }

    /**
     * Checks if a given year is a leap year.
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Generates a random date for the current year.
     *
     * @param r the Random instance to use for generating the date
     * @return a random LocalDate for the current year
     */
    public static LocalDate randomDate(Random r)
    {
        return randomDate(r, LocalDate.now().getYear());
    }

    /**
     * Generates a random date for a specified year.
     *
     * @param r the Random instance to use for generating the date
     * @param year the year for which to generate the date
     * @return a random LocalDate for the specified year
     */
    public static LocalDate randomDate(Random r, int year)
    {
        return randomDate(r, year, year);
    }

    /**
     * Generates a random date within a specified range of years.
     *
     * @param r the Random instance to use for generating the date
     * @param minYear the minimum year (inclusive)
     * @param maxYear the maximum year (inclusive)
     * @return a random LocalDate within the specified range of years
     */
    public static LocalDate randomDate(Random r, int minYear, int maxYear)
    {
        int year = r.nextInt(maxYear - minYear + 1) + minYear;
        int month = r.nextInt(12) + 1;
        int day = r.nextInt(month == 2 && isLeapYear(year) ? 29 : DAYS[month]) + 1;

        return LocalDate.of(year, month, day);
    }

    /**
     * Returns the current instant obtained from an NTP server using the default socket timeout.
     *
     * This is a convenience overload that delegates to {@link #instantFromNTP(int)} using
     * the constant NTP_SOCKET_TIMEOUT.
     *
     * @return an {@link Instant} representing the time retrieved from the NTP server
     * @throws DateTimeException if an I/O error occurs while contacting the NTP server
     */
    public static Instant instantFromNTP()
    {
        return instantFromNTP(NTP_SOCKET_TIMEOUT);
    }

    /**
     * Returns the current date-time (UTC) obtained from an NTP server using the default socket timeout.
     *
     * This is a convenience overload that delegates to {@link #dateTimeFromNTP(int)} using
     * the constant NTP_SOCKET_TIMEOUT.
     *
     * @return a {@link LocalDateTime} in UTC representing the time retrieved from the NTP server
     * @throws DateTimeException if an I/O error occurs while contacting the NTP server
     */
    public static LocalDateTime dateTimeFromNTP()
    {
        return dateTimeFromNTP(NTP_SOCKET_TIMEOUT);
    }

    /**
     * Contacts a Network Time Protocol (NTP) server and returns the time as an {@link Instant}.
     *
     * The method:
     * - Builds a 48-byte NTP request packet and sets the first byte to 0x1B (NTP client mode).
     * - Sends the request via UDP to the configured NTP host and port.
     * - Waits for a response using the provided socket timeout.
     * - Parses the transmit timestamp from the response and converts it to an {@link Instant}.
     *
     * The NTP timestamp is converted from the 1900-based epoch to the Java epoch (1970) by
     * subtracting the constant SECONDS_FROM_1900_TO_1970 and converting the fractional part to nanoseconds.
     *
     * @param timeoutMillis the socket timeout in milliseconds to wait for an NTP response
     * @return an {@link Instant} representing the time returned by the NTP server
     * @throws DateTimeException wrapping an {@link IOException} if network I/O fails or the response cannot be received
     */
    public static Instant instantFromNTP(int timeoutMillis)
    {
        var buffer = new byte[NTP_PACKET_SIZE];
        buffer[0] = 0x1B;

        try (DatagramSocket socket = new DatagramSocket()) {
            var request = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(NTP_HOST), NTP_PORT);
            var response = new DatagramPacket(new byte[NTP_PACKET_SIZE], NTP_PACKET_SIZE);

            socket.setSoTimeout(timeoutMillis);
            socket.send(request);    // send NTP request
            socket.receive(response); // receive NTP response

            return getInstant(response); // parse and convert response to Instant
        }
        catch (IOException e) {
            // Wrap I/O exceptions in a runtime DateTimeException to unify error handling for callers
            throw new DateTimeException("dateTimeFromNTP", e);
        }
    }

    /**
     * Contacts an NTP server and returns the resulting time as a {@link LocalDateTime} in UTC.
     *
     * This method delegates to {@link #instantFromNTP(int)} to get an {@link Instant} and then
     * converts that instant to a {@link LocalDateTime} using {@link ZoneOffset#UTC}.
     *
     * @param timeoutMillis the socket timeout in milliseconds to wait for an NTP response
     * @return a {@link LocalDateTime} in UTC representing the time returned by the NTP server
     * @throws DateTimeException wrapping an {@link IOException} if network I/O fails or the response cannot be received
     */
    public static LocalDateTime dateTimeFromNTP(int timeoutMillis)
    {
        return LocalDateTime.ofInstant(instantFromNTP(timeoutMillis), ZoneOffset.UTC);
    }

    //...
}