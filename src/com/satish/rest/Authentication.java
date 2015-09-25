package com.satish.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.FeedPost;
import com.satish.model.User;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/user")
public class Authentication {

	@Path("/register")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// /@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response registerUser(@FormParam("name") String name,
			@FormParam("email") String email,
			@FormParam("password") String password) {
		System.out.println(name + "," + email + password);

		// creating json response

		JSONObject response = new JSONObject();
		try {
			DatabaseHandler db = new DatabaseHandler();
			

			if (!db.isUserExisted(email)) {
				User user = db.createUser(name, email, password);

				if (user != null) {
					response.put("success", true);
					response.put("error", "null");
					JSONObject profile = new JSONObject();
					profile.put("name", user.getName());
					profile.put("email", user.getEmail());
					profile.put("apikey", user.getApi_key());
					profile.put("id", user.getId());
					profile.put("created_at", user.getCreated_at());
					if (user.getImage()== null)
						profile.put("profile_image",
								Config.PROFILE_IAMGE_DEFAULT);
					else
						profile.put("profile_image",
								Config.PROFILE_IMAGE_URL +user.getImage());
					profile.put("created_at", user.getCreated_at());

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
				response.put("success", false);
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

	// login class
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
				User user = db.loginUser(email, password);
			if (user != null) {
				response.put("success", true);
				response.put("error", "null");
				JSONObject profile = new JSONObject();
				profile.put("name", user.getName());
				profile.put("email", user.getEmail());
				profile.put("apikey", user.getApi_key());
				profile.put("id", user.getId());
				if (user.getImage()== null)
					profile.put("profile_image",
							Config.PROFILE_IAMGE_DEFAULT);
				else
					profile.put("profile_image",
							Config.PROFILE_IMAGE_URL +user.getImage());
				profile.put("created_at", user.getCreated_at());
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

	// profile upload
	@Path("/profile_upload")
	@POST
	@Produces("application/json")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response userPost(
			@FormDataParam("user_id") int user_id,
			@FormDataParam("profile_image") InputStream uploadedInputStream,
			@FormDataParam("profile_image") FormDataContentDisposition fileDetail) {
		System.out.println("in authentication"+fileDetail.getFileName());
		String imageName = "";
		String uploadedFileLocation = "";
		int width = 0;
		int height = 0;
		// if user post a image
		if (uploadedInputStream != null) {
			imageName = new SimpleDateFormat("yyyyMMddhhmmss")
					.format(new Date())
					+ "."
					+ FilenameUtils.getExtension(fileDetail.getFileName());
			uploadedFileLocation = "/home/satish/Eclipseworkspace/facebook_rest/WebContent/images/profileImages/"
					+ imageName;

			writeToFile(uploadedInputStream, uploadedFileLocation);

			try {
				File f = new File(uploadedFileLocation);
				BufferedImage image = ImageIO.read(f);
				height = image.getHeight();
				width = image.getWidth();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		}
		// creating json object
		JsonObject response = new JsonObject();
		boolean uploadImage;
		try {
					DatabaseHandler db = new DatabaseHandler();
			
			uploadImage = db.uploadProfile(user_id, imageName, width,
					height);
			if (uploadImage) {
				response.addProperty("success", true);
				response.add("error", null);
			} else {
				response.addProperty("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_UNKNOWN);
				error.putOpt("message", "Server busy....!");
				response.addProperty("error", error.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(response.toString()).build();
	}

	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			// out.flush();
			// out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
