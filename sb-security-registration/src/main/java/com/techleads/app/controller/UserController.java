package com.techleads.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.techleads.app.model.User;
import com.techleads.app.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService registerService;

	
	@GetMapping(value = "/login1")
	public String login(@ModelAttribute User user,Model model) {
		model.addAttribute("user", new User());
		
		return "login";
		//return "redirect:/ui/";
	}
	
	@PostMapping(value = "/register")
	public String register(@ModelAttribute User user, Model model) {
		registerService.registerUser(user);
		//when there is a redirection, csrf is required 
		return "redirect:/login1";
	}
	
	@PostMapping(value = "/login1")
	public String login1(@ModelAttribute User user,Model model) {
		model.addAttribute("user", new User());
		
		User findByUsername = registerService.findByUsername(user.getUsername());
		if(findByUsername.getId()==0) {
			return "login";
		}
		//return "login";
		return "redirect:/ui/";
	}
	
	@GetMapping(value = "/ui")
	public String uiPage(Model model) {
		model.addAttribute("msg", "you are logged in");
		return "uipage";
	}
	
	@GetMapping(value = "/admin")
	public String adminPage(Model model) {
		model.addAttribute("msg", "you are logged in");
		return "admin";
	}
	
	@GetMapping(value = "/employee")
	public String empployeePage(Model model) {
		model.addAttribute("msg", "you are logged in");
		return "employee";
	}
}
