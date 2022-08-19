package com.pm0n3s.NewBTrackerAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pm0n3s.NewBTrackerAPI.model.Event;
import com.pm0n3s.NewBTrackerAPI.model.EventType;
import com.pm0n3s.NewBTrackerAPI.model.User;
import com.pm0n3s.NewBTrackerAPI.service.EventService;

@RestController
@CrossOrigin
public class EventController {

	@Autowired
	private EventService eventService;
	
	@GetMapping(value="/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		User u = eventService.findUser(id);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@PostMapping(value="/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createEvent(@PathVariable("id") Long id, @RequestBody Event event) {
		if(eventService.findUser(id).getId().equals(id)) {
			EventType et = eventService.saveEventType(event.getType());
			eventService.saveEvent(event, id, et);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/events/{id}/{eventId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> updateUser(@PathVariable("id") Long id, @RequestBody Event event) {
		Event ev = eventService.updateEvent(event, id);
		return new ResponseEntity<Event>(ev, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/events/{id}/{eventId}")
	public ResponseEntity<Void> deleteUser(@PathVariable("eventId") Long eventId) {
		eventService.deleteEventById(eventId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
