package com.active4j.hr.activiti.service;

import java.util.List;

import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WorkflowFormService extends IService<WorkflowFormEntity> {
	
	public List<WorkflowFormEntity> findWorkflowForm(String categoryId);
}
