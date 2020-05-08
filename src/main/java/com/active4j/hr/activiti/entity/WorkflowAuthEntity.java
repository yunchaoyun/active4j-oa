package com.active4j.hr.activiti.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title WorkflowAuthEntity.java
 * @description 工作流权限
 * @time 2020年4月20日 下午1:27:08
 * @author 麻木神
 * @version 1.0
 */
@TableName("wf_workflow_auth")
@Getter
@Setter
public class WorkflowAuthEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7542499266953335634L;

	// 权限类别,是按用户还是按角色
	@TableField("TYPE")
	private String type;

	// 用户或者角色ID
	@TableField("U_R_ID")
	private String uRId;

	// 流程ID
	@TableField("WORKFLOW_ID")
	private String workflowId;
}
