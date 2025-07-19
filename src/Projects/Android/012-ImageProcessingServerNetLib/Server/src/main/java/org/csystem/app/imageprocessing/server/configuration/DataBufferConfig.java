package org.csystem.app.imageprocessing.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DataBufferConfig {
    @Bean
    @Scope("prototype")
    public byte [] createIntBuffer()
    {
        return new byte[Integer.BYTES];
    }
}
