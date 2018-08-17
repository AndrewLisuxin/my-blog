package com.suxinli.response;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/*	Adapter pattern
 * */
public class GZIPServletOutputStream extends ServletOutputStream {

	private GZIPOutputStream internalGzipOS;
	public GZIPServletOutputStream(ServletOutputStream out) throws IOException {
		internalGzipOS = new GZIPOutputStream(out);
		
	}
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		//System.out.println("write to GZIPOutputStream");
		internalGzipOS.write(b);
	}
	
	@Override 
	public void write(byte[] b,
            int off,
            int len)
     throws IOException {
		internalGzipOS.write(b, off, len);
	}
	
	@Override
	public void write(byte[] b)
	           throws IOException {
		internalGzipOS.write(b);
	}
	
	public GZIPOutputStream getInternalOutputStream() {
		return internalGzipOS;
	}
	@Override
	public void flush() throws IOException {
		internalGzipOS.flush();
	}
	@Override
	public void close() throws IOException {
		/*
		 * this GZIPServletOutputStream itself does NOT store data, no buffer, so no need to flush
		 * */
		//System.out.println("GZIPOutputStream flush");
		internalGzipOS.close();
		
	}
	
}
