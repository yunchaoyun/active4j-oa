package com.active4j.hr.func.timer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuartzJobLogModel {
	
	/**
	 * 任务编号
	 */
	private String jobNo;
	
	/**
	 * 任务名称
	 */
	private String jobName;
	
	/**
	 * 任务组名
	 */
	private String jobGroup;
	
	/**
	 * 执行状态（0：成功，1：失败）
	 */
	private String status;
	
	/**
	 * 任务简称
	 */
	private String shortName;
	
	/**
	 * 日志信息
	 */
	private String jobMessage;
	
	/**
	 * 异常信息
	 */
	private String exceptionInfo;
	
	/**
	 * 调用参数
	 */
	private String invokeParams;
	
	/**
	 * 开始执行时间
	 */
	private String startTime;
	
	/**
	 * 结束执行时间
	 */
	private String endTime;
	
}
