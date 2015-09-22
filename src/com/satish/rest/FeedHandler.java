package com.satish.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

import com.satish.core.DatabaseHandler;
import com.satish.global.Config;
import com.satish.model.FeedPost;
import com.satish.model.User;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/feed")
public class FeedHandler {
	@Path("/create")
	@POST
	@Produces("application/json")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response userPost(@FormDataParam("user_id") int user_id,
			@FormDataParam("text") String text,
			@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition fileDetail) {
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
			uploadedFileLocation = "/home/satish/Eclipseworkspace/facebook_rest/WebContent/images/"
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
		JSONObject response = new JSONObject();
		try {
			// connect database
			DatabaseHandler db = new DatabaseHandler();
			FeedPost feedpost = db.feedPost(user_id, text, imageName, width,
					height);
			if (feedpost != null) {
				response.put("success", true);
				response.put("by", feedpost.getUsername());
				JSONObject post = new JSONObject();
				post.put("id", feedpost.getPost_id());
				post.put("text", feedpost.getText());
				post.put("image", feedpost.getImage());
				if (feedpost.getWidth() != 0) {
					post.put("width", feedpost.getWidth());
					post.put("height", feedpost.getHeight());
				}
				post.put("created_at", feedpost.getCreated_at());
				response.put("post", post);

			} else {
				response.put("success", false);
				JSONObject error = new JSONObject();
				error.put("code", Config.ERROR_UNKNOWN);
				error.putOpt("message", "Unknown error!");
				response.put("error", error);
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
