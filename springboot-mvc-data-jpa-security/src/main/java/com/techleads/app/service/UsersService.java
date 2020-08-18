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
import com.techleads.app.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService{
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UsersRepository userRepository;
	
	public Users saveUser(Users user) {
		
		String encode = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		Users save = userRepository.save(user);
		return save;
	}
//for login 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> user = userRepository.findByEmail(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}else {
			
			Users users = user.get();
//			return new User(username, users.getPassword(), 
//					users.getRoles().stream().map(role->new SimpleGrantedAuthority(role)).
//					collect(Collectors.toSet()));
//			
			return new org.springframework.security.core.userdetails.
					User(
							username, 
							users.getPassword(), 
							users.getRoles()
							.stream()
							.map(role -> new SimpleGrantedAuthority(role))
							.collect(Collectors.toSet())

							); 
			
		}
		
	}

}
