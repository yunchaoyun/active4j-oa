package com.active4j.hr.core.web.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *  @author chenxl
 * 类描述：列表工具条标签
 */
public class DataGridToolBarTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8198029397381720574L;
	private String type;
	private String url;
	private String label;
	private String funName;//自定义函数名称
	private String icon;//图标
	private String onclick;
	private String width;
	private String height;
	private String operationCode;//按钮的操作Code
	private String params; // 传递的参数
	private String operation;
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	public int doEndTag() throws JspTagException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setToolBar(type, url, label, icon, funName, onclick ,width, height, operationCode, params, operation);
		return EVAL_PAGE;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
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
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
}
