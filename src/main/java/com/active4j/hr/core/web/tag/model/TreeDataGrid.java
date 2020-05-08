package com.active4j.hr.core.web.tag.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.core.beanutil.ContextHolderUtils;


/**
 * easyui的datagrid向后台传递参数使用的model
 * 
 * @author
 * 
 */
public class TreeDataGrid {

	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sidx = null;// 排序字段名
	private String sord = "asc";// 按什么排序(asc,desc)
	private String field;//字段
	private List<?> results;// 结果集
	private int total;//总记录数
	private int totalPage;
	private String footer;//合计列
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getField() {
		return field;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		if(ContextHolderUtils.getRequest()!=null&&ContextHolderUtils.getParameter("rows")!=null){
			return rows;
		}
		return 10000;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	
	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		if(StringUtils.equals("null", sidx)){
			sidx = null;
		}
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}


	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
}
