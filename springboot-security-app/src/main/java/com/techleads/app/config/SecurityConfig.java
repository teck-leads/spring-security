package com.techleads.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("madhav").password("{noop}madhav")
		.authorities("EMP");
		auth.inMemoryAuthentication().withUser("teja").password("{noop}teja")
		.authorities("ADMIN");
		
		auth.inMemoryAuthentication().withUser("ram").password("{noop}ram")
		.authorities("STUDENT");
		
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/home").permitAll() //type 1
		
		.antMatchers("/emp").hasAuthority("EMP")
		.antMatchers("/std").hasAuthority("STUDENT") //type 2
		.antMatchers("/admin").hasAuthority("ADMIN")
		
		.antMatchers("/common").authenticated() //type 3
		
		
		.anyRequest().authenticated() //type 4
		
		//form 
		.and()
		.formLogin()
		.defaultSuccessUrl("/common",true)
		
		//logout
		.and()
		.logout()
		
		
		// access denied exception for wrong role access
		.and()
		.exceptionHandling()
		.accessDeniedPage("/denied")
		
		
		
		
		
		;
	}
	
	//Authorization
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		//everyone can access
		.antMatchers("/login").permitAll()
		.antMatchers("/contacts").permitAll()
		
		//Access after login only 
		.antMatchers("/inbox").authenticated()
		
		.antMatchers("/settings", "/format").authenticated()
		
		//by Admin only
		.antMatchers("/emp/save","emp/delete","emp/export").hasAuthority("ADMIN")
		.antMatchers("/emp/*").hasAuthority("ADMIN")
		
		.antMatchers("/emp/save","emp/delete/id","emp/export/pdf","emp/update").hasAuthority("ADMIN")
		.antMatchers("/emp/**").hasAuthority("ADMIN")
		//any number of /symbols are allowed
		
		.antMatchers("/approveLoad").hasAuthority("MGR")
		.antMatchers("/checkbalance").hasAnyAuthority("MGR","CASHIER");
	}*/
	
	/*
	 * Level of security 
	 * 1. permitAll
	 * 2. authenticated
	 * 3. hasAuthority
	 * 4. hasAnyAuthority
	 * 
	 */

}
