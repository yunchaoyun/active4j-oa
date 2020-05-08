package com.active4j.hr.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * 类描述：选择器标签
 * 
 */
public class ChooseTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5876519432866632383L;
	private String hiddenName;  //隐藏控件的name
	private String hiddenId; //隐藏控件的ID
	private String hiddenValue; //隐藏值
	private String textName;//显示文本框字段
	private String url; //弹出框的URL
	private String width; //弹出框的宽度
	private String height; //弹出框的高度
	private String textId;// 显示框取值ID
	private String title; //显示标题
	private String textValue; //显示值
	private boolean showClear = true; //是否显示清除按钮

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer(3000);
		//拼接显示控件
		sb = sb.append("<input type=\"text\" readonly=\"readonly\" class=\"form-control\" name=\"").append(this.getTextName()).append("\" value=\"").append(this.getTextValue()).append("\" id=\"").append(this.getTextId()).append("\">");
		//拼接隐藏控件
		sb = sb.append("<input type=\"hidden\" readonly=\"readonly\" class=\"form-control\" name=\"").append(this.getHiddenName()).append("\" value=\"").append(this.getHiddenValue()).append("\" id=\"").append(this.getHiddenId()).append("\">");
		//拼接按钮区域
		sb = sb.append("<span class=\"input-group-btn\">");
		sb = sb.append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"doSelect" + this.getHiddenId() + "();\">选择</button>");
		if(showClear) {
			sb = sb.append("<button type=\"button\" class=\"btn btn-primary\" style=\"margin-left: 1px;\" onclick=\"doClear" + this.getHiddenId() + "();\">清空</button>");
		}
		sb = sb.append("</span>");
		sb = sb.append("<script type=\"text/javascript\">");
		sb = sb.append("function doSelect"+this.getHiddenId()+"() {");
		sb = sb.append("layer.open({");
		sb = sb.append("type: 2,");
		sb = sb.append("btn: ['确定','取消'],");
		if(StringUtils.isEmpty(this.getTitle())) {
			sb = sb.append("title: '选择',");
		}else{
			sb = sb.append("title:").append("'").append(this.getTitle()).append("',");
		}
		sb = sb.append("shadeClose: true,");
		sb = sb.append("shade: 0.8,");
		sb = sb.append("area:[");
		if(StringUtils.isEmpty(this.getWidth())) {
			sb = sb.append("'60%',");
		}else {
			sb = sb.append("'").append(this.getWidth()).append("',");
		}
		if(StringUtils.isEmpty(this.getHeight())) {
			sb = sb.append("'75%'");
		}else{
			sb = sb.append("'").append(this.getHeight()).append("'");
		}
		sb = sb.append("],");
		sb = sb.append("content:").append("'").append(this.getUrl()).append("',");
		sb = sb.append("yes : function(index, layero) {");
//		sb = sb.append("var iframeWin = window[layero.find('iframe')[0]['name']];");
//		sb = sb.append("iframeWin.getValue();");
		sb = sb.append("var node = frames['layui-layer-iframe' + index].getValue();");
		sb = sb.append("$('#" + this.getTextId() + "').val(node.text);$('#" + this.getHiddenId() +"').val(node.id);layer.close(index);");
		sb = sb.append("},");
		sb = sb.append("cancel : function(index, layero) {");
		sb = sb.append("}});}");
		sb = sb.append("function doClear" + this.getHiddenId() + "() {");
		sb = sb.append("$(\"#").append(this.getTextId()).append("\").val('');");
		sb = sb.append("$(\"#").append(this.getHiddenId()).append("\").val('');");
		sb = sb.append("}");
		sb = sb.append("</script>");
				
		return sb;
	}

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

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getTextId() {
		return textId;
	}

	public void setTextId(String textId) {
		this.textId = textId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public boolean isShowClear() {
		return showClear;
	}

	public void setShowClear(boolean showClear) {
		this.showClear = showClear;
	}

	
}
