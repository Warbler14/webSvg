package com.study.websvg.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://localhost:8080/websvg/ImageGetSize?width=100&height=100
 */
@WebServlet("/ImageGetSize")
public class ImageGet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ImageGet.class);

	public ImageGet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String widthStr = request.getParameter("width");
		String heightStr = request.getParameter("height");
		ArrayList<String> messagelist = null;

		logger.info("width : " + widthStr + ", height : " + heightStr);

		int width = 0;
		int height = 0;
		if (!(widthStr == null || heightStr == null)) {

			try {

				width = Integer.valueOf(widthStr);
				height = Integer.valueOf(heightStr);

			} catch (NumberFormatException n) {
				if (messagelist == null) {
					messagelist = new ArrayList<String>();
				}

				messagelist.add(n.getMessage());

				logger.error("NumberFormatException : " + n.getMessage());

			}
		}

		if (width > 0 && height > 0) {

			try {

				response.setContentType("image/jpeg");
				BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

				OutputStream out = response.getOutputStream();
				ImageIO.write(bi, "jpg", out);
				out.close();

			} catch (Exception e) {
				logger.error("Exception : " + e.getMessage());
			}

		} else {
			response.getWriter().println("<h1> bad prameter </h1>");
			if ((messagelist != null) && !messagelist.isEmpty()) {
				for (int i = 0, j = messagelist.size(); i < j; i++) {
					response.getWriter().println("<h1> " + messagelist.get(i) + " </h1>");
				}
			}
		}

	}

}
