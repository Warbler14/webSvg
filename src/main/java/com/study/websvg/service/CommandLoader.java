package com.study.websvg.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandLoader{
	
	public static String CMD = "cmd";
	
	public String findPage(HttpServletRequest request, HttpServletResponse response) throws IOException ;
	
	public void pageMove(HttpServletRequest request, HttpServletResponse response, String page) throws IOException ;

	public void drawImage(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	
}
