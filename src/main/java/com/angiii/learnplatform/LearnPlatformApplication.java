package com.angiii.learnplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearnPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnPlatformApplication.class, args);
	}
}
