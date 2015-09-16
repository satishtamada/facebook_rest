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

@Path("/comment")
public class CommentsHandler {
	@Path("/create")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response loginUser(@FormParam("user1_id") int user1_id,
			@FormParam("post_id") int post_id,@FormParam("comment") String comment) {
		JsonObject response = new JsonObject();
		boolean comment_post;
		try {
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			comment_post= db.commentCreate(user1_id, post_id,comment);
			if (comment_post) {
				response.addProperty("success", true);
				response.add("error",null);
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
