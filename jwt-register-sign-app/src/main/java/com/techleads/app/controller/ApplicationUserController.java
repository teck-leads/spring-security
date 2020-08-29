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

import com.techleads.app.model.ApplicationUser;
import com.techleads.app.model.UserRequest;
import com.techleads.app.model.UserResponse;
import com.techleads.app.service.UserService;
import com.techleads.app.util.JWTUtil;

@RestController
public class ApplicationUserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JWTUtil jWTUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value = { "/register" })
	public ResponseEntity<ApplicationUser> appUserRegister(@RequestBody ApplicationUser applicationUser) {

		ApplicationUser savesUser = userService.saveUser(applicationUser);
		return new ResponseEntity<>(savesUser, HttpStatus.OK);
	}
	
	// Validate user and generate token
		@PostMapping(value = { "/signin" })
		public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
			
			//validate username/pwd with db
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
			String token = jWTUtil.generateToken(userRequest.getUsername());
			UserResponse response = new UserResponse();
			response.setToken(token);
			response.setMsg("Authentication successful!");
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
}
