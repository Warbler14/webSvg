package com.study.websvg.serviceImp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.websvg.define.Constants;
import com.study.websvg.model.ImgVo;
import com.study.websvg.model.SvgImgVo;
import com.study.websvg.service.SvgService;
import com.study.websvg.util.FileUtil;

@Service
public class SvgServiceImp implements SvgService {
	
	private static final Logger logger = LoggerFactory.getLogger(SvgServiceImp.class);
	
	@Autowired
	private SqlSession sqlSession;


	@Override
	public List<ImgVo> getList() {
		return sqlSession.selectList("SvgMapper.imgList");
	}
	
	
	@Override
	public Map<String, Object> insertBoard(HttpServletRequest request, HttpServletResponse response)  {
		String resourcePath = request.getSession().getServletContext().getRealPath("/resources");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//====================================================
			//업로드 이미지 저장
			//====================================================
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			MultipartFile multipartFile = null;
			
			List<Map<String, String>> imgFileList = new ArrayList<Map<String, String>>();
			List<ImgVo> imgList = new ArrayList<ImgVo>();
			
			while (iterator.hasNext()) {
				multipartFile = multipartHttpServletRequest.getFile(iterator.next());
				if (multipartFile.isEmpty() == false) {
					
					String name = multipartFile.getName();
					String fileName = multipartFile.getOriginalFilename();
					
					logger.debug("------------- file start -------------");
					logger.debug("name : " + name);
					logger.debug("filename : " + fileName);
					logger.debug("size : " + multipartFile.getSize());
					logger.debug("-------------- file end --------------\n");
					
					String savePath = resourcePath + FileUtil.FILE_SEP + Constants.UPLOAD_PATH;
					
					logger.debug("filePath : " + savePath);
					
					new FileUtil().writeFile(multipartFile, savePath, fileName );
					
					String saveFullPath = savePath +  Constants.FILE_SEP  + fileName;
					
					logger.debug("savePath : " + saveFullPath);
					
					Map<String, String> savePathMap = new HashMap<String, String>();
					savePathMap.put("fileName", fileName);
					savePathMap.put("filePath", saveFullPath);
					
					imgFileList.add( savePathMap );
					
					ImgVo baseImg = new ImgVo();
					baseImg.setImgPath( Constants.FILE_SEP + Constants.UPLOAD_PATH +  Constants.FILE_SEP  + fileName );
					imgList.add(baseImg);
					
				}
			}//end while
			
			String svgText = request.getParameter("svgText");
			
			logger.debug("---------------------svgText---------------------");
			logger.debug(svgText);
			
			//====================================================
			//svg 파일 저장
			//====================================================
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String time = format.format(new Date());
			
			String svgFilePath = resourcePath + FileUtil.FILE_SEP + Constants.UPLOAD_PATH;
			String svgFileName = time + ".svg";
			
			logger.debug("svgFilePath : " + svgFilePath);
			
			new FileUtil().write(svgText, svgFilePath, svgFileName );
			
			//==============================================
			//svg 파일로 이미지 생성
			//==============================================
			
			SvgImgVo svgImgVo = new SvgImgVo();
			svgImgVo.setSvgFilePath( FileUtil.FILE_SEP + Constants.UPLOAD_PATH + FileUtil.FILE_SEP +  svgFileName);
			
			String svgImgPath = FileUtil.FILE_SEP + Constants.UPLOAD_PATH + FileUtil.FILE_SEP +  time + ".png";
			ImgVo imgVo = new ImgVo();
			imgVo.setImgPath( svgImgPath );
			
			try {
				int svgPosX = Integer.valueOf( request.getParameter("svgPosX") );
				int svgPosY = Integer.valueOf( request.getParameter("svgPosY") );
				
				imgVo.setPositionX(svgPosX);
				imgVo.setPositionY(svgPosY);
				
			} catch (Exception e) {
				logger.debug( e.getMessage() );
				imgVo.setPositionX(50);
				imgVo.setPositionY(50);
			}
			imgList.add(imgVo);
			
			saveSvgImg( resourcePath, svgImgVo, imgVo);
			
			//====================================================
			//이미지 합성
			//====================================================
			
			BufferedImage image = drawImages( resourcePath, imgList, "png");
			
			//====================================================
			//로컬 파일로 저장
			//====================================================
			
			logger.debug("-----------save local resources file ----------------");
			String fileName = time + "Mix.png";
			String savePath = resourcePath + Constants.FILE_SEP + Constants.UPLOAD_PATH +  Constants.FILE_SEP  + fileName;
			
			logger.info("saveImgPath : " + savePath);
			
			OutputStream saveOut = new FileOutputStream( new File( savePath ) );
			ImageIO.write(image, "png", saveOut);
			
			String webPath = "/" + Constants.UPLOAD_PATH + "/" + fileName;
			
			resultMap.put("mixImg", webPath);
			
			//====================================================
			//반환값 설정
			//====================================================
			resultMap.put("result", true);
			resultMap.put("imgFileList", imgFileList);
			
			//====================================================
			
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
		}

