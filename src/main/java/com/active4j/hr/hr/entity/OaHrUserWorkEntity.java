package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrUserWorkEntity.java
 * @description 
		人力资源-人员工作经历实体
 * @time  2020年4月13日 上午10:55:29
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user_work")
@Getter
@Setter
public class OaHrUserWorkEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3527490428205223930L;

	/**
	 * 公司名称
	 */
	@TableField("COMPANY_NAME")
	private String companyName;
	
	/**
	 * 职位
	 */
	@TableField("JOB")
	private String job;
	
	/**
	 * 工作描述
	 */
	@TableField("WORK_INFO")
	private String workInfo;

	/**
	 * 开始时间
	 */
	@TableField("BEFORE_WORK_DATE")
	private String beforeWorkDate;
	
	/**
	 * 结束时间
	 */
	@TableField("END_WORK_DATE")
	private String endWorkDate;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
}

