package com.apache.ReflectionToString;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TestReflectionToString {

	private int id;
	private String name;
	private String description;
	public static final String KEY = "加密金鑰"; // static 屬性
	private transient String secretKey; // 標註 transient 屬性

	public TestReflectionToString(Integer id, String name, String description, String secretKey) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.secretKey = secretKey;
	}

	@Override
	public String toString() {
		boolean outputTransients = true; // 是否輸出 【transient】 屬性
		boolean outputStatics = false;   // 是否輸出 【static】 屬性
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE, outputTransients, outputStatics);
	}

	public static void main(String[] args) {
		TestReflectionToString demo = new TestReflectionToString(1001, "Roger", "經理-單身", "密鑰");
		System.out.println("測試 = " + demo);
	}

}
