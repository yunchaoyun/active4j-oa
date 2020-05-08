package com.active4j.hr.core.web.tag.model.tree;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * jqgrid 用户生成树形数据的model
 * @author chenxl
 *
 */
@Getter
@Setter
public class JggridTreeModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5729120931449458713L;
	
	//父节点ID
	private String parentId; 
	
	//层次
	private String level;
	
	//是否为叶 -- 不存在子节点了
	private boolean leaf;
	
	//是否加载数据
	private boolean loaded;
	
	//是否展开
	private boolean expanded;
	
	//显示图标
	private String icon;
	
	
}
