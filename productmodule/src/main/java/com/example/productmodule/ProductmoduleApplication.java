package com.example.productmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductmoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductmoduleApplication.class, args);
	}

}
