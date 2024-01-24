package com.StreamingApp.example.StreamingApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonS3Config {

    private static final String ACCESS_KEY = "AKIA3FLD52DEIOUKTBOT";
    private static final String SECRET_KEY = "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";
    private static final String REGION = "us-east-1";

    @Bean
    public S3Client s3Client() {
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY));
        return S3Client.builder()
                .region(Region.of(REGION))
                .credentialsProvider(credentialsProvider)
                .build();
    }
}



