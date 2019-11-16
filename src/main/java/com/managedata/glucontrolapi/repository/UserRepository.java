package com.managedata.glucontrolapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managedata.glucontrolapi.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findById(long id);
	
	Optional<User> findByEmail(String email);
	
	
}
