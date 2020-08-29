package com.techleads.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techleads.app.model.ApplicationUser;
import com.techleads.app.repository.ApplicationUserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private ApplicationUserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public ApplicationUser saveUser(ApplicationUser user) {
		try {

			String password = user.getPassword();
			password = bCryptPasswordEncoder.encode(password);
			user.setPassword(password);
			
			Set<String> roles = new HashSet<>();
			roles.add("USER");
			user.setRoles(roles);
			ApplicationUser savedUser = userRepository.save(user);
			return savedUser;
		} catch (Exception e) {
			throw e;
		}
	}

	public ApplicationUser findByUsername(String username) {
		Optional<ApplicationUser> findByUsername = userRepository.findById(username);
		//Optional<ApplicationUser> findByUsername = userRepository.findByUsername(username);

		if (findByUsername.isEmpty()) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			return findByUsername.get();
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ApplicationUser> findByUsername = userRepository.findById(username);
		//Optional<ApplicationUser> findByUsername = userRepository.findByUsername(username);
		if (findByUsername.isEmpty()) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			ApplicationUser users = findByUsername.get();
			return new User(username, users.getPassword(), users.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
//			return new User(username, users.getPassword(), users.getRoles().stream()
//					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
		}

	}

}
