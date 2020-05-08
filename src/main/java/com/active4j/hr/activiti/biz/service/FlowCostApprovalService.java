package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowCostApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FlowCostApprovalService extends IService<FlowCostApprovalEntity> {
	
	public void saveNewFlowCost(WorkflowBaseEntity workflowBaseEntity, FlowCostApprovalEntity flowCostApprovalEntity);

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowCostApprovalEntity flowCostApprovalEntity);
	
}
