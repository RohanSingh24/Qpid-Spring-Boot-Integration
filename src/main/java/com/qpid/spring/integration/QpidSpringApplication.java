package com.qpid.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QpidSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(QpidSpringApplication.class, args);
	}
}
