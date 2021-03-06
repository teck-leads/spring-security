package com.techleads.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techleads.app.model.Users;
import com.techleads.app.repository.SecurityRepository;

@Service
public class SecurityService {
	@Autowired
	private SecurityRepository securityRepository;
	
	public Users saveUser(Users user) {
		Users save = securityRepository.save(user);
		return save;
	}

}
