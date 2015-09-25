package com.satish.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.Comments;
import com.satish.model.Likes;
import com.satish.model.User;

@Path("/post")
public class PostCommentHandler {

	@Path("/comments")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postComments(@QueryParam("post_id") int post_id) {

		JsonObject response = new JsonObject();
		Gson gson=new Gson();
		ArrayList<Comments> comment_list;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			comment_list = db.comment(post_id);
			// create comment array
			JsonArray comments = new JsonArray();
			// if comment is not null
			if (comment_list != null) {
				response.addProperty("success", true);
				
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
			} else {
				response.addProperty("success", true);
				response.add("comments", comments);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(response.toString()).build();
	}

	@Path("/likes")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postLikes(@QueryParam("post_id") int post_id) {
		JSONObject response = new JSONObject();
		ArrayList<Likes> likes_list;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			likes_list = db.postLikes(post_id);
			System.out.println(likes_list.size());
			if (likes_list != null) {
				int count=db.countLikes(post_id);
				response.put("success", true);
				response.put("likes",count);
				JSONArray likes = new JSONArray();
				for (int i = 0; i < likes_list.size(); i++) {
					Likes l = likes_list.get(i);
					JSONObject likesObj = new JSONObject();
					likesObj.put("id", l.getLiked_user_id());
					likesObj.put("liked by",l.getLiked_by_name());
					likes.put(likesObj);
				}
               response.put("likes by", likes);
			}
		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}
}
