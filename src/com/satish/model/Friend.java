package com.satish.model;

public class Friend {
      int friend_id;
      String friend_name,profile_image;
	public Friend() {
	
	}
	public Friend(int friend_id, String friend_name,String profile_image) {
		super();
		this.friend_id = friend_id;
		this.friend_name = friend_name;
		this.profile_image=profile_image;
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
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}   
}
