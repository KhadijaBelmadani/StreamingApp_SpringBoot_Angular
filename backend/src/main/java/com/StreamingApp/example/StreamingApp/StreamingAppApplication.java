package com.StreamingApp.example.StreamingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication

public class StreamingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingAppApplication.class, args);
	}

}