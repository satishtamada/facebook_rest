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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.FeedPost;
import com.satish.model.Friend;
import com.satish.model.User;

@Path("/friend")
public class FriendsHandler {
	@Path("/friends_list")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response friendsList(@QueryParam("id") int id) {
		ArrayList<Friend> friendlist;
		Gson gson = new Gson();
		JsonObject response = new JsonObject();
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			friendlist = db.friendsList(id);
			if (friendlist != null) {
				response.addProperty("success", true);
				// create friends array
				JsonArray friends = new JsonArray();
				for (int i = 0; i < friendlist.size(); i++) {
					Friend f = friendlist.get(i);

					// adding elements to json object
					JsonObject cObj = new JsonObject();
					cObj.addProperty("user_id", f.getFriend_id());
					cObj.addProperty("username", f.getFriend_name());
					if (f.getProfile_image() == null)
						cObj.addProperty("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					else
						cObj.addProperty("profile_image",
								Config.PROFILE_IMAGE_URL + f.getProfile_image());
					friends.add(cObj);
				}
				response.add("friends", friends);
			} else {
				response.addProperty("success", false);
				response.addProperty("friends", gson.toJson(friendlist));

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
		JsonObject response = new JsonObject();
		Gson gson = new Gson();
		ArrayList<Friend> findfriendlist;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			findfriendlist = db.findFriendList(id);
			if (findfriendlist != null) {
				response.addProperty("success", true);
				// create friends array
				JsonArray friends = new JsonArray();
				for (int i = 0; i < findfriendlist.size(); i++) {
					Friend f = findfriendlist.get(i);
					// adding elements to json object
					JsonObject cObj = new JsonObject();
					cObj.addProperty("user_id", f.getFriend_id());
					cObj.addProperty("username", f.getFriend_name().trim());
					if (f.getProfile_image() == null)
						cObj.addProperty("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					else
						cObj.addProperty("profile_image",
								Config.PROFILE_IMAGE_URL + f.getProfile_image());
					// adding elements to friend array
					friends.add(cObj);
				}
				response.add("friends", friends);
			} else {
				response.addProperty("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_NO_FRIENDS);
				error.putOpt("message", "no friends available now!");
				response.addProperty("error", gson.toJson(error));
			}

		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

	@Path("/profile")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response profile(@QueryParam("id") int id) {
		JsonObject response = new JsonObject();
		int friendsCount = 0;
		int userPosts = 0;
		ArrayList<Friend> friendlist;
		ArrayList<FeedPost> posts;
		JsonArray imagesArray = null;
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			User user = db.profile(id);
			friendlist = db.friendsList(id);
			posts = db.posts(id);
			if (friendlist != null)
				friendsCount = friendlist.size();
			// user post Images and posts count
			if (posts != null) {
				userPosts = posts.size();
				imagesArray = new JsonArray();
				for (int i = 0; i < userPosts; i++) {
					FeedPost f = posts.get(i);
					JsonObject cObj = new JsonObject();

					if (f.getImage().length() != 0)
						cObj.addProperty("image",
								Config.IMAGE_SOURCE_FILE + f.getImage());
					else
						cObj.addProperty("image","");
					cObj.addProperty("post_id", f.getPost_id());
					cObj.addProperty("text", f.getText());
					cObj.addProperty("created_at", f.getCreated_at().toString());
					imagesArray.add(cObj);
				}
			}
			// create json for user profile
			if (user != null) {
				response.addProperty("success", true);
				JsonObject profile = new JsonObject();
				profile.addProperty("name", user.getName());
				profile.addProperty("email", user.getEmail());
				profile.addProperty("created_at", user.getCreated_at()
						.toString());
				profile.addProperty("friends_count", friendsCount);
				profile.addProperty("posts_count", userPosts);
				if (user.getImage() == null)
					profile.addProperty("profile_image",
							Config.PROFILE_IAMGE_DEFAULT);
				else
					profile.addProperty("profile_image",
							Config.PROFILE_IMAGE_URL + user.getImage());

				response.add("profile", profile);
				response.add("posts", imagesArray);
			} else {
				response.addProperty("success", false);
				JsonObject error = new JsonObject();
				error.addProperty("code",
						Integer.toString(Config.ERROR_UNKNOWN));
				error.addProperty("message", "server busy");
				response.add("error", error);
			}

		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

	@Path("/user_posts")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response posts(@QueryParam("id") int id) {
		Gson gson = new Gson();
		JsonObject response = new JsonObject();
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			ArrayList<FeedPost> posts = db.posts(id);
			if (posts != null) {
				response.addProperty("success", true);
				JsonArray friends = new JsonArray();
				for (int i = 0; i < posts.size(); i++) {
					FeedPost f = posts.get(i);
					// adding elements to json object
					JsonObject cObj = new JsonObject();
					cObj.addProperty("username", f.getUsername());
					cObj.addProperty("text", f.getText());
					cObj.addProperty("post_id", f.getPost_id());

					if (f.getProfile_image() != null)
						cObj.addProperty("profile_image",
								Config.PROFILE_IMAGE_URL + f.getProfile_image());
					else
						cObj.addProperty("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					if (f.getImage().length() != 0)
						cObj.addProperty("image",
								Config.IMAGE_SOURCE_FILE + f.getImage());
					else
						cObj.addProperty("image", "");
					cObj.addProperty("created_at", f.getCreated_at().toString());

					friends.add(cObj);
				}
				response.add("posts", friends);
			} else {
				response.addProperty("success", false);
				JsonObject error = new JsonObject();
				error.addProperty("code", Config.ERROR_UNKNOWN);
				error.addProperty("message", "server busy");
				response.add("error", error);
			}

		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

	@Path("/friend_posts")
	@GET
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response frinedPosts(@QueryParam("id") int id) {
		Gson gson = new Gson();
		JsonObject response = new JsonObject();
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			User user = db.profile(id);
			ArrayList<FeedPost> posts = db.friendsPosts(id);
			if (posts != null) {
				response.addProperty("success", true);
				response.addProperty("username", user.getName());
				if (user.getImage() == null)
					response.addProperty("profile_image",
							Config.PROFILE_IAMGE_DEFAULT);
				else
					response.addProperty("profile_image",
							Config.PROFILE_IMAGE_URL + user.getImage());
				System.out.println(posts.get(1).getText());
				JsonArray friends = new JsonArray();
				for (int i = 0; i < posts.size(); i++) {
					FeedPost f = posts.get(i);
					// adding elements to json object
					JsonObject cObj = new JsonObject();
					cObj.addProperty("username", f.getUsername());
					// checking if user not upload his profile image
					if (f.getProfile_image() == null)
						cObj.addProperty("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					else
						cObj.addProperty("profile_image",
								Config.PROFILE_IMAGE_URL + f.getProfile_image());
					cObj.addProperty("text", f.getText());
					if (f.getImage().length() == 0)
						cObj.addProperty("image", f.getImage());
					else
						cObj.addProperty("image",
								Config.IMAGE_SOURCE_FILE + f.getImage());
					cObj.addProperty("created_at",
							gson.toJson(f.getCreated_at()));
					friends.add(cObj);
				}
				response.add("posts", friends);
			} else {
				response.addProperty("success", false);
				JsonObject error = new JsonObject();
				error.addProperty("code", Config.ERROR_UNKNOWN);
				error.addProperty("message", "server busy");
				response.addProperty("error", gson.toJson(error));
			}

		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

	@Path("/add_friend")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addFriend(@FormParam("user_id") int user_id,
			@FormParam("friend_id") int frined_id) {
		JSONObject response = new JSONObject();
		try {
			// connecting database
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			Friend f = db.addFriend(user_id, frined_id);
			if (f != null) {
				response.put("success", true);
				response.put("message",
						"your request sent " + f.getFriend_name());
			} else {
				response.put("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_UNKNOWN);
				error.putOpt("message", "unknown error!");
				response.put("error", error);
			}

		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}
}
