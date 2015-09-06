package com.satish.model;

public class Likes {
	private String liked_by_name;
	private int liked_user_id;
	public Likes() {
		
	}
	public Likes(String liked_by_name, int liked_user_id) {
		super();
		this.liked_by_name = liked_by_name;
		this.liked_user_id = liked_user_id;
	}
	public String getLiked_by_name() {
		return liked_by_name;
	}
	public void setLiked_by_name(String liked_by_name) {
		this.liked_by_name = liked_by_name;
	}
	public int getLiked_user_id() {
		return liked_user_id;
	}
	public void setLiked_user_id(int liked_user_id) {
		this.liked_user_id = liked_user_id;
	}

}
