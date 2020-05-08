package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrTrainPlanCourseEntity.java
 * @description 
		课程计划-课程 多对多表
 * @time  2020年4月23日 上午10:04:42
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_train_plan_course")
@Getter
@Setter
public class OaHrTrainPlanCourseEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889257532324085629L;

	/**
	 * 关联计划表 ID
	 */
	@TableField("PLAN_ID")
	private String planId;
	
	/**
	 * 关联课程表 ID
	 */
	@TableField("COURSE_ID")
	private String courseId;
}
