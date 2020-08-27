package com.techleads.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techleads.app.model.UserPrincipal;
import com.techleads.app.model.User;
import com.techleads.app.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository usersRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User saveUser(User user) {
		String pwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		User savedUser = usersRepository.save(user);
		return savedUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = usersRepository.findByUsername(username);
		user.orElseThrow(()->new UsernameNotFoundException("not found "+username));
		return user.map(UserPrincipal::new).get();
	}

}
