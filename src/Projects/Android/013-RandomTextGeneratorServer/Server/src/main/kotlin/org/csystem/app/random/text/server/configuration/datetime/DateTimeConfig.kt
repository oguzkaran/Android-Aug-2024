package org.csystem.app.random.text.server.configuration.datetime

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.time.LocalDateTime

@Configuration
class DateTimeConfig {
    @Bean
    @Scope("prototype")
    fun now() : LocalDateTime = LocalDateTime.now()
}