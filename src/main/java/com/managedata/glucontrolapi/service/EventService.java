package com.managedata.glucontrolapi.service;

import java.util.List;

import com.managedata.glucontrolapi.models.Event;

public interface EventService {
	
	public List<Event> findAll();
	
	public Event save(Event event);

}
