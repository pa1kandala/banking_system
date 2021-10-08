package com.banking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	private static Connection connection = null;

	public Connection getConnection() throws ClassNotFoundException, SQLException {

		if (this.connection == null) {

			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","banking","root");
		}

		return this.connection;
	}
}