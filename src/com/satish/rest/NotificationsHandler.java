package com.satish.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.Comments;
import com.satish.model.Notifications;

@Path("/notification")
public class NotificationsHandler {
	@Path("/notifications")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response notificationsList(@QueryParam("user_id") int user_id) {

		JsonObject response = new JsonObject();
		Gson gson=new Gson();
		ArrayList<Notifications> notifications_list;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			notifications_list = db.notifications(user_id);
			// if comment is not null
			if (notifications_list != null) {
				response.addProperty("success", true);
				// create comment array
				JsonArray notifications = new JsonArray();
				
				for (int i = 0; i < notifications_list.size(); i++) {
					Notifications n = notifications_list.get(i);
					// adding elements to json object
					JsonObject cObj = new JsonObject();
					cObj.addProperty("user_id", n.getFriend_id());
					cObj.addProperty("username", n.getFriend_name());
					cObj.addProperty("message", n.getMessage());
					cObj.addProperty("created_at",n.getCreated_at().toString());
					cObj.addProperty("status", n.getStatus());
					cObj.addProperty("post_id",n.getPost_id());
					// checking if user not upload his profile image
					if (n.getProfile_image() == null)
						cObj.addProperty("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					else
						cObj.addProperty("profile_image",
								Config.PROFILE_IMAGE_URL + n.getProfile_image());
					//cObj.addProperty("created_at", c.getCreated_at().toString());
					// adding json object to comment array
					notifications.add(cObj);
				}
				response.add("notifications", notifications);
			} else {
				response.addProperty("success", false);
				JsonObject error = new JsonObject();
				error.addProperty("code", Config.ERROR_UNKNOWN);
				error.addProperty("message", "unknown error..!");
				response.addProperty("error", gson.toJson(error));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(response.toString()).build();
	}

}
