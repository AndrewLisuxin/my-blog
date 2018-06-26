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
		
		Article article = Article.searchArticle(new Integer(request.getParameter("id")));
		if(article != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/blog/article.jsp");
			request.setAttribute("article", article);
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.setAttribute("article", article);
			}
			
			List<Comment> comments = Comment.SearchCommentsByArticle(article);
			System.out.println(comments == null);
			request.setAttribute("comments", comments);
			rd.forward(request, response);
		} 
		else {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
