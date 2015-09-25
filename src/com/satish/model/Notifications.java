package com.satish.model;

import java.sql.Timestamp;

public class Notifications {
	private int user_id, friend_id, status, type,post_id;
	private String friend_name, message,profile_image;
	private Timestamp created_at;

	public Notifications() {
		super();
	}

	public Notifications(int user_id, int friend_id, int status, int type,int post_id,
			String friend_name, String message, Timestamp created_at,String profile_image) {
		super();
		this.user_id = user_id;
		this.friend_id = friend_id;
		this.status = status;
		this.type = type;
		this.friend_name = friend_name;
		this.message = message;
		this.created_at = created_at;
		this.profile_image=profile_image;
		this.post_id=post_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getUser_id() {
		return user_id;
	}

	public int getFriend_id() {
		return friend_id;
	}

	public int getStatus() {
		return status;
	}

	public int getType() {
		return type;
	}

	public String getFriend_name() {
		return friend_name;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getMessage() {
		return message;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

}
