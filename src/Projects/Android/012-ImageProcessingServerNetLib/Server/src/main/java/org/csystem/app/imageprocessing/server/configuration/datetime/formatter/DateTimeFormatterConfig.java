package org.csystem.app.imageprocessing.server.configuration.datetime.formatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatterConfig {
    @Bean
    public DateTimeFormatter dateTimeFormatter()
    {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-n");
    }
}
