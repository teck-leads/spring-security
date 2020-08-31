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

import com.techleads.app.model.Users;
import com.techleads.app.repository.UserRepository;
import com.techleads.app.util.UserResponse;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	private String EMAIL_ERROR="email Email cannot be blank";
	private String REGISTERED="User Registered";
	@Autowired
	private UserRepository userRepository;
	
	public UserResponse saveUser(Users user) {
		try {
			UserResponse respo=new UserResponse();
		final boolean emailEmpty = user.getEmail()==null|| user.getEmail()=="";
			if(emailEmpty) {
				respo.setData(EMAIL_ERROR);
			}else {
				
				String password = user.getPassword();
				password = bCryptPasswordEncoder.encode(password);
				user.setPassword(password);
				
				Set<String> roles = new HashSet<>();
				roles.add("USER");
				user.setRoles(roles);
				user = userRepository.save(user);
				respo.setData(REGISTERED);
			}
			return respo;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public Users findUserById(Integer id) {
		Optional<Users> findById = userRepository.findById(id);
		
		Users user=new Users();
		if(findById.isPresent()) {
			user = findById.get();
			
		}
		return user;
		
	}
	
	public Users findByUsername(String username) {
		Optional<Users> findByUsername = userRepository.findByName(username);

//		if (findByUsername.isEmpty()) {
		if (findByUsername==null) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			return findByUsername.get();
		}

	}
	
	public Users findByEmail(String email) {
		Optional<Users> findByUsername = userRepository.findByEmail(email);

//		if (findByUsername.isEmpty()) {
		if (findByUsername==null) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			return findByUsername.get();
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> findByEmail = userRepository.findByEmail(username);
		if (null==findByEmail) {
			throw new UsernameNotFoundException("User Not found");
		} else {
			Users users = findByEmail.get();
			return new User(username, users.getPassword(), users.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));

		}

	}
	

}
