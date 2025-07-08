package com.example.ThoughtCapsule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThoughtCapsuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThoughtCapsuleApplication.class, args);
	}

}
