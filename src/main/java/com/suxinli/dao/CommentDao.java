package com.suxinli.dao;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.suxinli.model.Article;
import com.suxinli.model.Comment;
import com.suxinli.model.User;
import com.suxinli.orm.BaseDao;
import com.suxinli.orm.Operation;

import javafx.util.Pair;

public class CommentDao extends BaseDao {
	public static Void addComment(final Comment comment) {
		return execute(new Operation<Void>() {
			public Void doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("INSERT INTO comments(content, user_id, article_id) VALUES(?, ?, ?)");
					statement.setString(1, comment.getContent());
					statement.setInt(2, comment.getUser().getId());
					statement.setInt(3, comment.getArticle().getId());
					statement.executeUpdate();
					return null;
				} catch(SQLException e) {
					throw new SQLException("the article does not exist any more!", e);
				}
				
			}
		});
	}
	
	/**
	 * @return comment lists of the article if it has, otherwise null
	 * */
	public static List<Comment> SearchCommentsByArticle(final Article article) {
		return execute(new Operation<List<Comment>>() {
			public List<Comment> doOperation(Connection connection) throws SQLException  {
				List<Comment> comments = null;
				try {
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE article_id=? ORDER BY create_time ASC");
					statement.setInt(1, article.getId());
					ResultSet res = statement.executeQuery();
					if(res.next()) {
						comments = new LinkedList<Comment>();
						do {
							User user = User.searchUser(res.getInt("user_id"));
							Comment comment = new Comment(res.getInt("id"), 
														  res.getString("content"), 
														  res.getTimestamp("create_time"), 
														  user,
														  article);
							comments.add(comment);
						} while(res.next());
					}
					return comments;
				} catch(SQLException e) {
					throw new SQLException("search comment list failed!", e);
				} 
				
			}
		});
	}
	
	public static Void deleteComment(final int id) {
		return execute(new Operation<Void>() {
			public Void doOperation(Connection connection) throws SQLException {
				try {
					PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id=?");
					statement.setInt(1, id);
					if(statement.executeUpdate() > 0) {
						return null;
					};
					throw new SQLException("the comment does not exist!");
				} catch(SQLException e) {
					throw new SQLException("delete comment failed!", e);
				}
				
			}
		});
	}
}
