package com.active4j.hr.system.service;

import com.active4j.hr.system.entity.SysLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysLogService extends IService<SysLogEntity> {
	
	/**
	 * 
	 * @description
	 *  	新增日志
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2020年2月4日 上午8:57:22
	 */
	public void addLog(SysLogEntity log);
	
}
