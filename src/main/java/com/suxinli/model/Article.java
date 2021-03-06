package com.suxinli.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.suxinli.dao.ArticleDao;

import javafx.util.Pair;

public class Article {

	
    
	private int id;
	private String title;
	private String content;
	private Date createTime;
	private Date lastUpdateTime;
	private int visit;
	private int like;
	
	public Article() {}
	
	public Article(int id, String title, String content, Date createTime, Date lastUpdateTime, int visit, int like) {
		setId(id);
		setTitle(title);
		setContent(content);
		setCreateTime(createTime);
		setLastUpdateTime(lastUpdateTime);
		setVisit(visit);
		setLike(like);
	}
	
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }
	
	public void setContent(String content) { this.content = content; }
	public String getContent() { return content; }
	
	public void setCreateTime(Date createTime) { this.createTime = createTime; }
	public Date getCreateTime() { return createTime; }
	
	public void setLastUpdateTime(Date lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }
	public Date getLastUpdateTime() { return lastUpdateTime; }
	
	public void setVisit(int visit) { this.visit = visit; }
	public int getVisit() { return visit; }
	
	public void setLike(int like) { this.like = like; }
	public int getLike() { return like; }
	
	public void addArticle() {
		int res = ArticleDao.addArticle(this);
		setId(res);
		
		
		
		
	}
	
	public static List<Pair<Integer, String>> fetchArticles() {
		return ArticleDao.fetchArticles();
	}
	
	public static Article searchArticle(int id) {
		return ArticleDao.searchArticle(id);
	}
	
	
	
	public static void deleteArticle(int id) {
		ArticleDao.deleteArticle(id);		
	}
	
	public static void incrementView(int id) {
		ArticleDao.incrementView(id);
	}
	
	public void incrementLike() {
		ArticleDao.incrementLike(this);
	}
	
	public void updateArticle() {
		ArticleDao.updateArticle(this);
	}
}

