package com.active4j.hr.work.service.impl;

import java.util.List;

import org.apache.catalina.Globals;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.work.dao.OaWorkTaskDao;
import com.active4j.hr.work.domain.OaWorkTaskStatusDomain;
import com.active4j.hr.work.entity.OaWorkTaskCommentsEntity;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.entity.OaWorkTaskExcuteEntity;
import com.active4j.hr.work.service.OaWorkTaskCommentsService;
import com.active4j.hr.work.service.OaWorkTaskExcuteService;
import com.active4j.hr.work.service.OaWorkTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @description 
		  工作中心 任务管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkTaskService")
@Transactional
public class OaWorkTaskServiceImpl extends ServiceImpl<OaWorkTaskDao, OaWorkTaskEntity> implements OaWorkTaskService {
	
	@Autowired
	private OaWorkTaskCommentsService oaWorkTaskCommentsService;
	
	@Autowired
	private OaWorkTaskExcuteService oaWorkTaskExcuteService;

	public List<OaWorkTaskStatusDomain> queryOaWorkTaskStatusStat(String userId) {
		return this.baseMapper.queryOaWorkTaskStatusStat(userId);
	}
	
	public List<OaWorkTaskEntity> findOaWorkTaskByUserId(String userId) {
		QueryWrapper<OaWorkTaskEntity> queryWrapper = new QueryWrapper<OaWorkTaskEntity>();
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		queryWrapper.or(x->x.like("APPOINT_USER_ID", userId).like("USER_ID", userId));
		
		return this.list(queryWrapper);
	}
	
	
	public List<OaWorkTaskEntity> findChildrenTask(String taskId) {
		QueryWrapper<OaWorkTaskEntity> queryWrapper = new QueryWrapper<OaWorkTaskEntity>();
		queryWrapper.eq("PARENT_TASK_ID", taskId);
		
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTaskCommentsEntity> findOaWorkTaskComments(OaWorkTaskEntity oaWorkTaskEntity){
		QueryWrapper<OaWorkTaskCommentsEntity> queryWrapper = new QueryWrapper<OaWorkTaskCommentsEntity>();
		queryWrapper.eq("OA_WORK_TASK_ID", oaWorkTaskEntity.getId());
		
		return oaWorkTaskCommentsService.list(queryWrapper);
	}
	
	public List<OaWorkTaskExcuteEntity> findOaWorkTaskExcute(OaWorkTaskEntity oaWorkTaskEntity){
		QueryWrapper<OaWorkTaskExcuteEntity> queryWrapper = new QueryWrapper<OaWorkTaskExcuteEntity>();
		queryWrapper.eq("OA_WORK_TASK_ID", oaWorkTaskEntity.getId());
		
		return oaWorkTaskExcuteService.list(queryWrapper);
	}
	
	public void saveOrUpdate(OaWorkTaskEntity oaWorkTaskEntity, String status){
		//当改变状态为进行中时 赋值任务实际开始时间
		if(StringUtils.equals(status, GlobalConstant.OA_WORK_TASK_STATUS_DOING)){
			oaWorkTaskEntity.setActStartTime(DateUtils.getDate());
		}
		this.saveOrUpdate(oaWorkTaskEntity);
		
		saveStatus(oaWorkTaskEntity, status);
	}

	private void saveStatus(OaWorkTaskEntity oaWorkTaskEntity, String status) {
		//子任务也要改变状态
		List<OaWorkTaskEntity> lstChildren = findChildrenTask(oaWorkTaskEntity.getId());
		if(null != lstChildren && lstChildren.size() > 0) {
			for(OaWorkTaskEntity task : lstChildren) {
				task.setStatus(status);
				//当改变状态为进行中时 赋值任务实际开始时间
				if(StringUtils.equals(status, GlobalConstant.OA_WORK_TASK_STATUS_DOING)){
					task.setActStartTime(DateUtils.getDate());
				}
				this.saveOrUpdate(task);
				
				saveStatus(task, status);
			}
		}
	}
}
