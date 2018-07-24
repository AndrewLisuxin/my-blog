package com.suxinli.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
 * Servlet implementation class UpdateUserServlet
 */
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletContext ctx;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
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
		//throw new ServletException(getServletName() + " " + request.getMethod() + "is not supported!");
		request.getRequestDispatcher("/WEB-INF/user/user.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 10, (File)ctx.getAttribute("tmpDir"));
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setSizeMax(1024 * 1024 * 2);
		User user = (User)request.getSession(false).getAttribute("user");
		FileItem profileItem = null;
		try {
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
		user.updateUser();
		if(profileItem != null && profileItem.getSize() > 0) {
			String fileName = "" + user.getId() + ProfileUtil.getFileExtension(profileItem.getName());
			if(ProfileUtil.uploadProfile(profileItem, (String)ctx.getAttribute("ultiPath"), fileName) && (user.getImage() == null || user.getImage().equals("default.png"))) {
				user.setImage(fileName);
				user.updateImage();
			}
		}
		
		response.sendRedirect(response.encodeRedirectURL((String)request.getSession(false).getAttribute("lastVisitUrl")));
	}

	protected void parseFormItem(FileItem item, User user) {
		if(item.getSize() > 0) {
			String name = item.getFieldName();
			String value;
			try {
				/* every multipart/form data has a content type, and request will parse it as "iso-8859-1" by default 
				 * even if the conf of server.xml and filter set is UTF-8*/
				value = item.getString("UTF-8");
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			
			System.out.println(name + ": " + value);
			if(name.equals("username")) {
				user.setUsername(value);
			} else if(name.equals("password")) {
				user.setPassword(value);
			} else if(name.equals("city")) {
				user.setCity(value);
			}
		}
	}
}
