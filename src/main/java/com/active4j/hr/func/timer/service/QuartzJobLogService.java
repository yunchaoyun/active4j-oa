package com.active4j.hr.func.timer.service;

import com.active4j.hr.func.timer.entity.QuartzJobLogEntity;
import com.active4j.hr.func.timer.model.QuartzJobLogModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 * @title QuartzJobLogService.java
 * @description 
 * @time  2019年12月10日 上午9:59:44
 * @author guyp
 * @version 1.0
 */
public interface QuartzJobLogService extends IService<QuartzJobLogEntity> {
	
	/**
	 * 
	 * @description
	 *  	清空日志表
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2019年12月12日 上午11:14:27
	 */
	public void emptyLogs();
	
	/**
	 * 
	 * @description
	 *  	获取日志明细
	 * @params
	 * @return QuartzJobLogModel
	 * @author guyp
	 * @time 2020年4月7日 下午4:11:51
	 */
	public QuartzJobLogModel getLogDetailModel(String id);
	
}
