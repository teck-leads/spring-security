package com.techleads.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.techleads.app.model.User;
import com.techleads.app.service.MyUserDetailsService;

@Controller
public class LoginController {
	@Autowired
	private MyUserDetailsService service;

	@GetMapping(value = "/login")
	public String login(@ModelAttribute User user,Model model,Principal p) {
		
		
		if(!StringUtils.isEmpty(p)) {
			return "redirect:/ui";
		}
		
		model.addAttribute("user", new User());
		
		return "login";
		//return "redirect:/ui/";
	}
	
	
	
	@PostMapping(value = "/register")
	public String register(@ModelAttribute User user,Model model) {
		service.saveUser(user);
		return "redirect:/login";
	}
	
	@GetMapping(value = "/ui")
	public String uiPage(Model model) {
		model.addAttribute("msg", "you are logged in");
		return "uipage";
	}
	
	

}
