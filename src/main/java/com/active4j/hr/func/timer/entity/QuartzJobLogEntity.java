package com.active4j.hr.func.timer.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title QuartzJobLogEntity.java
 * @description 
		定时任务日志实体
 * @time  2019年12月10日 上午9:53:51
 * @author guyp
 * @version 1.0
 */
@TableName("func_quartz_job_log")
@Getter
@Setter
public class QuartzJobLogEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1649976398110313717L;
	
	/**
	 * 执行状态（0：成功，1：失败）
	 */
	public static final String Status_Success = "0";
	public static final String Status_Failed = "1";
	
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
	 * 任务组名
	 */
	@TableField("JOB_GROUP")
	@QueryField(queryColumn="JOB_GROUP", condition=QueryCondition.eq)
	private String jobGroup;
	
	/**
	 * 执行状态（0：成功，1：失败）
	 */
	@TableField("STATUS")
	@QueryField(queryColumn="STATUS", condition=QueryCondition.eq)
	private String status;
	
	/**
	 * 任务简称
	 */
	@TableField("SHORT_NAME")
	@QueryField(queryColumn="SHORT_NAME", condition=QueryCondition.eq)
	private String shortName;
	
	/**
	 * 日志信息
	 */
	@TableField("JOB_MESSAGE")
	private String jobMessage;
	
	/**
	 * 异常信息
	 */
	@TableField("EXCEPTION_INFO")
	private String exceptionInfo;
	
	/**
	 * 调用参数
	 */
	@TableField("INVOKE_PARAMS")
	private String invokeParams;
	
	/**
	 * 开始执行时间
	 */
	@TableField("START_TIME")
	@QueryField(queryColumn="START_TIME", condition=QueryCondition.range)
	private Date startTime;
	
	/**
	 * 结束执行时间
	 */
	@TableField("END_TIME")
	private Date endTime;
	
}
