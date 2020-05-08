package com.active4j.hr.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.work.dao.OaWorkTaskExcuteDao;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.entity.OaWorkTaskExcuteEntity;
import com.active4j.hr.work.service.OaWorkTaskExcuteService;
import com.active4j.hr.work.service.OaWorkTaskService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @description 
		  工作中心 任务管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkTaskExcuteService")
@Transactional
public class OaWorkTaskExcuteServiceImpl extends ServiceImpl<OaWorkTaskExcuteDao, OaWorkTaskExcuteEntity> implements OaWorkTaskExcuteService {
	

	@Autowired
	private OaWorkTaskService oaWorkTaskService;
	
	/**
	 * 再添加执行的时候，根据进度维护主任务的进度 如果进度是100 则设置状态为已完成
	 */
	public void save(OaWorkTaskExcuteEntity oaWorkTaskExcuteEntity, OaWorkTaskEntity oaWorkTaskEntity){
		this.save(oaWorkTaskExcuteEntity);
		
		//任务本身
		oaWorkTaskEntity.setProgress(oaWorkTaskExcuteEntity.getProgress());
		if(oaWorkTaskEntity.getProgress() == 100) {
			oaWorkTaskEntity.setStatus(GlobalConstant.OA_WORK_TASK_STATUS_FINISH);
			oaWorkTaskEntity.setEndTime(DateUtils.getDate());
		}
		oaWorkTaskService.saveOrUpdate(oaWorkTaskEntity);
		
		while(StringUtils.isNotEmpty(oaWorkTaskEntity.getParentTaskId())) {
			//改变当前任务为父任务
			oaWorkTaskEntity = oaWorkTaskService.getById(oaWorkTaskEntity.getParentTaskId());
			
			//查询父任务下的子任务列表
			List<OaWorkTaskEntity> lstChildren = oaWorkTaskService.findChildrenTask(oaWorkTaskEntity.getId());
			
			if(null != lstChildren && lstChildren.size() > 0) {
				int total = 0;
				for(OaWorkTaskEntity t : lstChildren) {
					total += t.getProgress();
				}
				int parentProgress = total / lstChildren.size();
				
				oaWorkTaskEntity.setProgress(parentProgress);
				
				if(oaWorkTaskEntity.getProgress() == 100) {
					oaWorkTaskEntity.setStatus(GlobalConstant.OA_WORK_TASK_STATUS_FINISH);
					oaWorkTaskEntity.setEndTime(DateUtils.getDate());
				}
				
				oaWorkTaskService.saveOrUpdate(oaWorkTaskEntity);
			}
			
		}
	}
}
