package com.satish.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;

import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.Comment;

@Path("/post")
public class PostCommentHandler {
	@Path("/comments")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postComment(@QueryParam("post_id") int post_id) {

		JSONObject response = new JSONObject();
		ArrayList<Comment> comment_list;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			comment_list = db.comment(post_id);
			
			// if comment is not null
			if (comment_list != null) {
				response.put("success", true);
				// create comment array
				JSONArray comments = new JSONArray();
				for (int i = 0; i < comment_list.size(); i++) {
					Comment c = comment_list.get(i);
					// adding elements to json object
					JSONObject cObj = new JSONObject();
					cObj.put("id", c.getId());
					cObj.put("user_id", c.getCommented_user_id());
					cObj.put("username", c.getCommented_username());
					cObj.put("comment", c.getComment());
					cObj.put("created_at", c.getCreated_at());
					// adding json object to comment array
					comments.put(cObj);
				}
				response.put("comments", comments);
			}else {
				response.put("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_NO_COMMENTS);
				error.putOpt("message", "no comments on your post");
				response.put("error", error);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(response.toString()).build();
	}
}
