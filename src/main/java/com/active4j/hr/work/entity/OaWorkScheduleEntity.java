package com.active4j.hr.work.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkScheduleEntity.java
 * @description 日程管理
 * @time 2020年4月7日 上午11:58:48
 * @author 麻木神
 * @version 1.0
 */
@TableName("OA_WORK_Schedule")
@Getter
@Setter
public class OaWorkScheduleEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4817702373685105093L;

	// 日程用户
	@TableField("USER_ID")
	private String userId;

	@TableField("USER_NAME")
	private String userName;

	@TableField("CREATE_USER_ID")
	private String createUserId;

	@TableField("CREATE_USER_NAME")
	private String createUserName;

	// 日程类型 0：会议 1：任务 2:自定义
	@TableField("TYPE")
	private String type;

	// 日程类别 0：一次性 1：每天 2：每周 3：每月
	@TableField("CATEGORY")
	private String category;

	// 状态 0:未安排 1：已安排 2：已完成
	@TableField("STATUS")
	private String status;

	// 日程标题
	@TableField("TITLE")
	private String title;

	// 开始时间
	@TableField("START_TIME")
	private Date startTime;

	// 结束时间
	@TableField("END_TIME")
	private Date endTime;

	// 是否启用会议提醒
	@TableField("TIP")
	private boolean tip;

	// 提醒方式
	@TableField("TIP_TYPE")
	private int tipType;

	// 提醒时间
	@TableField("TIP_TIME")
	private Date tipTime;

	@TableField("BUSINESS_ID")
	private String businessId;

}
