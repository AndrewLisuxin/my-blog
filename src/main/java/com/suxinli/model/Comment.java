package com.suxinli.model;

import java.util.Date;
import java.util.List;

import com.suxinli.dao.*;

import javafx.util.Pair;
public class Comment {
	private int id;
	private String content;
	private Date createTime;
	private User user;
	private Article article;
	
	public Comment() {}
	public Comment(int id, String content, Date createTime, User user, Article article) {
		setId(id);
		setContent(content);
		setCreateTime(createTime);
		setUser(user);
		setArticle(article);
	}
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	
	public void setContent(String content) { this.content = content; }
	public String getContent() { return content; }
	
	public void setCreateTime(Date createTime) { this.createTime = createTime; }
	public Date getCreateTime() { return createTime; }
	
	public void setUser(User user) { this.user = user; }
	public User getUser() { return user; }
	
	public void setArticle(Article article) { this.article = article; }
	public Article getArticle() { return article; }
	
	public boolean addComment() {
		return CommentDao.addComment(this);
	}
	
	public static List<Comment> SearchCommentsByArticle(Article article) {
		return CommentDao.SearchCommentsByArticle(article);
	}
	
	public static boolean deleteComment(int id) {
		return CommentDao.deleteComment(id);
	}
}
