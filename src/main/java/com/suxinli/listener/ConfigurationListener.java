package com.suxinli.listener;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.suxinli.config.Configuration;
import com.suxinli.model.Article;

/**
 * Application Lifecycle Listener implementation class ConfigurationListener
 *
 */
public class ConfigurationListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ConfigurationListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	Configuration.init(sce.getServletContext());
    	
    	ServletContext ctx = sce.getServletContext();
    	/* initialize article cache list */
    	ctx.setAttribute("articleList", Article.fetchArticles());
    	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    	ctx.setAttribute("articleReadLock", rwl.readLock());
    	ctx.setAttribute("articleWriteLock", rwl.writeLock());
    	
    	/* */
    }
	
}
