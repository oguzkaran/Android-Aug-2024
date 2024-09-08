package org.csystem.demo;

public class Time {
    private int m_hour, m_minute, m_second, m_millisecond;

    //...

    public void setHour(int value)
    {
        if (value < 0 || value > 59)
            throw new IllegalArgumentException("Invalid value");

        m_hour = value;

        //...
    }

    public int getHour()
    {
        return m_hour;
    }

    //
}
