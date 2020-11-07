package com.managedata.glucontrolapi.service;

import java.util.List;
import java.util.Optional;

import com.managedata.glucontrolapi.entity.User;

public interface UserService {
	
	public List<User> findAll();
	
	public User findById(long id);

	public Optional<User> findByEmail(String email);
	
	public User save(User user);
	
	public User findByEmailAndPassword(String email, String password);

	public User update(User user);
	

}
