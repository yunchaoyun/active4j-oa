package com.active4j.hr.hr.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrRecruitViewEntity.java
 * @description 
		面试记录
 * @time  2020年4月22日 下午2:31:33
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_recruit_view")
@Getter
@Setter
public class OaHrRecruitViewEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6646502183374614958L;

	/**
	 * 简历id
	 */
	@TableField("CV_ID")
	private String cvId;
	
	/** 
	 * 面试时间
	 */
	@TableField("VIEW_DATE")
	private Date viewDate;
	
	/** 
	 * 面试方式
	 */
	@TableField("VIEW_TYPE")
	private String viewType;
	
	/** 
	 * 面试内容
	 */
	@TableField("CONTENT")
	private String content;
	
	/** 
	 * 面试意见
	 */
	@TableField("SUGGESTION")
	private String suggestion;
	
	/** 
	 * 面试人员
	 */
	@TableField("STAFF")
	private String staff;
	
}

