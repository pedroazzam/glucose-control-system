package com.managedata.glucontrolapi.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.managedata.glucontrolapi.models.Event;
import com.managedata.glucontrolapi.models.GlucosePrediction;
import com.managedata.glucontrolapi.models.User;
import com.managedata.glucontrolapi.service.EventService;
import com.managedata.glucontrolapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Api(value = "REST API - EVENT")
public class EventController {

	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;

	@GetMapping("/event/all")
	@ApiOperation(value = "Return all events")
	public List<Event> findAll() {
		List<Event> newListEvent = eventService.findAll();
		// Sort By DateTime
		newListEvent.sort(Comparator.comparing(Event::getDate));
		newListEvent.forEach(event -> {
			System.out.println("DATE-TIME: " + event.getDate().toString());
		});
		return newListEvent;
	}

	@PostMapping("/event/user")
	@ApiOperation(value = "Return all events for an user (through a POST method)")
	public List<Event> findByUser(@RequestBody User user) {
		User newUser = userService.findById(user.getId());
		List<Event> newListEvent = eventService.findByUser(newUser);
		newListEvent.forEach(event -> event.setUser(null));
		// Sort By DateTime
		newListEvent.sort(Comparator.comparing(Event::getDate));
		return newListEvent;
	}

	@GetMapping("event/user/{id}")
	@ApiOperation(value = "Return all events for an user (through a GET method)")
	public List<Event> findByUserId(@PathVariable(value = "id") long id) {
		User newUser = userService.findById(id);
		List<Event> newListEvent = eventService.findByUser(newUser);
		newListEvent.forEach(event -> event.setUser(null));
		// Sort By DateTime
		newListEvent.sort(Comparator.comparing(Event::getDate));
		return newListEvent;
	}

	@GetMapping("event/userdates")
	@ApiOperation(value = "Return all events for an user between two dates (through a GET method) plus variations calculation")
	public List<Event> findByUserIdDateFromTo(@RequestParam long id, @RequestParam String dateTimeFrom,
			@RequestParam String dateTimeTo) {
		LocalDateTime newDateTimeFrom = LocalDateTime.parse(dateTimeFrom);
		LocalDateTime newDateTimeTo = LocalDateTime.parse(dateTimeTo);
		User newUser = userService.findById(id);
		List<Event> eventList = eventService.findByUser(newUser);
		List<Event> newEventList = new ArrayList<Event>();

		AtomicReference<Event> firstEvent = new AtomicReference<Event>();
		AtomicReference<Event> lastEvent = new AtomicReference<Event>();

		// CHECK THE ORDER OF EVENTS! MASS WITH THE TIME!
		// PRECISO ACERTAR A DINAMICA DOS DADOS CLACULADOS. ESTAMOS CALCULANDO O P2P E O
		// T2P, MAS FALTA AJUSTAR O T2P PARA SER UM CALCULO QUE VAI ACUMULANDO A CADA
		// PONTO!!!

		// order by DateTime cresc
		eventList.sort(Comparator.comparing(Event::getDate));
		eventList.forEach(event -> {
			System.out.println("DATE-TIME: " + event.getDate().toString());
		});

		eventList.forEach(event -> {
			if ((event.getDate().isAfter(newDateTimeFrom) || event.getDate().isEqual(newDateTimeFrom))
					&& (event.getDate().isBefore(newDateTimeTo) || event.getDate().isEqual(newDateTimeTo))) {
				if ((firstEvent.get() == null) && (event.getType().equalsIgnoreCase("Glucose"))) {
					firstEvent.set(event);
				} else if (firstEvent.get() != null){
					lastEvent.set(event);
					calculateVarPerMinT2P(firstEvent.get(), lastEvent.get());
				}
				newEventList.add(event);
			}
		});

		System.out.println("First Event Id: " + firstEvent.get().getId());
		System.out.println("Last Event Id: " + lastEvent.get().getId());

		return newEventList;
	}

