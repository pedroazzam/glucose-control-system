package com.managedata.glucontrolapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managedata.glucontrolapi.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	
}
