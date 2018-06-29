package com.suxinli.orm;

import java.sql.Connection;
import java.sql.SQLException;

import com.suxinli.jdbc.ConnectionFactory;

public abstract class BaseDao {
	protected static <T> T execute(Operation<T> operation) {
		//Connection connection = null;
		try (Connection connection = ConnectionFactory.getConnection()) {
			return operation.doOperation(connection);
		} catch(SQLException e) {
            throw new RuntimeException("FAILURE!", e);     
		}
	}
	
	protected static <T> T execute(TransactionalOperation<T> transactionalOperation) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			T res = transactionalOperation.doOperation(connection);
			connection.commit();
			return res;
		} catch(Exception e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch(SQLException e1) {
					throw new RuntimeException(e1);
				}
			} 
			throw new RuntimeException(e);
		} finally {
			try {
				if(connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
 