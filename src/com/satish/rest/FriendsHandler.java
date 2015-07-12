package com.satish.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;

import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.Friend;
import com.sun.jersey.multipart.FormDataParam;

@Path("/friend")
public class FriendsHandler {
	@Path("/friends_list")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response friendsList(@QueryParam("id") int id) {
		ArrayList<Friend> friendlist;
		JSONObject response = new JSONObject();
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			friendlist = db.friendsList(id);
			if (friendlist != null) {
				response.put("success", true);
				// create friends array
				JSONArray friends = new JSONArray();
				for (int i = 0; i < friendlist.size(); i++) {
					Friend f = friendlist.get(i);
					// adding elements to json object
					JSONObject cObj = new JSONObject();
					cObj.put("user_id", f.getFriend_id());
					cObj.put("username", f.getFriend_name());
					// adding
					friends.put(cObj);
				}
				response.put("friends", friends);
			} else {
				response.put("success", false);
				response.put("friends", friendlist);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(response.toString()).build();

	}

	@Path("/find_friend")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response findFriend(@QueryParam("id") int id) {
		JSONObject response = new JSONObject();
		ArrayList<Friend> findfriendlist;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			findfriendlist=db.findFriendList(id);
			if (findfriendlist != null) {
				response.put("success", true);
				// create friends array
				JSONArray friends = new JSONArray();
				for (int i = 0; i < findfriendlist.size(); i++) {
					Friend f = findfriendlist.get(i);
					// adding elements to json object
					JSONObject cObj = new JSONObject();
					cObj.put("user_id", f.getFriend_id());
					cObj.put("username", f.getFriend_name());
					// adding elements to friend array
					friends.put(cObj);
				}
				response.put("friends", friends);
			}
			else{
				response.put("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_NO_FRIENDS);
				error.putOpt("message", "no friends available now!");
				response.put("error", error);
			}
			
		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

	@Path("/add_friend")
	@POST
	@Produces("application/json")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addFriend(@FormDataParam("user_id") int user_id,
			@FormDataParam("friend_id") int frined_id){
		JSONObject response = new JSONObject();
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			Friend friend =db.addFriend(user_id,frined_id);
			if (friend != null) {
				response.put("success", true);
					
			}
			else{
				response.put("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_NO_FRIENDS);
				error.putOpt("message", "no friends available now!");
				response.put("error", error);
			}
			
		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}
}
