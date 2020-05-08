package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrRecruitPlanNeedEntity.java
 * @description 
		招聘计划-招聘需求 多对多表
 * @time  2020年4月21日 下午5:39:11
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_recruit_plan_need")
@Getter
@Setter
public class OaHrRecruitPlanNeedEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4436599695234623812L;

	/**
	 * 关联计划表 ID
	 */
	@TableField("PLAN_ID")
	private String planId;
	
	/**
	 * 关联需求表 ID
	 */
	@TableField("NEED_ID")
	private String needId;
}
