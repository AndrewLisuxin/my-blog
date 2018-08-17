package com.suxinli.response;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
public class CompressionResponseWrapper extends HttpServletResponseWrapper {
	private ServletOutputStream out = null;
	private PrintWriter pw = null;
	public CompressionResponseWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if(pw != null) {
			throw new IllegalStateException();
		}
		if(out == null) {
			out = new GZIPServletOutputStream(getResponse().getOutputStream());
		}
		return out;
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		if(out != null) {
			throw new IllegalStateException();
		}
		if(pw == null) {
			//System.out.println("create PrintWriter");
			pw = new PrintWriter(
					new OutputStreamWriter(
							new GZIPServletOutputStream(getResponse().getOutputStream()), getCharacterEncoding()
							)
					);
		}
		return pw;
	}
	
	public void close() throws IOException {
		if(out != null) {
			out.close();
			out = null;
		} else if(pw != null) {
			pw.close();
			pw = null;
		}
	}
	@Override
	public void flushBuffer() throws IOException {
		System.out.println("flushBuffer");
		super.flushBuffer();
		
	}
}
