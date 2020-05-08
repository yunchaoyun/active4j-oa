package com.active4j.hr.activiti.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.biz.dao.FlowContractApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowContractApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowContractApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title FlowContractApprovalServiceImpl.java
 * @description 
		工作流 合同审批
 * @time  2020年4月28日 下午5:48:09
 * @author guyp
 * @version 1.0
 */
@Service("flowContractApprovalService")
@Transactional
public class FlowContractApprovalServiceImpl extends ServiceImpl<FlowContractApprovalDao, FlowContractApprovalEntity> implements FlowContractApprovalService {
	
	@Autowired
	private WorkflowBaseService workflowBaseService;

	public void saveNewContract(WorkflowBaseEntity workflowBaseEntity, FlowContractApprovalEntity flowContractApprovalEntity) {
		
		this.save(flowContractApprovalEntity);
		
		workflowBaseEntity.setBusinessId(flowContractApprovalEntity.getId());
		workflowBaseService.save(workflowBaseEntity);
	}

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowContractApprovalEntity flowContractApprovalEntity) {
		
		this.saveOrUpdate(flowContractApprovalEntity);
		
		workflowBaseService.saveOrUpdate(workflowBaseEntity);
	}
	

}
