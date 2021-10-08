package com.banking.bean;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import com.banking.bean.User;
import com.banking.bean.Account;

public class LoginServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String unm = (String) request.getParameter("user_nm");
		String pwd = (String) request.getParameter("pass_wd");

		session.setAttribute("user_nm", unm);

		try {

			User objUser = new User();
			RequestDispatcher rd = null;

			objUser.setUserName(unm);
			objUser.setPassword(pwd);

			int uid = objUser.searchUser();

			if (uid > 0) {

				session.setAttribute("user_id", uid);

				Account objAccount = new Account();
				int aid = objAccount.getAccountIDFromUserID(uid);

				if (aid > 0) {

					session.setAttribute("acct_uid", aid);
				}

				rd = request.getRequestDispatcher("main.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("error", "yes");
				rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}

		} catch(Exception e){

			e.printStackTrace();
		} 
	}
}