package com.backend.loggerstarter.config;

import com.backend.loggerstarter.aspect.ExecutionTimeLoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionTimeLoggerConfig {

    @Bean
    public ExecutionTimeLoggerAspect executionTimeLoggerAspect() {
        return new ExecutionTimeLoggerAspect();
    }
}
