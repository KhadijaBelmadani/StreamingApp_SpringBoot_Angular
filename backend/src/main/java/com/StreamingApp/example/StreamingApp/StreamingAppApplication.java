package com.StreamingApp.example.StreamingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.StreamingApp.example.StreamingAppApplication"})
public class StreamingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingAppApplication.class, args);
	}

}
