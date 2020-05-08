package com.active4j.hr.core.web.tag;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.oConvertUtils;
import com.active4j.hr.core.web.tag.model.DataGridColumn;
import com.active4j.hr.core.web.tag.model.DataGridToolBarUrl;
import com.active4j.hr.core.web.tag.model.DataGridUrl;
import com.active4j.hr.core.web.tag.model.OptTypeDirection;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;


/**
 * jqgrid表格处理类
 * @author chenxl
 * @version 1.0
 */
public class DataGridTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1095062373096776623L;
	private String tableContentId;
	//获取数据的地址
	private String actionUrl;
	//显示字段
	private String fields = "";
	//表格名称
	private String caption;
	//表格高度
	private String height = "";
	//表格宽度设置
	private boolean autoWidth = true;
	private int width;
	//此属性用来说明当初始化列宽度时候的计算类型，如果为ture，则按比例初始化列宽度。如果为false，则列宽度使用colModel指定的宽度
	private boolean fit = true;
	//排序设置
	private boolean sortable = true;
	private String sortName;
	private String sortOrder = "asc";
	//是否显示默认序号
	private boolean rownumbers = true;
	//序号宽度
	private int rownumWidth = 25;
	//是否多选
	private boolean multiSelect = false;
	//是否分页
	private int pageSize = 20;
	//表格是否可以隐藏
	private boolean hidegrid = false;
	//表格ID
	private String name;
	//是否要显示总记录数
	private boolean viewRecords = true;
	//表格是否为树形表格
	private boolean treeGrid = false;
	//树形表格的展开列列名
	private String treeColomnName;
	
	//查询面板所在的Id
	private String searchGroupId;
	
	private boolean load = false;
	
	private String loadFun = "";
	
	// 表格中数据显示
	private List<DataGridColumn> columnList = new ArrayList<DataGridColumn>();
	// 表格中操作显示
	private List<DataGridUrl> urlList = new ArrayList<DataGridUrl>();
	// 表格上方工具条
	private List<DataGridToolBarUrl> toolBarUrl = new ArrayList<DataGridToolBarUrl>();

	//设置列元素
	public void setColumn(String align, String classes, String datefmt, String defval, boolean fixed, boolean hidden, boolean key, String label, String name, boolean sortable, Integer width, String dictionary, String replace, boolean query, String common, String queryId, String valueId, String labelCol, String inputCol, String queryModel, String datePlugin, String display, boolean image, String imageSize){
		DataGridColumn dataGridColumn = new DataGridColumn();
		dataGridColumn.setAlign(align);
		dataGridColumn.setClasses(classes);
		dataGridColumn.setDatefmt(datefmt);
		dataGridColumn.setDefval(defval);
		dataGridColumn.setFixed(fixed);
		dataGridColumn.setHidden(hidden);
		dataGridColumn.setKey(key);
		dataGridColumn.setLabel(label);
		dataGridColumn.setName(name);
		dataGridColumn.setSortable(sortable);
		dataGridColumn.setWidth(width);
		dataGridColumn.setQuery(query);
		dataGridColumn.setDictionary(dictionary);
		dataGridColumn.setReplace(replace);
		dataGridColumn.setCommon(common);
		dataGridColumn.setQueryId(queryId);
		dataGridColumn.setValueId(valueId);
		dataGridColumn.setLabelCol(labelCol);
		dataGridColumn.setInputCol(inputCol);
		dataGridColumn.setQueryModel(queryModel);
		dataGridColumn.setDatePlugin(datePlugin);
		dataGridColumn.setDisplay(display);
		dataGridColumn.setImage(image);
		dataGridColumn.setImageSize(imageSize);
		
		//数据字典不为空
		if(StringUtils.isNotEmpty(dictionary)) {
			List<SysDicValueEntity> typeList = SystemUtils.getDictionaryLst(dictionary);
			if(null != typeList && typeList.size() > 0) {
				//存放数据字典的key value值
				Map<String, String> map = new HashMap<String, String>();
				for (SysDicValueEntity type : typeList) {
					map.put(type.getValue(), type.getLabel());
				}
				dataGridColumn.setMap(map);
			}
		}
		
		//值替换
		if(StringUtils.isNotEmpty(replace)) {
			String[] strs = replace.split(",");
			Map<String, String> map = new HashMap<String, String>();
			for (String string : strs) {
				map.put(string.substring(string.indexOf("_") + 1), string.substring(0, string.indexOf("_")));
			}
			dataGridColumn.setMap(map);
		}
		
		if(StringUtils.isNotEmpty(display) && StringUtils.equals("percent", display)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("display", "display");
			dataGridColumn.setMap(map);
		}
		
		columnList.add(dataGridColumn);
		
		//设置显示属性
		fields += name + ",";
		
	}
	
	/**
	 * 设置列表中的删除按钮
	 * @param url
	 * @param label
	 * @param message
	 * @param operationCode
	 */
	public void setDelUrl(String url, String label, String message, String operationCode) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setUrl(url);
		dataGridUrl.setLabel(label);
		dataGridUrl.setMessage(message);
		dataGridUrl.setType(OptTypeDirection.Del);
		installOperationCode(dataGridUrl, operationCode, urlList);
	}
	
	/**
	 * 设置列表中的操作按钮
	 * @param label
	 * @param funName
	 * @param operationCode
	 */
	public void setFunUrl(String label, String funName, String operationCode, String icon) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setLabel(label);
		dataGridUrl.setFunName(funName);
		dataGridUrl.setIcon(icon);
		dataGridUrl.setType(OptTypeDirection.Fun);
		installOperationCode(dataGridUrl, operationCode, urlList);
	}
	
	/**
	 * 设置列表上方的工具条
	 * @param type
	 * @param url
	 * @param label
	 * @param icon
	 * @param funName
	 * @param onclick
	 * @param width
	 * @param height
	 * @param operationCode
	 */
	public void setToolBar(String type, String url, String label, String icon, String funName, String onclick, String width, String height, String operationCode, String params, String operation){
		DataGridToolBarUrl toolBar = new DataGridToolBarUrl();
		toolBar.setType(type);
		toolBar.setUrl(url);
		toolBar.setLabel(label);
		toolBar.setIcon(icon);
		toolBar.setFunName(funName);
		toolBar.setOnclick(onclick);
		toolBar.setWidth(width);
		toolBar.setHeight(height);
		toolBar.setParams(params);
		toolBar.setOperation(operation);
		installOperationCode(toolBar, operationCode, toolBarUrl);
	}
	

	/**
	 * jqgrid 生成表格方法
	 * @return
	 */
	public StringBuffer jqgrid() {
		StringBuffer sb = new StringBuffer();
		//开始拼接
		sb.append("<script type=\"text/javascript\">");
		sb.append("$(function(){");
		
		//生成表格
		sb.append("var tableHtml = \"<table id='" + this.getName() +"'></table>\"; ");
//		//生成分页工具条div
		sb.append("var tablePagerHtml = \"<div id='" + this.getName() +"Pager'></div>\"; ");
		//将分页表格插入到表格容器中
		sb.append("$(\"#" + this.getTableContentId() + "\").append(tableHtml).append(tablePagerHtml); ");
		
		//jqgrid api 生成表格
		sb.append("$(\'#" + this.getName() + "\').jqGrid({");
		sb.append("url:\'" + actionUrl + "?field=" + fields + "\',");
		sb.append("datatype : \"JSON\",");
		//默认值查询
		sb.append("postData : {");
		boolean isQuery = true;
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			if(column.isQuery()) {
				if(StringUtils.isNotEmpty(column.getDefval())) {
					sb.append("\"" + column.getName() + "\"").append(":").append("\"" + column.getDefval() + "\",");
				}
				if(isQuery) {
					isQuery = false;
				}
			}
		}
		sb.append("},");
		if(StringUtils.isEmpty(this.getHeight())) {
			if(isQuery) {
				sb.append("height: $(window).height() - 200,");
			}else {
				sb.append("height: $(window).height() - 370,");
			}
			
		}else{
			if(StringUtils.equals("auto", this.getHeight())) {
				sb.append("height: \"auto\",");
			}else {
				sb.append("height: \"" + this.getHeight() + "\",");
			}
		}
		if(this.isSortable()) {
			sb.append("sortname: \"" + this.getSortName() + "\",");
			sb.append("sortorder: \"" + this.getSortOrder() + "\",");
		}
		sb.append("autowidth: " + this.isAutoWidth() + ",");
		sb.append("width: " + this.getWidth() + ",");
		sb.append("shrinkToFit: " + this.isFit() + ",");
		//树形结构 不能显示行号
		if(!this.isTreeGrid()) {
			sb.append("rownumbers: " + this.isRownumbers() + ",");
		}
		
		sb.append("rowNum: " + this.getPageSize() + ",");
		sb.append("multiselect: " + this.isMultiSelect() + ",");
		sb.append("rowList : [" + pageSize * 1 + "," + pageSize * 2 + "," + pageSize * 3 + "],");
		sb.append("pager: \"#" + this.getName() + "Pager\",");
		sb.append("viewrecords: " + this.isViewRecords() + ",");
		sb.append("caption: \"" + this.getCaption() + "\",");
		sb.append("hidegrid: " + this.isHidegrid() + ",");
		//表格显示列处理
		getColumns(sb);
		
		//树形表格处理
		if(treeGrid) {
			getTreeColumns(sb);
		}
		
		//json格式的数据处理
		sb.append("jsonReader : {results : \"results\", page : \"page\", total : \"totalPage\", records : \"total\", repeatitems : false}");
		
		//jqgrid事件处理
		if(load) {
			sb.append(",loadComplete:function(){" + this.getLoadFun() + "}");
		}
		
		
		sb.append("});");
		
		//表格工具条的处理
		if(null != toolBarUrl && toolBarUrl.size() > 0) {
			StringBuffer sbToolBar = new StringBuffer();
			sbToolBar.append("\"<p>");
			for(DataGridToolBarUrl toolBar : toolBarUrl) {
				/**
				 * 这里设置几个常用的按钮，新增，编辑，查看，导出，导入，打印，上传，下载等，后期可以加
				 */
				if(StringUtils.equals("add", toolBar.getType())) {
					//新增
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=addQh(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'"+ (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\',\'"+(StringUtils.isEmpty(toolBar.getParams()) ? "" : toolBar.getParams())+"\');><i class=\'fa fa-file-o\'></i>&nbsp;新增</button>");
				}else if(StringUtils.equals("addNo", toolBar.getType())){
					//新增 不带确定按钮
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=addQhNo(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'"+ (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\',\'"+(StringUtils.isEmpty(toolBar.getParams()) ? "" : toolBar.getParams())+"\');><i class=\'fa fa-file-o\'></i>&nbsp;新增</button>");
				}else if(StringUtils.equals("edit", toolBar.getType())){
					//编辑
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=editQh(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'" + (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\',\'"+(StringUtils.isEmpty(toolBar.getParams()) ? "" : toolBar.getParams())+"\');><i class=\'fa fa-paste\'></i>&nbsp;编辑</button>");
				}else if(StringUtils.equals("editNo", toolBar.getType())){
					//编辑 不带确定按钮
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=editQhNo(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'" + (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\',\'"+(StringUtils.isEmpty(toolBar.getParams()) ? "" : toolBar.getParams())+"\');><i class=\'fa fa-paste\'></i>&nbsp;编辑</button>");
				}else if(StringUtils.equals("view", toolBar.getType())){
					//查看
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=viewQh(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'" + (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\',\'"+(StringUtils.isEmpty(toolBar.getParams()) ? "" : toolBar.getParams())+"\');><i class=\'fa fa-eye\'></i>&nbsp;查看</button>");
				}else if(StringUtils.equals("export", toolBar.getType())){
					//导出
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=\'" + toolBar.getFunName() + "();\'><i class=\'fa fa-level-up\'></i>&nbsp;导出</button>");
				}else if(StringUtils.equals("import", toolBar.getType())){
					//导入
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:5px;\' type=\'button\' onclick=\'importQh();\'><i class=\'fa fa-level-down\'></i>&nbsp;导入</button>");
				}else if(StringUtils.equals("print", toolBar.getType())){
					//打印
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=\'printQh();\'><i class=\'fa fa-print\'></i>&nbsp;打印</button>");
				}else if(StringUtils.equals("print", toolBar.getType())){
					//上传
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=\'uploadQh();\'><i class=\'fa fa-upload\'></i>&nbsp;上传</button>");
				}else if(StringUtils.equals("print", toolBar.getType())){
					//下载
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=\'downloadQh();\'><i class=\'fa fa-download\'></i>&nbsp;下载</button>");
				}else if(StringUtils.equals("pop", toolBar.getType())){
					//弹出框类型 输入值  保存
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=pop(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'" + toolBar.getLabel() + "\',\'"+ (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\');><i class=\'" + toolBar.getIcon() + "\'></i>&nbsp;" + toolBar.getLabel() + "</button>");
				}else if(StringUtils.equals("read", toolBar.getType())){
					//弹出框类型 输入值  无保存
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=read(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'" + toolBar.getLabel() + "\',\'"+ (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\');><i class=\'" + toolBar.getIcon() + "\'></i>&nbsp;" + toolBar.getLabel() + "</button>");
				}else if(StringUtils.equals("check", toolBar.getType())){
					//弹出框类型 输入值  无保存 无取消
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=docheck(\'" + this.getName() + "\',\'" + toolBar.getUrl() + "\',\'" + toolBar.getLabel() + "\',\'"+ (StringUtils.isEmpty(toolBar.getWidth()) ? "" : toolBar.getWidth()) +"\',\'"+(StringUtils.isEmpty(toolBar.getHeight()) ? "" : toolBar.getHeight())+"\');><i class=\'" + toolBar.getIcon() + "\'></i>&nbsp;" + toolBar.getLabel() + "</button>");
				}else if(StringUtils.equals("define", toolBar.getType())){
					//自定义的函数
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=\'" + toolBar.getFunName() + "();\'><i class=\'" + toolBar.getIcon() + "\'></i>&nbsp;" + toolBar.getLabel() + "</button>");
				}else if(StringUtils.equals("refresh", toolBar.getType())){
					//刷新
					sbToolBar.append("<button class=\'btn btn-primary\' style=\'margin-left:3px;\' type=\'button\' onclick=reloadTable(\'" + this.getName() + "\');><i class=\'fa fa-refresh\'></i>&nbsp;刷新</button>");
				}else if(StringUtils.equals("jump", toolBar.getType())){
					if(StringUtils.equals("add", toolBar.getOperation())) {
						//跳转到新增标签页
						sbToolBar.append("<a class=\'btn btn-primary J_menuItem2\' style=\'margin-left:3px;\' href=\'" + toolBar.getUrl() + "\' data-index=\'11001\'> <i class=\'fa fa-file-o\'></i>&nbsp;新增</a>");
					}else if(StringUtils.equals("edit", toolBar.getOperation())) {
						//跳转到编辑标签页
						sbToolBar.append("<a class=\'btn btn-primary J_menuItem4\' style=\'margin-left:3px;\' href=\'" + toolBar.getUrl() + "\' urlId=\'" + this.getName() + "\' data-index=\'11002\'> <i class=\'fa fa-paste\'></i>&nbsp;编辑</a>");
					}
					
				}else{
					
				}
				
			}
			sbToolBar.append("</p>\"");
			
			sb.append("$(\"#" + this.getTableContentId() + "\").before(" + sbToolBar + ");");
		}
		
		//查询界面的处理
		getSearchColumns(sb);
		
		//如果有日期查询框，则初始化日期时间控件
		int result = hasDateTime();
		if(result == 1) {
			sb.append("  $('.form_datetime').datetimepicker({language: 'zh-CN',weekStart: 1, todayBtn: 1,autoclose: 1,todayHighlight: 1,startView: 2,minuteStep:1,minView:0,forceParse: 0,format:'yyyy-mm-dd hh:ii:ss',showMeridian: 1}); ");
		} else if(result == 2){
			sb.append("  $('.form_datetime').datetimepicker({language: 'zh-CN',weekStart: 1, todayBtn: 1,autoclose: 1,todayHighlight: 1,startView: 2,minuteStep:1,forceParse: 0,format:'yyyy-mm-dd hh:ii',showMeridian: 1}); ");
		} else if(result == 3){
			sb.append("  $('.form_datetime').datetimepicker({language: 'zh-CN',weekStart: 1, todayBtn: 1,autoclose: 1,todayHighlight: 1,startView: 2,minuteStep:1,forceParse: 0,format:'yyyy-mm-dd hh:ii',showMeridian: 1}); ");
		} else if(result == 4){
			sb.append("  $('.form_date').datetimepicker({language: 'zh-CN',weekStart: 1, todayBtn: 1,autoclose: 1,todayHighlight: 1,startView: 2,minView: 2,forceParse: 0,format:'yyyy-mm-dd'}); ");
		} else if(result == 5){
			//待处理部分
		} else if(result == 6){
		} else if(result == 7){
		}
		
		sb.append(" }); ");
		
		//刷新表格操作
//		sb.append(" function reloadTable(){ ");
//		sb.append(" try{ ");
//		sb.append(" $(\'#" + this.getName() + "\').trigger(\'reloadGrid\'); ");
//		sb.append(" }catch(ex){} ");
//		sb.append(" } ");
		
		
		//查询方法的处理
		getSearchAction(sb);
		//如果有部门的选择，则显示一下代码
		if(hasDepart()) {
			sb.append(" function doSelect22() {top.layer.open({type: 2,btn: ['确定','取消'],title: '选择',shadeClose: true,shade: 0.8,area:['60%','75%'],content:'common/selectDepart',yes : function(index, layero) {var node = top.frames['layui-layer-iframe' + index].getValue(); $('#departLabel').val(node.text);$('#deptId').val(node.id);top.layer.close(index);},cancel : function(index, layero) {}});};");
			sb.append(" function doClear22(){$('#departLabel').val(\"\"); $('#deptId').val(\"\");}; ");
		}
		//如果有岗位的选择，则显示一下代码
		if(hasJob()){
			sb.append(" function doSelect33() {top.layer.open({type: 2,btn: ['确定','取消'],title: '选择',shadeClose: true,shade: 0.8,area:['60%','75%'],content:'commonController.do?selectJob',yes : function(index, layero) {var node = top.frames['layui-layer-iframe' + index].getValue(); $('#jobLabel').val(node.text);$('#tsJobId').val(node.id);top.layer.close(index);},cancel : function(index, layero) {}});};");
			sb.append(" function doClear33(){$('#jobLabel').val(\"\"); $('#tsJobId').val(\"\");}; ");
		}
		
		sb.append("</script>");
		return sb;
	}
	
	/**
	 * 表格显示列的处理
	 * @param sb
	 * @return
	 */
	private StringBuffer getColumns(StringBuffer sb) {
		sb = sb.append("colModel : [");
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			sb = sb.append("{");
			sb = sb.append("align : \"").append(column.getAlign()).append("\", ");
			sb = sb.append("name : \"").append(column.getName()).append("\", ");
			sb = sb.append("index : \"").append(column.getName()).append("\", ");
			sb = sb.append("classes : \"").append(column.getClasses()).append("\", "); 
			if(StringUtils.isNotEmpty(column.getDatefmt())) {
				sb = sb.append("datefmt : \"").append(column.getDatefmt()).append("\", ");
				if(StringUtils.equals(DateUtils.YYYY_MM_DD_MM_HH_SS, column.getDatefmt())) {
					sb = sb.append("formatter : 'date',formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'},");
				}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD_MM_HH, column.getDatefmt())) {
					sb = sb.append("formatter : 'date',formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i'},");
				}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD, column.getDatefmt())) {
					sb = sb.append("formatter : 'date',formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d'},");
				}else if(StringUtils.equals(DateUtils.STR_HH_MM, column.getDatefmt())) {
					sb = sb.append("formatter : 'date',formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'H:i'},");
				}
				
			}
			if(StringUtils.isNotEmpty(column.getDefval())) {
				sb = sb.append("defval : \"").append(column.getDefval()).append("\", "); 
			}
			
			if(null != column.getMap()) {
				Map<String, String> map = column.getMap();
				if(map.size() > 0) {
					sb = sb.append("formatter : function(cellvalue, options, rowObject){");
					for(String key : map.keySet()) {
						if(StringUtils.isEmpty(column.getDisplay())) {
							sb.append("if(cellvalue == '").append(key).append("') return '").append(map.get(key)).append("'; ");
						}else if(StringUtils.equals("level", column.getDisplay())) {
							if(StringUtils.equals(key, "0")) {
								//一般
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-primary\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "1")) {
								//重要
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-warning\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "2")) {
								//紧急
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-danger\">" + map.get(key) + "</span>").append("'; ");
							}else{
								sb.append("if(cellvalue == '").append(key).append("') return '").append(map.get(key)).append("'; ");
							}
							
						}else if(StringUtils.equals("switch", column.getDisplay())) {
							if(StringUtils.equals(key, "true")) {
								//一般
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-primary\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "false")) {
								//重要
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-warning\">" + map.get(key) + "</span>").append("'; ");
							}
						}else if(StringUtils.equals("zeroOrOne", column.getDisplay())) {
							if(StringUtils.equals(key, "1")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-primary\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "0")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-warning\">" + map.get(key) + "</span>").append("'; ");
							}
						}else if(StringUtils.equals("percent", column.getDisplay())) {
							//<span class="pie">0.52/1.561
							sb.append(" var num0 = Number(cellvalue); ");
							sb.append(" return '<span class=\"pie\">'+num0+'/100</span>('+num0+'%)'");
						}else if(StringUtils.equals("status", column.getDisplay())) {
							if(StringUtils.equals(key, "0")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-primary\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "1")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-info\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "2")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-success\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "3")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-warning\">" + map.get(key) + "</span>").append("'; ");
							}else if(StringUtils.equals(key, "4")) {
								sb.append("if(cellvalue == '").append(key).append("') return '").append("<span class=\"label label-danger\">" + map.get(key) + "</span>").append("'; ");
							}else{
								sb.append("if(cellvalue == '").append(key).append("') return '").append(map.get(key)).append("'; ");
							}
						}
						
					}
					sb.append("}, ");
				}
			}
			
			//显示列有图片的处理
			if(column.isImage()) {
				if(StringUtils.isEmpty(column.getImageSize())) {
					sb = sb.append(" formatter : function(cellvalue, options, rowObject){");
					sb.append(" return '").append("<img border=\"0\" src=\"'+cellvalue+'\"/>").append("'; ");
					sb.append("}, ");
				}else {
					String[] tld = column.getImageSize().split(",");
					sb = sb.append(" formatter : function(cellvalue, options, rowObject){");
					sb.append(" return '").append("<img width=\"" + tld[0] + "\" height=\"" + tld[1]
                                    + "\" border=\"0\" src=\"'+cellvalue+'\"/>").append("'; ");
					sb.append("}, ");
				}
			}
			
			//表格中的操作链接
			if(StringUtils.equals(column.getName(), "opt")) {
				sb = sb.append("formatter : function(cellvalue, options, rowObject){");
				if(null != urlList && urlList.size() > 0) {
					StringBuffer sbUrl = new StringBuffer();
					for(DataGridUrl dataGridUrl : urlList) {
						//删除操作，最常用的一个
						if(OptTypeDirection.Del.equals(dataGridUrl.getType())) {
							if(StringUtils.isNotEmpty(dataGridUrl.getMessage())) {
								sbUrl.append("\"<button class='btn btn-primary btn-xs' type='button' onclick=delObj(\'" + this.getName() + "\',\'" + formatUrl(dataGridUrl.getUrl()) + "\',\'"+dataGridUrl.getMessage()+"\')><i class='fa fa-remove'></i> <span class='bold'>删除</span></button>\"");
							}else {
								sbUrl.append("\"<button class='btn btn-primary btn-xs' type='button' onclick=delObj(\'" + this.getName() + "\',\'" + formatUrl(dataGridUrl.getUrl()) + "\',\'你确定要删除该记录吗?\')><i class='fa fa-remove'></i> <span class='bold'>删除</span></button>\"");
							}
						}else if(OptTypeDirection.Fun.equals(dataGridUrl.getType())) {
							String name = TagUtil.getFunction(dataGridUrl.getFunName());
							String parmars = TagUtil.getFunParams(dataGridUrl.getFunName());
							if(sbUrl.length() > 0) {
								sbUrl.append(" + ");
							}
							//自定义操作函数
							if(StringUtils.isEmpty(dataGridUrl.getIcon())) {
								sbUrl.append(" \" <button class='btn btn-primary btn-xs' type='button' onclick=" + name + "(" + parmars + ")><i class='fa fa-remove'></i> <span class='bold'>" + dataGridUrl.getLabel() +"</span></button>\"");
							}else {
								sbUrl.append(" \" <button class='btn btn-primary btn-xs' type='button' onclick=" + name + "(" + parmars + ")><i class='" + dataGridUrl.getIcon() + "'></i> <span class='bold'>" + dataGridUrl.getLabel() +"</span></button>\"");
							}
						}
					}
					if(sbUrl.length() > 0) {
						sb.append(" return ").append(sbUrl);
					}else {
						sb.append(" return ''");
					}
				}else {
					sb.append(" return ''");
				}
//				sb.append("return \"<button class='btn btn-primary btn-xs' type='button'><i class='fa fa-remove'></i> <span class='bold'>删除\" + rowObject.id + \"</span></button>&nbsp;<button class='btn btn-primary btn-xs' type='button'><i class='fa fa-remove'></i> <span class='bold'>删除</span></button>\" ; ");
				sb.append(" ; }, ");
			}
			
			sb = sb.append("label : \"").append(column.getLabel()).append("\", "); 
			sb = sb.append("width : ").append(column.getWidth()).append(", "); 
			sb = sb.append("fixed : ").append(column.isFixed()).append(", "); 
			sb = sb.append("hidden : ").append(column.isHidden()).append(", "); 
			sb = sb.append("key : ").append(column.isKey()).append(", "); 
			sb = sb.append("sortable : ").append(column.isSortable()).append(", "); 
			if(i + 1 == columnList.size()) {
				sb = sb.append("}");
			}else {
				sb = sb.append("}, ");
			}
			
		}
		
		sb = sb.append("],");
		return sb;
	}
	
	/**
	 * 树形表格的结构处理
	 * @param sb
	 */
	private void getTreeColumns(StringBuffer sb) {
		sb.append("treeGrid: true,");
		sb.append("treedatatype: \"json\",");
		sb.append("treeGridModel: \"adjacency\",");
		sb.append("loadonce: false,");
		sb.append("ExpandColumn: \"" + this.getTreeColomnName() + "\",");
		sb.append("treeReader: {\"parent_id_field\" : \"parentId\", \"level_field\" : \"level\", \"leaf_field\":\"leaf\",\"expanded_field\":\"expanded\",\"loaded\":\"loaded\",\"icon_field\":\"icon\"},");
	}
	
	/**
	 * 对于表格查询列的处理
	 * @param sb
	 * @return
	 */
	private StringBuffer getSearchColumns(StringBuffer sb){
		boolean hasQuery = false;
		//验证列  检查是否存在查询列，如果存在，则进行查询处理，如果不存在，直接返回
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			if(column.isQuery()) {
				hasQuery = true;
				break;
			}
		}
		
		if(hasQuery) {
			//开始拼接查询框的html代码
			sb.append(" var searchHtml = \"<div class='ibox float-e-margins'><div class='ibox-title'><h5>查询</h5><div class='ibox-tools'><a class='collapse-link'> <i class='fa fa-chevron-up'></i></a></div></div>\";");
			sb.append(" searchHtml += \"<div class='ibox-content'><form method='post' class='form-horizontal'><div class='form-group'><div class='row'>\";");
			//查询条件拼接
			for (int i = 0; i < columnList.size(); i++) {
				DataGridColumn column = columnList.get(i);
				if(column.isQuery()) {
					if(StringUtils.isNotEmpty(column.getLabelCol())) {
						sb.append(" searchHtml += \"<label class='col-sm-" + column.getLabelCol() + " control-label'>").append(column.getLabel()).append("</label>\";");
					}else {
						sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append(column.getLabel()).append("</label>\";");
					}
					
					//查询列的处理
					if(StringUtils.isNotEmpty(column.getCommon())) {
						//特殊列的查询，比如弹出框选择
						if(StringUtils.equals("depart", column.getCommon())) {
							//<div class="col-sm-2">
							//<div class="input-group">
                            //<t:choose url="commonController.do?selectDepart" hiddenName="TSDepart.id" hiddenValue="${user.TSDepart.id }" textValue="${user.TSDepart.departname }" textName="departLabel" hiddenId="departId" textId="departLabel" showClear="false" ></t:choose>
							//</div>
							//</div>
							//<input id="departLabel" class="form-control" type="text" value="" name="departLabel" readonly="readonly">
							//<input id="departId" class="form-control" type="hidden" value="" name="TSDepart.id" readonly="readonly">
							//<span class="input-group-btn">
							//<button class="btn btn-primary" onclick="doSelect();" type="button">选择</button>
							//</span>
							//<script type="text/javascript">
							//function doSelect() {layer.open({type: 2,btn: ['确定','取消'],title: '选择',shadeClose: true,shade: 0.8,area:['60%','75%'],content:'commonController.do?selectDepart',yes : function(index, layero) {var iframeWin = window[layero.find('iframe')[0]['name']];iframeWin.getValue();},cancel : function(index, layero) {}});};}
							//</script>
							if(StringUtils.isNotEmpty(column.getInputCol())) {
								sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div class='input-group'><input id='departLabel' class='form-control' type='text' value='' name='departLabel' readonly='readonly'><input id='" + column.getValueId() + "' class='form-control' type='hidden' value='' name='" + column.getQueryId() + "' readonly='readonly'><span class='input-group-btn'><button class='btn btn-primary' onclick='doSelect22();' type='button'><i class='fa fa-eye'></i></button><button class='btn btn-primary' onclick='doClear22();' type='button'><i class='fa fa-remove'></i></button></span></div></div> \" ;");
							}else {
								sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div class='input-group'><input id='departLabel' class='form-control' type='text' value='' name='departLabel' readonly='readonly'><input id='" + column.getValueId() + "' class='form-control' type='hidden' value='' name='" + column.getQueryId() + "' readonly='readonly'><span class='input-group-btn'><button class='btn btn-primary' onclick='doSelect22();' type='button'><i class='fa fa-eye'></i></button><button class='btn btn-primary' onclick='doClear22();' type='button'><i class='fa fa-remove'></i></button></span></div></div> \" ;");
							}
						}else if(StringUtils.equals("job", column.getCommon())) {
							if(StringUtils.isNotEmpty(column.getInputCol())) {
								sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div class='input-group'><input id='jobLabel' class='form-control' type='text' value='' name='departLabel' readonly='readonly'><input id='" + column.getValueId() + "' class='form-control' type='hidden' value='' name='" + column.getQueryId() + "' readonly='readonly'><span class='input-group-btn'><button class='btn btn-primary' onclick='doSelect33();' type='button'><i class='fa fa-eye'></i></button><button class='btn btn-primary' onclick='doClear33();' type='button'><i class='fa fa-remove'></i></button></span></div></div> \" ;");
							}else {
								sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div class='input-group'><input id='jobLabel' class='form-control' type='text' value='' name='departLabel' readonly='readonly'><input id='" + column.getValueId() + "' class='form-control' type='hidden' value='' name='" + column.getQueryId() + "' readonly='readonly'><span class='input-group-btn'><button class='btn btn-primary' onclick='doSelect33();' type='button'><i class='fa fa-eye'></i></button><button class='btn btn-primary' onclick='doClear33();' type='button'><i class='fa fa-remove'></i></button></span></div></div> \" ;");
							}
						}
					}else if(StringUtils.isNotEmpty(column.getDictionary())) {
						//字典值
						if(StringUtils.isNotEmpty(column.getInputCol())) {
							sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'>\";");
						}else {
							sb.append(" searchHtml += \"<div class='col-sm-2 m-b'>\";");
						}
						
						sb.append(" searchHtml += \"<select class='form-control' name='" + (StringUtils.isEmpty(column.getQueryId())?column.getName():column.getQueryId()) + "' id='" + (StringUtils.isEmpty(column.getQueryId())?column.getName():column.getQueryId()) + "' >\";");
						sb.append(" searchHtml += \"<option value=''></option>\";");
						List<SysDicValueEntity> typeList = SystemUtils.getDictionaryLst(column.getDictionary());
						if(null != typeList && typeList.size() > 0) {
							for (SysDicValueEntity type : typeList) {
								//默认值的处理
								if(StringUtils.equals(type.getValue(), column.getDefval())) {
									sb.append("searchHtml += \"<option selected='selected' value='" + type.getValue() + "'>" + type.getLabel() + "</option>\";");
								}else {
									sb.append("searchHtml += \"<option value='" + type.getValue() + "'>" + type.getLabel() + "</option>\";");
								}
							}
						}
						sb.append(" searchHtml += \"</select>\";");
						sb.append(" searchHtml += \"</div>\";");
					}else if(StringUtils.isNotEmpty(column.getReplace())){
						//replace替换值
						if(StringUtils.isNotEmpty(column.getInputCol())) {
							sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'>\";");
						}else {
							sb.append(" searchHtml += \"<div class='col-sm-2 m-b'>\";");
						}
						sb.append(" searchHtml += \"<select class='form-control' name='" + column.getName() + "' id='" + column.getName() + "' >\";");
						sb.append(" searchHtml += \"<option value=''></option>\";");
						//替换值的处理
						String value  = column.getReplace();
						String[] values = value.split(",");
						if(null != values && values.length> 0) {
							for (String v : values) {
								String[] vs = v.split("_");
								//默认值的处理
								if(StringUtils.equals(vs[1], column.getDefval())) {
									sb.append("searchHtml += \"<option selected='selected' value='" + vs[1] + "'>" + vs[0] + "</option>\";");
								}else {
									sb.append("searchHtml += \"<option value='" + vs[1] + "'>" + vs[0] + "</option>\";");
								}
							}
						}
						sb.append(" searchHtml += \"</select>\";");
						sb.append(" searchHtml += \"</div>\";");
					}else if(StringUtils.isNotEmpty(column.getDatefmt())) {
						if(StringUtils.isNotEmpty(column.getDatePlugin()) && StringUtils.equals("bootstrap", column.getDatePlugin())) {
							//bootstrap样式的时间控件
							if(StringUtils.isNotEmpty(column.getQueryModel()) && StringUtils.equals("group", column.getQueryModel())) {
								//时间的组合查询 从-到-
								//日期时间控件处理
								if(StringUtils.isNotEmpty(column.getInputCol())) {
									//控件占格
									if(StringUtils.equals(DateUtils.YYYY_MM_DD_MM_HH_SS, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_begin' data-link-format='yyyy-mm-dd hh:ii:ss'><input class='form-control' id='chen" + column.getName() + "_begin' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_begin' value='' /></div>\"; ");
										sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_end' data-link-format='yyyy-mm-dd hh:ii:ss'><input class='form-control' id='chen" + column.getName() + "_end' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_end' value='' /></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD_MM_HH, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_begin' data-link-format='yyyy-mm-dd hh:ii'><input class='form-control' id='chen" + column.getName() + "_begin' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_begin' value='' /></div>\"; ");
										sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_end' data-link-format='yyyy-mm-dd hh:ii'><input class='form-control' id='chen" + column.getName() + "_end' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_end' value='' /></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_date' data-link-field='" + column.getName() + "_begin' data-link-format='yyyy-mm-dd'><input class='form-control' id='chen" + column.getName() + "_begin' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_begin' value='' /></div>\"; ");
										sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_date' data-link-field='" + column.getName() + "_end' data-link-format='yyyy-mm-dd'><input class='form-control' id='chen" + column.getName() + "_end' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_end' value='' /></div>\"; ");
									}
									
								}else {
									if(StringUtils.equals(DateUtils.YYYY_MM_DD_MM_HH_SS, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_begin' data-link-format='yyyy-mm-dd hh:ii:ss'><input class='form-control' id='chen" + column.getName() + "_begin' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_begin' value='' /></div>\"; ");
										sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_end' data-link-format='yyyy-mm-dd hh:ii:ss'><input class='form-control' id='chen" + column.getName() + "_end' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_end' value='' /></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD_MM_HH, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_begin' data-link-format='yyyy-mm-dd hh:ii'><input class='form-control' id='chen" + column.getName() + "_begin' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_begin' value='' /></div>\"; ");
										sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "_end' data-link-format='yyyy-mm-dd hh:ii'><input class='form-control' id='chen" + column.getName() + "_end' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_end' value='' /></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_date' data-link-field='" + column.getName() + "_begin' data-link-format='yyyy-mm-dd'><input class='form-control' id='chen" + column.getName() + "_begin' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_begin' value='' /></div>\"; ");
										sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_date' data-link-field='" + column.getName() + "_end' data-link-format='yyyy-mm-dd'><input class='form-control' id='chen" + column.getName() + "_end' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "_end' value='' /></div>\"; ");
									}
								}
							}else {
								//单时间查询
								//日期时间控件处理
								if(StringUtils.isNotEmpty(column.getInputCol())) {
									//控件占格
									if(StringUtils.equals(DateUtils.YYYY_MM_DD_MM_HH_SS, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "' data-link-format='yyyy-mm-dd hh:ii:ss'><input class='form-control' id='chen" + column.getName() + "' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "' value='' /><br/></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD_MM_HH, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "' data-link-format='yyyy-mm-dd hh:ii'><input class='form-control' id='chen" + column.getName() + "' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "' value='' /><br/></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><div style='width:22rem;' class='input-group date form_date' data-link-field='" + column.getName() + "' data-link-format='yyyy-mm-dd'><input class='form-control' id='chen" + column.getName() + "' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "' value='' /><br/></div>\"; ");
									}
									
								}else {
									if(StringUtils.equals(DateUtils.YYYY_MM_DD_MM_HH_SS, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "' data-link-format='yyyy-mm-dd hh:ii:ss'><input class='form-control' id='chen" + column.getName() + "' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "' value='' /><br/></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD_MM_HH, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_datetime' data-link-field='" + column.getName() + "' data-link-format='yyyy-mm-dd hh:ii'><input class='form-control' id='chen" + column.getName() + "' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "' value='' /><br/></div>\"; ");
									}else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD, column.getDatefmt())) {
										sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><div style='width:22rem;' class='input-group date form_date' data-link-field='" + column.getName() + "' data-link-format='yyyy-mm-dd'><input class='form-control' id='chen" + column.getName() + "' type='text' value='' readonly='readonly'><span class='input-group-addon'><span class='glyphicon glyphicon-remove'></span></span><span class='input-group-addon'><span class='glyphicon glyphicon-calendar'></span></span></div><input type='hidden' id='" + column.getName() + "' value='' /><br/></div>\"; ");
									}
								}
							}
						}else if(StringUtils.isNotEmpty(column.getDatePlugin()) && StringUtils.equalsIgnoreCase("laydate", column.getDatePlugin())){
							//laydate样式的时间控件
							if(StringUtils.isNotEmpty(column.getQueryModel()) && StringUtils.equals("group", column.getQueryModel())) {
								//控件占格
								//日期时间控件处理
								if(StringUtils.isNotEmpty(column.getInputCol())) {
									sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'> <input class='laydate-icon form-control layer-date' id='" + column.getName() + "_begin' name='" + column.getName() + "_begin'></div>\"; ");
									sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
									sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'> <input class='laydate-icon form-control layer-date' id='" + column.getName() + "_end' name='" + column.getName() + "_end'></div>\"; ");
								}else{
									sb.append(" searchHtml += \"<div class='col-sm-2 m-b'> <input class='laydate-icon form-control layer-date' id='" + column.getName() + "_begin' name='" + column.getName() + "_begin'></div>\"; ");
									sb.append(" searchHtml += \"<label class='col-sm-1 control-label'>").append("到").append("</label>\";");
									sb.append(" searchHtml += \"<div class='col-sm-2 m-b'> <input class='laydate-icon form-control layer-date' id='" + column.getName() + "_end' name='" + column.getName() + "_end'></div>\"; ");
								}
							}else{
								//单时间查询
								//日期时间控件处理
								if(StringUtils.isNotEmpty(column.getInputCol())) {
									sb.append(" searchHtml += \"<div class='col-sm-" + column.getInputCol() + " m-b'><input class='laydate-icon form-control layer-date' id='" + column.getName() + "' name='" + column.getName() + "'</div>\"; ");
								}else{
									sb.append(" searchHtml += \"<div class='col-sm-2 m-b'><input class='laydate-icon form-control layer-date' id='" + column.getName() + "' name='" + column.getName() + "'></div>\"; ");
								}
							}
						}
						
					}else {
						//其余均为文本框
						//默认值的处理
						if(StringUtils.isNotEmpty(column.getDefval())) {
							if(StringUtils.isNotEmpty(column.getInputCol())) {
								sb.append(" searchHtml += \" <div class='col-sm-" + column.getInputCol() + " m-b'><input type='text' value='" + column.getDefval() + "' id='" + column.getName() + "' class='form-control'></div>\";");
							}else {
								sb.append(" searchHtml += \" <div class='col-sm-2 m-b'><input type='text' value='" + column.getDefval() + "' id='" + column.getName() + "' class='form-control'></div>\";");
							}
							
						}else {
							if(StringUtils.isNotEmpty(column.getInputCol())) {
								sb.append(" searchHtml += \" <div class='col-sm-" + column.getInputCol() + " m-b'><input type='text' id='" + column.getName() + "' class='form-control'></div>\";");
							}else {
								sb.append(" searchHtml += \" <div class='col-sm-2 m-b'><input type='text' id='" + column.getName() + "' class='form-control'></div>\";");
							}
							
						}
						
					}
					
				}
			}
			
			sb.append(" searchHtml += \"</div><div class='row'><div class='col-sm-12 text-right'><p><button class='btn btn-info' type='button' onclick='doBtnSearchAction();'><i class='fa fa-search'></i>&nbsp;&nbsp;查询</button><button class='btn btn-info' type='button' style='margin-left:3px;' onclick='doBtnResetAction();'><i class='fa fa-refresh'></i>&nbsp;&nbsp;重置</button></p></div></div></div></form></div></div>\";");
			sb.append("$(\"#" + this.getSearchGroupId()+ "\").append(searchHtml); ");
		
		
		}
		
		return sb;
	}
	
	/**
	 * 拼接查询方法
	 * @param sb
	 * @return
	 */
	private StringBuffer getSearchAction(StringBuffer sb) {
		
		boolean hasQuery = false;
		//验证列  检查是否存在查询列，如果存在，则进行查询处理，如果不存在，直接返回
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			if(column.isQuery()) {
				hasQuery = true;
				break;
			}
		}
		
		if(hasQuery) {
			//拼接查询方法
			sb.append("function doBtnSearchAction() {");
			for (int i = 0; i < columnList.size(); i++) {
				DataGridColumn column = columnList.get(i);
				if(column.isQuery()) {
					//关于部门ID，需要特殊处理一下
					if(StringUtils.isNotEmpty(column.getValueId())) {
						sb.append("var ").append(column.getName()).append(" = ").append("$(\"#"+column.getValueId()+"\").val();");
					}else if(StringUtils.isNotEmpty(column.getDatefmt()) && StringUtils.isNotEmpty(column.getQueryModel()) && StringUtils.equals("group", column.getQueryModel())){
						sb.append("var ").append(column.getName() + "_begin").append(" = ").append("$(\"#"+column.getName()+"_begin\").val(); ");
						sb.append("var ").append(column.getName() + "_end").append(" = ").append("$(\"#"+column.getName()+"_end\").val();");
					}else{
						sb.append("var ").append(column.getName()).append(" = ").append("$(\"#" + column.getName() + "\").val();");
					}
					
				}
			}
			sb.append("$(\"#" + this.getName()+ "\").jqGrid('setGridParam',{postData:{");
			for (int i = 0; i < columnList.size(); i++) {
				DataGridColumn column = columnList.get(i);
				if(column.isQuery()) {
					if(StringUtils.isNotEmpty(column.getQueryId())) {
						sb.append("\"" + column.getQueryId() + "\"").append(":").append(column.getName()).append(",");
					}else if(StringUtils.isNotEmpty(column.getDatefmt()) && StringUtils.isNotEmpty(column.getQueryModel()) && StringUtils.equals("group", column.getQueryModel())) {
						sb.append("\"" + column.getName() + "_begin\"").append(":").append(column.getName() + "_begin").append(",");
						sb.append("\"" + column.getName() + "_end\"").append(":").append(column.getName() + "_end").append(",");
					}else {
						sb.append("\"" + column.getName() + "\"").append(":").append(column.getName()).append(",");
					}
					
				}
			}
			sb.append("},}).trigger('reloadGrid');};");
			
			//拼接重置方法
			sb.append("function doBtnResetAction() {");
//			for (int i = 0; i < columnList.size(); i++) {
//				DataGridColumn column = columnList.get(i);
//				if(column.isQuery()) {
//					sb.append("$(\"#" + column.getName() + "\").val('');");
//				}
//			}
			sb.append("$(\"#" + this.getSearchGroupId()+ "\").find(\":input\").val(\"\");");
			sb.append("doBtnSearchAction();");
			sb.append("};");
			
			//查询面板 小工具栏 收缩图标事件
			sb.append("$(function() {$('.collapse-link').click(function() {var o = $(this).closest('div.ibox'), e = $(this).find('i'), i = o.find('div.ibox-content');i.slideToggle(200), e.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down'), o.toggleClass('').toggleClass('border-bottom'), setTimeout(function() {o.resize(), o.find('[id^=map-]').resize();}, 50);});});");
		}
		
		
		return sb;
	}
	
	/**
	 * 判断查询列中有没有部门列，有的话，拼接部门选择代码
	 * @return
	 */
	private boolean hasDepart() {
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			if(column.isQuery() && StringUtils.isNotEmpty(column.getCommon()) && StringUtils.equals("depart", column.getCommon())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断查询列中有没有岗位列，有的话，拼接岗位选择代码
	 * @return
	 */
	private boolean hasJob() {
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			if(column.isQuery() && StringUtils.isNotEmpty(column.getCommon()) && StringUtils.equals("job", column.getCommon())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断查询列中，是否存在日期时间格式
	 * @return
	 */
	private int hasDateTime() {
		
		int result = 0;
		
		boolean has1 = true;
		boolean has2 = true;
		boolean has3 = true;
		
		for (int i = 0; i < columnList.size(); i++) {
			DataGridColumn column = columnList.get(i);
			
			if(column.isQuery() && StringUtils.isNotEmpty(column.getDatefmt()) && StringUtils.equals("bootstrap", column.getDatePlugin())) {
				if(StringUtils.equals(DateUtils.YYYY_MM_DD_MM_HH_SS, column.getDatefmt())) {
					if(has1) {
						result = result + 1;
						has1 = false;
					}
				} else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD_MM_HH, column.getDatefmt()) && StringUtils.equals("bootstrap", column.getDatePlugin())) {
					if(has2) {
						result = result + 2;
						has2 = false;
					}
				} else if(StringUtils.equals(DateUtils.STR_YYYY_MM_DD, column.getDatefmt()) && StringUtils.equals("bootstrap", column.getDatePlugin())) {
					if(has3) {
						result = result + 4;
						has3 = false;
					}
				}
			}
		}
		return result;
	}
	
	
	public int doStartTag() throws JspTagException {
		// 清空资源
		columnList.clear();
		urlList.clear();
		toolBarUrl.clear();
		fields = "";
		//树形表格的处理
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			
			//树形表格的处理
			if(treeGrid) {
				fields = fields + "parentId," + "level," + "leaf," + "loaded," + "expanded," + "icon";
			}
			
			out.print(jqgrid().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	
	/**
	 * 描述：组装菜单按钮操作权限
	 * dateGridUrl：url
	 * operationCode：操作码
	 * optList： 操作列表
	 * @version 1.0
	 */
	private void installOperationCode(DataGridUrl dataGridUrl,String operationCode, List<DataGridUrl> optList){
		if(ShiroUtils.isAdmin()){
			optList.add(dataGridUrl);
		}else if(StringUtils.isNotEmpty(operationCode)){
			Set<String> operationCodes = ShiroUtils.getSessionUser().getPermissions();
			if(null != operationCodes && operationCodes.size() > 0 && operationCodes.contains(operationCode)) {
				optList.add(dataGridUrl);
			}
			
		}else {
			optList.add(dataGridUrl);
		}
	}
	
	/**
	 * 表格工具栏的权限控制
	 * @param toolBar
	 * @param operationCode
	 * @param toolBarUrl
	 */
	private void installOperationCode(DataGridToolBarUrl toolBar, String operationCode, List<DataGridToolBarUrl> toolBarUrl) {
		if(ShiroUtils.isAdmin()) {
			toolBarUrl.add(toolBar);
		}else if(StringUtils.isNotEmpty(operationCode)) {
			Set<String> operationCodes = ShiroUtils.getSessionUser().getPermissions();
			if(null != operationCodes && operationCodes.size() > 0 && operationCodes.contains(operationCode)) {
				toolBarUrl.add(toolBar);
			}
		}else {
			toolBarUrl.add(toolBar);
		}
	}
	
	/**
	 * 格式化URL
	 * 
	 * @return
	 */
	protected String formatUrl(String url) {
		MessageFormat formatter = new MessageFormat("");
		String parurlvalue = "";
		if (url.indexOf("?") >= 0) {
			String beforeurl = url.substring(0, url.indexOf("?"));// 截取请求地址
			String parurl = url.substring(url.indexOf("?") + 1, url.length());// 截取参数
			String[] pras = parurl.split("&");
			List value = new ArrayList<Object>();
			int j = 0;
			for (int i = 0; i < pras.length; i++) {
				if (pras[i].indexOf("{") >= 0 || pras[i].indexOf("#") >= 0) {
					String field = pras[i].substring(pras[i].indexOf("{") + 1, pras[i].lastIndexOf("}"));
					parurlvalue += "?" + pras[i].replace("{" + field + "}", "{" + j + "}");
					value.add("\"+rowObject." + field + " +\"");
					j++;
				} else {
					parurlvalue += "?" + pras[i];
				}
			}
			url = formatter.format(beforeurl + parurlvalue, value.toArray());
		}
		return url;

	}
	
	
	public boolean isViewRecords() {
		return viewRecords;
	}

	public void setViewRecords(boolean viewRecords) {
		this.viewRecords = viewRecords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableContentId() {
		return tableContentId;
	}

	public void setTableContentId(String tableContentId) {
		this.tableContentId = tableContentId;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public boolean isAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(boolean autoWidth) {
		this.autoWidth = autoWidth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isFit() {
		return fit;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isRownumbers() {
		return rownumbers;
	}

	public void setRownumbers(boolean rownumbers) {
		this.rownumbers = rownumbers;
	}

	public int getRownumWidth() {
		return rownumWidth;
	}

	public void setRownumWidth(int rownumWidth) {
		this.rownumWidth = rownumWidth;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isHidegrid() {
		return hidegrid;
	}

	public void setHidegrid(boolean hidegrid) {
		this.hidegrid = hidegrid;
	}

	public List<DataGridColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DataGridColumn> columnList) {
		this.columnList = columnList;
	}

	public List<DataGridUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<DataGridUrl> urlList) {
		this.urlList = urlList;
	}

	public String getSearchGroupId() {
		return searchGroupId;
	}

	public void setSearchGroupId(String searchGroupId) {
		this.searchGroupId = searchGroupId;
	}

	public boolean isTreeGrid() {
		return treeGrid;
	}

	public void setTreeGrid(boolean treeGrid) {
		this.treeGrid = treeGrid;
	}

	public String getTreeColomnName() {
		return treeColomnName;
	}

	public void setTreeColomnName(String treeColomnName) {
		this.treeColomnName = treeColomnName;
	}


	public String getLoadFun() {
		return loadFun;
	}

	public void setLoadFun(String loadFun) {
		this.loadFun = loadFun;
	}

	public boolean isLoad() {
		return load;
	}

	public void setLoad(boolean load) {
		this.load = load;
	}
	
}
