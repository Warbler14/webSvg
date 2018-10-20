package com.study.websvg.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;


@Service
public interface SvgService {

	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	public void getSvgImg( HttpServletRequest request, HttpServletResponse response );
	
	public void getLocalImg( HttpServletRequest request, HttpServletResponse response );
	
	public void getMixSvgJpgImg( HttpServletRequest request, HttpServletResponse response );
	
	public void getMixSvgPngImg( HttpServletRequest request, HttpServletResponse response );
}
