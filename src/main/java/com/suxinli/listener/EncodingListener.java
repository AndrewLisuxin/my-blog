package com.suxinli.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Application Lifecycle Listener implementation class EncodingListener
 *
 */
public class EncodingListener implements ServletRequestListener {

    /**
     * Default constructor. 
     */
    public EncodingListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0)  { 
         // TODO Auto-generated method stub
    	try {
    		arg0.getServletRequest().setCharacterEncoding("UTF-8");
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    	
    	
    }
	
}
