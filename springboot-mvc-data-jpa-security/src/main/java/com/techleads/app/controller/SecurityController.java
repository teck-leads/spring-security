package com.techleads.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.techleads.app.model.Users;
import com.techleads.app.service.UsersService;

@Controller
public class SecurityController {
	@Autowired
	private UsersService usersService;
	
	@GetMapping("/register")
	public String showReg() {
		return "register";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute Users user, Model model) {
		
		
		Users saveUser = usersService.saveUser(user);
		String message ="User saved "+saveUser.getId();
		
		model.addAttribute("msg",message);
		return "register";
	}

	
	
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
