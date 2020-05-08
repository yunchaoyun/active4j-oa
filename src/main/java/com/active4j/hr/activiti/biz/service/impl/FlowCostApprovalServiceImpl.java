package com.active4j.hr.activiti.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.biz.dao.FlowCostApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowCostApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowCostApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title WorkflowAuthService.java
 * @description 
		  工作流
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("flowCostApprovalService")
@Transactional
public class FlowCostApprovalServiceImpl extends ServiceImpl<FlowCostApprovalDao, FlowCostApprovalEntity> implements FlowCostApprovalService {
	
	@Autowired
	private WorkflowBaseService workflowBaseService;
	
	public void saveNewFlowCost(WorkflowBaseEntity workflowBaseEntity, FlowCostApprovalEntity flowCostApprovalEntity) {
		
		this.save(flowCostApprovalEntity);
		
		workflowBaseEntity.setBusinessId(flowCostApprovalEntity.getId());
		workflowBaseService.save(workflowBaseEntity);
		
	}
	
	public void saveNewFlowCostProcess(WorkflowBaseEntity workflowBaseEntity, FlowCostApprovalEntity flowCostApprovalEntity) {
		
		this.save(flowCostApprovalEntity);
		
		workflowBaseEntity.setBusinessId(flowCostApprovalEntity.getId());
		workflowBaseService.save(workflowBaseEntity);
		
	}
	
	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowCostApprovalEntity flowCostApprovalEntity) {
		this.saveOrUpdate(flowCostApprovalEntity);
		
		workflowBaseService.saveOrUpdate(workflowBaseEntity);
		
	}
}
