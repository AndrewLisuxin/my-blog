package com.suxinli.config;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;

//import freemarker.template.Version;

import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author suxin li
 * 
 * */

public class Configuration {
	private static Properties properties = new Properties();
	
	//private static final freemarker.template.Configuration ftlConfiguration;
	/**
	 * load all property files into properties, and configure freemark
	 * */
	public static void init(ServletContext ctx) {
		
		File classpath = getFile("/");
		File[] propertyFiles = classpath.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".properties");
			}
		});
		System.out.println(propertyFiles.length);
		for(File f : propertyFiles) {
			try {
				properties.load(new FileInputStream(f));
			} catch(IOException e) {
				
			}
			
		}
		
		/* c dir to store profiles */
		String tmpPath = ctx.getRealPath(Configuration.get("tmp_profile_dir"));
    	File tmpDir = new File(tmpPath);
    	ctx.setAttribute("tmpPath", tmpPath);
    	ctx.setAttribute("tmpDir", tmpDir);
    	if(!tmpDir.exists()) {
    		tmpDir.mkdirs();
    		System.out.println("create tmp dir: " + tmpPath);
    	}
    	String ultiPath = ctx.getRealPath(Configuration.get("ultimate_profile_dir"));
    	File ultiDir = new File(ultiPath);
    	ctx.setAttribute("ultiPath", ultiPath);
    	ctx.setAttribute("ultiDir", ultiDir);
    	if(!ultiDir.exists()) {
    		ultiDir.mkdirs();
    		System.out.println("create ultimate dir: " + ultiPath);
    	}
		//ftlConfiguration = new freemarker.template.Configuration(new Version(2, 3, 22));
	
	}
	
	
	
	public static File getFile(String path) {
		return new File(Configuration.class.getClassLoader().getResource(path).getFile());
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
}
