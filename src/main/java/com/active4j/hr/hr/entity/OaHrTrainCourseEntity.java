package com.active4j.hr.hr.entity;

import java.math.BigDecimal;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrTrainCourseEntity.java
 * @description 
		课程实体
 * @time  2020年4月23日 上午9:31:17
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_train_course")
@Getter
@Setter
public class OaHrTrainCourseEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8933492494450439963L;
	
	/**
	 * 课程名称
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
	/** 
	 * 课时
	 */
	@TableField("COURSE_HOUR")
	private BigDecimal courseHour;
	
	/** 
	 * 授课方式（数据字典）
	 */
	@TableField("COURSE_TYPE")
	@QueryField(queryColumn="COURSE_TYPE", condition=QueryCondition.eq)
	private String courseType;
	
	/** 
	 * 费用
	 */
	@TableField("NEED_MONEY")
	private BigDecimal needMoney;
	
	/** 
	 * 目标
	 */
	@TableField("TARGET")
	private String target;
	
	/** 
	 * 说明
	 */
	@TableField("MEMO")
	private String memo;
	
	/** 
	 * 资料附件
	 */
	@TableField("ATTACHMENT")
	private String attachment;
	
	/** 
	 * 类别id
	 */
	@TableField("CATE_ID")
	@QueryField(queryColumn="CATE_ID", condition=QueryCondition.eq)
	private String cateId;
	
}

