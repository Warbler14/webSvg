package com.study.websvg.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TestMapResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -66095498065418627L;

	
	private Boolean status = true;
	private String message = "success";
	private String code = "";
	
	private List<Map<String,String>> data;
	
	public TestMapResult() {}
	
	public TestMapResult(List<Map<String,String>> data) {
		this.data = data;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Map<String,String>> getData() {
		return data;
	}

	public void setData(List<Map<String,String>> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Result [status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append(", code=");
		builder.append(code);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
	
}
