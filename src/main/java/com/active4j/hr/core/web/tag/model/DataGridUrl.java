package com.active4j.hr.core.web.tag.model;

/**
 * 
 * @author  chenxl
 *
 */
public class DataGridUrl {
	private String url;//操作链接地址
	private String label;//按钮名称
	private OptTypeDirection type;//按钮类型
	private String message;//询问链接的提示语
	private String funName; //js操作函数
	private String icon; //按钮显示的图标
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
	public OptTypeDirection getType() {
		return type;
	}
	public void setType(OptTypeDirection type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
