package com.suxinli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.suxinli.model.User;
import com.suxinli.orm.BaseDao;
import com.suxinli.orm.Operation;
import com.suxinli.orm.TransactionalOperation;

public class UserDao extends BaseDao {
	public static User getUser(final String email) {
		return execute(new Operation<User>() {
			public User doOperation(Connection connection) {
				User user = null;
				try {
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email=?");
					statement.setString(1, email);
					ResultSet res = statement.executeQuery();
					if(res.next()) {
						user = loadUser(res);
					}
					
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
				return user;
			}
		});
	}
	
	public static User login(final String email, final String password) {
		return execute(new Operation<User>() {
			public User doOperation(Connection connection) {
				User user = null;
				try {
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
					statement.setString(1, email);
					statement.setString(2, password);
					ResultSet res = statement.executeQuery();
					if(res.next()) {
						user = loadUser(res);
					}
					
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
				return user;
			}
		});
	}
	
	public static Integer signup(final User user) {
		return execute(new Operation<Integer>() {
			public Integer doOperation(Connection connection) {
				if(getUser(user.getEmail()) != null) {
					return -1;
				} 
				try {
					System.out.println("here1");
					PreparedStatement statement = connection.prepareStatement("INSERT INTO users(email, username, password, city) VALUES (?, ?, ?, ?)");
					statement.setString(1, user.getEmail());
					System.out.println(user.getEmail());
					statement.setString(2, user.getUsername());
					System.out.println(user.getUsername());
					statement.setString(3, user.getPassword());
					System.out.println(user.getPassword());
					statement.setString(4, user.getCity());
					System.out.println(user.getCity());
					System.out.println(statement.executeUpdate());
					System.out.println("here2");
					return getUser(user.getEmail()).getId();
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
				
			}
		});
	}
	
	public static User searchUser(final int id) {
		return execute(new Operation<User>() {
			public User doOperation(Connection connection) {
				User user = null;
				try {
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
					statement.setInt(1, id);
					ResultSet res = statement.executeQuery();
					if(res.next()) {
						user = loadUser(res);
					}
					
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
				return user;
			}
		});
	}
	
	
	private static User loadUser(ResultSet res) throws SQLException {
		User user = new User();
		user.setId(res.getInt("id"));
		user.setEmail(res.getString("email"));
		user.setUsername(res.getString("username"));
		user.setPassword(res.getString("password"));
		user.setImage(res.getString("image"));
		System.out.println(res.getString("image"));
		
		user.setCity(res.getString("city"));
		return user;
	}
	
	public static Boolean updateImage(final User user) {
		return execute(new Operation<Boolean>() {
			public Boolean doOperation(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("UPDATE users SET image=? WHERE id=?");
					statement.setString(1, user.getImage());
					statement.setInt(2, user.getId());
					statement.executeUpdate();
					return true;
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
				
			}
		});
	}
	
	public static Boolean updateUser(final User user) {
		return execute(new Operation<Boolean>() {
			public Boolean doOperation(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("UPDATE users SET username=?, password=?, city=? WHERE id=?");
					statement.setString(1, user.getUsername());
					statement.setString(2, user.getPassword());
					statement.setString(3, user.getCity());
					statement.setInt(4, user.getId());
					statement.executeUpdate();
					return true;
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
				
			}
		});
	}
}
