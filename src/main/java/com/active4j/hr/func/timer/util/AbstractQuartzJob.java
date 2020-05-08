package com.active4j.hr.func.timer.util;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.func.timer.constant.ScheduleConstant;
import com.active4j.hr.func.timer.entity.QuartzJobEntity;
import com.active4j.hr.func.timer.entity.QuartzJobLogEntity;
import com.active4j.hr.func.timer.service.QuartzJobLogService;
import com.active4j.hr.func.timer.service.QuartzJobService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title AbstractQuartzJob.java
 * @description 
		抽象quartz调用
 * @time  2019年12月10日 上午11:43:49
 * @author guyp
 * @version 1.0
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {
	
	private static QuartzJobLogService quartzJobLogService = ApplicationContextUtil.getContext().getBean(QuartzJobLogService.class);
	
	private static QuartzJobService quartzJobService = ApplicationContextUtil.getContext().getBean(QuartzJobService.class);
	
	/**
	 * 线程本地变量
	 */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	QuartzJobEntity job = new QuartzJobEntity();
    	try {
    		//对象属性拷贝
    		BeanUtils.copyProperties(job, context.getMergedJobDataMap().get(ScheduleConstant.QUARTZ_JOB_PARAM_KEY));;
    		//执行前
	    	doBefore(context, job);
	        if (null != job) {
	        	//执行任务
	        	doExecute(context, job);
	        }
	        //执行完成
	        doAfter(context, job, null, QuartzJobEntity.Job_Execute_Status_Complete);
    	} catch (Exception e) {
    		log.error("定时任务任务执行报错，错误原因：{}", e.getMessage());
    		doAfter(context, job, e.getMessage(), QuartzJobEntity.Job_Execute_Status_Error);
    	}
    }

    /**
     * 
     * @description
     *  	执行前
     * @params
     *      context 传输对象
     *      job 定时任务
     * @return void
     * @author guyp
     * @time 2019年12月10日 上午11:42:30
     */
    protected void doBefore(JobExecutionContext context, QuartzJobEntity job) {
    	//线程内部储存任务开始时间
        threadLocal.set(new Date());
        //将任务执行状态修改为执行中
        job.setUpdateName("system");
        job.setJobExecuteStatus(QuartzJobEntity.Job_Execute_Status_Running);
        //上次执行时间赋值
        job.setPreviousTime(new Date());
        quartzJobService.updateJobById(job);
    }
    
    /**
     * 
     * @description
     *  	执行后
     * @params
     *      context 传输对象
     *      job 定时任务
     * @return void
     * @author guyp
     * @time 2019年12月10日 上午11:42:59
     */
    protected void doAfter(JobExecutionContext context, QuartzJobEntity job, String message, String jobExecuteStatus) {
    	//获取开始执行时间，释放对象
        Date startTime = threadLocal.get();
        threadLocal.remove();
        
      	//修改任务执行状态
      	job.setJobExecuteStatus(jobExecuteStatus);
        //修改定时任务（状态，触发时间信息）
        quartzJobService.updateJobById(job);
        //构造日志实体
        final QuartzJobLogEntity jobLog = new QuartzJobLogEntity();
        jobLog.setCreateName("system");
        jobLog.setJobNo(job.getJobNo());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setShortName(job.getShortName());
        jobLog.setInvokeParams(job.getInvokeParams());
        jobLog.setStartTime(startTime);
        jobLog.setEndTime(new Date());
        long runMs = jobLog.getEndTime().getTime() - jobLog.getStartTime().getTime();
        jobLog.setJobMessage(job.getShortName() + " 总耗时：" + runMs + "毫秒");
        //异常信息处理
        if (StringUtils.isNotEmpty(message)) {
        	jobLog.setStatus(QuartzJobLogEntity.Status_Failed);
            String errorMsg = StringUtils.substring(message, 0, 1900);
            jobLog.setExceptionInfo(errorMsg);
        }else {
        	jobLog.setStatus(QuartzJobLogEntity.Status_Success);
        }
        //保存进库
        quartzJobLogService.save(jobLog);
    }

    /**
     * 
     * @description
     *  	执行方法，由子类重载
     * @params
     *      context 传输对象
     *      job 定时任务
     * @return void
     * @author guyp
     * @time 2019年12月10日 上午11:41:19
     */
    protected abstract void doExecute(JobExecutionContext context, QuartzJobEntity job) throws Exception;
}
