package com.managedata.glucontrolapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managedata.glucontrolapi.models.Event;
import com.managedata.glucontrolapi.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Api(value="REST API - EVENT")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@GetMapping("/event/all")
	@ApiOperation(value="Return all events")
	public List<Event> findAll(){
		return eventService.findAll();
	}
	
	@PostMapping("event/add")
	@ApiOperation(value="Register new event")
	public Event registerEvent(@RequestBody Event event) {
		Event newEvent = eventService.save(event);
		return newEvent;
		
	}

}
