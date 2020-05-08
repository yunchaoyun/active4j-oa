package com.active4j.hr.hr.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrRecruitOfferEntity.java
 * @description 
		offer记录
 * @time  2020年4月22日 下午4:20:03
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_recruit_offer")
@Getter
@Setter
public class OaHrRecruitOfferEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 450694371500406549L;

	/**
	 * 简历id
	 */
	@TableField("CV_ID")
	private String cvId;
	
	/** 
	 * 要求入职时间
	 */
	@TableField("REPORT_DATE")
	private Date reportDate;
	
	/** 
	 * 入职部门
	 */
	@TableField("JOB_DEPART")
	private String jobDepart;
	
	/** 
	 * 职位
	 */
	@TableField("JOB")
	private String job;
	
	/** 
	 * 试用期薪资
	 */
	@TableField("TEST_WAGE")
	private BigDecimal testWage;
	
	/** 
	 * 转正后薪资
	 */
	@TableField("FORMAL_WAGE")
	private BigDecimal formalWage;
	
	/**
	 * 状态
	 */
	@TableField("STATUS")
	private String status;
	
	/** 
	 * 其他事宜
	 */
	@TableField("MEMO")
	private String memo;
	
}

