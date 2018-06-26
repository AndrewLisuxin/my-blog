package com.suxinli.orm;

import java.sql.Connection;

public interface Operation<T> {
	T doOperation(Connection connection);
}
