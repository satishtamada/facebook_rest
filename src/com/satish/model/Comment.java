package com.satish.model;

import java.sql.Timestamp;

public class Comment {
	/*"id": 0,
    "user_id": 0,
    "username": "satish",
    "comment": "Nice post",
    "created_at": "2015-07-11T17:37:34.775Z"*/
    	private int id,commented_user_id;
        private String commented_username,comment;
        private Timestamp created_at;
        
		public Comment() {
		
		}

		public Comment(int id, int commented_user_id,
				String commented_username, String comment, Timestamp created_at) {
			super();
			this.id = id;
			this.commented_user_id = commented_user_id;
			this.commented_username = commented_username;
			this.comment = comment;
			this.created_at = created_at;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCommented_user_id() {
			return commented_user_id;
		}

		public void setCommented_user_id(int commented_user_id) {
			this.commented_user_id = commented_user_id;
		}

		public String getCommented_username() {
			return commented_username;
		}

		public void setCommented_username(String commented_username) {
			this.commented_username = commented_username;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public Timestamp getCreated_at() {
			return created_at;
		}

		public void setCreated_at(Timestamp created_at) {
			this.created_at = created_at;
		}
        
        

}
