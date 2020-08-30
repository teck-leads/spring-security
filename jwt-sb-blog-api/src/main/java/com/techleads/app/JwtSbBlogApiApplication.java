package com.techleads.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.techleads.app.repository.PostRepository;
import com.techleads.app.repository.UserRepository;
@EnableJpaRepositories(basePackageClasses = {PostRepository.class,UserRepository.class})
@SpringBootApplication
public class JwtSbBlogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSbBlogApiApplication.class, args);
	}

}
