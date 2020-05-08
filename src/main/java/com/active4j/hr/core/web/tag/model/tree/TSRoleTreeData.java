package com.active4j.hr.core.web.tag.model.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * 菜单的树形结构实体
 * @author chenxl
 *
 */
@Getter
@Setter
public class TSRoleTreeData extends JggridTreeModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1247724125682398088L;

	private String id;
	
	//菜单名称
	private String roleName;
	
	//菜单地址
	private String roleCode;
	
	//角色排序
	private int orderNo;
	
	//菜单地址
	private String description;
}
