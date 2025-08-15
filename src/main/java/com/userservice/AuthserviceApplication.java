package com.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.userservice")
public class AuthserviceApplication {
	//pr
	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

}
