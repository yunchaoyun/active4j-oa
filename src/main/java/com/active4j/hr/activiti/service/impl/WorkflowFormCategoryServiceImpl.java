package com.active4j.hr.activiti.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowFormCategoryDao;
import com.active4j.hr.activiti.entity.WorkflowFormCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowFormCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title WorkflowCategoryService.java
 * @description 
		  表单类别
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("workflowFormCategoryService")
@Transactional
public class WorkflowFormCategoryServiceImpl extends ServiceImpl<WorkflowFormCategoryDao, WorkflowFormCategoryEntity> implements WorkflowFormCategoryService {
	
	
}
