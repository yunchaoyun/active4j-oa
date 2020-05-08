package com.active4j.hr.core.web.tag.model.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title TSDicTreeData.java
 * @description 
		数据字典的树形结构
 * @time  2020年2月7日 下午12:43:59
 * @author guyp
 * @version 1.0
 */
@Getter
@Setter
public class TSDicTreeData extends JggridTreeModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2697236118064862192L;

	private String id;
	
	//字典名称
	private String dicName;
	
	//字典编码
	private String dicCode;
	
}
