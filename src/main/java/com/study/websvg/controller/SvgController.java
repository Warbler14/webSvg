package com.study.websvg.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * http://localhost:8080/websvg/viewSvgImg
 */
@Controller
public class SvgController {

	private static final Logger logger = LoggerFactory.getLogger(SvgController.class);


	@RequestMapping(value = "/viewSvgImg", method = RequestMethod.GET)
	public void home(HttpServletRequest request, HttpServletResponse response) {

		try {
			response.setContentType("image/jpeg");
			OutputStream out = response.getOutputStream();
			
			String svgFileName = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg.svg");
			
			logger.debug(">>sample svgFile = " + svgFileName );
			logger.debug(">>sample file exists = " + new File(svgFileName).exists() );
			
//			URL url = new URL("http://localhost:8080/websvg/resources/svg/testSvg.svg");
//			TranscoderInput input_svg_image = new TranscoderInput( url.toString() );
			
			TranscoderInput input_svg_image = new TranscoderInput("file:" + svgFileName);
			
			TranscoderOutput output_jpg_image = new TranscoderOutput(out);
			
			JPEGTranscoder converter_jpg = new JPEGTranscoder();
			converter_jpg.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
			converter_jpg.transcode(input_svg_image, output_jpg_image);
			
			out.flush();
			out.close();
			

		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
	}

}
