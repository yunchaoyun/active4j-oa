package com.active4j.hr.func.timer.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.active4j.hr.func.timer.entity.QuartzJobEntity;

/**
 * 
 * @title QuartzDisallowConcurrentExecution.java
 * @description 
		定时任务处理（禁止并发执行）
 * @time  2019年12月10日 下午12:21:05
 * @author guyp
 * @version 1.0
 */
@DisallowConcurrentExecution
public class QuartzJobDisallowConcurrentExecution extends AbstractQuartzJob {

	@Override
	protected void doExecute(JobExecutionContext context, QuartzJobEntity job) throws Exception {
		QuartzJobInvokeUtil.invokeMethod(job);
	}
}
