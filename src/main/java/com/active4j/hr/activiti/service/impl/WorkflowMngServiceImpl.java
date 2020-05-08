package com.active4j.hr.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowMngDao;
import com.active4j.hr.activiti.entity.WorkflowAuthEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowAuthService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@Service("workflowMngService")
@Transactional
public class WorkflowMngServiceImpl extends ServiceImpl<WorkflowMngDao, WorkflowMngEntity> implements WorkflowMngService {
	
	@Autowired
	private WorkflowAuthService workflowAuthService;
	
	public void saveWorkflowMng(WorkflowMngEntity workflowMngEntity, String[] RList, String[] UList) {
		//保存
		this.save(workflowMngEntity);
		
		
		//角色权限保存
		List<WorkflowAuthEntity> lstAuths = new ArrayList<WorkflowAuthEntity>();
		if(null != RList) {
			for(String str : RList) {
				WorkflowAuthEntity auth = new WorkflowAuthEntity();
				auth.setType("R");
				auth.setURId(str);
				auth.setWorkflowId(workflowMngEntity.getId());
				lstAuths.add(auth);
			}
		}
		//用户权限保存
		if(null != UList) {
			for(String str : UList) {
				WorkflowAuthEntity auth = new WorkflowAuthEntity();
				auth.setType("U");
				auth.setURId(str);
				auth.setWorkflowId(workflowMngEntity.getId());
				lstAuths.add(auth);
			}
		}
		
		//保存权限
		workflowAuthService.saveBatch(lstAuths);
	}
	
	
	public void saveOrUpdateWorkflowMng(WorkflowMngEntity workflowMngEntity, String[] RList, String[] UList) {
		this.saveOrUpdate(workflowMngEntity);
		
		//清除权限重新保存
		workflowAuthService.deleteAuthByflowId(workflowMngEntity.getId());
		
		//角色权限保存
		List<WorkflowAuthEntity> lstAuths = new ArrayList<WorkflowAuthEntity>();
		if(null != RList) {
			for(String str : RList) {
				WorkflowAuthEntity auth = new WorkflowAuthEntity();
				auth.setType("R");
				auth.setURId(str);
				auth.setWorkflowId(workflowMngEntity.getId());
				lstAuths.add(auth);
			}
		}
		//用户权限保存
		if(null != UList) {
			for(String str : UList) {
				WorkflowAuthEntity auth = new WorkflowAuthEntity();
				auth.setType("U");
				auth.setURId(str);
				auth.setWorkflowId(workflowMngEntity.getId());
				lstAuths.add(auth);
			}
		}
		
		//保存权限
		workflowAuthService.saveBatch(lstAuths);
	}
	
	public void delete(String id) {
		this.removeById(id);
		
		//清除权限重新保存
		workflowAuthService.deleteAuthByflowId(id);
	}
	
	
	public List<WorkflowMngEntity> findWorkflowMngByUserIdAndRoleIds(String userId, List<String> roleIds) {
		return this.baseMapper.findWorkflowMngByUserIdAndRoleIds(userId, roleIds);
		
	}
	
}
