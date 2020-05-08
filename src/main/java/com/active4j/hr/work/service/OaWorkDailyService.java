package com.active4j.hr.work.service;

import com.active4j.hr.work.entity.OaWorkDailyEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkDailyService extends IService<OaWorkDailyEntity> {
	
	/**
	 * 
	 * @description
	 *  	根据用户ID  时间获取日报
	 * @return OaWorkDailyEntity
	 * @author 麻木神
	 * @time 2020年4月4日 下午9:19:33
	 */
	public OaWorkDailyEntity getOaWorkDaily(String userId, String strDate);
}
