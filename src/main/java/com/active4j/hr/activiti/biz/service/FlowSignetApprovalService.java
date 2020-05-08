package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowSignetApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FlowSignetApprovalService extends IService<FlowSignetApprovalEntity> {
	
	public void saveNewSignet(WorkflowBaseEntity workflowBaseEntity, FlowSignetApprovalEntity flowSignetApprovalEntity);

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowSignetApprovalEntity flowSignetApprovalEntity);
	
}
