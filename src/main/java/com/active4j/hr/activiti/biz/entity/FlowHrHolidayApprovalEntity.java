package com.active4j.hr.activiti.biz.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title HrHolidayApprovalEntity.java
 * @description 
		人事 请假
 * @time  2020年4月28日 上午10:37:23
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
@TableName("flow_hr_holiday_approval")
public class FlowHrHolidayApprovalEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3234270096222798664L;

	/**
	 * 开始日期
	 */
	@TableField("START_DAY")
	private Date startDay;
	
	/**
	 * 结束日期
	 */
	@TableField("END_DAY")
	private Date endDay;
	
	/**
	 * 请假天数
	 */
	@TableField("DAYS")
	private int days;
	
	/**
	 * 理由
	 */
	@TableField("REASON")
	private String reason;
	
}
