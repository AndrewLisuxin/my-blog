package com.suxinli.orm;

import java.sql.Connection;
import java.sql.SQLException;

public interface Operation<T> {
	T doOperation(Connection connection) throws SQLException;
}
