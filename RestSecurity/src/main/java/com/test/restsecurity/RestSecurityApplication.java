package com.test.restsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.test.restsecurity"})
public class RestSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestSecurityApplication.class, args);
	}
}
