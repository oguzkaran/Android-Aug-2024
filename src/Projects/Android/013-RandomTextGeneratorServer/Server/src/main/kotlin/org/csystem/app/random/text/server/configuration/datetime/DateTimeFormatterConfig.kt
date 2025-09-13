package org.csystem.app.random.text.server.configuration.datetime

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class DateTimeFormatterConfig {
    @Bean
    fun dateTimeFormatterText(): DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss-n");
}