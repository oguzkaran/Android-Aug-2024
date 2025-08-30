package org.csystem.app.random.text.client.configuration.thread

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
class ThreadPoolConfig {
    @Bean
    fun threadPool() : ExecutorService = Executors.newSingleThreadExecutor()
}