package com.oracle.survey_creator_service.dto;

public class UserResult {
	
	String user;
	Integer count;
	
	public UserResult() {
		// TODO Auto-generated constructor stub
	}

	public UserResult(String user, Integer count) {
		super();
		this.user = user;
		this.count = count;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	

}
