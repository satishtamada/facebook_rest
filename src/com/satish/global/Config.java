package com.satish.global;

public class Config {
	public static final int ERROR_UNKNOWN = 111;
	public static final int ERROR_EMAIL_EXISTED = 100;
	public static final int ERROR_VALID_EMAIL_PASSWORD=101;
	public static final int ERROR_NO_FRIENDS=102;
	public static final int ERROR_NO_COMMENTS=103;
	public static final int ERROR_FRIEND_EXISTED=104;
	public static final int ERROR_NO_NOTIFICATIONS=105;
	public static final String URL="http://192.168.0.100:8080/facebook_rest/";
	public static final String IMAGE_SOURCE_FILE=URL+"images/";
	public static final String PROFILE_IMAGE_URL=URL+"images/profileImages/";
	public static final String PROFILE_IAMGE_DEFAULT=URL+"images/profileImages/profile_selected.jpg";
	public static final int NOTIFICATION_COMMENT = 1;
	
	
	//Parse config
	public static final String PARSE_API_ID = "3M4IbwBiVGDiJLLRWbaUWDbBujhyI4mj0R2U7Fvg";
	public static final String PARSE_CLIENT_KEY = "y9wSfJF9JBQiE86rxOJ99v0EZ7d9LnAasujGN8v7";
	
	
	public static final String PUSH_MESSAGE_NEW_COMMENT = "<b> #name#</b> has commented on your post. \"#comment# ...\"";
}
