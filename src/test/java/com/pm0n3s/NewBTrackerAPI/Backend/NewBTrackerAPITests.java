package com.pm0n3s.NewBTrackerAPI.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pm0n3s.NewBTrackerAPI.model.Event;
import com.pm0n3s.NewBTrackerAPI.model.User;
import com.pm0n3s.NewBTrackerAPI.model.enums.ChangeType;
import com.pm0n3s.NewBTrackerAPI.model.type.Change;
import com.pm0n3s.NewBTrackerAPI.model.type.Feed;
import com.pm0n3s.NewBTrackerAPI.model.type.Sleep;
import com.pm0n3s.NewBTrackerAPI.repository.EventRepository;
import com.pm0n3s.NewBTrackerAPI.repository.EventTypeRepository;
import com.pm0n3s.NewBTrackerAPI.repository.UserRepository;
import com.pm0n3s.NewBTrackerAPI.service.EventService;

@Test
public class NewBTrackerAPITests extends AbstractTestNGSpringContextTests {

	@Mock
	EventRepository eventRepo;

	@Mock
	EventTypeRepository eventTypeRepo;

	@Mock
	UserRepository userRepo;

	@InjectMocks
	EventService eventService;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@AfterClass
	public void tearDown() throws Exception {
		MockitoAnnotations.openMocks(this).close();
	}
	
	@Test
	public void givenValidUser_whenAccessingDashboard_eventsShouldBeAccessable() {
		User user = mockUser();
		
		when(userRepo.save(any())).thenReturn(user);
		when(userRepo.findById(any())).thenReturn(Optional.of(user));
		
		User returnedUser = eventService.findUser(1L);
		
		assertEquals(returnedUser, user);
	}
	
	@Test
	public void givenInvalidUser_nullShouldBeThrown() {
		when(userRepo.findById(any())).thenReturn(Optional.empty());
		
		User returnedUser = eventService.findUser(1L);
		
		assertEquals(returnedUser, null);
	}
	
	@Test
	public void givenValidEvent_whenAddEvent_EventShouldBeSaved() {
		User user = mockUser();
		Event ev = mockEvent1();
		
		when(userRepo.save(any())).thenReturn(user);
		when(userRepo.findById(any())).thenReturn(Optional.of(user));
		when(eventRepo.save(any())).thenReturn(ev);
		
		Event returnedEvent = eventService.saveEvent(ev, user.getId(), ev.getType());
		assertEquals(returnedEvent, ev);
		
		verify(eventRepo).save(ev);
	}
	
	@Test
	public void givenValidEvent_whenUpdateEvent_EventShouldBeUpdated() {
		User user = mockUser();
		Event ev = mockEvent1();
		Event ev2 = mockEvent1();
		ev2.setNotes("updated notes");
		
		when(userRepo.findById(any())).thenReturn(Optional.of(user));
		when(eventRepo.save(any())).thenReturn(ev);
		when(eventRepo.findById(any())).thenReturn(Optional.of(ev2));
		
		Event returnedEvent = eventService.saveEvent(ev, user.getId(), ev.getType());
		
		assertEquals(returnedEvent, ev);
		verify(eventRepo).save(ev);

		Event updatedEvent = eventService.updateEvent(ev2, user.getId());
		
		assertEquals(updatedEvent, ev2);
	}
	
	@Test
	public void givenInvalidEvent_whenUpdateEvent_nullShouldBeThrown() {
		when(eventRepo.findById(any())).thenReturn(Optional.empty());
		
		Event returnedEvent = eventService.findEventById(5L);
		
		assertEquals(returnedEvent, null);
	}
	
	@Test
	public void givenValidEvent_whenDeleteEvent_EventShouldBeDeleted() {
        doNothing().when(eventRepo).deleteById(anyLong());
        when(eventRepo.existsById(anyLong())).thenReturn(true);

		eventService.deleteEventById(1L);
        verify(eventRepo).deleteById(anyLong());
	}
	
//	setup methods
	
	private User mockUser() {
		User user = new User();

		user.setId(1L);
		user.setEmail("testemail@test.com");
		user.setUserName("testuser");
		user.setPassword("pass");
		user.setPhone("555-5555");
		user.setEventList(new ArrayList<Event>());
		
		return user;
	}

	private Change mockChange() {
		Change change = new Change();

		change.setChangeType(ChangeType.PEE);
		change.setId(2L);

		return change;
	}

	private Event mockEvent1() {
		Event event = new Event();

		event.setId(5L);
		event.setTime("08-22-2022 12:00");
		event.setNotes("no notes");
		event.setType(mockChange());
		
		return event;
	}
}
