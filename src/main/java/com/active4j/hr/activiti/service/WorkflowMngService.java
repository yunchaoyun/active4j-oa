package com.active4j.hr.activiti.service;

import java.util.List;

import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WorkflowMngService extends IService<WorkflowMngEntity> {
	
	public void saveWorkflowMng(WorkflowMngEntity workflowMngEntity, String[] RList, String[] UList);
	
	public void saveOrUpdateWorkflowMng(WorkflowMngEntity workflowMngEntity, String[] RList, String[] UList);
	
	public void delete(String id);
	
	public List<WorkflowMngEntity> findWorkflowMngByUserIdAndRoleIds(String userId, List<String> roleIds);
}
