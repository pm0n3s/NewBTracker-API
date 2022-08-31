package com.pm0n3s.NewBTrackerAPI.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm0n3s.NewBTrackerAPI.model.Event;
import com.pm0n3s.NewBTrackerAPI.model.EventType;
import com.pm0n3s.NewBTrackerAPI.model.User;
import com.pm0n3s.NewBTrackerAPI.repository.EventRepository;
import com.pm0n3s.NewBTrackerAPI.repository.EventTypeRepository;
import com.pm0n3s.NewBTrackerAPI.repository.UserRepository;

@Service
@Transactional
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EventTypeRepository eventTypeRepository;

	@Autowired
	private UserRepository userRepository;

	public User findUser(Long id) {
		if (userRepository.findById(id).isPresent()) {
			User u = userRepository.findById(id).get();
			return u;
		}
		return null;
	}

	public Event findEventById(Long eventId) {
		if (eventRepository.findById(eventId).isPresent()) {
			Event e = eventRepository.findById(eventId).get();
			return e;
		}
		return null;
	}

	public EventType saveEventType(EventType et) {
		return eventTypeRepository.save(et);
	}

	public Event saveEvent(Event e, Long id, EventType et) {
		User u = findUser(id);
		e.setUserId(u);
		e.setType(et);
		return eventRepository.save(e);
	}

	public Event updateEvent(Event e, Long id) {
		Event currentEvent = findEventById(e.getId());
		saveEventType(e.getType());

		currentEvent.setTime(e.getTime());
		currentEvent.setNotes(e.getNotes());

		saveEvent(currentEvent, id, e.getType());

		return findEventById(currentEvent.getId());
	}

	public void deleteEventById(Long eventId) {
		eventRepository.deleteById(eventId);
	}
}