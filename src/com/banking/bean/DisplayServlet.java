package com.banking.bean;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import com.banking.bean.AccountTransactions;

public class DisplayServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		int acct_uid = (int) session.getAttribute("acct_uid");

		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");

		try {

			RequestDispatcher rd = null;

			AccountTransactions objLogAccount = new AccountTransactions();

			ArrayList <AccountTransactions> transactions = objLogAccount.getTransactions(acct_uid, start_date, end_date);

			int list_size = transactions.size();

			if (list_size <= 0) {

				request.setAttribute("emptylist", "yes");

			} else {

				request.setAttribute("emptylist", "no");
			}

			request.setAttribute("transactions", transactions);

			rd = request.getRequestDispatcher("display.jsp");
			rd.forward(request, response);

		} catch(Exception e){

			e.printStackTrace();
		}
	}
}