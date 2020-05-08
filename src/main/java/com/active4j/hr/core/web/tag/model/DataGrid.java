package com.active4j.hr.core.web.tag.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title DataGrid.java
 * @description 
		  数据表格接受前端参数
 * @time  2020年1月27日 下午11:29:45
 * @author 麻木神
 * @version 1.0
 */
@Getter
@Setter
public class DataGrid {

	private int page = 1;// 当前页
	private int rows = 20;// 每页显示记录数
	private String sidx;// 排序字段名
	private String sord = "asc";// 按什么排序(asc,desc)
	private String field;//字段
	
	
}
