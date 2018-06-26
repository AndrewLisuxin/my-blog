package com.suxinli.model;

import com.suxinli.dao.UserDao;

public class User {
	private int id;
	private String email;
	private String username;
	private String password;
	private String image;
	private String city;
	
	public User() {}
	public User(int id, String email, String username, String password, String city) {
		setId(id);
		setEmail(email);
		setUsername(username);
		setPassword(password);
		setCity(city);
	}
	public User(String email, String username, String password, String city) {
		setEmail(email);
		setUsername(username);
		setPassword(password);
		setCity(city);
	}

	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	
	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return email; }
	
	public void setUsername(String username) { this.username = username; }
	public String getUsername() { return username; }
	
	public void setPassword(String password) { this.password = password; }
	public String getPassword() { return password; }
	
	public void setImage(String image) { this.image = image; }
	public String getImage() { return image; }
	
	public void setCity(String city) { this.city = city; }
	public String getCity() { return city; }
	
	public static User login(String email, String password) {
		return UserDao.login(email, password);
	}
	public static User searchUser(int id) {
		return UserDao.searchUser(id);
	}
	public int signup() {
		int id = UserDao.signup(this);
		if(id != -1) {
			setId(id);
		}
		return id;
	}
	
	public boolean updateImage() {
		return UserDao.updateImage(this);
	}
	
	public boolean updateUser() {
		return UserDao.updateUser(this);
	}
}
