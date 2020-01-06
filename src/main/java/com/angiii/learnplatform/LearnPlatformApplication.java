package com.angiii.learnplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.angiii.learnplatform.dao")
public class LearnPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnPlatformApplication.class, args);
	}

}
