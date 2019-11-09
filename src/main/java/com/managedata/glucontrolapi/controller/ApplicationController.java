package com.managedata.glucontrolapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApplicationController {
	
	@GetMapping("/getMsg")
	public String greeting() {
		return "spring security example";
	}

}
