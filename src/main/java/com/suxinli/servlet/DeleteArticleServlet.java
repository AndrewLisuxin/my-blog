package com.suxinli.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suxinli.model.Article;

import javafx.util.Pair;

/**
 * Servlet implementation class DeleteArticleServlet
 */
public class DeleteArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteArticleServlet() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		Article.deleteArticle(id);
		
		ServletContext ctx = getServletContext();
		((WriteLock)ctx.getAttribute("articleWriteLock")).lock();
		List<Pair<Integer, String>> articleList = (List<Pair<Integer, String>>)ctx.getAttribute("articleList");
		for(int i = 0; i < articleList.size(); ++i) {	
			if(articleList.get(i).getKey() == id) {
				articleList.remove(i);	
				break;
			}
		}
		((WriteLock)ctx.getAttribute("articleWriteLock")).unlock();
		
		response.sendRedirect(response.encodeRedirectURL("index.jsp"));
	}

}
