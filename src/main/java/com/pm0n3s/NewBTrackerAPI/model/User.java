package com.pm0n3s.NewBTrackerAPI.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User{

	public User() {};

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	@Column(name="username")
	private String userName;
	@Column(name="email")
	private String email;
	@Column(name="phone")
	private String phone;
	@Column(name="password")
	private String password;
	@OneToMany(mappedBy="userId", fetch=FetchType.EAGER)
	private List<Event> eventList;

	public User(Long id, String userName, String email, String phone, String password) {
		super();
		this.Id = id;
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.eventList = new ArrayList<Event>();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Event> getEventList(){
		return eventList;
	}
	
	public void setEvent(Event event) {
		this.eventList.add(event);
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", userName=" + userName + ", email=" + email + ", phone=" + phone + ", password="
				+ password + "]";
	}
	
}