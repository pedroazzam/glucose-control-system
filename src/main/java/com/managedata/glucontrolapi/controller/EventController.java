package com.managedata.glucontrolapi.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managedata.glucontrolapi.models.Event;
import com.managedata.glucontrolapi.models.User;
import com.managedata.glucontrolapi.service.EventService;
import com.managedata.glucontrolapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Api(value="REST API - EVENT")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/event/all")
	@ApiOperation(value="Return all events")
	public List<Event> findAll(){
		List<Event> newListEvent = eventService.findAll();
		newListEvent.sort(Comparator.comparing(Event::getDate));
		
		calculateVarPerMin(newListEvent);
		
		return newListEvent;
	}
	
	@PostMapping("/event/user")
	@ApiOperation(value="Return all events for an user")
	public List<Event> findByUser(@RequestBody User user){
		User newUser = userService.findById(user.getId());
		List<Event> newListEvent = eventService.findByUser(newUser);
		newListEvent.forEach(event -> event.setUser(null));
		//Sort
		newListEvent.sort(Comparator.comparing(Event::getDate));
		return newListEvent;
	}
	
	@PostMapping("event/add")
	@ApiOperation(value="Register new event")
	public Event registerEvent(@RequestBody Event event) {
		Event newEvent = eventService.save(event);
		
		// Depois de salvar, tenho que buscar todos os eventos existentes para este cliente, colocá-los em ordem crescente e verificar
		// a varPerMin dos eventos de glicose e reescrevê-los
		User newUser = event.getUser();
		List<Event> newListEvent = eventService.findByUser(newUser);
		newListEvent.sort(Comparator.comparing(Event::getDate));
		
		calculateVarPerMin(newListEvent);
		// Na hora do retorno eu busco novamente para pegar com a métrica da variação já calculada!
		return eventService.findById(newEvent.getId());
		
	}
	
	public void calculateVarPerMin(List<Event> list) {
		List<Event> newList = new ArrayList<Event>();
		
		list.forEach(event -> {
			if (event.getType().equalsIgnoreCase("Glucose")) {
				newList.add(event);
			}
		});
		
		newList.forEach(event -> {
			BigDecimal difMin;
			BigDecimal difGluc;
			BigDecimal varPerMin;
			int ind = list.indexOf(event);
			
			//System.out.println("O INDEXOF É: " + ind);
			if (ind > 0){
				difMin = BigDecimal.valueOf((list.get(ind).getDate().getTime()/60000) - (list.get(ind - 1).getDate().getTime()/60000));
				//System.out.println("Difference in minutes: " + difMin);
				difGluc = list.get(ind).getValue().subtract(list.get(ind-1).getValue());
				//System.out.println("Difference in glucose values: " + difGluc);
				
				varPerMin = difGluc.divide(difMin,4,BigDecimal.ROUND_HALF_UP);
				//System.out.println("Variation per minute: " + varPerMin);
				event.setVarPerMin(varPerMin);
				eventService.update(event);
			}
		});
		
	}

}
