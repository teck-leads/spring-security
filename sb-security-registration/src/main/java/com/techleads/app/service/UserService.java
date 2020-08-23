package com.techleads.app.service;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.techleads.app.model.User;
import com.techleads.app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void registerUser(User user) {

		String password = user.getPassword();
		password=bCryptPasswordEncoder.encode(password);
		user.setPassword(password);
		User save = userRepository.save(user);
		System.out.println(save);
	}
	
	public User findByUsername(String userName) {

		User findByUsername = userRepository.findByUsername(userName);
		if (findByUsername == null) {
			return new User();
		} else {
			return findByUsername;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println(username);
		
		if(StringUtils.isEmpty(username)) {
			System.out.println("empty "+username);
		}
		
		if (userRepository.findByUsername(username) == null) {
			throw new UsernameNotFoundException("not found");
		} else {
			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.
						User(
								username, 
								userRepository.findByUsername(username).getPassword(), 
								//Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")) //hasRole
								//Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")) //hasRole
								//Arrays.asList(new SimpleGrantedAuthority("ADMIN")) //hasAuthority
								Collections.singleton(new SimpleGrantedAuthority("ADMIN"))

								);
			return user;
		}
		
		
	}

	
}
