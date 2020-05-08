package com.active4j.hr.core.web.tag.model.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * 部门树形结构的model
 * @author chenxl
 *
 */

@Getter
@Setter
public class TSDepartTreeData extends JggridTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3672122875733580975L;

	
	
	private String id;
	//部门名称
	private String departName;
	//部门编号
	private String departNo;
	
	//职能描述
	private String description;
	
	private String type;
	
	
}
