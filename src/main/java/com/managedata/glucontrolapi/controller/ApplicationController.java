package com.managedata.glucontrolapi.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.managedata.glucontrolapi.entity.Authority;
import com.managedata.glucontrolapi.entity.User;
import com.managedata.glucontrolapi.service.AuthorityService;
import com.managedata.glucontrolapi.service.UserService;



@Controller
public class ApplicationController {
	
	@Autowired
	UserService userService;
	
	AuthorityService authorityService;
	
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Logged as " + authentication.getName().toString());
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
	
	@GetMapping("/user/addevent")
	public String addEvent() {
		return "addevent";
	}
	
	@PostMapping("/user/save") 
	public String newUser(@ModelAttribute("user") User theUser) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		theUser.setPassword(bCryptPasswordEncoder.encode(theUser.getPassword()));
		theUser.setEnabled(true);//For a while all users are enabled=true! But the target is to be enabled=false by default and turn to true only after e-mail link check!
		System.out.println("New User:");//DELETE ME!
		System.out.println("User [" +
				"firstName=" + theUser.getFirstName() +
				", lastName=" + theUser.getLastName() +
				", email=" + theUser.getEmail() +
				", drFullName=" + theUser.getDrFullName() +
				", drEmail=" + theUser.getDrEmail() + "]");//DELETE ME!
		userService.save(theUser);
		
		 
	 	return "redirect:/"; 
	 }
	
	//Populate authority table with available roles!
	public void populateAuthority() {
		Authority authority = new Authority();
		authority.setId(1L);
		authority.setAuthority("ROLE_ADMIN");
		authorityService.saveAuthority(authority);
		
		authority.setId(2L);
		authority.setAuthority("ROLE_USER");
		authorityService.saveAuthority(authority);
	}
	 
	 

}
