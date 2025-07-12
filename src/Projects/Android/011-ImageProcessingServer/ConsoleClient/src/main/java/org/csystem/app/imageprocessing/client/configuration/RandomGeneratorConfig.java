package org.csystem.app.imageprocessing.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.random.RandomGenerator;

@Configuration
public class RandomGeneratorConfig {
    @Bean
    public RandomGenerator randomGenerator()
    {
        return new Random();
    }
}
