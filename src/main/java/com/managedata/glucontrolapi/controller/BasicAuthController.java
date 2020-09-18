package com.managedata.glucontrolapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managedata.glucontrolapi.bean.AuthenticationBean;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*") //edited
@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        System.out.println("...You are authenticated...");
        return new AuthenticationBean("You are authenticated");
    }
}
