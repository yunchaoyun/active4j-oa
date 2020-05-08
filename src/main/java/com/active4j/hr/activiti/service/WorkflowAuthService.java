package com.active4j.hr.activiti.service;

import java.util.List;

import com.active4j.hr.activiti.entity.WorkflowAuthEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WorkflowAuthService extends IService<WorkflowAuthEntity> {
	
	public List<WorkflowAuthEntity> findWorkflowAuthByFlowId(String workflowId);
	
	/**
	 * 根据流程ID 删除该流程的权限
	 * @param id
	 */
	public void deleteAuthByflowId(String id);
	
}
