package com.suxinli.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.suxinli.dao.ArticleDao;

import javafx.util.Pair;

public class Article {
	private static List<Pair<Integer, String>> articleList;
	private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();
	
    
	private int id;
	private String title;
	private String content;
	private Date createTime;
	private int visit;
	private int like;
	
	public Article() {}
	
	public Article(int id, String title, String content, Date createTime, int visit, int like) {
		setId(id);
		setTitle(title);
		setContent(content);
		setCreateTime(createTime);
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
	
	public void setVisit(int visit) { this.visit = visit; }
	public int getVisit() { return visit; }
	
	public void setLike(int like) { this.like = like; }
	public int getLike() { return like; }
	
	public int addArticle() {
		int res = ArticleDao.addArticle(this);
		if(res != -1) {
			w.lock();
			articleList.add(0, new Pair<Integer, String>(res, title));
			w.unlock();
		}
		return res;
		
	}
	
	public static void fetchArticles() {
		articleList = ArticleDao.fetchArticles();
	}
	
	public static Article searchArticle(int id) {
		return ArticleDao.searchArticle(id);
	}
	
	public static List<Pair<Integer, String>> getArticleList( ) {
		return articleList;
	}
	
	public static Lock getReadLock() {
		return r;
	}
	public static Lock getWriteLock() {
		return w;
	}
	
	public static boolean deleteArticle(int id) {
		if(ArticleDao.deleteArticle(id)) {
			w.lock();
			for(int i = 0; i < articleList.size(); ++i) {
					if(articleList.get(i).getKey() == id) {
						articleList.remove(i);
						break;
					}
			}
			w.unlock();
			return true;
		}
		else {
			return false;
		}
	}
	
}

