package com.ajay.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsCofig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:8080"
                )
                .allowedMethods("PUT","GET","POST","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
