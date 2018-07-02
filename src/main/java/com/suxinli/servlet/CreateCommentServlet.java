package com.suxinli.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suxinli.model.Article;
import com.suxinli.model.Comment;
import com.suxinli.model.User;

/**
 * Servlet implementation class CreateCommentServlet
 */
public class CreateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCommentServlet() {
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
		Comment comment = new Comment();
		System.out.println(request.getParameter("content"));
		comment.setContent(request.getParameter("content"));
		comment.setUser((User)request.getSession(false).getAttribute("user"));
		Article article = (Article)request.getSession(false).getAttribute("article");
		comment.setArticle(article);
		comment.addComment();
		RequestDispatcher rd = request.getRequestDispatcher("/ViewArticle?id=" +  article.getId());
		rd.forward(request, response);
	}

}
