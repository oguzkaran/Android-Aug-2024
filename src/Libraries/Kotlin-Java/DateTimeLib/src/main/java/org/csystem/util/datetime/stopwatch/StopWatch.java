package org.csystem.util.datetime.stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * A utility class for measuring elapsed time.
 */
public class StopWatch {
    private long m_start;
    private long m_stop;

    /**
     * Gets the start time in nanoseconds.
     *
     * @return the start time in nanoseconds
     */
    public long getStart()
    {
        return m_start;
    }

    /**
     * Gets the stop time in nanoseconds.
     *
     * @return the stop time in nanoseconds
     */
    public long getStop()
    {
        return m_stop;
    }

    /**
     * Starts the stopwatch.
     */
    public void start()
    {
        m_start = System.nanoTime();
    }

    /**
     * Stops the stopwatch.
     */
    public void stop()
    {
        m_stop = System.nanoTime();
    }

    /**
     * Gets the elapsed time in the specified time unit.
     *
     * @param timeUnit the time unit for the elapsed time
     * @return the elapsed time in the specified time unit
     */
    public long getElapsed(TimeUnit timeUnit)
    {
        return timeUnit.convert(m_stop - m_start, TimeUnit.NANOSECONDS);
    }

    /**
     * Gets the elapsed time in nanoseconds.
     *
     * @return the elapsed time in nanoseconds
     */
    public long getElapsedAsNano()
    {
        return m_stop - m_start;
    }

    /**
     * Gets the elapsed time in milliseconds.
     *
     * @return the elapsed time in milliseconds
     */
    public long getElapsedAsMillis()
    {
        return getElapsed(TimeUnit.MILLISECONDS);
    }

    /**
     * Gets the elapsed time in seconds.
     *
     * @return the elapsed time in seconds
     */
    public long getElapsedAsSeconds()
    {
        return getElapsed(TimeUnit.SECONDS);
    }

    /**
     * Gets the total elapsed time in seconds as a double.
     *
     * @return the total elapsed time in seconds
     */
    public double totalSeconds()
    {
        return getElapsedAsNano() / 1_000_000_000.;
    }

    //...
}