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
 * @title OaHrTrainPlanEntity.java
 * @description 
		培训计划
 * @time  2020年4月23日 上午9:37:20
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_train_plan")
@Getter
@Setter
public class OaHrTrainPlanEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2783395248055765439L;

	/**
	 * 计划名称
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
	/** 
	 * 计划周期
	 */
	@TableField("CYCLE")
	@QueryField(queryColumn="CYCLE", condition=QueryCondition.eq)
	private String cycle;
	
	/** 
	 * 开始日期
	 */
	@TableField("START_DATE")
	private Date startDate;
	
	/** 
	 * 结束日期
	 */
	@TableField("END_DATE")
	private Date endDate;
	
	/** 
	 * 培训目标
	 */
	@TableField("TARGET")
	private String target;
	
	/** 
	 * 培训说明
	 */
	@TableField("MEMO")
	private String memo;
	
}

