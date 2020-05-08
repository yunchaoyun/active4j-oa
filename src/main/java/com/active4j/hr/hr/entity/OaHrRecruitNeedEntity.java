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
 * @title OaHrRecruitNeedEntity.java
 * @description 
		招聘需求实体
 * @time  2020年4月21日 下午3:02:06
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_recruit_need")
@Getter
@Setter
public class OaHrRecruitNeedEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7475201786032104325L;

	/**
	 * 部门ID
	 */
	@TableField("DEPART_ID")
	private String departId;
	
	/**
	 * 部门名称
	 */
	@TableField("DEPART_NAME")
	private String departName;
	
	/**
	 * 部门编号
	 */
	@TableField("DEPART_CODE")
	private String departCode;

	/**
	 * 需求职位
	 */
	@TableField("NEED_JOB")
	@QueryField(queryColumn="NEED_JOB", condition=QueryCondition.eq)
	private String needJob;
	
	/**
	 * 需求人数
	 */
	@TableField("NEED_NUM")
	private Integer needNum;
	
	/**
	 * 预计用工时间
	 */
	@TableField("PRE_DATE")
	private Date preDate;
	
	/**
	 * 提出时间
	 */
	@TableField("PUT_DATE")
	private Date putDate;
}

