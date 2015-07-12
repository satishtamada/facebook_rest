package com.satish.model;

public class Friend {
      int friend_id;
      String friend_name;
	public Friend() {
	
	}
	public Friend(int friend_id, String friend_name) {
		super();
		this.friend_id = friend_id;
		this.friend_name = friend_name;
	}
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}   
}
