package com.suxinli.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.suxinli.model.User;
import com.suxinli.util.ProfileUtil;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletContext ctx;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	ctx = getServletContext();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 10, (File)ctx.getAttribute("tmpDir")); // 10kb
		ServletFileUpload uploader = new ServletFileUpload(factory);
    	uploader.setSizeMax(1024 * 1024 * 2);
		User user = null;
		FileItem profileItem = null;
		try {
			user = new User();
			List<FileItem> items = uploader.parseRequest(request);
			Iterator<FileItem> it = items.iterator();
			while(it.hasNext()) {
				FileItem item = it.next();
				if(item.isFormField()) {
					parseFormItem(item, user);
				} 
				else {
					profileItem = item;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		int id = -1;
		if((id = user.signup()) != -1) {
			if(profileItem != null && profileItem.getSize() > 0) {
				String fileName = "" + user.getId() + ProfileUtil.getFileExtension(profileItem.getName());
				if(ProfileUtil.uploadProfile(profileItem, (String)ctx.getAttribute("ultiPath"), fileName)) {
					user.setImage(fileName);
					user.updateImage();
				}
			}
			response.sendRedirect(response.encodeRedirectURL("index.jsp"));
		}
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/user/signup.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>The email has been registered.</font>");
			rd.include(request, response);
		}
		
	}

	protected void parseFormItem(FileItem item, User user) {
		if(item.getSize() > 0) {
			String name = item.getFieldName();
			String value;
			try {
				value = item.getString("UTF-8");
			} catch(UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			
			if(name.equals("id")) {
				user.setId(Integer.parseInt(value));
			} else if(name.equals("email")) {
				user.setEmail(value);
			} else if(name.equals("username")) {
				user.setUsername(value);
			} else if(name.equals("password")) {
				user.setPassword(value);
			} else if(name.equals("city")) {
				user.setCity(value);
			}
		}
	}
}
