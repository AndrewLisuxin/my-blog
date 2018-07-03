package com.suxinli.model;

import com.suxinli.dao.UserDao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import javafx.util.*;
public class User implements HttpSessionBindingListener {
	/* avoid multiple-device log in one account
	 * 
	 * only the first log in of every account valid, 
	 * 
	 * cons: if a logged user close the browser before log out, all users need to wait until 
	 * the session expire include the original user. (auto log in does NOT work in the duration)
	 * ---> so the session MaxInactiveInterval should be small.
	 *  */
	private static Map<String, HttpSession> logins = new ConcurrentHashMap<String, HttpSession>();
	
	private boolean logged = false;
	
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
	
	public static User checkUser(String email, String password) {
		return UserDao.checkUser(email, password);
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
	
	public void updateImage() {
		UserDao.updateImage(this);
	}
	
	public void updateUser() {
		UserDao.updateUser(this);
	}
	
	public boolean isLogged() {
		return logged;
	}
	
	public void login() {
		assert logged == false;
		logged = true;
	}
	public static Map<String, HttpSession> getLoginUsers() {
		return logins;
	}
	
	
	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		/* after this user is added into a session */
		synchronized(logins) {
			if(logins.containsKey(email)) {
				logged = true;
			}
			else {
				logins.put(email, arg0.getSession());
			}
		}
		
		
	}
	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		/* before this user is removed from a session */
		if(arg0.getSession() == logins.get(email)) {
			logins.remove(email);
			logged = false;
		}
		
		
		
		
		
	}
}
