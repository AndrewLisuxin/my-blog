package com.suxinli.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

public class ProfileUtil {
	/* reader-wirter lock to handle multi-thread access */
	private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static final Lock r = rwl.readLock();
	private static final Lock w = rwl.writeLock();
	
	public static boolean uploadProfile(FileItem item, String filePath, String fileName) {
		w.lock();
		try {
			File file = new File(filePath + File.separator + fileName);
			item.write(file);
			return true;
			
		} catch(Exception e) {
			return false;
		} finally {
			w.unlock();
		}
	}
	
	public static String getFileExtension(String fileName) {
		String res = "";
		int idx;
		if((idx = fileName.lastIndexOf(".")) > 0) {
			res = fileName.substring(idx, fileName.length());
		}
		return res;
	}
	
	public static void downloadProfile(HttpServletResponse response, String filePath, String fileName) {
		r.lock();
		File file = new File(filePath + File.separator + fileName);
		if(file.exists()) {
			String extension = getFileExtension(fileName);
			String type = "application/octet-stream";
			if(extension.endsWith("png")) {
				type = "image/png";
			} else if(extension.endsWith("jpg")) {
				type = "image/jpeg";
			}
			response.setContentType(type);
		
			byte[] buffer = new byte[1024];
			int len;
			try(OutputStream out = response.getOutputStream(); InputStream in = new FileInputStream(file)) {
			
				while((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.flush();
			} catch(IOException e) {
				// log
			} finally {
				r.unlock();
			}
		}
		else {
			r.unlock();
		}
	}
}
