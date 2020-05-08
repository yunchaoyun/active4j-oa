package com.active4j.hr.work.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkDailyDao;
import com.active4j.hr.work.entity.OaWorkDailyEntity;
import com.active4j.hr.work.service.OaWorkDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaWorkDialyServiceImpl.java
 * @description 
		  工作中心 日报管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkDialyService")
@Transactional
public class OaWorkDailyServiceImpl extends ServiceImpl<OaWorkDailyDao, OaWorkDailyEntity> implements OaWorkDailyService {
	
	/**
	 * 
	 * @description
	 *  	根据用户ID  时间获取日报
	 * @return OaWorkDailyEntity
	 * @author 麻木神
	 * @time 2020年4月4日 下午9:19:33
	 */
	public OaWorkDailyEntity getOaWorkDaily(String userId, String strDate) {
		QueryWrapper<OaWorkDailyEntity> queryWrapper = new QueryWrapper<OaWorkDailyEntity>();
		queryWrapper.eq("USER_ID", userId);
		queryWrapper.eq("STR_SUBMIT_DATE", strDate);
		
		return this.getOne(queryWrapper);
	}
	
}
