package com.satish.rest;


import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.helper.Parse;
import com.satish.model.Comments;
import com.satish.model.User;

@Path("/comment")
public class CommentsHandler {
	@Path("/create")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response loginUser(@FormParam("user1_id") int user1_id,
			@FormParam("post_id") int post_id,@FormParam("comment") String comment) {
		JsonObject response = new JsonObject();
		ArrayList<Comments> comment_list;
		boolean comment_post;
		try {		
			DatabaseHandler db = new DatabaseHandler();
			comment_post= db.commentCreate(user1_id, post_id,comment);
			comment_list = db.comment(post_id);
			if (comment_post) {
				response.addProperty("success", true);
				response.add("error",null);
				response.addProperty("comments_count", comment_list.size());
				System.out.println(comment_list.size());
				
				
				// get post owner
				User userOwner = db.getUserByPost(post_id);
				// get post owner
				User userCommentOwner = db.getUserByCommented(user1_id);
				
				System.out.println("user by post: " + userCommentOwner.getId() + ", name: " + userCommentOwner.getName());
				
				if(userOwner != null){
					
					int length = comment.length();
					length = length >= 20 ? 20 : length;
					String commentShort = comment.substring(0, length);
					
					// create notification row
					String message = Config.PUSH_MESSAGE_NEW_COMMENT.replace("#name#", userCommentOwner.getName()).replace("#comment#", commentShort);
					String db_message= Config.DB_MESSAGE_NEW_COMMENT.replace("#name#", userCommentOwner.getName()).replace("#comment#", commentShort);
					boolean notification = db.createNotification(user1_id,post_id, db_message, Config.NOTIFICATION_COMMENT, 0);
					
					if(notification){
						// send push notification
						JSONObject jObj = new JSONObject();
						jObj.put("flag", 1);
						jObj.put("is_background", false);
						
						JSONObject jData = new JSONObject();
						jData.put("message", message);
						jData.put("title", "New Comment");
						
						jObj.put("data", jData);
						
						
						System.out.println("push: "+ jObj.toString());
						
						Parse parse = new Parse();
						// parse.sendPush(json);
						parse.sendPushNotification(userOwner.getEmail(), jObj.toString());
					}else{
						System.out.println("Failed to store notification");
					}
				}
				
				
			}else {
				response.addProperty("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_UNKNOWN);
				error.putOpt("message",
						"Server busy....!");
				response.addProperty("error", error.toString());
			}
		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

}
