package com.logger.enhancer.LoggerEnhancer.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // allows all paths
                .allowedOrigins("http://localhost:3000") // allow your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // typical HTTP methods
                .allowedHeaders("*") // allow all headers
                .allowCredentials(true); // allow cookies, if needed
    }
}
