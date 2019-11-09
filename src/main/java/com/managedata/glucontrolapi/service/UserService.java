package com.managedata.glucontrolapi.service;

import java.util.List;

import com.managedata.glucontrolapi.models.User;

public interface UserService {
	
	public List<User> findAll();
	
	public User findById(long id);
	
	public User save(User user);
	
	public User findByEmailAndPassword(String email, String password);

}
