package com.active4j.hr.system.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MenuModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516450772634970274L;

	private String id;
	
	private String title;
	
	private String url;
	
	private String icon;
	
	private int orderNo;
	
	private boolean hasChildren;
	
	private List<MenuModel> children;

}
