package com.active4j.hr.work.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkCalDao;
import com.active4j.hr.work.entity.OaWorkScheduleEntity;
import com.active4j.hr.work.service.OaWorkCalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaWorkDialyServiceImpl.java
 * @description 
		  工作中心 日程管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkCalService")
@Transactional
public class OaWorkCalServiceImpl extends ServiceImpl<OaWorkCalDao, OaWorkScheduleEntity> implements OaWorkCalService {
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取日程
	 * @return List<OaWorkScheduleEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 下午1:31:11
	 */
	public List<OaWorkScheduleEntity> getOaWorkScheduleByUserId(String userId){
		QueryWrapper<OaWorkScheduleEntity> queryWrapper = new QueryWrapper<OaWorkScheduleEntity>();
		queryWrapper.eq("USER_ID", userId);
		queryWrapper.eq("STATUS", "0");
		queryWrapper.orderByDesc("CREATE_DATE");
		return this.list(queryWrapper);
	}
	
	public List<OaWorkScheduleEntity> getOaWorkScheduleByBusessionId(String businessId) {
		QueryWrapper<OaWorkScheduleEntity> queryWrapper = new QueryWrapper<OaWorkScheduleEntity>();
		queryWrapper.eq("BUSINESS_ID", businessId);
		return this.list(queryWrapper);
	}
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取日程
	 * @return List<OaWorkScheduleEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 下午1:31:11
	 */
	public List<OaWorkScheduleEntity> getOaWorkScheduleByUserId(String userId, Date startDate, Date endDate) {
		QueryWrapper<OaWorkScheduleEntity> queryWrapper = new QueryWrapper<OaWorkScheduleEntity>();
		queryWrapper.eq("USER_ID", userId);
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		queryWrapper.gt("START_TIME", startDate);
		queryWrapper.lt("END_TIME", endDate);
		return this.list(queryWrapper);
	}
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取日程
	 * @return List<OaWorkScheduleEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 下午1:31:11
	 */
	public List<OaWorkScheduleEntity> getOaWorkScheduleByUserId(Object[] ids, Date startDate, Date endDate) {
		QueryWrapper<OaWorkScheduleEntity> queryWrapper = new QueryWrapper<OaWorkScheduleEntity>();
		queryWrapper.in("USER_ID", ids);
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		queryWrapper.gt("START_TIME", startDate);
		queryWrapper.lt("END_TIME", endDate);
		return this.list(queryWrapper);
	}
	

	/**
	 * 添加日程  提醒功能  定时任务
	 * @param tmp
	 */
	public void saveAndTip(OaWorkScheduleEntity oaWorkScheduleEntity) {
		if(oaWorkScheduleEntity.isTip()) {
			//新增定时任务
			
		}
		this.save(oaWorkScheduleEntity);
	}

	/**
	 * 删除日程，并删除提醒
	 * @param oaWorkScheduleEntity
	 */
	public void deleteAndTip(OaWorkScheduleEntity oaWorkScheduleEntity) {
		this.removeById(oaWorkScheduleEntity.getId());
		
		if(oaWorkScheduleEntity.isTip()) {
			//删除提醒 定时任务
			
		}
	}
}
