package com.suxinli.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suxinli.model.Article;
import com.suxinli.model.Comment;

/**
 * Servlet implementation class DeleteCommentServlet
 */
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCommentServlet() {
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
		Comment.deleteComment(Integer.parseInt(request.getParameter("id")));
		Article article = (Article)request.getSession(false).getAttribute("article");
		RequestDispatcher rd = request.getRequestDispatcher("/ViewArticleServlet?id=" +  article.getId());
		rd.forward(request, response);
		
	}

}
