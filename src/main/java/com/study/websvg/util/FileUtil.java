package com.study.websvg.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static final String FILE_SEP = System.getProperty("file.separator");
	static Logger logger = Logger.getLogger(FileUtil.class);

	public final static String TYPE_STRING = "String";
	public final static String TYPE_ARRAY_STRING = "ArrayList<String>";
	
	private ArrayList<String> contentList = null;
	private String content = "";
	
	public FileUtil() {}

	public boolean readFile(String filePath, String type) {
		boolean status = false;
		FileInputStream fis = null;
		BufferedReader br = null;

		File file = new File(filePath);

		if (file.exists()) {

			try {
				fis = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

				String temp = "";

				while ((temp = br.readLine()) != null) {

					if (TYPE_STRING.equals(type)) {
						content += temp;
					} else if (TYPE_ARRAY_STRING.equals(type)) {
						contentList.add(temp);
					}
				}

				status = true;

			} catch (Exception e) {
				logger.error(CommonUtil.replaceStringCRLF(e.getMessage()));

			} finally {
				try {
					br.close();
				} catch (Exception fe) {
					logger.error(CommonUtil.replaceStringCRLF(fe.getMessage()));
				}
			}
		}

		return status;
	}

	public String getContent() {
		return content;
	}

	public ArrayList<String> getContentList() {
		return contentList;
	}
	
	public void writeEmptyFile(String fileDirs, String fileName) {

		File fileDir = new File(fileDirs);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(fileDirs + FILE_SEP + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(CommonUtil.replaceStringCRLF(e.getMessage()));
			}
		}

	}

	public boolean write(String content, String fileDirs, String fileName) {
		boolean status = false;

		FileOutputStream out = null;
		BufferedInputStream bis = null;
		long datasize = 0;

		File fileDir = new File(fileDirs);

		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(fileDirs + FILE_SEP + fileName);

		try {

			out = new FileOutputStream(file);
			bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes("UTF-8")));

			byte[] buffer = new byte[1024];
			int read = 0;

			out.write(CommonUtil.BOM);

			while ((read = bis.read(buffer)) > 0) {
				out.write(buffer, 0, read);
				datasize += read;
			}

			status = true;

		} catch (Exception e) {
			logger.error(CommonUtil.replaceStringCRLF(e.getMessage()));
		} finally {

			logger.info("file_size : " + datasize + " bytes");

			try {
				if (bis != null)
					bis.close();
				if (out != null)
					out.close();
			} catch (Exception e) {
				logger.error(CommonUtil.replaceStringCRLF(e.getMessage()));
			}

		}

		return status;
	}

	// 현재 시간을 기준으로 파일 이름 생성
	public String genSaveFileName(String extName) {
		String fileName = "";

		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;

		return fileName;
	}

	// 파일을 실제로 write 하는 메서드
	public boolean writeFile(MultipartFile multipartFile, String saveFilePath, String saveFileName) throws IOException {
		boolean result = false;

		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(saveFilePath + "/" + saveFileName);
		fos.write(data);
		fos.close();

		return result;
	}

}
