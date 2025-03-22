package org.csystem.util.datetime;


import org.csystem.util.datetime.converter.DateTimeConvertUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class DateTimeConverterUtilTest {
    @Test
    public void givenValue_whenLocalDateTime_thenConvertToExpectedMilliseconds()
    {
        var expected = 1725544800000L;
        var dateTime = LocalDateTime.of(2024, Month.SEPTEMBER, 5, 17, 0);
        var millisecond = DateTimeConvertUtil.toMilliseconds(dateTime);

        Assert.assertEquals(expected, millisecond);
    }


    @Test
    public void givenValue_whenLocalDate_thenConvertToExpectedMilliseconds()
    {
        var expected = 1742590800000L;
        var date = LocalDate.of(2025, Month.MARCH, 22);
        var millisecond = DateTimeConvertUtil.toMilliseconds(date);

        Assert.assertEquals(expected, millisecond);
    }
}
