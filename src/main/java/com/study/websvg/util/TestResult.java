package com.study.websvg.util;

import java.io.Serializable;
import java.util.List;

public class TestResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2960034674940437387L;
	
	private Boolean status = true;
	private String message = "success";
	private String code = "";
	
	private List<TestModel> data;
	
	public TestResult() {}
	
	public TestResult(List<TestModel> data) {
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

	public List<TestModel> getData() {
		return data;
	}

	public void setData(List<TestModel> data) {
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
