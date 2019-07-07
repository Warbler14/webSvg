package com.study.websvg.controller;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.websvg.serviceImp.CommandLoader026;

/**
 *  power by https://webgl2fundamentals.org, https://jsfiddle.net/qm3gzthL/
 */



@Controller
public class WebGlController {
	private static final Logger logger = LoggerFactory.getLogger(WebGlController.class);

	CommandLoader026 service;

	@RequestMapping(value = "/webgl/test01/cube", method = RequestMethod.GET)
	public String page(Locale locale, Model model) {
		
		logger.debug("webgl/test01/cube start");
		
		return "webgl/test01/cube";
	}
	
	
}