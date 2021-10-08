package com.banking.bean;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import com.banking.db.DBConnectionManager;
import com.banking.bean.AccountTransactions;

public class TransactionServlet extends HttpServlet {

	private static Connection conn = null;
	private static PreparedStatement pstd = null;

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {

			DBConnectionManager dbconn = new DBConnectionManager();
			conn = dbconn.getConnection();
			pstd = conn.prepareStatement("INSERT INTO account_transactions (txn_id, acct_id, txn_type, txn_amount, txn_balance_amount, txn_comments, txn_date) VALUES (act_txn_seq.nextval,?,?,?,?,?,?)");

		} catch(Exception e){

			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		int acct_uid = (int) session.getAttribute("acct_uid");
		int acct_num = Integer.parseInt(request.getParameter("acct_num"));
		int acct_amt = Integer.parseInt(request.getParameter("acct_amt"));

		String actn_type = request.getParameter("actn_type");
		String acct_cmts = request.getParameter("acct_cmts");

		try {

			RequestDispatcher rd = null;

			AccountTransactions logUserAccount = new AccountTransactions();
			AccountTransactions txnUserAccount = new AccountTransactions();

			logUserAccount.setAcccountId(acct_uid);
			txnUserAccount.setAcccountId(acct_num);

			AccountTransactions objLogAccount = logUserAccount.getMaxTransactionIDInfo();
			AccountTransactions objTxnAccount = txnUserAccount.getMaxTransactionIDInfo();

			if (objLogAccount == null) {

				request.setAttribute("message", "Account ID doesn't exists for logged-in user!!");

				rd = request.getRequestDispatcher("transactions.jsp");
				rd.forward(request, response);

			} else if (objTxnAccount == null) {

				request.setAttribute("message", "Invalid Account Number Entered!!");

				rd = request.getRequestDispatcher("transactions.jsp");
				rd.forward(request, response);

			} else {

				int lbal_amount = objLogAccount.getTransactionBalanceAmount();
				int tbal_amount = objTxnAccount.getTransactionBalanceAmount();

				if (actn_type.equals("debit") && acct_amt > tbal_amount) {
					
					request.setAttribute("message", "Insufficient Balance for the given Account Number!!");

				} else if (actn_type.equals("credit") && acct_amt > lbal_amount) {
					
					request.setAttribute("message", "Insufficient Balance for the Logged User Account Number!!");

				} else {

					int log_actid = objLogAccount.getAcccountId();
					int txn_actid = objTxnAccount.getAcccountId();

					String log_type = null;
					String txn_type = null;

					if (actn_type.equals("debit")) {

						log_type = "credit";
						txn_type = "debit";

						tbal_amount = tbal_amount - acct_amt;
						lbal_amount = lbal_amount + acct_amt;

					} else if (actn_type.equals("credit")) {

						txn_type = "credit";
						log_type = "debit";

						lbal_amount = lbal_amount - acct_amt;
						tbal_amount = tbal_amount + acct_amt;
					}

					Date date = new Date();
					String DATE_FORMAT = "MM/dd/yy";
					SimpleDateFormat cur_date = new SimpleDateFormat(DATE_FORMAT);

					conn.setAutoCommit(false);

					pstd.setInt(1, log_actid);
					pstd.setString(2, log_type);
					pstd.setInt(3, acct_amt);
					pstd.setInt(4, lbal_amount);
					pstd.setString(5, acct_cmts);
					pstd.setString(6, cur_date.format(date));
					pstd.addBatch();

					pstd.setInt(1, txn_actid);
					pstd.setString(2, txn_type);
					pstd.setInt(3, acct_amt);
					pstd.setInt(4, tbal_amount);
					pstd.setString(5, acct_cmts);
					pstd.setString(6, cur_date.format(date));
					pstd.addBatch();

					int count[] = pstd.executeBatch();
					int aff_rows = count.length;

					if (aff_rows == 2) {

						request.setAttribute("message", "Transaction Completed Successfully!!");
						conn.commit();

					} else {

						request.setAttribute("message", "Transaction Failed!!");
						conn.rollback();
					}
				}

				rd = request.getRequestDispatcher("transactions.jsp");
				rd.forward(request, response);
			}

		} catch(Exception e){

			e.printStackTrace();
		}
	}
}