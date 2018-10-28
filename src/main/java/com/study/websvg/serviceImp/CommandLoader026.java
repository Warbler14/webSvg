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

import org.apache.log4j.Logger;

import com.study.websvg.service.CommandLoader;

public class CommandLoader026 implements CommandLoader{
	static Logger logger = Logger.getLogger(CommandLoader026.class);
	
	private String move_page [][] ;
	private String parameters [] ;
	
	public CommandLoader026( String [][] move_page, String [] parameters ){
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
		HashMap<String, String> dataMap = loadParam( request, messagelist );
		if( dataMap != null && !dataMap.isEmpty() ){
			
			try{
				
				paintProcessJPG( request, response, BufferedImage.TYPE_INT_RGB 
						, new Paint[]{Color.red, Color.green, Color.yellow }
						, dataMap);
				
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
	
	private HashMap<String, String> loadParam( HttpServletRequest request, ArrayList<String> messagelist ){
		//---------------------------------------------------------------------------------
		HashMap<String, String> dataMap = new HashMap<String, String>();
		//---------------------------------------------------------------------------------
		
		try{
			for( int y = 0, yy = parameters.length ; y< yy ; y++ ){
				String param = request.getParameter( parameters[y] );
				logger.debug("param : " + parameters[y] + ", value : " + param);
				
				dataMap.put( parameters[y] ,  param  );
				
				
				logger.debug( "[" + y + "] : " + dataMap.get(parameters[y]) );
				
			}//end for
			logger.debug( "----------------------------------------------------------" );
			
		}catch (Exception e) {
			if(messagelist == null){
				messagelist = new ArrayList<String>();
			}
			
			messagelist.add( e.getMessage() );
			
			logger.error("Exception : " + e.getMessage() );
		}
		
		return dataMap;
	}
	
	private static void paintProcessJPG( HttpServletRequest request, HttpServletResponse response
			, int imageType, Paint [] baseColors, HashMap<String, String> dataMap ) throws Exception{
		
		
		int boxWidth = Integer.valueOf( dataMap.get( "boxWidth" ) );
		int boxHeight = Integer.valueOf( dataMap.get( "boxHeight" ) );
		int countX = Integer.valueOf( dataMap.get( "countX" ) );
		int countY = Integer.valueOf( dataMap.get( "countY" ) );
		String [] dataArr = (dataMap.get( "data" )).split("_");
		boolean isNotEmptyDataArr = false;
		
		if( dataArr != null && dataArr.length > 1 ){
			logger.debug( "dataArr length : " + dataArr.length );
			
			isNotEmptyDataArr = true;
			
			for(int i = 0, ii=dataArr.length ; i<ii ; i++ ){
				logger.debug("[" + i + "][" + dataArr[i] + "]");
			}
		}
		
		//----------------------------------------------
		response.setContentType("image/jpeg");
		BufferedImage bi = new BufferedImage( boxWidth * countX , boxHeight * countY, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		//----------------------------------------------
		
		
		
		
		int pointer = 0;
		for( int i = 0 ; i<countY ; i++ ){
			for( int j = 0 ; j<countX ; j++ ){
				
				
				if( isNotEmptyDataArr ){
					if( pointer < dataArr.length ){
						
						
						logger.debug( Color.decode( getHex( dataArr[pointer] ) ));
						g.setPaint( Color.decode( getHex( dataArr[pointer] ) ) );
						
						
					}
				}else{
					g.setPaint(baseColors[ pointer%2 ]);
					
				}
				
				g.fillRect( j * boxWidth, i * boxHeight , boxWidth, boxHeight);
				
				pointer++;
			}//end for
			
			if( !isNotEmptyDataArr ){
				pointer++;
			}
		}//end for
		
		
		
		//----------------------------------------------
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
		//----------------------------------------------
		
	}
	
	private static String getHex( String code ){
		return "#" + code;
	}
}
