package com.managedata.glucontrolapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managedata.glucontrolapi.entity.User;
import com.managedata.glucontrolapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository newUserRepository) {
		userRepository = newUserRepository;
	}
	
	@Override
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@Override
	public User findById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User update(User user){
		return userRepository.save(user);
	}

}
