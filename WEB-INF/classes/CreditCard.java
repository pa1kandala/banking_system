package com.banking.bean;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.banking.db.DBConnectionManager;

public class CreditCard {

	private long card_number;
	private int card_cvv;
	private int card_limit;
	private String card_name;
	private String card_expirydate;

	Statement stmt = null;
	ResultSet rest = null;
	Connection conn = null;

	public CreditCard() throws Exception {

		DBConnectionManager dbconn = new DBConnectionManager();

		this.conn = dbconn.getConnection();
		this.stmt = conn.createStatement();
	}

	public long getCardNumber() {
		return card_number;
	}

	public void setCardNumber(long card_number) {
		this.card_number = card_number;
	}

	public String getCardName() {
		return card_name;
	}

	public void setCardName(String card_name) {
		this.card_name = card_name;
	}

	public String getCardExpiryDate() {
		return card_expirydate;
	}

	public void setCardExpiryDate(String card_expirydate) {
		this.card_expirydate = card_expirydate;
	}

	public int getCardCVV() {
		return card_cvv;
	}

	public void setCardCVV(int card_cvv) {
		this.card_cvv = card_cvv;
	}

	public int getCardLimit() {
		return card_limit;
	}

	public void setCardLimit(int card_limit) {
		this.card_limit = card_limit;
	}

	public CreditCard authorizeCard(long card_number) {

		CreditCard objCreditCard = null;

		try {

			String query = "SELECT * FROM credit_card WHERE card_number=" + card_number;

			ResultSet rest = stmt.executeQuery(query);

			while (rest.next()) {

				this.setCardNumber(rest.getLong(1));
				this.setCardName(rest.getString(2));
				this.setCardExpiryDate(rest.getString(3));
				this.setCardCVV(rest.getInt(4));
				this.setCardLimit(rest.getInt(5));

				objCreditCard = this;
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return objCreditCard;
	}
}