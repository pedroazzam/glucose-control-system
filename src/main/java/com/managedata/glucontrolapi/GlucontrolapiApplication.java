package com.managedata.glucontrolapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class GlucontrolapiApplication {

	public static void main(String[] args) {
		//instanceof Object;
		
		SpringApplication.run(GlucontrolapiApplication.class, args);
	}

}
