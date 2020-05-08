package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrTrainCourseCateEntity.java
 * @description 
		课程类别实体
 * @time  2020年4月23日 上午9:20:18
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_train_course_cate")
@Getter
@Setter
public class OaHrTrainCourseCateEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2381815122212541069L;

	/**
	 * 类别名称
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
}

