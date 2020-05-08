package com.active4j.hr.activiti.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.biz.dao.FlowHrHolidayApprovalDao;
import com.active4j.hr.activiti.biz.entity.FlowHrHolidayApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowHrHolidayApprovalService;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title HrHolidayApprovalServiceImpl.java
 * @description 
		工作流 请假审批
 * @time  2020年4月28日 上午10:59:34
 * @author guyp
 * @version 1.0
 */
@Service("flowHrHolidayApprovalService")
@Transactional
public class FlowHrHolidayApprovalServiceImpl extends ServiceImpl<FlowHrHolidayApprovalDao, FlowHrHolidayApprovalEntity> implements FlowHrHolidayApprovalService {
	
	@Autowired
	private WorkflowBaseService workflowBaseService;

	public void saveNewHrHoliday(WorkflowBaseEntity workflowBaseEntity,
			FlowHrHolidayApprovalEntity flowHrHolidayApprovalEntity) {
		
		this.save(flowHrHolidayApprovalEntity);
		
		workflowBaseEntity.setBusinessId(flowHrHolidayApprovalEntity.getId());
		workflowBaseService.save(workflowBaseEntity);
	}

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowHrHolidayApprovalEntity flowHrHolidayApprovalEntity) {
		
		this.saveOrUpdate(flowHrHolidayApprovalEntity);
		
		workflowBaseService.saveOrUpdate(workflowBaseEntity);
	}
	

}
