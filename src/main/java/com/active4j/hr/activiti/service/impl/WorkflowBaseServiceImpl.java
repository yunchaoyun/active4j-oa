package com.active4j.hr.activiti.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowBaseDao;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.core.model.AjaxJson;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title WorkflowAuthService.java
 * @description 
		  工作流 业务
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("workflowBaseService")
@Transactional
@Slf4j
public class WorkflowBaseServiceImpl extends ServiceImpl<WorkflowBaseDao, WorkflowBaseEntity> implements WorkflowBaseService {
	
	public AjaxJson validWorkflowBase(WorkflowBaseEntity workflowBaseEntity, AjaxJson j) {
		if(StringUtils.isBlank(workflowBaseEntity.getName())) {
			j.setSuccess(false);
			j.setMsg("流程名称不能为空");
			return j;
		}
		
		if(StringUtils.isBlank(workflowBaseEntity.getProjectNo())) {
			j.setSuccess(false);
			j.setMsg("流程编号不能为空");
			return j;
		}
		
		if(StringUtils.isBlank(workflowBaseEntity.getWorkflowId())) {
			j.setSuccess(false);
			j.setMsg("流程参数不能为空");
			return j;
		}
		
		if(StringUtils.isBlank(workflowBaseEntity.getLevel())) {
			j.setSuccess(false);
			j.setMsg("流程紧急程度不能为空");
			return j;
		}
		return j;
	}
	
}
