package com.managedata.glucontrolapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managedata.glucontrolapi.entity.Authority;
import com.managedata.glucontrolapi.repository.AuthorityRepository;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	
	AuthorityRepository authorityRepository;
	
	@Autowired
	public AuthorityServiceImpl(AuthorityRepository newAuthorityRepository) {
		authorityRepository = newAuthorityRepository;
	}
	
	@Override
	public Authority saveAuthority(Authority authority) {
		authorityRepository.save(authority);
		return authority;
	}

}
