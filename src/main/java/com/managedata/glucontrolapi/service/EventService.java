package com.managedata.glucontrolapi.service;

import java.util.List;

import com.managedata.glucontrolapi.entity.Event;
import com.managedata.glucontrolapi.entity.User;

public interface EventService {
	
	public List<Event> findAll();
	
	public Event save(Event event);
	
	public List<Event> findByUser(User user);
	
	public Event findById(long id);
	
	public void update(Event event);
	

}
