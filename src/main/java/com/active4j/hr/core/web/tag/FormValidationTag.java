package com.active4j.hr.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author  chenxl
 *
 */
public class FormValidationTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4864029171083732744L;
	private String formid = "commonForm";// 表单FORM ID
	private String action;// 表单提交路径
	private String beforeSubmit;
	private String cssClass;
	
	//标签起始位子
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();
			
			if(StringUtils.isNotEmpty(cssClass)) {
				sb.append("<form class=\"" + cssClass + "\" id=\"" + this.getFormid() + "\" action=\"" + this.getAction() + "\" method=\"post\" >");
			}else {
				sb.append("<form class=\"form-horizontal m-t\" id=\"" + this.getFormid() + "\" action=\"" + this.getAction() + "\" method=\"post\" >");
			}
			
			
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	//标签结束位子
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();
			
			sb.append("</form>");
			//按钮部分
//			sb.append(" <div class=\"row\">");
//			sb.append("<div class=\"col-sm-12 text-right\">");
//			sb.append("<p>");
//			sb.append("<button class=\"btn btn-info\" onclick=submitL(); type=\"submit\">");
//			sb.append("<i class=\"fa fa-check\"></i>&nbsp;&nbsp;确定");
//			sb.append("</button>");
//			sb.append("<button class=\"btn btn-outline btn-primary\"  onclick=closeL(); type=\"button\" style=\"margin-left:4px;\">");
//			sb.append("<i class=\"fa fa-close\"></i>&nbsp;&nbsp;关闭");
//			sb.append("</button>");
//			sb.append("</p></div></div>");
			
			//脚本部分
			sb.append("<script type=\"text/javascript\">");
			
			//表单验证初始化方法
			sb.append("$(function() { $(\"#" + this.getFormid() +  "\").validate({ submitHandler : function(form) {$(form).ajaxSubmit({ success : function(data) {")
				.append(" if(data.success) { parent.qhTipSuccess(\'保存成功\'); closeL(); } else { qhTipWarning(data.msg);}},error : function(data){qhTipError('系统错误，请联系系统管理员');}});}});});");
			
			//提交按钮部分
			sb.append(" function submitL() { if(!beforeSubmit()){ return; } $(\"#" + this.getFormid() + "\").submit(); } ");
			sb.append(" function beforeSubmit() { ");
			if(StringUtils.isNotEmpty(beforeSubmit)) {
				sb.append(" return ").append(beforeSubmit);
			}else {
				sb.append(" return true; ");
			}
			sb.append("}");
			
			//关闭按钮部分
			sb.append("function closeL() {").append(" var index = parent.layer.getFrameIndex(window.name); ").append(" parent.layer.close(index); }");
			sb.append(" </script>");
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	public String getFormid() {
		return formid;
	}
	public void setFormid(String formid) {
		this.formid = formid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getBeforeSubmit() {
		return beforeSubmit;
	}

	public void setBeforeSubmit(String beforeSubmit) {
		this.beforeSubmit = beforeSubmit;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
}
