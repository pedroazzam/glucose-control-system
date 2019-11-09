package com.managedata.glucontrolapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managedata.glucontrolapi.models.Event;
import com.managedata.glucontrolapi.models.User;

public interface EventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findByUser(User user);
	
	Event findById(long id);
	

}
