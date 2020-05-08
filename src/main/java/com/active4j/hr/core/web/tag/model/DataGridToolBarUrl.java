package com.active4j.hr.core.web.tag.model;

/**
 * 
 * @author  chenxl
 *
 */
public class DataGridToolBarUrl {
	private String url;//操作链接地址
	private String label;//按钮名称
	private String type;//按钮类型
	private String message;//询问链接的提示语
	private String icon;
	private String funName;
	private String onclick;
	private String width;
	private String height;
	private String params; //参数
	private String operation;
	
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}

	
}
