package com.active4j.hr.work.service;

import java.util.Date;
import java.util.List;

import com.active4j.hr.work.entity.OaWorkScheduleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkCalService extends IService<OaWorkScheduleEntity> {
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取日程
	 * @return List<OaWorkScheduleEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 下午1:31:11
	 */
	public List<OaWorkScheduleEntity> getOaWorkScheduleByUserId(String userId);
	
	
	public List<OaWorkScheduleEntity> getOaWorkScheduleByBusessionId(String businessId);
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取日程
	 * @return List<OaWorkScheduleEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 下午1:31:11
	 */
	public List<OaWorkScheduleEntity> getOaWorkScheduleByUserId(String userId, Date startDate, Date endDate);
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取日程
	 * @return List<OaWorkScheduleEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 下午1:31:11
	 */
	public List<OaWorkScheduleEntity> getOaWorkScheduleByUserId(Object[] ids, Date startDate, Date endDate);
	

	/**
	 * 添加日程  提醒功能  定时任务
	 * @param tmp
	 */
	public void saveAndTip(OaWorkScheduleEntity oaWorkScheduleEntity);

	/**
	 * 删除日程，并删除提醒
	 * @param oaWorkScheduleEntity
	 */
	public void deleteAndTip(OaWorkScheduleEntity oaWorkScheduleEntity);
}
