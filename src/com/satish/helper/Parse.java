package com.satish.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.satish.global.Config;

public class Parse {

	public Parse() {
		
	}
	
	public void sendPushNotification(String email, String json) throws Exception {
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	    HttpResponse response = null;
	    HttpEntity entity = null;
	    String responseString = null;
	    HttpPost httpost = new HttpPost("https://api.parse.com/1/push"); 
	    httpost.addHeader("X-Parse-Application-Id", Config.PARSE_API_ID);
	    httpost.addHeader("X-Parse-REST-API-Key", Config.PARSE_CLIENT_KEY);
	    httpost.addHeader("Content-Type", "application/json");
	    
	    JSONObject jObj = new JSONObject();
	    
	    String[] channel = new String[]{email.replace("@", "at").replace(".", "dot")};
	    jObj.put("channels", channel);
	    
	    jObj.put("data", json);
	    
	    System.out.println("JSON: " + jObj.toString());
	    
	    StringEntity reqEntity = new StringEntity(jObj.toString());
	    httpost.setEntity(reqEntity);
	    response = httpclient.execute(httpost);
	    entity = response.getEntity();
	    if (entity != null) {
	         responseString = EntityUtils.toString(response.getEntity());  
	    }

	    System.out.println("Parse respone: " + responseString);
	}

}