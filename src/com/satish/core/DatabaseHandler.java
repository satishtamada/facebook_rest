package com.satish.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.satish.global.Config;
import com.satish.helper.PasswordUtils;
import com.satish.model.Comments;
import com.satish.model.FeedPost;
import com.satish.model.Friend;
import com.satish.model.Likes;
import com.satish.model.User;

public class DatabaseHandler {
	private String DRIVER = "com.mysql.jdbc.Driver";
	private String CONNECTION_URL = "jdbc:mysql://localhost:3306/facebook";
	private String USERNAME = "root";
	private String PASSWORD = "root";
	private ArrayList<Comments> comment_list;
	private ArrayList<Friend> friend_list;
	private ArrayList<FeedPost> friendFeedPost;
	private ArrayList<Likes> likes_list;

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
			//preparedStatement.setString(5, profileImage);
			int row = preparedStatement.executeUpdate();
			System.out.println(row);

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
					user.setId(rs.getInt("id"));
					user.setCreated_at(rs.getTimestamp("created_at"));
					//user.setImage(Config.PROFILE_IMAGE_URL+ rs.getString("profile_image"));

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
			// int row = preparedStatement.executeUpdate();
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
					user.setId(rs.getInt("id"));
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
				System.out.println(rs + "" + user_id + ",,," + rs);
				if (rs.last()) {
					FeedPost feedpost = new FeedPost();
					feedpost.setUser_id(rs.getInt("user_id"));
					feedpost.setText(rs.getString("text"));
					feedpost.setPost_id(rs.getInt("id"));
					feedpost.setUsername(rs.getString("username"));
					System.out.println(rs.getInt("user_id"));
					System.out.println(rs.getString("text"));
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

	public ArrayList<Comments> comment(int post_id) {

		try {
			PreparedStatement ps = con
					.prepareStatement("select comment from comments where post_id=?");
			ps.setInt(1, post_id);
			ResultSet rs1 = ps.executeQuery();
			if (rs1.next()) {
				comment_list = new ArrayList<Comments>();
				PreparedStatement preparedStatement = con
						.prepareStatement("select u.name as username,profile_image,c.id as id,c.user1_id as userid,c.comment as comment,c.created_at as created_at from users u,comments c where  c.post_id=? and c.user1_id=u.id");
				preparedStatement.setInt(1, post_id);

				ResultSet rs = preparedStatement.executeQuery();
				// set values for comment model
				while (rs.next()) {
					Comments comment = new Comments();
					comment.setId(rs.getInt("id"));
					comment.setCommented_username(rs.getString("username"));
					comment.setCommented_user_id(rs.getInt("userid"));
					comment.setComment(rs.getString("comment"));
					comment.setCreated_at(rs.getTimestamp("created_at"));
					comment.setProfile_image(rs.getString("profile_image"));
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
					.prepareStatement("select u.id as id,u.name as friendName,u.profile_image as profile_image from users u where u.id in "
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
					friend.setProfile_image(rs.getString("profile_image"));
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
					.prepareStatement(" select u.id as id,u.name as friendName,u.profile_image as profile_image from users u where u.id not in (select f.user1_id from friends f where f.user2_id=? UNION select f.user2_id from friends f where f.user1_id = ?) and u.id != ?");
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
					friend.setProfile_image(rs.getString("profile_image"));
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
					.prepareStatement("select distinct u.id,u.name as name from users u,friends f where u.id not in (select f.user1_id from friends f where f.user2_id=? UNION select f.user2_id from friends f where f.user1_id = ?) and u.id != ? and f.status!=0");
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			preparedStatement.setInt(3, user_id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				PreparedStatement preparedStatement1 = con
						.prepareStatement("insert into friends (user1_id,user2_id,status) values(?,?,0)");
				preparedStatement1.setInt(1, user_id);
				preparedStatement1.setInt(2, frined_id);
				int row = preparedStatement1.executeUpdate();
				if (row > 0) {
					Friend f = new Friend();
					f.setFriend_name(rs.getString("name"));
					return f;
				} else
					return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Likes> postLikes(int post_id) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select u.name as name,u.id as id from users u,likes l where l.post_id=? and l.user_id=u.id and l.like_status=1");
			preparedStatement.setInt(1, post_id);
			ResultSet rs = preparedStatement.executeQuery();
			likes_list = new ArrayList<Likes>();
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					Likes l = new Likes();
					l.setLiked_user_id(rs.getInt("id"));
					l.setLiked_by_name(rs.getString("name"));
					likes_list.add(l);
				}
				return likes_list;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int countLikes(int post_id) {
		int count = 0;
		try {

			PreparedStatement preparedStatement = con
					.prepareStatement("select count(*) as count from users u,likes l where l.post_id=? and l.user_id=u.id and l.like_status=1");
			preparedStatement.setInt(1, post_id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next())
				count = rs.getInt("count");
			System.out.println(rs.getInt("count"));
			return count;
		} catch (Exception e) {
		}
		return count;

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

	public ArrayList<FeedPost> friendsPosts(int user_id) {

		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select image,text,name,profile_image,posts.created_at as created_at from posts,users where"
							+ " users.id=posts.user_id and user_id in(select u.id as id from users u,friends f where u.id in"
							+ "(select f.user2_id from friends f,users u where f.user1_id=? and f.user2_id=u.id)"
							+ "or(select f.user1_id from friends f where f.user2_id=? and f.user1_id=u.id) and ?) order by posts.created_at desc");

			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			preparedStatement.setInt(3, user_id);
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println("result" + rs);
			if (rs.next()) {
				System.out.println("true");
				friendFeedPost = new ArrayList<FeedPost>();
				rs.beforeFirst();
				while (rs.next()) {
					FeedPost friend = new FeedPost();
					friend.setUsername(rs.getString("name"));
					friend.setProfile_image(rs.getString("profile_image"));
					friend.setImage(rs.getString("image"));
					friend.setText(rs.getString("text"));
					friend.setCreated_at(rs.getTimestamp("created_at"));
					System.out.println(rs.getString("name")+"  "+rs.getTimestamp("created_at"));
					friendFeedPost.add(friend);
				}
				return friendFeedPost;
			} else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public User profile(int id) {

		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select name,email,profile_image,created_at from users where id = ?");
			preparedStatement.setInt(1, id);
			// int row = preparedStatement.executeUpdate();
			// check id is existed or not
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setCreated_at(rs.getTimestamp("created_at"));
				user.setImage(rs.getString("profile_image"));
				System.out.println(rs.getString("name") + rs.getString("email")
						+ rs.getString("profile_image")
						+ rs.getTimestamp("created_at"));
				return user;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<FeedPost> posts(int id) {
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("select posts.id as id,user_id,text,image,image_width,image_height,profile_image,posts.created_at as created_at,name from posts,users where users.id=posts.user_id and user_id=?");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println(rs.next());
			if (rs.next()) {
				friendFeedPost = new ArrayList<FeedPost>();
				rs.beforeFirst();
				while (rs.next()) {
					FeedPost friend = new FeedPost();
					friend.setPost_id(rs.getInt("id"));
					friend.setUser_id(rs.getInt("user_id"));
					friend.setText(rs.getString("text"));
					friend.setImage(rs.getString("image"));
					friend.setUsername(rs.getString("name"));
					friend.setCreated_at(rs.getTimestamp("created_at"));
					friend.setProfile_image(rs.getString("profile_image"));
					System.out.println(rs.getString("image")+"gg");
					friendFeedPost.add(friend);
				}
				return friendFeedPost;
			} else
				return null;
		} catch (Exception e) {

		}
		return null;
	}

}
