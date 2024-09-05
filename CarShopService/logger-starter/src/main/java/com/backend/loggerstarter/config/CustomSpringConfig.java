package com.backend.loggerstarter.config;

import com.backend.loggerstarter.listener.CustomSpringListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomSpringConfig {

    @Bean
    public CustomSpringListener customSpringListener() {
        return new CustomSpringListener();
    }

}
