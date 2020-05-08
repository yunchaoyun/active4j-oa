package com.active4j.hr.activiti.biz.service;

import com.active4j.hr.activiti.biz.entity.FlowHrHolidayApprovalEntity;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FlowHrHolidayApprovalService extends IService<FlowHrHolidayApprovalEntity> {
	
	public void saveNewHrHoliday(WorkflowBaseEntity workflowBaseEntity, FlowHrHolidayApprovalEntity flowHrHolidayApprovalEntity);

	public void saveUpdate(WorkflowBaseEntity workflowBaseEntity, FlowHrHolidayApprovalEntity flowHrHolidayApprovalEntity);
	
}
