package com.active4j.hr.activiti.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title WorkflowEntity.java
 * @description 工作流 流程
 * @time 2020年4月16日 下午2:41:54
 * @author 麻木神
 * @version 1.0
 */
@TableName("wf_workflow_mng")
@Getter
@Setter
public class WorkflowMngEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1519195734849856624L;

	// 流程名称
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.like)
	private String name;

	// 流程编号
	@TableField("WORKFLOW_NO")
	@QueryField(queryColumn="WORKFLOW_NO", condition=QueryCondition.eq)
	private String workflowNo;

	// 流程类别
	@TableField("CATEGORY_ID")
	@QueryField(queryColumn="CATEGORY_ID", condition=QueryCondition.eq)
	private String categoryId;

	// 流程状态 0:正常 1:停用: 2:过期
	@TableField("STATUS")
	@QueryField(queryColumn="STATUS", condition=QueryCondition.eq)
	private String status;

	// 权限类型 0:所有人 1：指定人
	@TableField("TYPE")
	private String type;

	// 表单ID
	@TableField("FORM_ID")
	private String formId;

	// 流程定义ID
	@TableField("PROCESS_DEFINE_ID")
	private String processDefineId;

	// 流程KEY
	@TableField("PROCESS_KEY")
	private String processKey;

	// 部署ID
	@TableField("DEPLOY_ID")
	private String deployId;

	// 流程备注
	@TableField("MEMO")
	private String memo;

}
