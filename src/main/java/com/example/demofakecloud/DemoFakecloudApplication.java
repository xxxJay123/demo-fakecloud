package com.example.demofakecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demofakecloud")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoFakecloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoFakecloudApplication.class, args);
	}

}
