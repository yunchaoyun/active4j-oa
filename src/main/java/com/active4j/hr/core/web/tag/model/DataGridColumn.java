package com.active4j.hr.core.web.tag.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 类描述：列表字段模型
 * 
 * @author:  陈晓磊
 * @version 1.0
 */
public class DataGridColumn {
	//列的对齐方式：left, center, right
	protected String align;
	//设置列的css。多个class之间用空格分隔，如：'class1 class2' 。表格默认的css属性是ui-ellipsis
	protected String classes;
	//日期格式化 ”/”, ”-”, and ”.”都是有效的日期分隔符
	protected String datefmt;
	//查询字段的默认值
	protected String defval;
	//列宽度是否要固定不可变
	protected boolean fixed;
	//在初始化表格时是否要隐藏此列
	protected boolean hidden;
	//当从服务器端返回的数据中没有id时，将此作为唯一rowid使用只有一个列可以做这项设置。如果设置多于一个，那么只选取第一个，其他被忽略
	protected boolean key;
	//设置列值
	protected String label;
	//表格列的名称，所有关键字，保留字都不能作为名称使用包括subgrid, cb and rn.
	protected String name;
	//是否可排序
	protected boolean sortable;
	//宽度
	protected Integer width;
	
	//是否是查询列
	protected boolean query;
	
	//列的字典
	protected String dictionary;
	//列的替换值
	protected String replace;
	//特殊列的查询
	protected String common;
	
	protected String queryId;
	
	protected String valueId;
	
	//bootstrap网格所占行数
	private String labelCol;
	private String inputCol;
	private String queryModel;
	
	private String datePlugin;
	
	private String display;
	
	protected boolean image;//是否是图片
	protected String imageSize;//自定义图片显示大小
	
	//数据字典map  key value
	private Map<String, String> map = new HashMap<String, String>();
	
	
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getDatefmt() {
		return datefmt;
	}
	public void setDatefmt(String datefmt) {
		this.datefmt = datefmt;
	}
	public String getDefval() {
		return defval;
	}
	public void setDefval(String defval) {
		this.defval = defval;
	}
	public boolean isFixed() {
		return fixed;
	}
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean isKey() {
		return key;
	}
	public void setKey(boolean key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public boolean isQuery() {
		return query;
	}
	public void setQuery(boolean query) {
		this.query = query;
	}
	public String getDictionary() {
		return dictionary;
	}
	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}
	public String getReplace() {
		return replace;
	}
	public void setReplace(String replace) {
		this.replace = replace;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getValueId() {
		return valueId;
	}
	public void setValueId(String valueId) {
		this.valueId = valueId;
	}
	public String getLabelCol() {
		return labelCol;
	}
	public void setLabelCol(String labelCol) {
		this.labelCol = labelCol;
	}
	public String getInputCol() {
		return inputCol;
	}
	public void setInputCol(String inputCol) {
		this.inputCol = inputCol;
	}
	public String getQueryModel() {
		return queryModel;
	}
	public void setQueryModel(String queryModel) {
		this.queryModel = queryModel;
	}
	public String getDatePlugin() {
		return datePlugin;
	}
	public void setDatePlugin(String datePlugin) {
		this.datePlugin = datePlugin;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public boolean isImage() {
		return image;
	}
	public String getImageSize() {
		return imageSize;
	}
	public void setImage(boolean image) {
		this.image = image;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	
}
