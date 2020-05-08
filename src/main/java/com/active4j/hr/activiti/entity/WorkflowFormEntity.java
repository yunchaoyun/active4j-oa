package com.active4j.hr.activiti.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title WorkflowFormEntity.java
 * @description 表单
 * @time 2020年4月18日 下午9:51:04
 * @author 麻木神
 * @version 1.0
 */
@TableName("wf_flow_form")
@Getter
@Setter
public class WorkflowFormEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5449389222077422337L;

	// 表单名称
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.like)
	private String name;

	// 表单标识
	@TableField("CODE")
	private String code;

	// 表单类型 0:系统表单 1:自定义表单
	@TableField("TYPE")
	private String type;

	// 表单的路径
	@TableField("PATH")
	private String path;

	// 自定义表单内容
	@TableField("CONTENT")
	private String content;
	
	@TableField("CATEGORY_ID")
	@QueryField(queryColumn="CATEGORY_ID", condition=QueryCondition.eq)
	private String categoryId;
	
	

}
