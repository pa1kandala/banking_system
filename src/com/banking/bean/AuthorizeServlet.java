package com.banking.bean;

import java.io.*;
import java.util.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import com.banking.bean.CreditCard;
import java.text.SimpleDateFormat;

public class AuthorizeServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cname = request.getParameter("card_name");

		long cnumb = Long.parseLong(request.getParameter("card_number"));
		int cncvv = Integer.parseInt(request.getParameter("card_cvv"));
		int tamnt = Integer.parseInt(request.getParameter("txn_amount"));

		try {

			RequestDispatcher rd = null;

			CreditCard objCreditCard = new CreditCard();
			CreditCard creditCardObj = objCreditCard.authorizeCard(cnumb);

			if (creditCardObj == null) {

				request.setAttribute("action", "yes");
				request.setAttribute("message", "Transaction Rejected - Invalid Card Number Entered!!");

				rd = request.getRequestDispatcher("authorize.jsp");
				rd.forward(request, response);

			} else {

				int card_cvv = creditCardObj.getCardCVV();
				int card_limit = creditCardObj.getCardLimit();

				Date date = new Date();
				String DATE_FORMAT = "mm/dd/yy";
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

				String c_date = sdf.format(date);
				String e_date = creditCardObj.getCardExpiryDate();

				Date cdate = sdf.parse(c_date);
				Date edate = sdf.parse(e_date);

				if (cncvv != card_cvv) {

					request.setAttribute("action", "yes");
					request.setAttribute("message", "Transaction Rejected - Invalid CVV Entered!!");

				} else if (cdate.compareTo(edate) > 0) {

					request.setAttribute("action", "yes");
					request.setAttribute("message", "Transaction Rejected - Credit Card Expired!!");

				} else if (tamnt > card_limit) {

					request.setAttribute("action", "yes");
					request.setAttribute("message", "Transaction Rejected - Amount should be less than 1 Lakh!!");

				} else {

					request.setAttribute("action", "yes");
					request.setAttribute("message", "Transaction Approved!!");
				}
			}

			rd = request.getRequestDispatcher("authorize.jsp");
			rd.forward(request, response);

		} catch(Exception e){

			e.printStackTrace();
		} 
	}
}