		return resultMap;
	}
	
	
	
	
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
			
			String serverImagePath = request.getSession().getServletContext().getRealPath("/resources/image/bird.jpg" );
			
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
			//==============================================
			//svg 파일로 이미지 생성
			//==============================================
			String resourecePath = request.getSession().getServletContext().getRealPath("/resources");
			
			SvgImgVo svgImgVo = new SvgImgVo();
			svgImgVo.setSvgFilePath("/svg/testSvg2.svg");
			
			ImgVo imgVo = new ImgVo();
			imgVo.setImgPath( "/svg/testSvg.jpg");
			imgVo.setPositionX(50);
			imgVo.setPositionY(50);
			
			saveSvgImg( resourecePath, svgImgVo, imgVo);
			
			//-----------------------------------------------------------------------------------------
			List<ImgVo> imgList = new ArrayList<ImgVo>();
			
			ImgVo baseImg = new ImgVo();
			baseImg.setImgPath("/image/bird.jpg");
			
			logger.debug(">>>>base img " + baseImg.getImgPath());
			
			imgList.add(baseImg);
			
			imgList.add(imgVo);
			
			BufferedImage image = drawImages( response, resourecePath, imgList, "jpg");
			
			//-----------------------------------------------------------------------------------------
			logger.debug("-----------save local resources file ----------------");
			
			String saveImgPath = request.getSession().getServletContext().getRealPath("/resources/svg/viewMixSvgPngImg.png" );
			
			logger.info("saveImgPath : " + saveImgPath);
			
			File saveFile = new File( saveImgPath );
			OutputStream saveOut = new FileOutputStream(saveFile);
			ImageIO.write(image, "png", saveOut);
			
			//-----------------------------------------------------------------------------------------
			
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
		
	}
	
	@Override
	public void getMixSvgPngImg( HttpServletRequest request, HttpServletResponse response ) {

		try {
			//==============================================
			//svg 파일로 이미지 생성
			//==============================================
			String resourecePath = request.getSession().getServletContext().getRealPath("/resources");
			
			SvgImgVo svgImgVo = new SvgImgVo();
			svgImgVo.setSvgFilePath("/svg/testSvg2.svg");
			
			ImgVo imgVo = new ImgVo();
			imgVo.setImgPath( "/svg/testSvg2.png");
			imgVo.setPositionX(50);
			imgVo.setPositionY(50);
			
			saveSvgImg( resourecePath, svgImgVo, imgVo);
			
			//-----------------------------------------------------------------------------------------
			List<ImgVo> imgList = new ArrayList<ImgVo>();
			
			ImgVo baseImg = new ImgVo();
			baseImg.setImgPath("/image/bird.jpg");
			
			logger.debug(">>>>base img " + baseImg.getImgPath());
			
			imgList.add(baseImg);
			
			imgList.add(imgVo);
			
			BufferedImage image = drawImages( response, resourecePath, imgList, "png");
			
			//-----------------------------------------------------------------------------------------
			logger.debug("-----------save local resources file ----------------");
			
			String saveImgPath = request.getSession().getServletContext().getRealPath("/resources/svg/viewMixSvgPngImg.png" );
			
			logger.info("saveImgPath : " + saveImgPath);
			
			File saveFile = new File( saveImgPath );
			OutputStream saveOut = new FileOutputStream(saveFile);
			ImageIO.write(image, "png", saveOut);
			
			//-----------------------------------------------------------------------------------------
			
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
		
	}
	
	private void saveSvgImg( String resourecePath, SvgImgVo svgImgVo, ImgVo imgVo ) {
		
		try {
			//==============================================
			//svg 파일로 이미지 생성
			//==============================================
//			String tempSvgImgPath = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg2.png" );
			String tempSvgImgPath = resourecePath + imgVo.getImgPath();
			
			logger.info("tempSvgImgPath : " + tempSvgImgPath);
			
			File tempFile = new File( tempSvgImgPath );
			if (!tempFile.exists()) {
				System.out.println("create");
				tempFile.createNewFile();
			}
			
			OutputStream svgOut = new FileOutputStream(tempFile);
			
//			String svgFileName = request.getSession().getServletContext().getRealPath("/resources/svg/testSvg2.svg");
			String svgFileName =  resourecePath + svgImgVo.getSvgFilePath();
			
			logger.debug(">>sample svgFile = " + svgFileName );
			logger.debug(">>sample file exists = " + new File(svgFileName).exists() );
			
			TranscoderInput input_svg_image = new TranscoderInput("file:" + svgFileName);
			
			TranscoderOutput output_image = new TranscoderOutput( svgOut );
			
			String fileType = tempSvgImgPath.substring( tempSvgImgPath.lastIndexOf(".") + 1 , tempSvgImgPath.length() ).toUpperCase();
			
			logger.debug("File is " + fileType );
			
			switch( fileType ) {
			case "PNG" : 
				PNGTranscoder converter_png = new PNGTranscoder();
				converter_png.transcode(input_svg_image, output_image);

				break;
			case "JPG" :
			case "JPEG" :
				JPEGTranscoder converter_jpg = new JPEGTranscoder();
				converter_jpg.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
				converter_jpg.transcode(input_svg_image, output_image);
				
				break;
			default :
				logger.debug("Out of type");
			}
			
			svgOut.flush();
			svgOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage drawImages( HttpServletResponse response, String resourecePath, List<ImgVo> imgList , String convertType) {
		
		if( imgList == null || imgList.isEmpty() ) { 
			logger.debug("img is empty");
			return null;
		}
		BufferedImage image = null;
		
		try {
			ImgVo imgVo = imgList.get(0);
			response.setContentType("image/" + convertType);
			
			File serverImgFile = new File( resourecePath + imgVo.getImgPath() );
			
			image = ImageIO.read( serverImgFile );
			Graphics2D g = (Graphics2D) image.getGraphics();
			
			for( int idx0 = 1, end0 =  imgList.size() ; idx0 < end0 ; idx0++) {
				imgVo = imgList.get( idx0 );
				
				logger.debug(">img " + idx0 + imgVo.getImgPath());
				
				File tempFile = new File( resourecePath + imgVo.getImgPath() );
				BufferedImage bi = ImageIO.read( tempFile );
				
				g.drawImage(bi, imgVo.getPositionX(), imgVo.getPositionY(), null );
				
			}//end for
			
			OutputStream out = response.getOutputStream();
			
			ImageIO.write(image, convertType, out);
			
			
		} catch (Exception e) {
			logger.error( e.getMessage() );
		}
		
		return image;
	}
	
	private BufferedImage drawImages( String resourecePath, List<ImgVo> imgList , String convertType) {
		
		if( imgList == null || imgList.isEmpty() ) { 
			logger.debug("img is empty");
			return null;
		}
		BufferedImage image = null;
		
		try {
			ImgVo imgVo = imgList.get(0);
			
			File serverImgFile = new File( resourecePath + imgVo.getImgPath() );
			
			image = ImageIO.read( serverImgFile );
			Graphics2D g = (Graphics2D) image.getGraphics();
			
			for( int idx0 = 1, end0 =  imgList.size() ; idx0 < end0 ; idx0++) {
				imgVo = imgList.get( idx0 );
				
				logger.debug(">img " + idx0 + imgVo.getImgPath() + ", x : " + imgVo.getPositionX() + ", y : " + imgVo.getPositionY());
				
				File tempFile = new File( resourecePath + imgVo.getImgPath() );
				BufferedImage bi = ImageIO.read( tempFile );
				
				g.drawImage(bi, imgVo.getPositionX(), imgVo.getPositionY(), null );
				
			}//end for
			
			
		} catch (Exception e) {
			logger.error( e.getMessage() );
		}
		
		return image;
	}
	
	

}
