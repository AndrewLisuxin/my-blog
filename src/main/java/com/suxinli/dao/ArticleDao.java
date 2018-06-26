package com.suxinli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.suxinli.model.Article;
import com.suxinli.orm.BaseDao;
import com.suxinli.orm.Operation;
import com.suxinli.orm.TransactionalOperation;

import javafx.util.Pair;

public class ArticleDao extends BaseDao {
	/**
	 * @return the id of the article if the insertion success, otherwise -1
	 * */
	public static Integer addArticle(final Article article) {
		return execute(new TransactionalOperation<Integer>() {
			public Integer doOperation(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("INSERT INTO articles(title, content) VALUEs(?, ?)");
					statement.setString(1, article.getTitle());
					statement.setString(2, article.getContent());
					statement.executeUpdate();
					Statement statement2 = connection.createStatement();
					ResultSet res = statement2.executeQuery("SELECT LAST_INSERT_ID()");
					if(res.next()) {
						return res.getInt(1);
					}
					return -1;
				} catch(SQLException e) {
					e.printStackTrace();
					return -1;
				}
			}
		});
	}
	
	public static List<Pair<Integer, String>> fetchArticles() {
		return execute(new Operation<List<Pair<Integer, String>>>() {
			public List<Pair<Integer, String>> doOperation(Connection connection) {
				List<Pair<Integer, String>> articleList = new LinkedList<Pair<Integer, String>>();
				try {
					Statement statement = connection.createStatement();
					ResultSet res = statement.executeQuery("SELECT id, title FROM articles ORDER BY id DESC");
					while(res.next()) {
						articleList.add(new Pair<Integer, String> (res.getInt("id"), res.getString("title")));
					}
					return articleList;
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}) ;
	}
	
	public static Article searchArticle(final int id) {
		return execute(new Operation<Article>() {
			public Article doOperation(Connection connection) {
				Article article = null;
				try {
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM articles WHERE id=?");
					statement.setInt(1, id);
					ResultSet res = statement.executeQuery();
					if(res.next()) {
						article = new Article(res.getInt("id"), 
										      res.getString("title"), 
										      res.getString("content"), 
										      res.getTimestamp("create_time"), 
										      res.getInt("visit_time"), 
										      res.getInt("like_time"));
					}
					return article;
				} catch(SQLException e) {
					e.printStackTrace();
				} 
				return null;
				
			}
		}) ;
	}
	
	public static Boolean deleteArticle(final int id) {
		return execute(new TransactionalOperation<Boolean>() {
			public Boolean doOperation(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("DELETE FROM articles WHERE id=?");
					statement.setInt(1, id);
					statement.executeUpdate();
					return true;
				} catch(SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		});
	}
}
