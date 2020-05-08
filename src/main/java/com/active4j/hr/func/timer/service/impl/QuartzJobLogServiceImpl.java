package com.active4j.hr.func.timer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.func.timer.dao.QuartzJobLogDao;
import com.active4j.hr.func.timer.entity.QuartzJobLogEntity;
import com.active4j.hr.func.timer.model.QuartzJobLogModel;
import com.active4j.hr.func.timer.service.QuartzJobLogService;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title QuartzJobLogServiceImpl.java
 * @description 
		定时任务日志
 * @time  2019年12月10日 上午10:00:45
 * @author guyp
 * @version 1.0
 */
@Service("quartzJobLogService")
@Transactional
public class QuartzJobLogServiceImpl extends ServiceImpl<QuartzJobLogDao, QuartzJobLogEntity> implements QuartzJobLogService {

	/**
	 * 
	 * @description
	 *  	清空日志表
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2019年12月12日 上午11:14:27
	 */
	public void emptyLogs() {
		this.remove(new QueryWrapper<QuartzJobLogEntity>());
	}

	/**
	 * 
	 * @description
	 *  	获取日志明细
	 * @params
	 * @return QuartzJobLogModel
	 * @author guyp
	 * @time 2020年4月7日 下午4:12:26
	 */
	public QuartzJobLogModel getLogDetailModel(String id) {
		QuartzJobLogModel model = new QuartzJobLogModel();
		//根据id获取任务实体
		QuartzJobLogEntity log = this.getById(id);
		if(null != log) {
			//常规赋值
			model.setJobNo(log.getJobNo());
			model.setJobName(log.getJobName());
			model.setShortName(log.getShortName());
			model.setJobMessage(log.getJobMessage());
			model.setExceptionInfo(log.getExceptionInfo());
			model.setInvokeParams(log.getInvokeParams());
			//任务分组的处理
			model.setJobGroup(SystemUtils.getDictionaryValue("func_timer_job_group", log.getJobGroup()));
			//任务执行状态的处理
			model.setStatus(SystemUtils.getDictionaryValue("func_timer_job_log_status", log.getStatus()));
			//开始执行时间的处理
			if(null != log.getStartTime()) {
				model.setStartTime(DateUtils.getDate2Str(log.getStartTime()));
			}
			//结束执行时间的处理
			if(null != log.getEndTime()) {
				model.setEndTime(DateUtils.getDate2Str(log.getEndTime()));
			}
		}
		return model;
	}

}
