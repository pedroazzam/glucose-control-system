package com.managedata.glucontrolapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managedata.glucontrolapi.models.Event;
import com.managedata.glucontrolapi.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService{
	
	EventRepository eventRepository;
	
	@Autowired
	public EventServiceImpl(EventRepository newEventRepository) {
		eventRepository = newEventRepository;
	}
	
	@Override
	public List<Event> findAll(){
		return eventRepository.findAll();
	}
	
	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}

}
