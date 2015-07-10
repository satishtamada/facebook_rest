package com.satish.model;

import java.sql.Timestamp;

public class FeedPost {
	private int post_id,user_id,likes,width,height;
	String username,text,image;
	private Timestamp created_at;
	public FeedPost() {
		
	}
	public FeedPost(int post_id, int user_id, int likes, String username,String text,
			String image,int width,int height,Timestamp created_at) {
		super();
		this.post_id = post_id;
		this.user_id = user_id;
		this.likes = likes;
		this.text = text;
		this.image = image;
		this.username=username;
		this.width=width;
		this.height=height;
		this.created_at=created_at;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

}
