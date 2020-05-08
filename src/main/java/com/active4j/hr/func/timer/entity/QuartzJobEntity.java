package com.active4j.hr.func.timer.entity;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.active4j.hr.func.timer.util.CronUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Setter;

/**
 * 
 * @title QuartzJobEntity.java
 * @description 
		定时任务实体
 * @time  2019年12月9日 上午10:12:16
 * @author guyp
 * @version 1.0
 */
@TableName("func_quartz_job")
@Setter
public class QuartzJobEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4415281953510069503L;
	
	/**
	 * 任务执行状态（0：就绪，1：正在执行，2：完成，3：暂停，4：异常）
	 */
	public static final String Job_Execute_Status_Ready = "0";
	public static final String Job_Execute_Status_Running = "1";
	public static final String Job_Execute_Status_Complete = "2";
	public static final String Job_Execute_Status_Pause = "3";
	public static final String Job_Execute_Status_Error = "4";
	
	/**
	 * 任务状态（0：启用，3：暂停）
	 */
	public static final String Job_Status_Start = "0";
	public static final String Job_Status_Pause = "3";
	
	/**
	 * 并发状态（0：禁止，1：允许）
	 */
	public static final String Concurrent_Status_No = "0";
	public static final String Concurrent_Status_Yes = "1";
	
	/**
	 * 计划策略（0：默认，1：立即触发执行，2：触发一次执行，3：不触发立即执行）
	 */
	public static final String Misfire_Policy_Default = "0";
	public static final String Misfire_Policy_Ignore_Misfires = "1";
	public static final String Misfire_Policy_Fire_And_Proceed = "2";
	public static final String Misfire_Policy_Do_Nothing = "3";
	
	/**
	 * 任务编号
	 */
	@TableField("JOB_NO")
	private String jobNo;
	
	/**
	 * 任务名称
	 */
	@TableField("JOB_NAME")
	private String jobName;
	
	/**
	 * 任务分组
	 */
	@TableField("JOB_GROUP")
	@QueryField(queryColumn="JOB_GROUP", condition=QueryCondition.eq)
	@NotEmpty(message="任务分组不能为空")
	@Size(min = 0, max=100, message="任务分组最多只能输入100个字符")
	private String jobGroup;
	
	/**
	 * 任务状态（0：启用，3：暂停）
	 */
	@TableField("JOB_STATUS")
	@QueryField(queryColumn="JOB_STATUS", condition=QueryCondition.eq)
	@NotEmpty(message="任务状态不能为空")
	@Size(min = 0, max=10, message="任务状态最多只能输入10个字符")
	private String jobStatus;
	
	/**
	 * 执行状态（0：就绪，1：正在执行，2：完成，3：暂停，4：异常）
	 */
	@TableField("JOB_EXECUTE_STATUS")
	private String jobExecuteStatus;
	
	/**
	 * 任务运行时间表达式
	 */
	@TableField("CRON_EXPRESSION")
	@NotEmpty(message="cron表达式不能为空")
	@Size(min = 0, max=200, message="cron表达式最多只能输入200个字符")
	private String cronExpression;
	
	/**
	 * 任务简称
	 */
	@TableField("SHORT_NAME")
	@QueryField(queryColumn="SHORT_NAME", condition=QueryCondition.eq)
	@NotEmpty(message="任务简称不能为空")
	@Size(min = 0, max=100, message="任务简称最多只能输入100个字符")
	private String shortName;
	
	/**
	 * 任务描述
	 */
	@TableField("DESCRIPTION")
	@Size(min = 0, max=250, message="任务描述最多只能输入250个字符")
	private String description;
	
	/**
	 * 调用参数
	 */
	@TableField("INVOKE_PARAMS")
	@NotEmpty(message="调用参数不能为空")
	@Size(min = 0, max=500, message="调用参数最多只能输入500个字符")
	private String invokeParams;
	
	/**
	 * 上次执行时间
	 */
	@TableField("PREVIOS_TIME")
	private Date previousTime;
	
	/**
	 * 下次执行时间（不保存库）
	 */
	@TableField(exist = false)
	private Date nextTime;
	
	/**
	 * 并发状态（0：禁止并发，1：允许并发）
	 */
	@TableField("CONCURRENT_STATUS")
	@NotEmpty(message="并发执行不能为空")
	@Size(min = 0, max=10, message="并发执行最多只能输入10个字符")
	private String concurrentStatus;
	
	/**
	 * 执行策略（0：默认，1：立即触发执行，2：触发一次执行，3：不触发立即执行）
	 */
	@TableField("MISFIRE_POLICY")
	@NotEmpty(message="执行策略不能为空")
	@Size(min = 0, max=10, message="执行策略最多只能输入10个字符")
	private String misfirePolicy;

	public String getJobNo() {
		return jobNo;
	}

	public String getJobName() {
		return jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public String getJobExecuteStatus() {
		return jobExecuteStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDescription() {
		return description;
	}

	public String getInvokeParams() {
		return invokeParams;
	}

	public Date getPreviousTime() {
		return previousTime;
	}

	public Date getNextTime() {
		//返回下次执行时间
		return CronUtils.getNextExecution(cronExpression);
	}

	public String getConcurrentStatus() {
		return concurrentStatus;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}
	
	
}
