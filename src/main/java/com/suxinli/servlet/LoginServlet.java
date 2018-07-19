package com.suxinli.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.suxinli.model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		throw new ServletException(getServletName() + " (" + request.getMethod() + ") is not supported!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		/* search user in db */
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = User.checkUser(email, password);
		HttpSession session = request.getSession();
		if(user != null) {
			
			/* set session, if 5 minutes in inactive, then invalidate it */
			/* notice session cookie will be delete immediately after the browser is closed */
			
			session.setMaxInactiveInterval(5 * 60);
			
			/* the HttpSessionBindingListener will automatically login this user */
			session.setAttribute("user", user);
			
			if(user.isLogged()) {
				/* previous logged */
				session.invalidate();
				printRepeatedLoginMsg(out);
			}
			else {
				user.login();
				/* add cookie for next auto-login after users close the browser, valid for 30 minutes */
				Cookie emailCookie = new Cookie("email", email);
				emailCookie.setMaxAge(30 * 60);
				response.addCookie(emailCookie);
				
				Cookie passwordCookie = new Cookie("password", password);
				passwordCookie.setMaxAge(30 * 60);
				response.addCookie(passwordCookie);
			}
			
			
			
		}
		else {
			printNotFoundUserMsg(out);
		}
		
		
		
		/* dispatcher */
		RequestDispatcher rd = request.getRequestDispatcher(((String)session.getAttribute("lastVisitUrl")).substring(8));
		rd.include(request, response);
		
		
		
	}

	protected void printRepeatedLoginMsg(PrintWriter out) {
		out.print("<font color='red'>the user has logged in somewhere else!</font>");
	}
	
	protected void printNotFoundUserMsg(PrintWriter out) {
		out.print("<font color='red'>invalid email or password!</font>");
	}
}
