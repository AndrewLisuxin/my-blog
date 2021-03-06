package com.suxinli.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.suxinli.model.Article;
import com.suxinli.model.Comment;

/**
 * Servlet implementation class ViewArticleServlet
 */
public class ViewArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int id = new Integer(request.getParameter("id"));
		Article article = Article.searchArticle(id);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/blog/article.jsp");
		request.setAttribute("article", article);
		HttpSession session = request.getSession(false);
		System.out.println("session == null: " + (session == null));
		
		//if(session != null) {
			session.setAttribute("article", article);
		//}
		session.setAttribute("lastVisitUrl", "/my-blog/ViewArticle?id=" + id);
		
		List<Comment> comments = Comment.SearchCommentsByArticle(article);
		request.setAttribute("comments", comments);
		/*
		 * if include a resource that calls forward(), the compression will go wrong!?
		 * */
		rd.include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
