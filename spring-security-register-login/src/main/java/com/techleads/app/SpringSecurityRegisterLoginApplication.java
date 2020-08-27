package com.techleads.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class SpringSecurityRegisterLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityRegisterLoginApplication.class, args);
	}

}
