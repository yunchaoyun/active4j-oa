package com.active4j.hr.core.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;

/**
 * 
 * 选择下拉框  根据数据字典取值
 * 
 * @author: chenxl
 * 
 */
public class DictSelectTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4778844658142375673L;

	
	//表单提交name
	private String name;
	
	private String type;
	
	//字典key
	private String typeGroupCode;
	
	private String defaultVal;
	
	private String clickEvent;
	
	
	@Override
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}
	
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

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if(StringUtils.equals("radio", this.getType())) {
			sb.append("<div class=\"radio i-checks\">");
			if(StringUtils.isNotEmpty(this.getTypeGroupCode())) {
				List<SysDicValueEntity> types = SystemUtils.getDictionaryLst(this.typeGroupCode);
				if(null != types && types.size() > 0) {
					for(SysDicValueEntity type : types) {
						radio(type.getLabel(), type.getValue(), sb);
					}
					
				}
			}
			sb.append("</div>");
		}else if(StringUtils.equals("select", this.getType())) {
			if(StringUtils.isNotEmpty(clickEvent)) {
				sb.append("<select name=\"" + this.getName() + "\" class=\"form-control\" onchange=\"" + this.getClickEvent() + "\">");
			}else{
				sb.append("<select name=\"" + this.getName() + "\" class=\"form-control\">");
			}
			
			if(StringUtils.isNotEmpty(this.getTypeGroupCode())) {
				List<SysDicValueEntity> types = SystemUtils.getDictionaryLst(this.typeGroupCode);
				if(null != types && types.size() > 0) {
					for(SysDicValueEntity type : types){
						select(type.getLabel(), type.getValue(), sb);
					}
				}
			}
			sb.append("</select>");
		}
		
		return sb;
	}

	/**
	 * 单选框方法
	 * 
	 * @param name
	 * @param code
	 * @param sb
	 */
	private void radio(String name, String code, StringBuffer sb) {
		if (StringUtils.equals(code, this.defaultVal)) {
			sb.append("<label><input type=\"radio\" name=\"" + this.getName() + "\" checked=\"checked\" value=\"" + code + "\"");
			
			sb.append(" />&nbsp;" + name + "</label>");
		} else {
			
			sb.append("<label><input type=\"radio\" name=\"" + this.getName() + "\" value=\"" + code + "\"");
			
			sb.append(" />&nbsp;" + name + "</label>");
		}
		
	}
	
	/**
	 * 下拉框方法
	 * 
	 * @param name
	 * @param code
	 * @param sb
	 */
	private void select(String name, String code, StringBuffer sb) {
		if (StringUtils.equals(code, this.defaultVal)) {
			sb.append("<option value='" + code + "' selected='selected'>" + name + "</option>");
		} else {
			
			sb.append("<option value='" + code + "'>" + name + "</option>");
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeGroupCode() {
		return typeGroupCode;
	}

	public void setTypeGroupCode(String typeGroupCode) {
		this.typeGroupCode = typeGroupCode;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getClickEvent() {
		return clickEvent;
	}

	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}
	
	
}
