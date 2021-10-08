package com.banking.bean;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import com.banking.bean.Account;
import com.banking.bean.AccountTransactions;

public class AccountServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String user_name = request.getParameter("user_name");
		String user_dtob = request.getParameter("user_dtob");
		String user_addr = request.getParameter("user_addr");
		String user_acct = request.getParameter("user_acct");
		String user_email = request.getParameter("user_email");

		int user_id	 = (int) session.getAttribute("user_id");

		try {

			Account objAccount = new Account();
			AccountTransactions objAccountTransactions = new AccountTransactions();
			RequestDispatcher rd = null;

			objAccount.setUserId(user_id);
			objAccount.setAccountName(user_name);
			objAccount.setAccountDOB(user_dtob);
			objAccount.setAccountAddress(user_addr);
			objAccount.setAccountEmail(user_email);
			objAccount.setAccountType(user_acct);

			boolean act_flag = objAccount.saveAccountDetails();

			if (act_flag == true) {

				Account acctObj = objAccount.getMaxAccountIDInfo();

				int acct_uid = 0;
				int txn_amount = 5000;
				String acct_type = "";
				String trxn_type = "credit";
				String trxn_cmts = "Account Created!!";

				Date date = new Date();
				String DATE_FORMAT = "MM/dd/yy";
				SimpleDateFormat cur_date = new SimpleDateFormat(DATE_FORMAT);

				if (acctObj != null) {

					acct_uid	= acctObj.getAcccountId();
					acct_type	= acctObj.getAccountType();

					if (acct_type.equals("current")) {

						txn_amount = 10000;
					}
				}

				objAccountTransactions.setAcccountId(acct_uid);
				objAccountTransactions.setTransactionType(trxn_type);
				objAccountTransactions.setTransactionAmount(txn_amount);
				objAccountTransactions.setTransactionBalanceAmount(txn_amount);
				objAccountTransactions.setTransactionComments(trxn_cmts);
				objAccountTransactions.setTransactionDate(cur_date.format(date));

				boolean txn_flag = objAccountTransactions.saveAccountTransactions();

				if (txn_flag == true) {

					session.setAttribute("acct_uid", acct_uid);

					rd = request.getRequestDispatcher("success.jsp");
					rd.forward(request, response);

				} else {

					request.setAttribute("error", "yes");
					rd = request.getRequestDispatcher("account.jsp");
					rd.forward(request, response);
				}

			} else {

				request.setAttribute("error", "yes");
				rd = request.getRequestDispatcher("account.jsp");
				rd.forward(request, response);
			}

		} catch(Exception e){

			e.printStackTrace();
		}
	}
}