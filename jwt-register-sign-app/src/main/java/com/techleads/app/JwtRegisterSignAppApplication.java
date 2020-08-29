package com.techleads.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.techleads.app.repository.ApplicationUserRepository;
@EnableJpaRepositories(basePackageClasses = {ApplicationUserRepository.class})
@SpringBootApplication
public class JwtRegisterSignAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtRegisterSignAppApplication.class, args);
	}

}
