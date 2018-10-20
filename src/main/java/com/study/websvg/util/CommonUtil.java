package com.study.websvg.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

public class CommonUtil {

	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

	private static String NETWORK_CHK = "cmd.exe /c netstat -ano |findstr :"; // 포트 확인 CMD

	public static final String LISTEN = "LISTEN";

	public static final String ESTABLISHED = "ESTABLISHED";

	public final static byte[] BOM = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }; // #text file, byte order mark

	// #replace text
	public final static int[][] REMOVE_ESSEN = { { 92, 114, 92, 110 }, { 13, 10 }, { 92 } }; // remover ( /r/n, /r, /n,
																								// // )

	public final static String[][] REPLACE = { { "&nbsp;", "|" }, { "&lt;", "<" }, { "&gt;", ">" }, { "&amp;", "&" },
			{ "&Prime;", "\"" }, { "&prime;", "\'" }, { "&ldquo;", "“" }, { "&rdquo;", "”" }, { "&lsquo;", "‘" },
			{ "&rsquo;", "’" }, { "&sbquo;", "‚" }, { "&bdquo;", "„" }, { "&dagger;", "†" }, { "&Dagger;", "‡" },
			{ "&permil;", "‰" }, { "&lsaquo;", "‹" }, { "&rsaquo;", "›" }, { "&spades;", "♠" }, { "&clubs;", "♣" },
			{ "&hearts;", "♥" }, { "&diams;", "♦" }, { "&oline;", "‾" }, { "&larr;", "←" }, { "&uarr;", "↑" },
			{ "&rarr;", "→" }, { "&darr;", "↓" }, { "&trade;", "™" }, { "&#x2122;", "™" }, { "&#33;", "!" },
			{ "&#34;", "\"" }, { "&quot;", "\"" }, { "&#35;", "#" }, { "&#36;", "$" }, { "&#37;", "%" },
			{ "&#38;", "&" }, { "&#39;", "'" }, { "&#40;", "(" }, { "&#41;", ")" }, { "&#42;", "*" }, { "&#43;", "+" },
			{ "&#44;", "-" }, { "&#45;", "%" }, { "&#46;", "." }, { "&#47;", "/" }, { "&frasl;", "/" },
			{ "&#58;", ":" }, { "&#59;", ";" }, { "&#60;", "<" }, { "&#61;", "=" }, { "&#62;", ">" }, { "&#63;", "?" },
			{ "&#64;", "@" } }; // replacer

	/**
	 * 서버 IP 정보를 반환한다.
	 * 
	 * @return
	 */
	public String getServerIpAddress() {
		String ip = "";
		try {
			OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			if (osbean.getName().equals("Linux")) {
				try {
					boolean isLoopBack = true;
					Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
					while (en.hasMoreElements()) {
						NetworkInterface ni = en.nextElement();
						if (ni.isLoopback())
							continue;

						Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
						while (inetAddresses.hasMoreElements()) {
							InetAddress ia = inetAddresses.nextElement();
							if (ia.getHostAddress() != null && ia.getHostAddress().indexOf(".") != -1) {
								ip = ia.getHostAddress();
								isLoopBack = false;
								break;
							}
						}
						if (!isLoopBack)
							break;
					}
				} catch (SocketException e1) {
					LOGGER.debug(replaceStringCRLF(e1.getMessage()));
				}
			} else {
				// IP 정보 리턴.
				ip = InetAddress.getLocalHost().getHostAddress();
			}
		} catch (Exception e) {
			LOGGER.error(replaceStringCRLF(e.getMessage()));
			// 실행시 Exception 발생해도 후행 프로세스는 동작해야한다.
			// return 값으로 판단하여 로직처리한다.
		}
		return ip;
	}

	/**
	 * PORT의 PID를 반환 한다.
	 * 
	 * @param port
	 * @return
	 */
	public String getPortPid(String port) {
		String pid = "";
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		try {

			if (osbean.getName().equals("Linux")) {
				String[] commandLinux = { "/bin/sh", "-c", "netstat -anp |grep :" + port };
				String checkData = commandExeOutArray(commandLinux);
				pid = checkData.split("\n")[0].split("LISTEN")[1].replace("/java", "").trim();

			} else {
				String checkData = commandExeOut(NETWORK_CHK + port);
				pid = checkData.split("\n")[0].split("LISTENING")[1].trim();
			}

		} catch (Exception e) {
			LOGGER.info("[getPortPid] e : " + replaceStringCRLF(e.getMessage()));
			return "";
		}
		return pid;
	}

	/**
	 * VM PORT의 PID를 반환 한다.
	 * 
	 * @param port
	 * @return
	 */
	public String getVmPortPid(String port) {
		String pid = "";
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		try {

			if (osbean.getName().equals("Linux")) {
				String[] commandLinux = { "/bin/sh", "-c", "ps -ef |grep \"UIGenerator.jar " + port
						+ "\" |grep -v \"grep UIGenerator.jar " + port + "\" | awk '{print $2}'" };
				String checkData = commandExeOutArray(commandLinux);
				pid = checkData.split("\n")[1];

				LOGGER.info("[getVmPortPid] commandLinux : "
						+ replaceStringCRLF(Arrays.toString(commandLinux) + ", pid : " + pid));

			} else {
				String checkData = commandExeOut(NETWORK_CHK + port);
				pid = checkData.split("\n")[0].split("LISTENING")[1].trim();
			}

		} catch (Exception e) {
			LOGGER.info("[getVmPortPid] e : " + replaceStringCRLF(e.getMessage()));
			return "";
		}
		return pid;
	}

	/**
	 * 할당된 되어 사용되는 PORT PID
	 * 
	 * @MethodName : getAssignPortUsed
	 * @작성자 : KYRYU
	 * @작성일 : 2016. 6. 14.
	 * @변경이력 :
	 * @Method 설명 :
	 * 
	 * @param port
	 * @return
	 */
	public String getAssignPortUsed(String port) {
		String pid = "";
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		try {

			if (osbean.getName().equals("Linux")) {
				String[] commandLinux = { "/bin/sh", "-c", "netstat -anp |grep :" + port };
				String checkData = commandExeOutArray(commandLinux);
				if (checkData.split("\n")[1].split("ESTABLISHED").length > 1)
					pid = checkData.split("\n")[1].split("ESTABLISHED")[1].replace("/java", "").trim();

			} else {
				String checkData = commandExeOut(NETWORK_CHK + port);
				if (checkData.split("\n")[1].split("ESTABLISHED").length > 1)
					pid = checkData.split("\n")[1].split("ESTABLISHED")[1].trim();
			}

		} catch (Exception e) {
			return "";
		}
		return pid;
	}

	/**
	 * Commend 실행 및 실행 결과 Return (for Windos)
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */

	public String commandExeOut(String command) throws Exception {

		LOGGER.debug("[commandExeOut] Start");
		final long startTime = System.currentTimeMillis();

		StringBuffer sb = new StringBuffer();

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		Process process = null;

		try {
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(command);

			is = process.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}

		} catch (Exception e) {
			throw e;
		} finally {

			try {
				if (process.getInputStream() != null)
					process.getInputStream().close();
			} catch (Exception e2) {
				LOGGER.debug(replaceStringCRLF(e2.getMessage()));
			}

			try {
				if (process.getInputStream() != null)
					process.getOutputStream().close();
			} catch (Exception e2) {
				LOGGER.debug(replaceStringCRLF(e2.getMessage()));
			}

			try {
				if (process.getInputStream() != null)
					process.getErrorStream().close();
			} catch (Exception e2) {
				LOGGER.debug(replaceStringCRLF(e2.getMessage()));
			}

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				LOGGER.debug(replaceStringCRLF(e2.getMessage()));
			}
			try {
				if (isr != null)
					isr.close();
			} catch (Exception e2) {
				LOGGER.debug(replaceStringCRLF(e2.getMessage()));
			}
			try {
				if (is != null)
					is.close();
			} catch (Exception e2) {
				LOGGER.debug(replaceStringCRLF(e2.getMessage()));
			}
		}

		final long endTime = System.currentTimeMillis();
		LOGGER.info("[commandExeOut] End in " + (endTime - startTime) / 1000.0f + " secs\n");

		return sb.toString();
	}

	/**
	 * Commend 실행 및 실행 결과 Return (for Linux)
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public String commandExeOutArray(String[] command) throws Exception {

		LOGGER.debug("[commandExeOutArray] Start");
		final long startTime = System.currentTimeMillis();

		for (int i = 0; i < command.length; i++) {
			LOGGER.debug("command : " + replaceStringCRLF(command[i]));
		}

		StringBuffer sb = new StringBuffer();

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		Process process = null;

		try {

			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(command);

			is = process.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}

		} catch (Exception e) {
			throw e;
		} finally {

			try {
				if (process.getInputStream() != null)
					process.getInputStream().close();
			} catch (Exception e2) {
				LOGGER.error(replaceStringCRLF(e2.getMessage()));
			}

			try {
				if (process.getInputStream() != null)
					process.getOutputStream().close();
			} catch (Exception e2) {
				LOGGER.error(replaceStringCRLF(e2.getMessage()));
			}

			try {
				if (process.getInputStream() != null)
					process.getErrorStream().close();
			} catch (Exception e2) {
				LOGGER.error(replaceStringCRLF(e2.getMessage()));
			}

			try {
				if (br != null)
					br.close();
			} catch (Exception e2) {
				LOGGER.error(replaceStringCRLF(e2.getMessage()));
			}
			try {
				if (isr != null)
					isr.close();
			} catch (Exception e2) {
				LOGGER.error(replaceStringCRLF(e2.getMessage()));
			}
			try {
				if (is != null)
					is.close();
			} catch (Exception e2) {
				LOGGER.error(replaceStringCRLF(e2.getMessage()));
			}
		}

		final long endTime = System.currentTimeMillis();
		LOGGER.debug("[commandExeOutArray] End in " + (endTime - startTime) / 1000.0f + " secs\n");

		return sb.toString();
	}

	public String[] getKILLCMDARRAY(String pid) {
		String[] returnDate = { "/bin/sh", "-c", "kill -9 " + pid };
		return returnDate;
	}

	public String getKILLCMD() {
		return "cmd.exe /c taskkill /f /pid ";
	}

	public String toMB(long size) {
		return (int) (size / (1024 * 1024)) + "";
	}

	/**
	 * 현재날짜와 시간을 yyyyMMddHHmmss 타입으로 리턴.
	 */
	public static String getDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		Date xDate = new Date();
		String xStrDate = formatter.format(xDate);
		return xStrDate.trim();
	}

	/**
	 * 현재날짜와 시간을 yyyyMMddHHmmss 타입으로 리턴.
	 */
	public static String getDateTime(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		Date xDate = new Date(time);
		String xStrDate = formatter.format(xDate);
		return xStrDate.trim();
	}

	public static String replaceStringCRLF(Object obj) {
		String retStr = null;

		if (obj instanceof String) {
			retStr = obj.toString().replaceAll("\r\n", "");
		} else if (obj instanceof List) {
			retStr = obj.toString().replaceAll("\r\n", "");
		} else if (obj instanceof Map) {
			retStr = obj.toString().replaceAll("\r\n", "");
		} else if (obj instanceof Exception) {
			retStr = ((Exception) obj).getMessage().toString().replaceAll("\r\n", "");
		}

		return retStr;
	}

}
