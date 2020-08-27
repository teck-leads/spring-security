package com.techleads.app.controller;

import java.security.Principal;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@GetMapping("/")
	public String home(){
		return ("<h1>Welcome home page</h1>");
	}

	@GetMapping("/users")
	public String user(){
		return ("<h1>Welcome user</h1>");
	}


	@GetMapping("/admin")
	public String admin(){
		return ("<h1>Welcome Admin</h1>");
	}
	@GetMapping("/username")
	public String loggedInUser(Principal p) {
	
		if(!StringUtils.isEmpty(p)) {
			
			return ("<h1>Welcome home page "+p.getName()+"</h1>");
		}else {
			return "No user is logged in ";
		}
		
	}

}
