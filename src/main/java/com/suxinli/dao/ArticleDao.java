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
			public Integer doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("INSERT INTO articles(title, content) VALUEs(?, ?)");
					statement.setString(1, article.getTitle());
					statement.setString(2, article.getContent());
					statement.executeUpdate();
					Statement statement2 = connection.createStatement();
					ResultSet res = statement2.executeQuery("SELECT LAST_INSERT_ID()");
					res.next();
					return res.getInt(1);
					
				} catch(SQLException e) {
					throw new SQLException("Create article failed!", e);
				}
					
				
			}
		});
	}
	
	public static List<Pair<Integer, String>> fetchArticles() {
		return execute(new Operation<List<Pair<Integer, String>>>() {
			public List<Pair<Integer, String>> doOperation(Connection connection) throws SQLException {
				try {
					List<Pair<Integer, String>> articleList = new LinkedList<Pair<Integer, String>>();
					
					Statement statement = connection.createStatement();
					ResultSet res = statement.executeQuery("SELECT id, title FROM articles ORDER BY id DESC");
					while(res.next()) {
						articleList.add(new Pair<Integer, String> (res.getInt("id"), res.getString("title")));
					}
					return articleList;
				} catch(SQLException e) {
					throw new SQLException("Fetch article list failed!", e);
				}
				
				
			}
		}) ;
	}
	
	public static Article searchArticle(final int id) {
		return execute(new Operation<Article>() {
			public Article doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM articles WHERE id=?");
					statement.setInt(1, id);
					ResultSet res = statement.executeQuery();
					if(res.next()) {
						Article article = new Article(res.getInt("id"), 
							                  res.getString("title"), 
										      res.getString("content"), 
											  res.getTimestamp("create_time"), 
											  res.getTimestamp("last_update_time"),
											  res.getInt("visit_time"), 
											  res.getInt("like_time"));
						return article;
					}
					throw new SQLException("the article does not exist!!");
				} catch(SQLException e) {
					throw new SQLException("search article failed!", e);
				}
				
					
			}
		}) ;
	}
	
	public static Void deleteArticle(final int id) {
		return execute(new Operation<Void>() {
			public Void doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("DELETE FROM articles WHERE id=?");
					statement.setInt(1, id);
					if(statement.executeUpdate() > 0) {
						return null;
					}
					throw new SQLException("the article does not exist!!");
				} catch(SQLException e) {
					throw new SQLException("delete article failed!", e);
				}
					
					
				
			}
		});
	}
	
	public static Void incrementView(final int id) {
		return execute(new Operation<Void>() {
			public Void doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("UPDATE articles SET visit_time = visit_time + 1 WHERE id=?");
					statement.setInt(1, id);
					if(statement.executeUpdate() > 0) {
						return null;
					}
					throw new SQLException("the article does not exist!!");
				} catch(SQLException e) {
					throw new SQLException("View article failed!", e);
				}
			}
		});
	}
	
	public static Void incrementLike(final Article article) {
		return execute(new Operation<Void>() {
			public Void doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("UPDATE articles SET like_time = like_time + 1 WHERE id=?");
					statement.setInt(1, article.getId());
					if(statement.executeUpdate() > 0) {
						return null;
					}
					throw new SQLException("the article does not exist!!");
				}  catch(SQLException e) {
					throw new SQLException("like article failed!", e);
				}
			}
		});
	}
	
	public static Void updateArticle(final Article article) {
		return execute(new Operation<Void>() {
			public Void doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("UPDATE articles SET title = ?, content = ? WHERE id = ?");
					statement.setString(1, article.getTitle());
					statement.setString(2, article.getContent());
					statement.setInt(3, article.getId());
					if(statement.executeUpdate() > 0) {
						return null;
					}
					throw new SQLException("the article does not exist!!");
					
				} catch(SQLException e) {
					
					throw new SQLException("Update article fails!", e);
					
				}
			}
		});
	}
	
}
