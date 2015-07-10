package com.satish.model;

public class User {
	private String name, email,api_key;
	private int id;

	public User() {

	}

	public User(int id, String name, String email,String api_key) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.api_key=api_key;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
}
