package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowContractApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FlowContractApprovalService extends IService<FlowContractApprovalEntity> {
	
	public void saveNewContract(WorkflowBaseEntity workflowBaseEntity, FlowContractApprovalEntity flowContractApprovalEntity);

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowContractApprovalEntity flowContractApprovalEntity);
	
}
