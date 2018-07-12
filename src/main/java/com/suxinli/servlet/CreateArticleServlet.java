package com.suxinli.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suxinli.model.Article;

import javafx.util.Pair;

/**
 * Servlet implementation class CreateArticleServlet
 */
public class CreateArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateArticleServlet() {
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
		ServletContext ctx = getServletContext();
		Article article = new Article();
		article.setTitle(request.getParameter("title"));
		article.setContent(request.getParameter("content"));
		
		article.addArticle();
		
		((WriteLock)ctx.getAttribute("articleWriteLock")).lock();
		((List<Pair<Integer, String>>)ctx.getAttribute("articleList")).add(0, new Pair<Integer, String>(article.getId(), article.getTitle()));
		((WriteLock)ctx.getAttribute("articleWriteLock")).unlock();
		
		response.sendRedirect(response.encodeRedirectURL("ViewArticle?id=" +  article.getId()));
		
	}

}
