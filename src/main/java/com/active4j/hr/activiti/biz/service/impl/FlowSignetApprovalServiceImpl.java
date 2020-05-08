package com.active4j.hr.activiti.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.biz.dao.FlowSignetApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowSignetApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowSignetApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title FlowSignetApprovalServiceImpl.java
 * @description 
		工作流 用章审批
 * @time  2020年4月28日 下午2:37:24
 * @author guyp
 * @version 1.0
 */
@Service("flowSignetApprovalService")
@Transactional
public class FlowSignetApprovalServiceImpl extends ServiceImpl<FlowSignetApprovalDao, FlowSignetApprovalEntity> implements FlowSignetApprovalService {
	
	@Autowired
	private WorkflowBaseService workflowBaseService;

	public void saveNewSignet(WorkflowBaseEntity workflowBaseEntity, FlowSignetApprovalEntity flowSignetApprovalEntity) {
		
		this.save(flowSignetApprovalEntity);
		
		workflowBaseEntity.setBusinessId(flowSignetApprovalEntity.getId());
		workflowBaseService.save(workflowBaseEntity);
	}

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowSignetApprovalEntity flowSignetApprovalEntity) {
		
		this.saveOrUpdate(flowSignetApprovalEntity);
		
		workflowBaseService.saveOrUpdate(workflowBaseEntity);
	}
	

}
