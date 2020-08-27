package com.techleads.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
//https://dzone.com/articles/spring-security-5-form-login-with-database-provide
//https://www.youtube.com/watch?v=TNt3GHuayXs
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/users").hasAuthority("USER")
		.antMatchers("/","/user/register","/register","/login","/h2-console/**").permitAll()
		.and().formLogin()
		.loginPage("/login")
		.permitAll();
		 http.csrf().ignoringAntMatchers("/user/register","/register","/login","/h2-console/**");
	     http.headers().frameOptions().sameOrigin();
	}
	

	

}
