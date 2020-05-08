package com.active4j.hr.func.timer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title QuartzJobModel.java
 * @description 
		定时任务实体model
 * @time  2020年4月7日 上午10:46:24
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
public class QuartzJobModel {
	
	/**
	 * 任务编号
	 */
	private String jobNo;
	
	/**
	 * 任务名称
	 */
	private String jobName;
	
	/**
	 * 任务分组
	 */
	private String jobGroup;
	
	/**
	 * 任务状态（0：启用，3：暂停）
	 */
	private String jobStatus;
	
	/**
	 * 执行状态（0：就绪，1：正在执行，2：完成，3：暂停，4：异常）
	 */
	private String jobExecuteStatus;
	
	/**
	 * 任务运行时间表达式
	 */
	private String cronExpression;
	
	/**
	 * 任务简称
	 */
	private String shortName;
	
	/**
	 * 任务描述
	 */
	private String description;
	
	/**
	 * 调用参数
	 */
	private String invokeParams;
	
	/**
	 * 上次执行时间
	 */
	private String previousTime;
	
	/**
	 * 下次执行时间（不保存库）
	 */
	private String nextTime;
	
	/**
	 * 并发状态（0：禁止并发，1：允许并发）
	 */
	private String concurrentStatus;
	
	/**
	 * 执行策略（0：默认，1：立即触发执行，2：触发一次执行，3：不触发立即执行）
	 */
	private String misfirePolicy;	
	
}
