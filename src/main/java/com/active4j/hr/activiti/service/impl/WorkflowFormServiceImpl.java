package com.active4j.hr.activiti.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowFormDao;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title WorkflowformService.java
 * @description 
		  表单类别
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("workflowFormService")
@Transactional
public class WorkflowFormServiceImpl extends ServiceImpl<WorkflowFormDao, WorkflowFormEntity> implements WorkflowFormService {
	
	public List<WorkflowFormEntity> findWorkflowForm(String categoryId){
		QueryWrapper<WorkflowFormEntity> queryWrapper = new QueryWrapper<WorkflowFormEntity>();
		queryWrapper.eq("CATEGORY_ID", categoryId);
		return this.list(queryWrapper);
	}
	
}
