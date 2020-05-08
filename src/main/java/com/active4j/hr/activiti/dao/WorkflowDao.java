package com.active4j.hr.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @title WorkflowDao.java
 * @description 
		  流程dao
 * @time  2020年4月27日 下午7:09:17
 * @author 麻木神
 * @version 1.0
*/
public interface WorkflowDao {

   public IPage<WorkflowBaseEntity> findGroupTaskStrsByUserName(IPage<WorkflowBaseEntity> page, @Param("userName") String userName,
		   @Param("categoryId") String categoryId, @Param("projectNo") String projectNo,
		   @Param("name") String name, @Param("applyName") String applyName,
		   @Param("applyDateStart") String applyDateStart, @Param("applyDateEnd") String applyDateEnd);
   
   
   public List<String> findGroupTaskIdByBusinessKey(@Param("businessKey") String businessKey, @Param("userName") String userName);
   
   public IPage<WorkflowBaseEntity> findFinishedTaskByUserName(IPage<WorkflowBaseEntity> page, @Param("userName") String userName,
		   @Param("categoryId") String categoryId, @Param("projectNo") String projectNo,
		   @Param("name") String name, @Param("applyName") String applyName,
		   @Param("applyDateStart") String applyDateStart, @Param("applyDateEnd") String applyDateEnd);
   
   public IPage<WorkflowBaseEntity> findTaskStrsByUserName(IPage<WorkflowBaseEntity> page, @Param("userName") String userName,
		   @Param("categoryId") String categoryId, @Param("projectNo") String projectNo,
		   @Param("name") String name, @Param("applyName") String applyName,
		   @Param("applyDateStart") String applyDateStart, @Param("applyDateEnd") String applyDateEnd);
   
}
