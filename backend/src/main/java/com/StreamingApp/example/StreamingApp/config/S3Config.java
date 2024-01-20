package com.StreamingApp.example.StreamingApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        // Configure and return an instance of S3Client
        return S3Client.builder().build();
    }
}
