package com.active4j.hr.core.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * int类型的转换器， 将页面空字符串转为后台int:0
 */
public class IntegerConvertEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isEmpty(text)) {
			text = "0";
		}
		Integer i = Integer.parseInt(text);
		super.setValue(i);
	}
	
}
