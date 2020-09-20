package com.study.websvg.serviceImp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.study.websvg.service.CommandLoader;

public class CommandLoader017 implements CommandLoader{
	private static final Logger logger = LoggerFactory.getLogger(CommandLoader017.class);
	
	private String move_page [][] ;
	private String parameters [] ;
	
	public CommandLoader017( String [][] move_page, String [] parameters ){
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
		HashMap<String, Integer> dataMap = loadParam( request, messagelist );
		if( dataMap != null && !dataMap.isEmpty() ){
			
			try{
				
				paintProcessJPG( request, response, BufferedImage.TYPE_INT_RGB , Color.white, dataMap);
				
			}catch(Exception e){
				logger.error("!! Exception : " + e.getMessage() );
				e.printStackTrace();
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
	
		//---------------------------------------------------------------------------------
	private HashMap<String, Integer> loadParam( HttpServletRequest request, ArrayList<String> messagelist ){
		HashMap<String, Integer> dataMap = new HashMap<String, Integer>();
		//---------------------------------------------------------------------------------
		
		try{
			for( int y = 0, yy = parameters.length ; y< yy ; y++ ){
				String param = request.getParameter( parameters[y] );
				logger.debug("param : " + parameters[y] + ", value : " + param);
				
				logger.info("param : " + param);
				int value = (int)Math.round(Double.valueOf(param));
				
				dataMap.put(parameters[y], value);
				
				
				
				logger.debug( "[" + y + "] : " + dataMap.get(parameters[y]) );
				
			}//end for
			logger.debug( "----------------------------------------------------------" );
			
		}catch (Exception e) {
			if(messagelist == null){
				messagelist = new ArrayList<String>();
			}
			
			messagelist.add( e.getMessage() );
			
			logger.error("~ Exception : " + e.getMessage() );
		}
		
		return dataMap;
	}
	
	private static void paintProcessJPG( HttpServletRequest request, HttpServletResponse response
			, int imageType, Paint baseColor, HashMap<String, Integer> dataMap ) throws Exception{
		
		final int dotSize = 50;
		final int module [] = {500,500};
		
		//----------------------------------------------
		response.setContentType("image/jpeg");
		BufferedImage bi = new BufferedImage(module[0], module[1], BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		//----------------------------------------------
		
		g.setPaint(baseColor);
		
		int x = dataMap.get( "x_pos" );
		int y = dataMap.get( "y_pos" );
		
		
		g.fillRect(x, y, dotSize, dotSize);
		
		
		//----------------------------------------------
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
		//----------------------------------------------
		
	}
	
	
}
