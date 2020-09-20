package com.study.websvg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class HttpUtil extends ObjectUtil{

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static int DEFAULT_HTTP_TIMEOUT = 1000;

	public static void main(String[] args) {

		String url = "http://localhost:8081/getTest";


		TestResult testResult1 = request(url, TestResult.class);
		logger.info("testResult1 : " + testResult1.toString());

		TestResult testResult2 = sendRequest(url, null, "POST", TestResult.class);
		logger.info("testResult2 : " + testResult2.toString());
		
		System.out.println("---------------------------");

		TestMapResult testResult3 = request(url, TestMapResult.class);
		logger.info("testResult3 : " + testResult3.toString());
		

		TestMapResult testResult4 = sendRequest(url, null, "POST", TestMapResult.class);
		logger.info("testResult4 : " + testResult4.toString());
		
		System.out.println("---------------------------");
		
//		Result<List<TestModel>> form = new Result<List<TestModel>>();
		Result<List<TestModel>> form = new Result<List<TestModel>>();

		
		Result<List<TestModel>> testResult5 = request(url, form.getClass());
		logger.info("testResult5 : " + testResult5.toString());
		
		

		Result<List<TestModel>> testResult6 = sendRequest(url, null, "POST", form.getClass());
		logger.info("testResult6 : " + testResult6.toString());
		
		
	}

	public static <T> T request(final String url, Class<T> valueType) {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(5000); // 읽기시간초과, ms
		factory.setConnectTimeout(3000); // 연결시간초과, ms
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100) // connection pool 적용
				.setMaxConnPerRoute(5) // connection pool 적용
				.build();
		factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
		RestTemplate restTemplate = new RestTemplate(factory);
//		String url = "http://testapi.com/search?text=1234"; // 예제니까 애초에 때려박음.. 
		T obj = restTemplate.getForObject(url, valueType);
		return obj;

	}

	public static <T> T sendRequest(String urls, Map<String, ?> paramMap, String method, Class<T> valueType) {
		T result = null;

		String params = getParameterString(paramMap);

		String httpReturnData = httpReturnData(urls, params, method);

		if (StringUtils.isEmpty(httpReturnData)) {
			return null;
		}

		result = getObjectByJackson(httpReturnData, valueType);

		return result;
	}

	public static <T> T getObjectByJackson(final String jsonDataString, Class<T> valueType) {
		T result = null;
		try {

			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(jsonDataString, valueType);

		} catch (UnrecognizedPropertyException upe) {
			logger.debug(upe.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		return result;
	}

	public static String getParameterString(Map<String, ?> map) {
		StringBuffer buffer = new StringBuffer();
		if (isNotEmpty(map)) {
			boolean isFirst = true;
			for (Map.Entry<String, ?> entry : map.entrySet()) {
				if (isFirst) {
					isFirst = false;
				} else {
					buffer.append("&");
				}
				buffer.append(entry.getKey());
				buffer.append("=");
				buffer.append(entry.getValue());
			}
		}
		return buffer.toString();
	}

	public static String httpReturnData(String urls, String param, String method) {
		return httpReturnData(urls, param, method, DEFAULT_HTTP_TIMEOUT, DEFAULT_HTTP_TIMEOUT);
	}

	public static String httpReturnData(String urls, String param, String method, int connectTimeout, int readTimeout) {
		HttpURLConnection huc = null;
		InputStream is = null;
		URL url = null;
		StringBuffer sb = new StringBuffer();
		int responseCode = 0;

		try {
			String fullUrl = urls + "?" + getParamEncoding(param);

			url = new URL(fullUrl);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod(method);
			if ("POST".equals(method)) {
				huc.setDoOutput(true);
			}
			huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			huc.setConnectTimeout(connectTimeout);
			huc.setReadTimeout(readTimeout);
			responseCode = huc.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				is = huc.getInputStream();
				if (is != null) {
					String tmpData = "";
					BufferedReader br = new BufferedReader(new InputStreamReader(is, DEFAULT_CHARSET));
					while ((tmpData = br.readLine()) != null) {
						sb.append(tmpData);
					}
					if (br != null) {
						br.close();
					}
				}
			}
		} catch (ConnectException ce) {
			logger.warn(ce.getMessage());
		} catch (SocketException se) {
			logger.warn(se.getMessage());
		} catch (SocketTimeoutException ste) {
			logger.warn(ste.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e2) {
			}
			try {
				if (huc != null) {
					huc.disconnect();
				}
			} catch (Exception e2) {
			}
		}
		return sb.toString();
	}

	private static String getParamEncoding(String originParam) throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] params = originParam.split(SIGN_AMPERSAND);
		for (String param : params) {
			String[] keyValue = param.split(SIGN_EQUAL);
			String key = keyValue[0];
			String value = keyValue.length == 1 ? "" : keyValue[1];
			String encodingValue = urlEncoding(value);

			sb.append(key).append(SIGN_EQUAL).append(encodingValue).append(SIGN_AMPERSAND);
		}

		return sb.substring(0, sb.length() - 1);
	}

	private static String urlEncoding(String msg) throws Exception {
		return URLEncoder.encode(msg, DEFAULT_CHARSET);
	}

}
