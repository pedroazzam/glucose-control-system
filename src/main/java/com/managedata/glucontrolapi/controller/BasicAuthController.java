package com.managedata.glucontrolapi.controller;

import org.springframework.web.bind.annotation.*;

import com.managedata.glucontrolapi.bean.AuthenticationBean;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*") //edited
@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {

    @PostMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        System.out.println("Logged in!");
        return new AuthenticationBean("You are authenticated");
    }


//    @GetMapping(path = "/basicauth")
//    public AuthenticationBean basicauth() {
//        System.out.println("Logged in!");
//        return new AuthenticationBean("You are authenticated");
//    }
    

}
