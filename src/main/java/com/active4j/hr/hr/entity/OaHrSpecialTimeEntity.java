package com.active4j.hr.hr.entity;

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
 * @title OaHrSpecialTimeEntity.java
 * @description 
		特殊考勤维护实体
 * @time  2020年4月24日 上午9:42:41
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_special_time")
@Getter
@Setter
public class OaHrSpecialTimeEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1261942915635388376L;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
	
	/**
	 * 用户名
	 */
	@TableField("USER_NAME")
	@QueryField(queryColumn="USER_NAME", condition=QueryCondition.eq)
	private String userName;
	
	/**
	 * 用户代码
	 */
	@TableField("USER_CODE")
	private String userCode;
	
	/**
	 * 部门id
	 */
	@TableField("DEPART_ID")
	@QueryField(queryColumn="DEPART_ID", condition=QueryCondition.eq)
	private String departId;
	
	/**
	 * 部门名称
	 */
	@TableField("DEPART_NAME")
	private String departName;
	
	/**
	 * 部门代码
	 */
	@TableField("DEPART_CODE")
	private String departCode;
	
	/**
	 * 类型
	 */
	@TableField("TYPE")
	private String type;
	
	/**
	 * 开始时间
	 */
	@TableField("START_TIME")
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	@TableField("END_TIME")
	private Date endTime;
	
	/**
	 * 合计多少小时
	 */
	@TableField("HOURS")
	private Double hours;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
	/** 
	 * 维护加班的ID
	 */
	@TableField("OVER_TIME_ID")
	private String overTimeId;
	
	/** 
	 * 维护年假的ID
	 */
	@TableField("YEAR_HOLIDAY_ID")
	private String yearHolidayId;
	
}

