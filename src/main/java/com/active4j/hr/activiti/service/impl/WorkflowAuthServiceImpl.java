package com.active4j.hr.activiti.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowAuthDao;
import com.active4j.hr.activiti.entity.WorkflowAuthEntity;
import com.active4j.hr.activiti.service.WorkflowAuthService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@Service("workflowAuthService")
@Transactional
public class WorkflowAuthServiceImpl extends ServiceImpl<WorkflowAuthDao, WorkflowAuthEntity> implements WorkflowAuthService {
	
	public List<WorkflowAuthEntity> findWorkflowAuthByFlowId(String workflowId){
		QueryWrapper<WorkflowAuthEntity> queryWrapper = new QueryWrapper<WorkflowAuthEntity>();
		queryWrapper.eq("WORKFLOW_ID", workflowId);
		return this.list(queryWrapper);
	}
	
	/**
	 * 根据流程ID 删除该流程的权限
	 * @param id
	 */
	public void deleteAuthByflowId(String id) {
		QueryWrapper<WorkflowAuthEntity> queryWrapper = new QueryWrapper<WorkflowAuthEntity>();
		queryWrapper.eq("WORKFLOW_ID", id);
		this.remove(queryWrapper);
	}
}
