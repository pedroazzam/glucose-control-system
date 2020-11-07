package com.managedata.glucontrolapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
		System.out.println("PASSOU AQUI TESTE 15-10-20");
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

		System.out.println("Entered in PostMapping(/signup");
		System.out.println(user.getPassword());


		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);//For a while all users are enabled=true! But the target is to be enabled=false by default and turn to true only after e-mail link check!

		System.out.println("New User:");//DELETE ME!
		System.out.println("User [" +
				"firstName=" + user.getFirstName() +
				", lastName=" + user.getLastName() +
				", email=" + user.getEmail() +
				", drFullName=" + user.getDrFullName() +
				", drEmail=" + user.getDrEmail() + "]");//DELETE ME!

		User newUser = userService.save(user);
		return newUser;
	}

	@PostMapping("/user/update")
	@ApiOperation(value="User update")
	public User update (@RequestBody User user){

		System.out.println("Entered in PostMapping(/user/update");
		System.out.println(user.getId());

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);//For a while all users are enabled=true! But the target is to be enabled=false by default and turn to true only after e-mail link check!

		System.out.println("User updating:");//DELETE ME!
		System.out.println("User [" +
				"firstName=" + user.getFirstName() +
				", lastName=" + user.getLastName() +
				", email=" + user.getEmail() +
				", drFullName=" + user.getDrFullName() +
				", drEmail=" + user.getDrEmail() + "]");//DELETE ME!

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

	@GetMapping("user/email/{email}")
	@ApiOperation(value="Return user by email")
	public Optional<User> findByEmail(@PathVariable(value="email") String email) {
		System.out.println("Passou por findByEmail!");
		return userService.findByEmail(email);
	}

	@GetMapping("/test")
	@ApiOperation(value="Test")
	public void tete(){

	}
	
	

}

