package com.techleads.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techleads.app.model.Users;
import com.techleads.app.repository.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository userRepository;
	
	public Users saveUser(Users user) {
		Users save = userRepository.save(user);
		return save;
	}

}
