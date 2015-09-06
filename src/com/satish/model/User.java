package com.satish.model;

import java.sql.Timestamp;

public class User {
	private String name, email,api_key,image;
	private int id;
	private Timestamp created_at;

	public User() {

	}

	public User(int id, String name, String email,String api_key,Timestamp created_at,String image) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.api_key=api_key;
		this.created_at = created_at;
		this.image=image;
		
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
	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
