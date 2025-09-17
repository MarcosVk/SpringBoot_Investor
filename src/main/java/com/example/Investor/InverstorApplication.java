package com.example.Investor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Investor")
public class InverstorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InverstorApplication.class, args);
	}
}
