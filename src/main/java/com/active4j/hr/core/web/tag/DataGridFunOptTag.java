package com.active4j.hr.core.web.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 类描述：列表自定义函数操作项处理标签
 * 
 @author 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class DataGridFunOptTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8055167634284278459L;
	private String label;
	private String funName;//自定义函数名称
	private String operationCode;//按钮的操作Code
	private String icon; //按钮的图标
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspTagException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setFunUrl(label, funName, operationCode, icon);
		return EVAL_PAGE;
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

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
}
