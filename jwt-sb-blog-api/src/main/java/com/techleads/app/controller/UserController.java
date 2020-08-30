package com.techleads.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.dto.LoginDto;
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

	@PostMapping(value = { "/register" })
	public ResponseEntity<UserResponse> registerUser(@RequestBody Users user) {
		try {
			UserResponse response = userService.saveUser(user);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}

	}
	
	@PostMapping(value = { "/login/" })
	public ResponseEntity<LoginDto> loginUser(@RequestBody LoginDto userRequest) {

		if (!userRequest.isValid(userRequest.getEmail())) {
			userRequest.setData("Invalid Username or Password");
			return new ResponseEntity<>(userRequest, HttpStatus.OK);
		}else {
			
			// validate username/pwd with db
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
			String token = jWTUtil.generateToken(userRequest.getEmail());
			userRequest.setToken(token);
			userRequest.setData(token);
			userRequest.setMsg("Authentication successful!");
			return new ResponseEntity<>(userRequest, HttpStatus.OK);
		}
		

	}
/*================================================================*/
	// Validate user and generate token
	/* working version-1
	@PostMapping(value = { "/login" })
	public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserRequest userRequest) {

		// validate username/pwd with db
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		String token = jWTUtil.generateToken(userRequest.getUsername());
		UserLoginResponse response = new UserLoginResponse();
		response.setToken(token);
		response.setMsg("Authentication successful!");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	*/
	/*================================================================*/
}
