package com.oracle.user_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//for auth
	private String password;
	private String email;
	
	private String name;
	private String phoneNumber;
	private LocalDateTime createdOn;
	
	private boolean isAdmin;
	private boolean isUser;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}


	public User(Long id, String password, String email, String name, String phoneNumber, LocalDateTime createdOn,
			boolean isAdmin, boolean isUser) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.createdOn = createdOn;
		this.isAdmin = isAdmin;
		this.isUser = isUser;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public boolean isUser() {
		return isUser;
	}


	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}


	
	
	

}
