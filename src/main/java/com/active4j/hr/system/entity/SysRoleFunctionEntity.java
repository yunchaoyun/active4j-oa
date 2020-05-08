package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;


/**
 * 角色跟菜单 多对多表
 * @author teli_
 *
 */
@TableName("sys_role_function")
@Getter
@Setter
public class SysRoleFunctionEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8012881716208953841L;

	/**
	 * 关联角色ID
	 */	
	@TableField("ROLE_ID")
	private String roleId;
	
	/**
	 * 关联菜单ID
	 */
	@TableField("MENU_ID")
	private String menuId;
	
	/**
	 * 按钮权限
	 */
	@TableField("OPERATION")
	private String operation;
}
