package com.active4j.hr.func.timer.util;

import org.quartz.JobExecutionContext;

import com.active4j.hr.func.timer.entity.QuartzJobEntity;

/**
 * 
 * @title QuartzJobExecution.java
 * @description 
		定时任务处理（允许并发执行）
 * @time  2019年12月10日 下午12:21:38
 * @author guyp
 * @version 1.0
 */
public class QuartzJobExecution extends AbstractQuartzJob {

	@Override
	protected void doExecute(JobExecutionContext context, QuartzJobEntity job) throws Exception {
		QuartzJobInvokeUtil.invokeMethod(job);
	}
}
