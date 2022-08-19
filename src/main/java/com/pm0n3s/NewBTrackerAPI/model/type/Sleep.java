package com.pm0n3s.NewBTrackerAPI.model.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.pm0n3s.NewBTrackerAPI.model.EventType;

@JsonTypeName("sleep")
@Entity
@DiscriminatorValue("sleep")
public class Sleep extends EventType{
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

	@JsonFormat(pattern= "MM-dd-yyyy HH:mm", shape= JsonFormat.Shape.STRING)
	@Column(name="wakeup")
	private LocalDateTime wakeup;
	
	public Sleep() {};
	
	public Sleep(LocalDateTime wakeup) {
		super();
		this.wakeup = wakeup.truncatedTo(ChronoUnit.MINUTES);
	}

	public LocalDateTime getWakeup() {
		return wakeup;
	}

	public void setWakeup(LocalDateTime wakeup) {
		this.wakeup = wakeup;
	}
	public void setWakeup(String time) {
		this.wakeup = LocalDateTime.parse(time, FORMATTER);
	}
}

