package com.study.websvg.util;

public class TestModel {

	private Integer testSeq;
	private String testName;
	private String testTime;
	
	public Integer getTestSeq() {
		return testSeq;
	}
	public void setTestSeq(Integer testSeq) {
		this.testSeq = testSeq;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestModel [");
		if (testSeq != null) {
			builder.append("testSeq=");
			builder.append(testSeq);
			builder.append(", ");
		}
		if (testName != null) {
			builder.append("testName=");
			builder.append(testName);
			builder.append(", ");
		}
		if (testTime != null) {
			builder.append("testTime=");
			builder.append(testTime);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
