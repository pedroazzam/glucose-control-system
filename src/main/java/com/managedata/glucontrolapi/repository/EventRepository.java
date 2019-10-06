package com.managedata.glucontrolapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managedata.glucontrolapi.models.Event;
import com.managedata.glucontrolapi.models.User;

public interface EventRepository extends JpaRepository<Event, Long> {
	
	Event findByUser(User user);

}
