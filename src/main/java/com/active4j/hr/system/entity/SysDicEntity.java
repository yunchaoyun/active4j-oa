package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典表
 * @author teli_
 *
 */
@TableName("sys_dic")
@Getter
@Setter
public class SysDicEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7516587511068473316L;

	/**
	 * 字典代码
	 */
	@TableField("CODE")
	private String code;
	
	/**
	 * 字段名称
	 */
	@TableField("NAME")
	private String name;
	
	/**
	 * 备注信息
	 */
	@TableField("MEMO")
	private String memo;
	
}
