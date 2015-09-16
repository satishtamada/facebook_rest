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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.Comments;

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
		try {
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			comment_list = db.commentCreate(user1_id, post_id,comment);
			if (comment_list != null) {
				response.addProperty("success", true);
				// create comment array
				JsonArray comments = new JsonArray();
				
				for (int i = 0; i < comment_list.size(); i++) {
					Comments c = comment_list.get(i);
					// adding elements to json object
					JsonObject cObj = new JsonObject();
					cObj.addProperty("id", c.getId());
					cObj.addProperty("user_id", c.getCommented_user_id());
					cObj.addProperty("username", c.getCommented_username());
					cObj.addProperty("comment", c.getComment());
					cObj.addProperty("created_at",c.getCreated_at().toString());
					// checking if user not upload his profile image
					if (c.getProfile_image() == null)
						cObj.addProperty("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					else
						cObj.addProperty("profile_image",
								Config.PROFILE_IMAGE_URL + c.getProfile_image());
					//cObj.addProperty("created_at", c.getCreated_at().toString());
					// adding json object to comment array
					comments.add(cObj);
				}
				response.add("comments", comments);
			}else {
				response.addProperty("success", false);

				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_UNKNOWN);
				error.putOpt("message",
						"Server busy!");

				response.addProperty("error", error.toString());
			}
		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

}
