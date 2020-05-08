package com.active4j.hr.activiti.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title BaseActivitiEntity.java
 * @description 流程业务实体 基础实体表
 * @time 2020年4月22日 下午1:16:23
 * @author 麻木神
 * @version 1.0
 */
@TableName("wf_base")
@Getter
@Setter
public class WorkflowBaseEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -274488600248750879L;

	// 名称
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.like)
	private String name;

	// 编号
	@TableField("PROJECT_NO")
	@QueryField(queryColumn="PROJECT_NO", condition=QueryCondition.eq)
	private String projectNo;

	// 流程状态 0：草稿 1： 已申请 2： 审批中 3： 已完成 4： 已归档 5：驳回
	@TableField("STATUS")
	private String status;

	// 流程紧急程度 0:一般 1：重要 2：紧急
	@TableField("LEVEL")
	private String level;

	// 流程类别
	@TableField("CATEGORY_ID")
	@QueryField(queryColumn="CATEGORY_ID", condition=QueryCondition.eq)
	private String categoryId;

	// 流程名称
	@TableField("WORKFLOW_NAME")
	private String workFlowName;

	// 申请人
	@TableField("APPLY_NAME")
	private String applyName;
	
	//申请人
	@TableField("USER_NAME")
	private String userName;

	// 申请日期
	@TableField("APPLY_DATE")
	@QueryField(queryColumn="APPLY_DATE", condition=QueryCondition.eq)
	private Date applyDate;

	// 流程Id
	@TableField("WORKFLOW_ID")
	private String workflowId;
	
	//业务表ID
	@TableField("BUSINESS_ID")
	private String businessId;
	
	@TableField("CREATE_DATE")
	@QueryField(queryColumn="CREATE_DATE", condition=QueryCondition.range)
	private Date createDate;

}
