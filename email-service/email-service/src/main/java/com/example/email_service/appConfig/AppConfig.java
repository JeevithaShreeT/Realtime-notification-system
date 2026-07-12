package com.example.email_service.appConfig;

import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
