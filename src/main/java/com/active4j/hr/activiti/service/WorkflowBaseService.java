package com.active4j.hr.activiti.service;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.core.model.AjaxJson;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WorkflowBaseService extends IService<WorkflowBaseEntity> {
	
	
	public AjaxJson validWorkflowBase(WorkflowBaseEntity workflowBaseEntity, AjaxJson j);
	

}
