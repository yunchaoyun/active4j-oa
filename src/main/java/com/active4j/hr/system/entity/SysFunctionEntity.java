package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title SysFunctionEntity.java
 * @description 
		  系统管理  菜单管理
 * @time  2020年1月15日 上午11:09:16
 * @author 麻木神
 * @version 1.0
*/
@TableName("sys_function")
@Getter
@Setter
public class SysFunctionEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3670746456596319466L;

	/**
	 * 菜单名称
	 */
	@TableField("NAME")
	private String name;
	
	/**
	 * 菜单Level
	 */
	@TableField("LEVEL")
	private int level;
	
	/**
	 * 菜单URL
	 */
	@TableField("URL")
	private String url;
	
	/**
	 * 菜单图标
	 */
	@TableField("ICON")
	private String icon;
	
	/**
	 * 菜单排序
	 */
	@TableField("ORDER_NO")
	private int orderNo;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
	/**
	 * 父级菜单ID
	 */
	@TableField(value = "PARENT_ID", strategy=FieldStrategy.IGNORED)
	private String parentId;
}
