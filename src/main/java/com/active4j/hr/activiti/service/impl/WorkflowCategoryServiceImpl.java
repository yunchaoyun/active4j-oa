package com.active4j.hr.activiti.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowCategoryDao;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title WorkflowCategoryService.java
 * @description 
		  工作流
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("workflowCategoryService")
@Transactional
public class WorkflowCategoryServiceImpl extends ServiceImpl<WorkflowCategoryDao, WorkflowCategoryEntity> implements WorkflowCategoryService {
	
	
}
