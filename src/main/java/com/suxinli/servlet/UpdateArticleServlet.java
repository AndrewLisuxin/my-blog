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
 * Servlet implementation class UpdateArticleServlet
 */
public class UpdateArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Article article = Article.searchArticle(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("article", article);
		request.getSession(false).setAttribute("article", article);
		request.getRequestDispatcher("/blog/updateArticle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("restriction")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		Article article = (Article)request.getSession(false).getAttribute("article");
		if(article != null) {
			article.setTitle(request.getParameter("title"));
			article.setContent(request.getParameter("content"));
			
			article.updateArticle();
			
			ServletContext ctx = getServletContext();
			((WriteLock)ctx.getAttribute("articleReadLock")).lock();
			@SuppressWarnings("unchecked")
			List<Pair<Integer, String>> articleList = (List<Pair<Integer, String>>)ctx.getAttribute("articleList");
			for(int i = 0; i < articleList.size(); ++i) {
				Pair<Integer, String> item = articleList.get(i);
				if(item.getKey() == article.getId()) {
					if(!item.getValue().equals(article.getTitle())) {
						articleList.set(i, new Pair<Integer, String> (article.getId(), article.getTitle()));
					}
					break;
				}
			}
			((WriteLock)ctx.getAttribute("articleReadLock")).unlock();
			response.sendRedirect(response.encodeRedirectURL("ViewArticle?id=" + article.getId()));
		}
		else {
			throw new ServletException("Invalid operation!");
		}
		
	}

}
