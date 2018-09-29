package com.study.websvg.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.websvg.service.TestService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private TestService testService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		
		logger.debug("----------------------------");
		String count = testService.getCount();
		logger.debug("count : " + count);
		logger.debug("----------------------------");
		//executeQuery();
		
		return "home";
	}

	
	
	public Object executeQuery(){
		
		Object result = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		String query  = "select count(*) cnt from linedb";
		try {
			System.out.println("!sql : " + query);
			con = DriverManager.getConnection("jdbc:sqlite:C:\\workSpaces\\workServer\\WebSvg\\sqlite\\SQLiteDB", "admin", "admin");
			//===================================================
			pstmt = con.prepareStatement( query );
			
			rs = pstmt.executeQuery();
			String cnt = rs.getString("cnt");
			System.out.println( cnt );
			//===================================================

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(query);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(query);
		}
			finally {
		
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
}
