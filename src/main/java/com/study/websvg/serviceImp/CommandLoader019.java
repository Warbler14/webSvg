package com.study.websvg.serviceImp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.websvg.service.CommandLoader;

public class CommandLoader019 implements CommandLoader{
	private static final Logger logger = LoggerFactory.getLogger(CommandLoader019.class);
	
	private String move_page [][] ;
	private String parameters [] ;
	
	public CommandLoader019( String [][] move_page, String [] parameters ){
		this.move_page = move_page;
		this.parameters = parameters;
	}
	@Override
	public String findPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String page = null;
		String cmd = request.getParameter( CommandLoader.CMD );
		
		if(cmd != null && cmd.length()>0){
			for( int i = 0, j = move_page.length ; i < j ; i++ ){
				if(move_page[i][0].equals(cmd)){
					
					page = move_page[i][1];
					
					logger.debug("page : " + page);
					
					break;
				}
			}
		}
		
		return page;
		
	}
	
	@Override
	public void pageMove(HttpServletRequest request, HttpServletResponse response, String page) throws IOException {
		
		try{
			
			RequestDispatcher rd = request.getRequestDispatcher( page );
			rd.forward(request,response);
					
		}catch( ServletException se ){
			logger.error( se.getMessage() );
		}
		
	}
	
	
	@Override
	public void drawImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ArrayList<String> messagelist = new ArrayList<String>();
		float [] dataArr = loadParam( request, messagelist );
		
		String boxSize = request.getParameter("size");
		
		logger.debug(">size : " + boxSize);
		
		if( dataArr.length > 0 ){
			
			try{
				
				paintProcessJPG( request, response, BufferedImage.TYPE_INT_RGB 
						, new Color(dataArr[0], dataArr[1],dataArr[2]), Integer.parseInt( boxSize ) );
				
			}catch(Exception e){
				logger.error("Exception : " + e.getMessage() );
			}
			
		}else{
			response.getWriter().println("<h1> bad prameter </h1>");
			if((messagelist != null) && !messagelist.isEmpty() ){ 
				for(int i = 0 , j = messagelist.size() ; i<j ; i++ ){
					response.getWriter().println("<h1> "+ messagelist.get(i) +" </h1>");
				}
			}
		}
	}
	
	private float [] loadParam( HttpServletRequest request, ArrayList<String> messagelist ){
		//---------------------------------------------------------------------------------
		float [] dataArr = new float [3];
		
		//---------------------------------------------------------------------------------
		
		try{
			
			String param = request.getParameter("color" );
			logger.info("param : " + param);
			
			String [] params = param.split("_");
			
			for( int i = 0 ; i<params.length ; i++ ){
				dataArr[i] = Float.valueOf( params[i] )/255;
				logger.debug( "[" + i + "] : " + dataArr[i] );
			}
			
			logger.debug( "----------------------------------------------------------" );
			
		}catch (Exception e) {
			if(messagelist == null){
				messagelist = new ArrayList<String>();
			}
			
			messagelist.add( e.getMessage() );
			
			logger.error("Exception : " + e.getMessage() );
		}
		
		return dataArr;
	}
	
	private static void paintProcessJPG( HttpServletRequest request, HttpServletResponse response
			, int imageType, Paint baseColor, int boxSize ) throws Exception{
		
		int dotSize = boxSize;
		if( dotSize <=0 ) {
			dotSize = 50;
		}
		
		//----------------------------------------------
		response.setContentType("image/jpeg");
		BufferedImage bi = new BufferedImage(dotSize, dotSize ,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		//----------------------------------------------
		
		g.setPaint(baseColor);
		
		g.fillRect(0, 0, dotSize, dotSize);
		
		
		//----------------------------------------------
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
		//----------------------------------------------
		
	}
	
}
