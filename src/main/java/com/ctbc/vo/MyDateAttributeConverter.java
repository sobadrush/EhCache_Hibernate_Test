package com.ctbc.vo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MyDateAttributeConverter implements AttributeConverter<java.sql.Date, String> {

	@Override
	public String convertToDatabaseColumn(java.sql.Date attribute) {
//		System.out.println("MyDateAttributeConverter , attribute >>> " + attribute);
		return attribute.toString();
	}

	@Override
	public java.sql.Date convertToEntityAttribute(String dbData) {
//		System.out.println("MyDateAttributeConverter , dbData >>> " + dbData);		
		return java.sql.Date.valueOf(dbData);
	}

}


