package com.suxinli.orm;

import java.sql.Connection;

public interface TransactionalOperation<T> {
	T doOperation(Connection connection);
}
