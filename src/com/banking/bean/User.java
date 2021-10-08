package com.banking.bean;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.banking.db.DBConnectionManager;

public class User {

	private int userid;
	private String username;
	private String password;

	Statement stmt = null;
	ResultSet rest = null;
	Connection conn = null;

	public User() throws Exception {

		DBConnectionManager dbconn = new DBConnectionManager();

		this.conn = dbconn.getConnection();
		this.stmt = conn.createStatement();
	}

	public int getUserId() {
		return userid;
	}

	public void setUserId(int userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int searchUser() {

		int user_id  = 0;

		try {

			String query = "SELECT user_id FROM user_pass WHERE username='"+this.username+"' AND password='"+this.password+"'";

			rest = stmt.executeQuery(query);

			if (rest.next()) {

				user_id = rest.getInt(1);
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return user_id;
	}
}