package com.managedata.glucontrolapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managedata.glucontrolapi.entity.User;
import com.managedata.glucontrolapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
@Api(value="REST API - USER")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("admin/user/all")
	@ApiOperation(value="Return all users")
	public List<User> userList(){
		return userService.findAll();
	}
	
	@GetMapping("user/{id}")
	@ApiOperation(value="Return user by id")
	public User userById(@PathVariable(value="id") long id) {
		return userService.findById(id);
		
	}
	
	@PostMapping("/signup")
	@ApiOperation(value="Register new user")
	public User saveUser (@RequestBody User user) {
		User newUser = userService.save(user);
		return newUser;
	}
	
	@PostMapping("/login")
	@ApiOperation(value="Login")
	public User saveUser (@RequestBody String email, @RequestBody String password) {
		User newUser = userService.findByEmailAndPassword(email, password);
		if (newUser != null) {
			System.out.println("é diferente de null!");
			return newUser;
		}else {
			System.out.println("é null!");
			throw new IllegalArgumentException("Access denied! Please try again or SignUp.");
		}
			
		
	}
	
	

}

