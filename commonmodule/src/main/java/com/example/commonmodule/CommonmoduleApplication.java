package com.example.commonmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommonmoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonmoduleApplication.class, args);
	}

}
