package com.techleads.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techleads.app.model.MyUserDetails;
import com.techleads.app.model.Users;
import com.techleads.app.repository.UsersRepository;

@Service
public class MyUserDetailsSevice implements UserDetailsService{
	@Autowired
	private UsersRepository usersRepository;

	public Users saveUser(Users user) {
		Users savedUser = usersRepository.save(user);
		return savedUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> user = usersRepository.findByUsername(username);
		user.orElseThrow(()->new UsernameNotFoundException("not found "+username));
		return user.map(MyUserDetails::new).get();
	}

}
