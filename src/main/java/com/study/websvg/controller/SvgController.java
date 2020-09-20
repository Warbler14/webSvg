package com.study.websvg.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.study.websvg.model.ImgVo;
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
	
	//--------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit/editList")
	public ModelAndView editList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		logger.debug( ">" + System.currentTimeMillis());
		
		mv.addObject("testvalue", "111222333AAA");
		
		try {
			List<ImgVo> svgList = svgService.getList();
			
			for (int i = 0; i < svgList.size() ; i++) {
				ImgVo vo = svgList.get(i);
				logger.debug(  vo.toStringMultiline() );
			}
			
			mv.addObject("svgList", svgList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/edit/editView")
	public ModelAndView editView(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		String id = request.getParameter("id");
		
		logger.debug("id : " + id + ", system time " + System.currentTimeMillis());
		
		mv.addObject("id", id);
		mv.addObject("testvalue", "111222333oo");
		
		return mv;
	}
	
	
	@RequestMapping(value="/imgUploadTest")
	public ModelAndView insertBoard(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();

		Map<String, Object> resultMap = svgService.insertBoard(request, response);
		
		logger.debug( "" + (Boolean)resultMap.get("result") );
		
		mv.setViewName("redirect:edit/editView");
		return mv;
	}
	
	@RequestMapping("/imgUploadTestAjax")
	@ResponseBody
	public String imgUploadTestAjax( HttpServletRequest request , HttpServletResponse response) {
		JsonObject jso = new JsonObject();    // JASON 객체생성
		
		logger.debug(">>imgUploadTestAjax");
		
		Map<String, Object> resultMap = svgService.insertBoard(request, response);
		logger.debug( "" + (Boolean)resultMap.get("result") );
		
		String fileName = (String) resultMap.get("mixImg");
			
		logger.debug(">fileName : " + fileName);
		jso.addProperty("fileName", fileName);
			
		
		return jso.toString();
	}
}
