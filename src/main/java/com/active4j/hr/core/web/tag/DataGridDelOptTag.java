package com.active4j.hr.core.web.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 类描述：列表删除操作项标签
 * 
 * @author chenxl
 * @version 1.0
 */
public class DataGridDelOptTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3088664766236016370L;
	//操作链接
	private String url;
	//链接名称
	protected String label;
	//询问链接的提示语
	private String message;
	//权限控制按钮的操作Code
	private String operationCode;
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	public int doEndTag() throws JspTagException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setDelUrl(url, label, message, operationCode);
		return EVAL_PAGE;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	
	
	
	
}
