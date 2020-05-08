package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典值
 * @author teli_
 *
 */
@TableName("sys_dic_value")
@Getter
@Setter
public class SysDicValueEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1184574569285002609L;

	/**
	 * 显示值
	 */
	@TableField("LABEL")
	private String label;
	
	/**
	 * 值
	 */
	@TableField("VALUE")
	private String value;
	
	/**
	 * 所属字典
	 */
	@TableField("PARENT_ID")
	private String parentId;
	
}
