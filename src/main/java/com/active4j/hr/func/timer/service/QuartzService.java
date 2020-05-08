package com.active4j.hr.func.timer.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.func.timer.constant.ScheduleConstant;
import com.active4j.hr.func.timer.entity.QuartzJobEntity;
import com.active4j.hr.func.timer.util.QuartzJobDisallowConcurrentExecution;
import com.active4j.hr.func.timer.util.QuartzJobExecution;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title QuartzService.java
 * @description 
 * @time  2019年12月9日 上午11:18:08
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class QuartzService {

	@Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
        	log.error("scheduler 启动报错，错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @description
     *  	新增定时任务
     * @params
     *      quartzJob 定时任务实体
     * @return boolean
     * @author guyp
     * @time 2019年12月9日 上午10:42:37
     */
    public boolean addJob(QuartzJobEntity quartzJob) {
    	try {
    		//判断实体为空性
    		if(null == quartzJob) {
    			return false;
    		}
    		String cronExpression = quartzJob.getCronExpression();
    		String jobName = quartzJob.getJobName();
    		String jobGroup = quartzJob.getJobGroup();
    		
		    //创建jobDetail实例，绑定Job实现类
		    //指明job的名称，所在组的名称，以及绑定job类
		    Class<? extends Job> jobClass = getQuartzJobClass(quartzJob);
        	JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
        	//放入参数，以便运行方法是获取
        	jobDetail.getJobDataMap().put(ScheduleConstant.QUARTZ_JOB_PARAM_KEY, quartzJob);
    		
    		//时间表达式调度构造器
        	CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(quartzJob, cronScheduleBuilder);
    		
            // 定义调度触发规则
        	// 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
        			.startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
        			.withSchedule(cronScheduleBuilder).build();
    		
            //判断任务是否存在
            if(scheduler.checkExists(getJobKey(jobName, jobGroup))) {
    			//防止创建时存在数据问题 先移除，然后在执行创建操作
            	this.deleteJob(jobName, jobGroup);
    		}
            // 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, trigger);
			
			//暂停任务
			if(StringUtils.equals(QuartzJobEntity.Job_Status_Pause, quartzJob.getJobStatus())) {
				this.pauseJob(jobName, jobGroup);
			}
			
	        log.info("新增定时任务，任务名称：{}", jobName);
	        return true;
    	} catch (Exception e) {
    		log.error("新增定时任务（实体）报错，错误信息：{}", e.getMessage());
			e.printStackTrace();
		}
		return false;
    }

    /**
     * 
     * @description
     *  	删除一个job
     * @params
     *      jobName 任务名称
     *      jobGroup 任务组名
     * @return boolean
     * @author guyp
     * @time 2019年12月9日 上午10:43:47
     */
    public boolean deleteJob(String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
            log.info("删除定时任务，jobName：{}，jobGroup：{}", jobName, jobGroup);
            return true;
        } catch (Exception e) {
        	log.error("删除定时任务报错，错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
		return false;
    }

    /**
     * 
     * @description
     *  	暂停一个job
     * @params
     *      jobName 任务名称
     *      jobGroup 任务组名
     * @return boolean
     * @author guyp
     * @time 2019年12月9日 上午10:44:12
     */
    public boolean pauseJob(String jobName, String jobGroup) {
        try {
            scheduler.pauseJob(getJobKey(jobName, jobGroup));
            log.info("暂停定时任务，jobName：{}，jobGroup：{}", jobName, jobGroup);
            return true;
        } catch (SchedulerException e) {
        	log.error("暂停定时任务报错，错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
		return false;
    }

    /**
     * 
     * @description
     *  	恢复一个job
     * @params
     *      jobName 任务名称
     *      jobGroup 任务组名
     * @return boolean
     * @author guyp
     * @time 2019年12月9日 上午10:46:20
     */
    public boolean resumeJob(String jobName, String jobGroup) {
        try {
            scheduler.resumeJob(getJobKey(jobName, jobGroup));
            log.info("恢复定时任务，jobName：{}，jobGroup：{}", jobName, jobGroup);
            return true;
        } catch (SchedulerException e) {
        	log.error("恢复定时任务报错，错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
		return false;
    }

    /**
     * 
     * @description
     *  	立即执行一个job
     * @params
     *      quartzJob 定时任务实体
     * @return boolean
     * @author guyp
     * @time 2019年12月9日 上午10:46:58
     */
    public boolean runAJobNow(QuartzJobEntity quartzJob) {
        try {
        	String jobName = quartzJob.getJobName();
        	String jobGroup = quartzJob.getJobGroup();
        	// 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleConstant.QUARTZ_JOB_PARAM_KEY, quartzJob);
            scheduler.triggerJob(getJobKey(jobName, jobGroup), dataMap);
            log.info("立即执行定时任务，jobName：{}，jobGroup：{}", jobName, jobGroup);
            return true;
        } catch (SchedulerException e) {
        	log.error("立即执行定时任务报错，错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
		return false;
    }
    
    /**
     * 
     * @description
     *  	得到quartz任务类
     * @params
     * @return Class<? extends Job>
     * @author guyp
     * @time 2019年12月10日 下午2:12:41
     */
    private static Class<? extends Job> getQuartzJobClass(QuartzJobEntity job) {
    	if(StringUtils.equals(QuartzJobEntity.Concurrent_Status_No, job.getConcurrentStatus())) {
    		return QuartzJobDisallowConcurrentExecution.class;
    	}else {
    		return QuartzJobExecution.class;
    	}
    }
    
    /**
     * 
     * @description
     *  	设置定时任务计划策略
     * @params
     * @return CronScheduleBuilder
     * @author guyp
     * @throws Exception 
     * @time 2019年12月10日 下午1:58:47
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(QuartzJobEntity job, CronScheduleBuilder cb) throws Exception {
    	switch (job.getMisfirePolicy()) {
            case QuartzJobEntity.Misfire_Policy_Default:
                return cb;
            case QuartzJobEntity.Misfire_Policy_Ignore_Misfires:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case QuartzJobEntity.Misfire_Policy_Fire_And_Proceed:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case QuartzJobEntity.Misfire_Policy_Do_Nothing:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
            	log.error("定时任务编号：{}，未能正确设置计划策略，策略：{}", job.getJobNo(), job.getMisfirePolicy());
            	throw new Exception("The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks");
        }
    }
    
    /**
     * 
     * @description
     *  	构造任务键对象
     * @params
     * @return JobKey
     * @author guyp
     * @time 2019年12月10日 下午2:05:35
     */
    public static JobKey getJobKey(String jobName, String jobGroup) {
        return JobKey.jobKey(jobName, jobGroup);
    }
    
}
