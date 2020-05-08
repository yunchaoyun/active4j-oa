package com.active4j.hr.system.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.system.dao.SysLogDao;
import com.active4j.hr.system.entity.SysLogEntity;
import com.active4j.hr.system.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 日志管理service类
 * @author 38943
 *
 */
@Service("sysLogService")
@Transactional
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {
	
	/**
	 * 新增日志
	 */
	public void addLog(SysLogEntity log) {
		
		//基础变量赋值
		log.setCreateName("system");
		
		//有些方法参数特别长，特殊处理一下
		if(StringUtils.length(log.getParams()) > 500) {
			log.setParams(StringUtils.substring(log.getParams(), 0, 500) + "...");
		}
		
		//保存
		this.save(log);
	}
	
}
