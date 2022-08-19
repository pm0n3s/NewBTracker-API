package com.pm0n3s.NewBTrackerAPI.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.DiscriminatorType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pm0n3s.NewBTrackerAPI.model.type.Change;
import com.pm0n3s.NewBTrackerAPI.model.type.Feed;
import com.pm0n3s.NewBTrackerAPI.model.type.Sleep;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "selectType")
@JsonSubTypes({ @Type(value = Change.class, name = "change"), @Type(value = Sleep.class, name = "sleep"),
		@Type(value = Feed.class, name = "feed") })
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="select_type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("abs")
@Table(name="event_type")
public class EventType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne(mappedBy="select_type")
	private Event event;

	public EventType() {}
	
	public EventType(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	};
	
	
}

