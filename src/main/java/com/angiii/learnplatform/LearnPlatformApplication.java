package com.angiii.learnplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class LearnPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnPlatformApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
	}
}
