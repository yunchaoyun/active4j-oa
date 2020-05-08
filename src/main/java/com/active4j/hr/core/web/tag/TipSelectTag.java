package com.active4j.hr.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 提示方式的选择
 * @author chenxl
 *
 */
public class TipSelectTag extends TagSupport {

	private String name;
	
	private int defValue;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6726769249968170791L;

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"checkbox i-checks\"><label>");
		if(1 == defValue) {
			sb.append("<input type=\"checkbox\" value=\"1\" checked='checked' name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" name=\""+this.getName()+"\"><i></i> 微信提示");
		}else if(2 == defValue){
			sb.append("<input type=\"checkbox\" value=\"1\" name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" checked='checked' name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" name=\""+this.getName()+"\"><i></i> 微信提示");
		}else if(3 == defValue){
			sb.append("<input type=\"checkbox\" value=\"1\" checked='checked' name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" checked='checked' name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" name=\""+this.getName()+"\"><i></i> 微信提示");
		}else if(4 == defValue){
			sb.append("<input type=\"checkbox\" value=\"1\" name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" checked='checked' name=\""+this.getName()+"\"><i></i> 微信提示");
		}else if(5 == defValue){
			sb.append("<input type=\"checkbox\" value=\"1\" checked='checked' name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" checked='checked' name=\""+this.getName()+"\"><i></i> 微信提示");
		}else if(6 == defValue){
			sb.append("<input type=\"checkbox\" value=\"1\" name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" checked='checked' name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" checked='checked' name=\""+this.getName()+"\"><i></i> 微信提示");
		}else if(7 == defValue){
			sb.append("<input type=\"checkbox\" value=\"1\" checked='checked' name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" checked='checked' name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" checked='checked' name=\""+this.getName()+"\"><i></i> 微信提示");
		}else {
			sb.append("<input type=\"checkbox\" value=\"1\" name=\""+this.getName()+"\"><i></i> 系统消息");
			sb.append("<input type=\"checkbox\" value=\"2\" name=\""+this.getName()+"\"><i></i> 短信提示");
			sb.append("<input type=\"checkbox\" value=\"4\" name=\""+this.getName()+"\"><i></i> 微信提示");
		}
		sb.append("</label></div>");
		return sb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDefValue() {
		return defValue;
	}

	public void setDefValue(int defValue) {
		this.defValue = defValue;
	}
	
	
}
