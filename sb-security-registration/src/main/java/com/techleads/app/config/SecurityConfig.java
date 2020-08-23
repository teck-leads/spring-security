package com.techleads.app.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//https://docs.spring.io/spring-security/site/docs/5.0.16.BUILD-SNAPSHOT/guides/html5/form-javaconfig.html
//https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/appendix-schema.html
	//@Autowired
	//private DataSource dataSource;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth
 		.userDetailsService(userDetailsService)
 		.passwordEncoder(bCryptPasswordEncoder)
 		;
 	}
	
	
	/* not working
	//TODO JDBC with sql query  authentication_Start
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth
 		.jdbcAuthentication().dataSource(dataSource).
 		usersByUsernameQuery("select fullname, username   from user  where username = ?")
 		.authoritiesByUsernameQuery("select fullname, username   from user  where username = ?")
 		.passwordEncoder(bCryptPasswordEncoder)
 		;
 	}
 	*/
 	
	//TODO JDBC with sql query authentication_End
	/*
	//TODO JDBC authentication_Start
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth
 		.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
 		//USER-1
 		.withUser("madhav").password(bCryptPasswordEncoder.encode("madhav"))
 		.roles("USER")
 		//USER-2
 		.and().withUser("teja")
 		.password(bCryptPasswordEncoder.encode("teja"))
 		.roles("USER", "ADMIN")
 		;
 	}
	//TODO JDBC authentication_End
	*/
	/*
	//TODO IN-Memory authentication_Start
 	@Override
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth
 		// enable in memory based authentication with a user named "user" and "admin"
 		.inMemoryAuthentication()
 		//USER-1
 		.withUser("madhav").password(bCryptPasswordEncoder.encode("madhav"))
 		.roles("USER")
 		//USER-2
 		.and().withUser("teja")
 		.password(bCryptPasswordEncoder.encode("teja"))
 		.roles("USER", "ADMIN")
 		;
 	}
 	//TODO IN-Memory authentication_End
 	*/

 	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		
 		http.authorizeRequests()
// 		.antMatchers("/admin").hasRole("ADMIN")
 		.antMatchers("/admin").hasAuthority("ADMIN")
// 		.antMatchers("/employee").hasRole("ADMIN")
 		.antMatchers("/employee").hasAuthority("ADMIN")
 		
 		.antMatchers("/login1","/register","/h2-console/**").permitAll()
 		.and()
 		.formLogin()
 		//.loginPage("/login1")
 		
 	// access denied exception for wrong role access
 			.and()
 			.exceptionHandling()
 			.accessDeniedPage("/denied");
 		
 		
 		
 		 http.csrf().ignoringAntMatchers("/h2-console/**");
	     http.headers().frameOptions().sameOrigin();
 		
 		/*http.authorizeRequests().antMatchers("/public/**").permitAll().anyRequest()
 				.hasRole("USER").and()
 				// Possibly more configuration ...
 				.formLogin() // enable form based log in
 				// set permitAll for all URLs associated with Form Login
 				//.permitAll()
 				;*/
 	}
 	
 	@Override
 	public void configure(WebSecurity web) throws Exception {
 		web.ignoring()
 		// Spring Security should completely ignore URLs starting with /resources/
 				.antMatchers("/resources/**");
 	}



}
