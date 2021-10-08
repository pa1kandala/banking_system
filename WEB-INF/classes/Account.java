package com.banking.bean;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import com.banking.db.DBConnectionManager;

public class Account {

	private int userid;
	private int acctid;
	private String acctname;
	private String acctdtob;
	private String acctaddr;
	private String acctemail;
	private String accttype;

	Statement stmt = null;
	ResultSet rest = null;
	Connection conn = null;
	PreparedStatement pstd = null;

	public Account() throws Exception {

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

	public int getAcccountId() {
		return acctid;
	}

	public void setAcccountId(int acctid) {
		this.acctid = acctid;
	}

	public String getAccountName() {
		return acctname;
	}

	public void setAccountName(String acctname) {
		this.acctname = acctname;
	}

	public String getAccountDOB() {
		return acctdtob;
	}

	public void setAccountDOB(String acctdtob) {
		this.acctdtob = acctdtob;
	}

	public String getAccountAddress() {
		return acctaddr;
	}

	public void setAccountAddress(String acctaddr) {
		this.acctaddr = acctaddr;
	}

	public String getAccountEmail() {
		return acctemail;
	}

	public void setAccountEmail(String acctemail) {
		this.acctemail = acctemail;
	}

	public String getAccountType() {
		return accttype;
	}

	public void setAccountType(String accttype) {
		this.accttype = accttype;
	}

	public Account getMaxAccountIDInfo() {

		Account objAccount = null;

		try {

			String query = "SELECT * FROM account_details WHERE acct_id=(SELECT MAX(acct_id) FROM account_details)";

			ResultSet rest = stmt.executeQuery(query);

			while (rest.next()) {

				this.setUserId(rest.getInt(1));
				this.setAcccountId(rest.getInt(2));
				this.setAccountName(rest.getString(3));
				this.setAccountDOB(rest.getString(4));
				this.setAccountAddress(rest.getString(5));
				this.setAccountEmail(rest.getString(6));
				this.setAccountType(rest.getString(7));

				objAccount = this;
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return objAccount;
	}

	public int getAccountIDFromUserID(int user_id) {

		int acct_id = 0;

		try {

			String query = "SELECT acct_id FROM account_details WHERE user_id=" + user_id;

			ResultSet rest = stmt.executeQuery(query);

			while (rest.next()) {

				acct_id = rest.getInt(1);
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return acct_id;
	}

	public boolean saveAccountDetails() {

		boolean flag = false;

		try {

			pstd = conn.prepareStatement("INSERT INTO account_details (user_id, acct_name, acct_dtob, acct_addr, acct_email, acct_type, acct_id) VALUES (?,?,?,?,?,?,act_det_seq.nextval)");

			pstd.setInt(1, this.getUserId());
			pstd.setString(2, this.getAccountName());
			pstd.setString(3, this.getAccountDOB());
			pstd.setString(4, this.getAccountAddress());
			pstd.setString(5, this.getAccountEmail());
			pstd.setString(6, this.getAccountType());

			int rec_count = pstd.executeUpdate();

			if (rec_count > 0) {

				flag = true;
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return flag;
	}

	public Account getUserAccountDetails(int user_id) {

		Account objAccount = null;

		try {

			String query = "SELECT * FROM account_details WHERE user_id=" + user_id;

			ResultSet rest = stmt.executeQuery(query);

			while (rest.next()) {

				this.setUserId(rest.getInt(1));
				this.setAcccountId(rest.getInt(2));
				this.setAccountName(rest.getString(3));
				this.setAccountDOB(rest.getString(4));
				this.setAccountAddress(rest.getString(5));
				this.setAccountEmail(rest.getString(6));
				this.setAccountType(rest.getString(7));

				objAccount = this;
			}

		} catch(Exception e){

			e.printStackTrace();
		}

		return objAccount;
	}
}