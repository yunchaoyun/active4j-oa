package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrUserStudyEntity.java
 * @description 
		人力资源-人员教育经历实体
 * @time  2020年4月13日 上午10:01:03
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user_study")
@Getter
@Setter
public class OaHrUserStudyEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8988469993192288450L;

	/**
	 * 学校名称
	 */
	@TableField("SCHOOL_NAME")
	private String schoolName;

	/**
	 * 开始时间
	 */
	@TableField("BEFORE_DATE")
	private String beforeDate;
	
	/**
	 * 结束时间
	 */
	@TableField("END_DATE")
	private String endDate;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
}

