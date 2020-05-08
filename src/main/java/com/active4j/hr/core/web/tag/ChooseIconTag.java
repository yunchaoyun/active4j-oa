package com.active4j.hr.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 图标的选择标签
 * @author chenxl
 *
 */
public class ChooseIconTag extends TagSupport{
	
	private String hiddenName;  //隐藏控件的name
	private String hiddenId; //隐藏控件的ID
	private String hiddenValue; //隐藏值
	
	
	public StringBuffer end() {
		StringBuffer sb = new StringBuffer(3000);
		
		sb = sb.append("<div class=\"input-group m-b\"><span class=\"input-group-addon\" id=\"myIcon\">" + this.getHiddenValue() +"</span>");
		sb = sb.append("<input id=\"" + this.getHiddenId() + "\" name=\"" + this.getHiddenName() + "\" type=\"text\" class=\"form-control\" value=\'" + this.getHiddenValue() + "\' readonly=\"readonly\">");
		sb = sb.append("<span class=\"input-group-btn\">").append("<button class=\"btn btn-primary\" onclick=\"doSelect();\" type=\"button\">选择</button><button class=\"btn btn-primary\" onclick=\"doClear();\" style=\"margin-left: 1px;\" type=\"button\">清空</button>");
		sb = sb.append("</span></div>");
		sb = sb.append("<script type=\"text/javascript\">");
		sb = sb.append(" function doSelect() { ");
		sb = sb.append(" layer.open({ ");
		sb = sb.append(" type : 2, ");
		sb = sb.append(" btn : [ '确定', '取消' ], ");
		sb = sb.append(" title : '选择', ");
		sb = sb.append(" shadeClose : true, ");
		sb = sb.append(" shade : 0.8, ");
		sb = sb.append(" area : [ '90%', '90%' ], ");
		sb = sb.append(" content : 'common/selectIcon', ");
		sb = sb.append(" yes : function(index, layero) { ");
		sb = sb.append(" var iframeWin = window[layero.find('iframe')[0]['name']]; ");
		sb = sb.append(" iframeWin.getValue(); ");
		sb = sb.append(" }, ");
		sb = sb.append(" cancel : function(index, layero) {}});} ");
		sb = sb.append(" function doClear() { $(\"#myIcon\").html(''); $(\"#" + this.getHiddenId() + "\").val('');}");
		sb = sb.append(" </script> ");
		return sb;
	}
	

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5656292771873553498L;

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public String getHiddenId() {
		return hiddenId;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	
}
