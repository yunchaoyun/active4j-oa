package com.active4j.hr.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.active4j.hr.core.util.oConvertUtils;


/**
 * 
 * @author  chenxl
 *
 */
public class BaseTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type = "default";// 加载类型

	public void setType(String type) {
		this.type = type;
	}
	
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();
			
			String types[] = type.split(",");
			
			//meta部分
			sb.append("<meta charset=\"utf-8\">");
			sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			sb.append("<meta name=\"renderer\" content=\"webkit\">");
			sb.append("<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />");
			sb.append("<title>active4j-oa演示系统</title>");
			sb.append("<meta name=\"keywords\" content=\"active4j-oa演示系统\">");
			sb.append("<meta name=\"description\" content=\"active4j-oa演示系统\">");
			sb.append("<!--[if lt IE 9]><meta http-equiv=\"refresh\" content=\"0;ie.html\" /><![endif]-->");
			//基础包含
			sb.append("<link rel=\"shortcut icon\" href=\"favicon.ico\">");
			sb.append("<link href=\"static/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
			sb.append("<link href=\"static/bootstrap/css/font-awesome.min.css\" rel=\"stylesheet\">");
			sb.append("<link href=\"static/bootstrap/css/animate.min.css\" rel=\"stylesheet\">");
			sb.append("<link href=\"static/bootstrap/css/style.min.css\" rel=\"stylesheet\">");
			sb.append("<link href=\"static/toastr/css/toastr.min.css\" rel=\"stylesheet\">");
			
			
			sb.append("<script type=\"text/javascript\" src=\"static/jquery/js/jquery.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/bootstrap/js/bootstrap.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/content.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/contabs.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/bootstrap/js/qq.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/validate/js/jquery.validate.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/validate/js/messages_zh.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/validate/js/jquery.form.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/tools/js/tools.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/pace.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/jquery.metisMenu.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/jquery.slimscroll.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/layer/layer.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/hplus.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/index/js/pace.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"static/toastr/js/toastr.min.js\"></script>");
			
			//表格插件
			if (oConvertUtils.isIn("jqgrid", types)) {
				//css
				sb.append("<link href=\"static/jqgrid/css/ui.jqgridffe4.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/jqgrid/js/grid.locale-cnffe4.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/jqgrid/js/jquery.jqGrid.minffe4.js\"></script>");
				sb.append("<script type=\"text/javascript\">$.jgrid.defaults.styleUI = \"Bootstrap\";</script>");
			}
			
			//select2 下拉选择框插件
			if(oConvertUtils.isIn("select2", types)) {
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/select2/select2.min.js\"></script>");
				//css
				sb.append("<link href=\"static/select2/select2.min.css\" rel=\"stylesheet\">");
				//初始化
				sb.append("<script type=\"text/javascript\">$(function() {$(\".select2\").select2({tags: true});})</script>");
			}
			
			if(oConvertUtils.isIn("icheck", types)) {
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/icheck/js/icheck.min.js\"></script>");
				//css
				sb.append("<link href=\"static/icheck/css/custom.css\" rel=\"stylesheet\">");
				//初始化
				sb.append("<script type=\"text/javascript\">$(function() {$(\".i-checks\").iCheck({checkboxClass:\"icheckbox_square-green\",radioClass:\"iradio_square-green\",});})</script>");
			}
			
			//jstree插件的引入
			if(oConvertUtils.isIn("jstree", types)) {
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/jstree/js/jstree.min.js\"></script>");
				//css
				sb.append("<link href=\"static/jstree/css/style.min.css\" rel=\"stylesheet\">");
			}
			
			//bootstrap tree view插件的引入
			if(oConvertUtils.isIn("treeview", types)) {
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/treeview/js/bootstrap-treeview.min.js\"></script>");
				//css
				sb.append("<link href=\"static/treeview/css/bootstrap-treeview.min.css\" rel=\"stylesheet\">");
			}
			
			//百度　webuploader
			if(oConvertUtils.isIn("webuploader", types)) {
				//css
				sb.append("<link href=\"static/webuploader/webuploader.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/webuploader/webuploader.min.js\"></script>");
			}
			
			//ckeditor
			if(oConvertUtils.isIn("ckeditor", types)) {
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/ckeditor/ckeditor.js\"></script>");
			}
			
			//bootstrap datetimePicker
			if(oConvertUtils.isIn("datetimePicker", types)) {
				//css
				sb.append("<link href=\"static/datetimepicker/bootstrap-datetimepicker.min.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/datetimepicker/bootstrap-datetimepicker.min.js\"></script>");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/datetimepicker/bootstrap-datetimepicker.zh-CN.js\"></script>");
			}
			
			if(oConvertUtils.isIn("datePicker", types)) {
				//css
				sb.append("<link href=\"static/datepicker/bootstrap-datepicker.min.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/datepicker/bootstrap-datepicker.min.js\"></script>");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/datepicker/bootstrap-datepicker.zh-CN.min.js\"></script>");
			}
			
			
			//pretty file prettyfile
			if(oConvertUtils.isIn("prettyfile", types)) {
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/prettyfile/bootstrap-prettyfile.js\"></script>");
				sb.append("<script type=\"text/javascript\">$(function() {$(\"input[type='file']\").prettyFile();});</script>");
			}
			
			//表单向导插件
			if(oConvertUtils.isIn("step", types)) {
				//css
				sb.append("<link href=\"static/step/css/jquery.steps.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/step/js/jquery.steps.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/step/js/init.js\"></script>");
			}
			
			//laydate插件
			if(oConvertUtils.isIn("laydate", types)) {
				sb.append("<script type=\"text/javascript\" src=\"static/laydate/laydate.js\"></script>");
			}
			
			//xform插件
			if(oConvertUtils.isIn("xform", types)){
				//css
				sb.append("<link href=\"static/xform/styles/xform.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/xform/xform-all.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/xform/adaptor.js\"></script>");
			}
			
			//时间选择
			if(oConvertUtils.isIn("clock", types)) {
				//css
				sb.append("<link href=\"static/clockpicker/css/clockpicker.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/clockpicker/js/clockpicker.js\"></script>");
			}
			
			//fullcalender日历 带阴历
			if(oConvertUtils.isIn("fullcalender", types)) {
				//css
				sb.append("<link href=\"static/fullcalendar/css/fullcalendar.min.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar/js/moment.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar/js/lunar.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar/js/fullcalendar.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar/js/zh-cn.js\"></script>");
				
			}
			
			//fullcalender日历管理
			if(oConvertUtils.isIn("fullcalender2", types)) {
				//css
				sb.append("<link href=\"static/fullcalendar2/css/fullcalendar.min.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar2/js/moment.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar2/js/fullcalendar.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/fullcalendar2/js/zh-cn.js\"></script>");
				
			}
			
			//summernote
			if(oConvertUtils.isIn("summernote", types)) {
				//css
				sb.append("<link href=\"static/summernote/summernote.css\" rel=\"stylesheet\">");
				sb.append("<link href=\"static/summernote/summernote-bs3.css\" rel=\"stylesheet\">");
				//js
				sb.append("<script type=\"text/javascript\" src=\"static/summernote/summernote.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/summernote/summernote-zh-CN.js\"></script>");
				
			}
			
			//jqueryui
			if(oConvertUtils.isIn("jqueryUi", types)) {
				sb.append("<script type=\"text/javascript\" src=\"static/jquery/js/jquery-ui-1.10.4.min.js\"></script>");
			}
			
			//peity
			if(oConvertUtils.isIn("peity", types)) {
				sb.append("<script type=\"text/javascript\" src=\"static/peity/js/jquery.peity.min.js\"></script>");
			}
			
			//ion.rangeSlider
			if(oConvertUtils.isIn("ionrange", types)) {
				
				sb.append("<link href=\"static/ionrange/css/ion.rangeSlider.css\" rel=\"stylesheet\">");
				sb.append("<link href=\"static/ionrange/css/ion.rangeSlider.skinFlat.css\" rel=\"stylesheet\">");
				
				sb.append("<script type=\"text/javascript\" src=\"static/ionrange/js/ion.rangeSlider.min.js\"></script>");
			}
			
			//echarts图表
			if(oConvertUtils.isIn("echarts", types)) {
				sb.append("<script type=\"text/javascript\" src=\"static/echarts/echarts.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/echarts/macarons.js\"></script>");
			}
			
			//cropper
			if(oConvertUtils.isIn("cropper", types)) {
				sb.append("<link href=\"static/cropper/cropper.min.css\" rel=\"stylesheet\">");
				
				sb.append("<script type=\"text/javascript\" src=\"static/cropper/cropper.min.js\"></script>");
				sb.append("<script type=\"text/javascript\" src=\"static/cropper/StackBlur.js\"></script>");
			}
			
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
