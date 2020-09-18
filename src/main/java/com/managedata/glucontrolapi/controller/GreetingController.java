package com.managedata.glucontrolapi.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.managedata.glucontrolapi.entity.Greeting;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="*")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("passou aki no Greeting");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}