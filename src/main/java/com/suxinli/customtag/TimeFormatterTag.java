/**
 * 
 */
package com.suxinli.customtag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author root
 *
 */
public class TimeFormatterTag extends SimpleTagSupport {
	private Date time;
	private static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().print(ft.format(time));
	}
}
