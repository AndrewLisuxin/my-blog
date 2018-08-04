package com.suxinli.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suxinli.response.CompressionResponseWrapper;
import com.suxinli.response.GZIPServletOutputStream;

/**
 * Servlet Filter implementation class CompressionFilter
 */
@WebFilter("/CompressionFilter")
public class CompressionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CompressionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String validEncodings = req.getHeader("Accept-Encoding");
		// pass the request along the filter chain
		if(req.getRequestURI().indexOf("Profile") == -1 && validEncodings.indexOf("gzip") > -1) {
			CompressionResponseWrapper wrapperdRes = new CompressionResponseWrapper(res);
			wrapperdRes.setHeader("Content-Encoding", "gzip");
			chain.doFilter(request, wrapperdRes);
			//((GZIPServletOutputStream)wrapperdRes.getOutputStream()).getInternalOutputStream().finish();
			wrapperdRes.close();
		} else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
