package com.techleads.app.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techleads.app.model.Users;
import com.techleads.app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Users saveUser(Users user) {
		try {

			String password = user.getPassword();
			password = bCryptPasswordEncoder.encode(password);
			user.setPassword(password);
			Users savedUser = userRepository.save(user);
			return savedUser;
		} catch (Exception e) {
			throw e;
		}
	}

	public Users findByUsername(String username) {
		Optional<Users> findByUsername = userRepository.findByUsername(username);

		if (findByUsername.isEmpty()) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			return findByUsername.get();
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> findByUsername = userRepository.findByUsername(username);
		if (findByUsername.isEmpty()) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			Users users = findByUsername.get();
			return new User(username, users.getPassword(), users.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
		}

	}

}
