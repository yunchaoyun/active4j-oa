package com.active4j.hr.hr.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrTrainPlanEntity.java
 * @description 
		培训实施
 * @time  2020年4月23日 上午9:37:20
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_train_force")
@Getter
@Setter
public class OaHrTrainForceEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 612424820748053382L;
	
	/**
	 * 开始日期
	 */
	@TableField("START_DATE")
	private Date startDate;
	
	/**
	 * 结束日期
	 */
	@TableField("END_DATE")
	private Date endDate;
	
	/**
	 * 培训地点
	 */
	@TableField("PLACE")
	private String place;
	
	/**
	 * 负责人
	 */
	@TableField("LEAD_PERSON")
	private String leadPerson;
	
	/**
	 * 参加人员
	 */
	@TableField("CAN_PERSON")
	private String canPerson;
	
	/**
	 * 计划id
	 */
	@TableField("PLAN_ID")
	private String planId;
	
}

