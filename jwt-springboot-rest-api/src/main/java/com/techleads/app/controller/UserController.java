package com.techleads.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.model.UserRequest;
import com.techleads.app.model.UserResponse;
import com.techleads.app.model.Users;
import com.techleads.app.service.UserService;
import com.techleads.app.util.JWTUtil;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JWTUtil jWTUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value = { "/users" })
	public ResponseEntity<Users> saveUser(@RequestBody Users user) {
		Users saveUser = userService.saveUser(user);
		return new ResponseEntity<Users>(saveUser, HttpStatus.CREATED);

	}

	// Validate user and generate token
	@PostMapping(value = { "/users/token" })
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
		
		//validate username/pwd with db
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		String token = jWTUtil.generateToken(userRequest.getUsername());
		UserResponse response = new UserResponse();
		response.setToken(token);
		response.setMsg("Success!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}
	
		//after login only get the token and access this endpoint
		@PostMapping(value = {"/users/welcome"})
		public ResponseEntity<String> accessData(Principal p){
			
			return new ResponseEntity<String>(("Hello User "+p.getName()),HttpStatus.OK);
		}

}
