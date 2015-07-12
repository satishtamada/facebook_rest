package com.satish.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.satish.global.Config;
import com.satish.helper.PasswordUtils;
import com.satish.model.Comment;
import com.satish.model.FeedPost;
import com.satish.model.Friend;
import com.satish.model.User;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class DatabaseHandler {
	private String DRIVER = "com.mysql.jdbc.Driver";
	private String CONNECTION_URL = "jdbc:mysql://localhost:3306/facebook";
	private String USERNAME = "root";
	private String PASSWORD = "root";
	private ArrayList<Comment> comment_list;
	private ArrayList<Friend> friend_list;

	private Connection con;

	public DatabaseHandler() {

	}

	public boolean connect() {

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/facebook", "root", "root");

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public User createUser(String name, String email, String password) {

		try {

			// user not existed

			String password_hash = PasswordUtils.getSaltedHash(password);
			String api_key_value = UUID.randomUUID().toString()
					.replaceAll("-", "");
			// insert new user
			PreparedStatement preparedStatement = con
					.prepareStatement("insert into users(name, email, password_hash,api_key) values(?,?,?,?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password_hash);
			preparedStatement.setString(4, api_key_value);

			int row = preparedStatement.executeUpdate();

			if (row > 0) {
				// get the newly inserted user
				preparedStatement = con
						.prepareStatement("select *from users where email = ?");
				preparedStatement.setString(1, email);

				ResultSet rs = preparedStatement.executeQuery();
				// checking email exited or not

				if (rs.next()) {
					User user = new User();
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setApi_key(rs.getString("api_key"));

					return user;
				} else {
					return null;
				}

			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public User loginUser(String email, String password) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select *from users where email = ?");
			preparedStatement.setString(1, email);
			int row = preparedStatement.executeUpdate();
			// check id is existed or not
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				boolean flag = PasswordUtils.check(password,
						rs.getString("password_hash"));
				if (flag) {
					User user = new User();
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setApi_key(rs.getString("api_key"));
					return user;
				} else {
					return null;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public FeedPost feedPost(int user_id, String postText, String imageName,
			int width, int height) {

		try {

			PreparedStatement preparedStatement = con
					.prepareStatement("insert into posts(user_id, text,image,image_width,image_height) values(?,?,?,?,?)");
			preparedStatement.setInt(1, user_id);
			preparedStatement.setString(2, postText);
			preparedStatement.setString(3, imageName);
			preparedStatement.setInt(4, width);
			preparedStatement.setInt(5, height);
			int row = preparedStatement.executeUpdate();

			if (row > 0) {
				// fetch post data
				preparedStatement = con
						.prepareStatement("select u.name as username,p.* from users u,posts p where u.id= ?");
				preparedStatement.setInt(1, user_id);
				ResultSet rs = preparedStatement.executeQuery();

				if (rs.last()) {
					FeedPost feedpost = new FeedPost();
					feedpost.setUser_id(rs.getInt("user_id"));
					feedpost.setText(rs.getString("text"));
					feedpost.setPost_id(rs.getInt("id"));
					feedpost.setUsername(rs.getString("username"));
					System.out.println(rs.getString("image").length() != 0);
					feedpost.setCreated_at(rs.getTimestamp("created_at"));
					// check if user upload image
					if (rs.getString("image").length() != 0) {
						feedpost.setImage(Config.IMAGE_SOURCE_FILE
								+ rs.getString("image"));
						feedpost.setWidth(rs.getInt("image_width"));
						feedpost.setHeight(rs.getInt("image_height"));
					}
					return feedpost;

				} else {
					return null;
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Comment> comment(int post_id) {

		try {
			PreparedStatement ps = con
					.prepareStatement("select comment from comments where post_id=?");
			ps.setInt(1, post_id);
			ResultSet rs1 = ps.executeQuery();
			if (rs1.next()) {
				comment_list = new ArrayList<Comment>();
				PreparedStatement preparedStatement = con
						.prepareStatement("select u.name as username,c.id as id,c.user1_id as userid,c.comment as comment,c.created_at as created_at from users u,comments c where  c.post_id=? and c.user1_id=u.id");
				preparedStatement.setInt(1, post_id);

				ResultSet rs = preparedStatement.executeQuery();
				// set values for comment model
				while (rs.next()) {
					Comment comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setCommented_username(rs.getString("username"));
					comment.setCommented_user_id(rs.getInt("userid"));
					comment.setComment(rs.getString("comment"));
					comment.setCreated_at(rs.getTimestamp("created_at"));
					comment_list.add(comment);

					System.out.println(rs.getString("username") + ","
							+ rs.getInt("userid") + ","
							+ rs.getString("comment"));

				}
				System.out.println(comment_list);
				return comment_list;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Friend> friendsList(int user_id) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select u.id as id,u.name as friendName from users u where u.id in "
							+ "(select f.user2_id from friends f,users u where f.user1_id = ? and f.user2_id = u.id) "
							+ "or (select f.user1_id from friends f where f.user2_id=? and f.user1_id = u.id)");

			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				friend_list = new ArrayList<Friend>();
				rs.beforeFirst();
				while (rs.next()) {
					Friend friend = new Friend();
					friend.setFriend_id(rs.getInt("id"));
					friend.setFriend_name(rs.getString("friendName"));
					friend_list.add(friend);
				}
				return friend_list;
			} else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Friend> findFriendList(int user_id) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement(" select u.id as id,u.name as friendName from users u where u.id not in (select f.user1_id from friends f where f.user2_id=? UNION select f.user2_id from friends f where f.user1_id = ?) and u.id != ?");
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			preparedStatement.setInt(3, user_id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				friend_list = new ArrayList<Friend>();
				rs.beforeFirst();
				while (rs.next()) {
					Friend friend = new Friend();
					friend.setFriend_id(rs.getInt("id"));
					friend.setFriend_name(rs.getString("friendName"));
					friend_list.add(friend);
				}

				return friend_list;
			} else
				return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Friend addFriend(int user_id, int frined_id) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select distinct u.id,u.name from users u,friends f where u.id not in (select f.user1_id from friends f where f.user2_id=? UNION select f.user2_id from friends f where f.user1_id = ?) and u.id != ? and f.status!=0");
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			preparedStatement.setInt(3, user_id);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
			Prepared
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean isUserExisted(String email) {
		try {
			PreparedStatement ps1 = con
					.prepareStatement("select email from users where email = ?");

			ps1.setString(1, email);

			ResultSet rs1 = ps1.executeQuery();

			// condition to check if result set contains any data
			if (rs1.isBeforeFirst()) {
				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}

}
