package com.study.websvg.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.study.websvg.model.JsonTestVO;
import com.study.websvg.service.SvgService;


@Controller
public class SvgController {

	private static final Logger logger = LoggerFactory.getLogger(SvgController.class);

	@Autowired
	private SvgService svgService;
	
	/**
	 * http://localhost:8080/websvg/viewSvgImg
	 */
	@RequestMapping(value = "/viewSvgImg", method = RequestMethod.GET)
	public void viewSvgImg(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(">viewSvgImg start");
		
		svgService.getSvgImg(request, response);
		
		logger.debug(">viewSvgImg end");
	}
	
	/**
	 * http://localhost:8080/websvg/viewLocalImg
	 */
	@RequestMapping(value = "/viewLocalImg", method = RequestMethod.GET)
	public void viewLocalImg(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(">viewMixSvgImg start");
		
		svgService.getLocalImg(request, response);
		
		logger.debug(">viewMixSvgImg end");
	}
	
	/**
	 * http://localhost:8080/websvg/viewMixSvgJpgImg
	 */
	@RequestMapping(value = "/viewMixSvgJpgImg", method = RequestMethod.GET)
	public void viewMixSvgJpgImg(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(">viewMixSvgJpgImg start");
		
		svgService.getMixSvgJpgImg(request, response);
		
		logger.debug(">viewMixSvgJpgImg end");
	}

	/**
	 * http://localhost:8080/websvg/viewMixSvgPngImg
	 */
	@RequestMapping(value = "/viewMixSvgPngImg", method = RequestMethod.GET)
	public void viewMixSvgPngImg(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(">viewMixSvgPngImg start");
		
		svgService.getMixSvgPngImg(request, response);
		
		logger.debug(">viewMixSvgPngImg end");
	}
	
	@RequestMapping(value = "/localImgPreview", method = RequestMethod.GET)
	public String localImgPreview(Locale locale, Model model) {
		
		
		model.addAttribute("testvalue", "111222333");
		
		return "localImgPreview";
	}
	
	@RequestMapping(value="/imgUploadTest")
	public ModelAndView insertBoard(HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView();
//		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");

		Map<String, Object> resultMap = svgService.insertBoard(request);
		
		logger.debug( "" + (Boolean)resultMap.get("result") );
		
		mv.setViewName("redirect:/localImgPreview");
		return mv;
	}
	
	@RequestMapping("/imgUploadTestAjax")
	@ResponseBody
	public void imgUploadTestAjax( HttpServletRequest request ) {
		
		logger.debug(">>imgUploadTestAjax");
		
		Map<String, Object> resultMap = svgService.insertBoard(request);
		
		logger.debug( "" + (Boolean)resultMap.get("result") );
		
	}
}
