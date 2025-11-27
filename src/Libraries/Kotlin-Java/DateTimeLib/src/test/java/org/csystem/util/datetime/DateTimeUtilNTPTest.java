package org.csystem.util.datetime;


import org.csystem.util.datetime.random.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateTimeUtilNTPTest {
    @Test
    public void test_dateTimeFromNTP()
    {
        var now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).withSecond(0).withNano(0);
        var ntpNow = DateTimeUtil.dateTimeFromNTP().withSecond(0).withNano(0);

        Assert.assertEquals(now, ntpNow);
    }
}
