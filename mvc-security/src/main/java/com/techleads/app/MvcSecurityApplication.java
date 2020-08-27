package com.techleads.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.techleads.app.repository.UsersRepository;
@EnableJpaRepositories(basePackageClasses = UsersRepository.class)
@SpringBootApplication
public class MvcSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcSecurityApplication.class, args);
	}

}
