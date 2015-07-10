package com.satish.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.User;

@Path("/user")
public class Authentication {

	@Path("/register")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response registerUser(@FormParam("name") String name,
			@FormParam("email") String email,
			@FormParam("password") String password) {
		System.out.println(name + "," + email + password);

		JSONObject response = new JSONObject();
		try {
			DatabaseHandler db = new DatabaseHandler();
			db.connect();

			if (!db.isUserExisted(email)) {
				User user = db.createUser(name, email, password);

				if (user != null) {
					response.put("success", true);
					response.put("error", "null");

					JSONObject profile = new JSONObject();
					profile.put("name", user.getName());
					profile.put("email", user.getEmail());
					profile.put("apikey", user.getApi_key());

					response.put("profile", profile);

				} else {
					response.put("success", false);

					JSONObject error = new JSONObject();
					error.put("code", Config.ERROR_UNKNOWN);
					error.putOpt("message", "Failed to create user");

					response.put("error", error);
				}
			} else {
				// user existed already

				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_EMAIL_EXISTED);
				error.putOpt("message", "Sorry! Email is already existed");

				response.put("error", error);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(response.toString()).build();

	}

	@Path("/login")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response loginUser(@FormParam("email") String email,
			@FormParam("password") String password) {
		System.out.println(email + password);
		JSONObject response = new JSONObject();
		try {
			DatabaseHandler db = new DatabaseHandler();
			db.connect();
			User user = db.loginUser(email, password);
			if (user != null) {
				response.put("success", true);
				response.put("error", "null");
				JSONObject profile = new JSONObject();
				profile.put("name", user.getName());
				profile.put("email", user.getEmail());
				profile.put("apikey", user.getApi_key());
				response.put("profile", profile);

			} else {
				response.put("success", false);

				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_VALID_EMAIL_PASSWORD);
				error.putOpt("message",
						"Please enter valid email and password!");

				response.put("error", error);
			}
		} catch (Exception e) {
		}
		return Response.status(200).entity(response.toString()).build();
	}

}
