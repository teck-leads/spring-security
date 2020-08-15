package com.techleads.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SecurityController {
	
	@GetMapping("/home")
	public String showHome() {
		return "home";
	}
	
	@GetMapping("/emp")
	public String emp() {
		return "emp";
	}
	
	@GetMapping("/std")
	public String std() {
		return "student";
	}
	
	@GetMapping("/common")
	public String common() {
		return "common";
	}
	
	@GetMapping("/denied")
	public String denied() {
		return "denied";
	}
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

}
