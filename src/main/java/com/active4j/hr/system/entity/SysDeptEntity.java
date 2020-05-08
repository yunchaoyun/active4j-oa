package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title SysDeptEntity.java
 * @description 
		  系统管理   部门管理
 * @time  2020年1月15日 上午10:48:20
 * @author 麻木神
 * @version 1.0
*/
@TableName("sys_depart")
@Getter
@Setter
public class SysDeptEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3540862564325838898L;

	/**
	 * 部门编号
	 */
	@TableField("DEPT_NO")
	private String deptNo;
	
	/**
	 * 部门名称  全称
	 */
	@TableField("NAME")
	private String name;
	
	/**
	 * 部门类型  0：集团      1： 公司：     2： 部门      3： 小组
	 */
	@TableField("TYPE")
	private String type;
	
	/**
	 * 上级部门ID
	 */
	@TableField("PARENT_ID")
	private String parentId;
	
	/**
	 * 部门排序
	 */
	@TableField("LEVEL")
	private int level;
	
	/**
	 * 部门描述
	 */
	@TableField("DESCRIPTION")
	private String description;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
}

