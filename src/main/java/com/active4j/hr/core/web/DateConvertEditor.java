package com.active4j.hr.core.web;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

/**
 * 日期类型的转换器
 *
 */
public class DateConvertEditor extends PropertyEditorSupport {
	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat datetime2Format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
			    if(text.indexOf(":") > 0 && text.length() == 5){
			    	setValue(this.timeFormat.parse(text));
			    }else if (text.indexOf(":") == -1 && text.length() == 10) {
					setValue(this.dateFormat.parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					setValue(this.datetimeFormat.parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 16) {
					setValue(this.datetime2Format.parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 21) {
					text = text.replace(".0", "");
					setValue(this.datetimeFormat.parse(text));
				} else {
					throw new IllegalArgumentException(
							"Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException(
						"Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}
}
