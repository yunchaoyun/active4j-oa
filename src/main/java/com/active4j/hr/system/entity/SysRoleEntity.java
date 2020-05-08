package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title SysRoleEntity.java
 * @description 
		  系统管理  角色管理
 * @time  2020年1月15日 上午10:57:08
 * @author 麻木神
 * @version 1.0
*/
@TableName("sys_role")
@Getter
@Setter
public class SysRoleEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1561725030342288226L;

	/**
	 * 角色名称
	 */
	@TableField("ROLE_NAME")
	private String roleName;
	
	/**
	 * 角色编号
	 */
	@TableField("ROLE_CODE")
	private String roleCode;
	
	/**
	 * 父角色ID
	 */
	@TableField(value="PARENT_ID", strategy=FieldStrategy.IGNORED)
	private String parentId;
	
	/*
	 * 角色排序
	 */
	@TableField("LEVEL")
	private int level;
	
	/**
	 * 角色排序
	 */
	@TableField("ORDER_NO")
	private int orderNo;

	/**
	 * 角色描述
	 */
	@TableField("DESCRIPTION")
	private String description;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
}
