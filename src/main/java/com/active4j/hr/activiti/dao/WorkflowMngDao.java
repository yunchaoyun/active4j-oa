package com.active4j.hr.activiti.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @title WorkflowCategoryDao.java
 * @description 
		  流程
 * @time  2020年4月16日 下午2:55:37
 * @author 麻木神
 * @version 1.0
*/
public interface WorkflowMngDao extends BaseMapper<WorkflowMngEntity>{

	public List<WorkflowMngEntity> findWorkflowMngByUserIdAndRoleIds(@Param("userId")String userId, @Param("roleIds")List<String> roleIds);
	
}
