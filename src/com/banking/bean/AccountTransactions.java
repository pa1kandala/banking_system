package com.banking.bean;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.banking.db.DBConnectionManager;

public class AccountTransactions {

	private int txnid;
	private int acctid;
	private int chequeno;
	private String txntype;
	private int txnamount;
	private int txnbalanceamount;
	private String txncomments;
	private String txndate;

	Statement stmt = null;
	ResultSet rest = null;
	Connection conn = null;
	PreparedStatement pstd = null;

	public AccountTransactions() throws Exception {

		DBConnectionManager dbconn = new DBConnectionManager();

		this.conn = dbconn.getConnection();
		this.stmt = conn.createStatement();
	}

	public int getTransactionId() {
		return txnid;
	}

	public void setTransactionId(int txnid) {
		this.txnid = txnid;
	}

	public int getAcccountId() {
		return acctid;
	}

	public void setAcccountId(int acctid) {
		this.acctid = acctid;
	}

	public int getChequeNo() {
		return chequeno;
	}

	public void setChequeNo(int chequeno) {
		this.chequeno = chequeno;
	}

	public String getTransactionType() {
		return txntype;
	}

	public void setTransactionType(String txntype) {
		this.txntype = txntype;
	}

	public int getTransactionAmount() {
		return txnamount;
	}

	public void setTransactionAmount(int txnamount) {
		this.txnamount = txnamount;
	}

	public int getTransactionBalanceAmount() {
		return txnbalanceamount;
	}

	public void setTransactionBalanceAmount(int txnbalanceamount) {
		this.txnbalanceamount = txnbalanceamount;
	}

	public String getTransactionComments() {
		return txncomments;
	}

	public void setTransactionComments(String txncomments) {
		this.txncomments = txncomments;
	}

	public String getTransactionDate() {
		return txndate;
	}

	public void setTransactionDate(String txndate) {
		this.txndate = txndate;
	}

	public AccountTransactions getMaxTransactionIDInfo() {

		AccountTransactions objAccountTransactions = null;

		try {

			String query = "SELECT * FROM account_transactions WHERE acct_id=" + this.getAcccountId() + " AND txn_id=(SELECT MAX(txn_id) FROM account_transactions WHERE acct_id=" + this.getAcccountId() + ")";

			ResultSet rest = stmt.executeQuery(query);

			while (rest.next()) {

				this.setTransactionId(rest.getInt(1));
				this.setAcccountId(rest.getInt(2));
				this.setChequeNo(rest.getInt(3));
				this.setTransactionType(rest.getString(4));
				this.setTransactionAmount(rest.getInt(5));
				this.setTransactionBalanceAmount(rest.getInt(6));
				this.setTransactionComments(rest.getString(7));
				this.setTransactionDate(rest.getString(8));

				objAccountTransactions = this;
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return objAccountTransactions;
	}

	public boolean saveAccountTransactions() {

		boolean flag = false;

		try {

			pstd = conn.prepareStatement("INSERT INTO account_transactions (acct_id, txn_type, txn_amount, txn_balance_amount, txn_comments, txn_date, txn_id) VALUES (?,?,?,?,?,?,act_txn_seq.nextval)");

			pstd.setInt(1, this.getAcccountId());
			pstd.setString(2, this.getTransactionType());
			pstd.setInt(3, this.getTransactionAmount());
			pstd.setInt(4, this.getTransactionBalanceAmount());
			pstd.setString(5, this.getTransactionComments());
			pstd.setString(6, this.getTransactionDate());

			int rec_count = pstd.executeUpdate();

			if (rec_count > 0) {

				flag = true;
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return flag;
	}

	public ArrayList <AccountTransactions> getTransactions(int acct_id, String start_date, String end_date) {

		ArrayList <AccountTransactions> transactions = new ArrayList<>();

		try {

			String query = "SELECT * FROM account_transactions WHERE acct_id=" + acct_id + " AND txn_date >='" + start_date + "'  AND txn_date <='" + end_date + "' ORDER BY txn_id ASC";

			ResultSet rest = stmt.executeQuery(query);

			while (rest.next()) {

				AccountTransactions objAccountTransactions = new AccountTransactions();

				objAccountTransactions.setTransactionId(rest.getInt(1));
				objAccountTransactions.setAcccountId(rest.getInt(2));
				objAccountTransactions.setChequeNo(rest.getInt(3));
				objAccountTransactions.setTransactionType(rest.getString(4));
				objAccountTransactions.setTransactionAmount(rest.getInt(5));
				objAccountTransactions.setTransactionBalanceAmount(rest.getInt(6));
				objAccountTransactions.setTransactionComments(rest.getString(7));
				objAccountTransactions.setTransactionDate(rest.getString(8));

				transactions.add(objAccountTransactions);
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return transactions;
	}
}