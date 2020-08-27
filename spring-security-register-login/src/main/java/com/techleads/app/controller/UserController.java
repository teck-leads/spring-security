package com.techleads.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.model.Users;
import com.techleads.app.service.MyUserDetailsSevice;

@RestController
public class UserController {

	@Autowired
	private MyUserDetailsSevice service;

	@GetMapping("/")
	public String home() {
		return ("<h1>Welcome home page</h1>");
	}

	@GetMapping("/users")
	public String user() {
		return ("<h1>Welcome user</h1>");
	}

	@PostMapping("/register")
	public ResponseEntity<Users> register(@RequestBody Users user) {

		Users saveUser = service.saveUser(user);
		return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
	}

	@GetMapping("/admin")
	public String admin() {
		return ("<h1>Welcome Admin</h1>");
	}

	@GetMapping("/username")
	public String loggedInUser(Principal p) {

		if (!StringUtils.isEmpty(p)) {

			return ("<h1>Welcome home page " + p.getName() + "</h1>");
		} else {
			return "No user is logged in ";
		}

	}

}
