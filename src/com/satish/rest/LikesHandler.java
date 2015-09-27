package com.satish.rest;

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
import com.satish.model.User;

@Path("/like")
public class LikesHandler {
	@Path("/create")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response loginUser(@FormParam("user_id") int user1_id,
			@FormParam("post_id") int post_id,@FormParam("like_status") int like_status) {
		JsonObject response = new JsonObject();
		boolean like_post;
		System.out.println(user1_id+"" +post_id+" "+like_status);
		try {
			DatabaseHandler db = new DatabaseHandler();
			like_post= db.likeCreate(user1_id, post_id,like_status);
			if (like_post) {
				response.addProperty("success", true);
				response.add("error",null);
				
				// get post owner
				User userOwner = db.getUserByPost(post_id);
				// get post owner
				User userLikeOwner = db.getUserByCommented(user1_id);
				
				System.out.println("user by post: " + userLikeOwner.getId() + ", name: " + userLikeOwner.getName());
				
				if(userOwner != null){	
					// create notification row
					String message = Config.PUSH_MESSAGE_NEW_LIKE.replace("#name#", userLikeOwner.getName());
					String db_message= Config.DB_MESSAGE_NEW_LIKE.replace("#name#", userLikeOwner.getName());
					boolean notification = db.createNotification(user1_id,post_id, db_message, Config.NOTIFICATION_LIKE, 0);
					
					if(notification){
						// send push notification
						JSONObject jObj = new JSONObject();
						jObj.put("flag", 3);
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