	@PostMapping("event/add")
	@ApiOperation(value = "Register new event")
	public Event registerEvent(@RequestBody Event event) {
		System.out.println("DATE IS: " + event.getDate());
		System.out.println("DATE NOW IS: " + new Date());

		Event newEvent = eventService.save(event);
		// Depois de salvar, tenho que buscar todos os eventos existentes para este
		// cliente, colocá-los em ordem crescente e verificar
		// a varPerMin dos eventos de glicose e reescrevê-los
		User user = event.getUser();
		List<Event> listEvent = eventService.findByUser(user);
		// Sort By DateTime
		listEvent.sort(Comparator.comparing(Event::getDate));
		calculateVarPerMinP2P(listEvent);
		// Na hora do retorno eu busco novamente para pegar com a métrica da variação já
		// calculada!
		return eventService.findById(newEvent.getId());
	}

	public void calculateVarPerMinP2P(List<Event> list) {
		List<Event> newList = new ArrayList<Event>();
		list.forEach(event -> {
			if (event.getType().equalsIgnoreCase("Glucose")) {
				newList.add(event);
			}
		});
		newList.sort(Comparator.comparing(Event::getDate));
		newList.forEach(event -> {
			BigDecimal difMin;
			BigDecimal difGluc;
			BigDecimal varPerMin;
			int ind = newList.indexOf(event);
			if (ind > 0) {
				LocalDateTime fromDateTime = newList.get(ind - 1).getDate();
				LocalDateTime toDateTime = newList.get(ind).getDate();
				LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
				Long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
				System.out.println("Possui " + minutes + " minutos de distância!");// DELETE ME!

				difMin = BigDecimal.valueOf(minutes);
				System.out.println("e em BigDecimal --> " + difMin);// DELETE ME!
				// difMin = BigDecimal.valueOf((newList.get(ind).getDate()./60000) -
				// (newList.get(ind - 1).getDate().getTime()/60000));//DELETE ME!
				difGluc = newList.get(ind).getValue().subtract(newList.get(ind - 1).getValue());
				varPerMin = difGluc.divide(difMin, 4, BigDecimal.ROUND_HALF_UP);
				event.setVarPerMinP2P(varPerMin);
				eventService.update(event);
			}
		});
	}

	public void calculateVarPerMinT2P(Event firstEvent, Event lastEvent) {
		if (firstEvent.getType().equalsIgnoreCase("Glucose") && lastEvent.getType().equalsIgnoreCase("Glucose")) {

			BigDecimal difMin;
			BigDecimal difGluc;
			BigDecimal varPerMin;

			LocalDateTime fromDateTime = firstEvent.getDate();
			LocalDateTime toDateTime = lastEvent.getDate();
			LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
			Long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
			
			System.out.println("Comparando a distância entre o id: " + firstEvent.getId() + " e o id: " + lastEvent.getId());
			System.out.println("Possui " + minutes + " minutos de distância!");// DELETE ME!

			difMin = BigDecimal.valueOf(minutes);
			
			System.out.println("e em BigDecimal --> " + difMin);// DELETE ME!
			// difMin = BigDecimal.valueOf((newList.get(ind).getDate()./60000) -
			// (newList.get(ind - 1).getDate().getTime()/60000));//DELETE ME!
			difGluc = lastEvent.getValue().subtract(firstEvent.getValue());
			varPerMin = difGluc.divide(difMin, 4, BigDecimal.ROUND_HALF_UP);
			lastEvent.setVarPerMinT2P(varPerMin);
			eventService.update(lastEvent);
		} else {
			firstEvent.setVarPerMinP2P(null);
			firstEvent.setVarPerMinT2P(null);
			eventService.update(firstEvent);

			lastEvent.setVarPerMinP2P(null);
			lastEvent.setVarPerMinT2P(null);
			eventService.update(lastEvent);
		}
	}

	public GlucosePrediction glucoseLevelPredictTwoPoints(LocalDateTime sourceDateFrom, LocalDateTime sourceDateTo) {

		return null;
	}

}
