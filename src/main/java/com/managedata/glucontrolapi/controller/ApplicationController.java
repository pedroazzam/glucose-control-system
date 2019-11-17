package com.managedata.glucontrolapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.managedata.glucontrolapi.entity.User;
import com.managedata.glucontrolapi.service.UserService;



@Controller
public class ApplicationController {
	
	@Autowired
	UserService userService;
	
	@GetMapping({"/", "/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@PostMapping("/user/save") 
	public String newUser(@ModelAttribute("user") User theUser) {
		System.out.println("PASSOU AKI01");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		System.out.println("PASSOU AKI02");
		theUser.setPassword(bCryptPasswordEncoder.encode(theUser.getPassword()));
		theUser.setEnabled(true);
		System.out.println("PASSOU AKI03");
		System.out.println(theUser.getId());
		userService.save(theUser);
		
		 
	 	return "redirect:/"; 
	 }
	 
	 

}
