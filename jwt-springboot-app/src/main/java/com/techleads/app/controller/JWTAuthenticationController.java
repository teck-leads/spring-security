package com.techleads.app.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.model.JWTRequest;
import com.techleads.app.model.JwtResponse;
import com.techleads.app.service.JWTService;
import com.techleads.app.util.JWTUtil;

@RestController
public class JWTAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtTokenUtil;

	@Autowired
	private JWTService userDetailsService;
	
	@GetMapping("/jwt")
	public ResponseEntity<String> jwtTest(){
		
		LocalTime now = LocalTime.now();
		
		String msg="JWT-Authentication-Test "+now.toString();
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	//@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
	}
}
