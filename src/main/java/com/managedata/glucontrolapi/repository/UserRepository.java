package com.managedata.glucontrolapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managedata.glucontrolapi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findById(long id);
	
	
}
