package com.active4j.hr.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.active4j.hr.core.shiro.ShiroUtils;


/**
 * 
 * @title MenuTag.java
 * @description 
		  系统导航菜单
 * @time  2020年1月16日 下午3:20:20
 * @author 麻木神
 * @version 1.0
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
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
		sb.append(ShiroUtils.getSessionUser().getMenus());
		return sb;
	}

	
	

}
