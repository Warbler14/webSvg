package com.study.websvg.serviceImp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.study.websvg.service.SvgService;

@Service
public class SvgServiceImp implements SvgService {
	
	private static final Logger logger = LoggerFactory.getLogger(SvgServiceImp.class);
	
//	@Autowired
//	private SqlSession sqlSession;

	@Override
	public void getSvgImg( HttpServletRequest request, HttpServletResponse response ) {

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
	
	@Override
	public void getLocalImg( HttpServletRequest request, HttpServletResponse response ) {

		try {
			
			String serverImagePath = request.getSession().getServletContext().getRealPath("/WEB-INF/image/bird.jpg" );
			
			logger.info("serverImagePath : " + serverImagePath);
			
			File f = new File( serverImagePath );
			
			if( f.exists() ){
				
				response.setContentType("image/jpeg");
				
				BufferedImage bi = ImageIO.read(f);
				Graphics2D g = (Graphics2D) bi.getGraphics();
				g.setPaint(Color.white);
				
				OutputStream out = response.getOutputStream();
				
				ImageIO.write(bi, "jpg", out);
				
				
			}else{
				response.getWriter().println("<h1>There is no image</h1>");
				return;
			}
			
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
		
	}
	
	@Override
	public void getMixSvgJpgImg( HttpServletRequest request, HttpServletResponse response ) {

		try {
			//====================
			String tempSvgImgPath = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg.jpg" );
			
			logger.info("tempSvgImgPath : " + tempSvgImgPath);
			
			File tempFile = new File( tempSvgImgPath );
			if (!tempFile.exists()) {
				System.out.println("create");
				tempFile.createNewFile();
			}
			
			OutputStream svgOut = new FileOutputStream(tempFile);
			
			String svgFileName = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg.svg");
			
			logger.debug(">>sample svgFile = " + svgFileName );
			logger.debug(">>sample file exists = " + new File(svgFileName).exists() );
			
			TranscoderInput input_svg_image = new TranscoderInput("file:" + svgFileName);
			
			TranscoderOutput output_jpg_image = new TranscoderOutput( svgOut );
			
			JPEGTranscoder converter_jpg = new JPEGTranscoder();
			converter_jpg.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
			converter_jpg.transcode(input_svg_image, output_jpg_image);
			
			svgOut.flush();
			svgOut.close();
			//====================
			
			String serverImagePath = request.getSession().getServletContext().getRealPath("/WEB-INF/image/bird.jpg" );
			
			logger.info("serverImagePath : " + serverImagePath);
			
			File serverImgFile = new File( serverImagePath );
			File tempSvgIngFile = new File( tempSvgImgPath );
			
			if( serverImgFile.exists() && tempSvgIngFile.exists() ){
				
				response.setContentType("image/jpeg");
				
				BufferedImage image = ImageIO.read( serverImgFile );
				BufferedImage bi1 = ImageIO.read( tempSvgIngFile );
				
				Graphics2D g = (Graphics2D) image.getGraphics();
//				g.setPaint(Color.white);
				
				g.drawImage(bi1, 50, 50, null );
				
				OutputStream out = response.getOutputStream();
				
				ImageIO.write(image, "jpg", out);
				
				
				//test
//				final ImageTranscoder trans = new JPEGTranscoder();
//				trans.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
//				final BufferedImage img = trans.createImage(1000, 1000);
//				Graphics2D g2d = img.createGraphics();
//				BufferedImage bi = ImageIO.read(f);
//				g2d.drawImage(bi, 300, 300, null);
//				g2d.drawLine(100, 100, 200, 200);
//				ImageIO.write(img, "jpg", out);
				
				
				
			}else{
				response.getWriter().println("<h1>There is no image</h1>");
				return;
			}
			
			//-------------------------------------------------------------
			
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
		
	}
	
	@Override
	public void getMixSvgPngImg( HttpServletRequest request, HttpServletResponse response ) {

		try {
			//====================
			String tempSvgImgPath = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg2.png" );
			
			logger.info("tempSvgImgPath : " + tempSvgImgPath);
			
			File tempFile = new File( tempSvgImgPath );
			if (!tempFile.exists()) {
				System.out.println("create");
				tempFile.createNewFile();
			}
			
			OutputStream svgOut = new FileOutputStream(tempFile);
			
			String svgFileName = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg2.svg");
			
			logger.debug(">>sample svgFile = " + svgFileName );
			logger.debug(">>sample file exists = " + new File(svgFileName).exists() );
			
			TranscoderInput input_svg_image = new TranscoderInput("file:" + svgFileName);
			
			TranscoderOutput output_png_image = new TranscoderOutput( svgOut );
			
			PNGTranscoder converter_png = new PNGTranscoder();
			converter_png.transcode(input_svg_image, output_png_image);
			
			svgOut.flush();
			svgOut.close();
			//====================
			
			String serverImagePath = request.getSession().getServletContext().getRealPath("/WEB-INF/image/bird.jpg" );
			
			logger.info("serverImagePath : " + serverImagePath);
			
			File serverImgFile = new File( serverImagePath );
			File tempSvgIngFile = new File( tempSvgImgPath );
			
			if( serverImgFile.exists() && tempSvgIngFile.exists() ){
				
				response.setContentType("image/png");
				
				BufferedImage image = ImageIO.read( serverImgFile );
				BufferedImage bi1 = ImageIO.read( tempSvgIngFile );
				
				Graphics2D g = (Graphics2D) image.getGraphics();
//				g.setPaint(Color.white);
				
				g.drawImage(bi1, 50, 50, null );
				
				OutputStream out = response.getOutputStream();
				
				ImageIO.write(image, "png", out);
				
				
			}else{
				response.getWriter().println("<h1>There is no image</h1>");
				return;
			}
			
			//-------------------------------------------------------------
			
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
		
	}
}
