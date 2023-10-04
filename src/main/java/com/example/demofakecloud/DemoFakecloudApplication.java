package com.example.demofakecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demofakecloud")
public class DemoFakecloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoFakecloudApplication.class, args);
	}

}
