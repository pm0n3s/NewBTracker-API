package com.pm0n3s.NewBTrackerAPI.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="event")
public class Event {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@JsonFormat(pattern= "MM-dd-yyyy HH:mm", shape= JsonFormat.Shape.STRING)
	@Column(name="event_time")
	private LocalDateTime eventTime;
	
	@OneToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name="selecttype_id")
	private EventType select_type;
	
	@Column(name="notes")
	private String notes;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="user_id")
	private User userId;
	
	public Event() {};
	
	public Event(Long id, String time, EventType type, String notes) {
		super();
		Id = id;
		this.eventTime = LocalDateTime.parse(time, FORMATTER);
		this.select_type = type;
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public LocalDateTime getTime() {
		return eventTime;
	}

	public void setTime(LocalDateTime time) {
		this.eventTime = time;
	}
	
	public void setTime(String time) {
		this.eventTime = LocalDateTime.parse(time, FORMATTER);
	}

	public EventType getType() {
		return select_type;
	}

	public void setType(EventType type) {
		this.select_type = type;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
}
