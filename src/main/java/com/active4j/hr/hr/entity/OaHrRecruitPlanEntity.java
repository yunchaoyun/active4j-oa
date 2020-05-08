package com.active4j.hr.hr.entity;

import java.math.BigDecimal;
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
 * @title OaHrRecruitPlanEntity.java
 * @description 
		招聘计划实体
 * @time  2020年4月21日 下午4:45:45
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_recruit_plan")
@Getter
@Setter
public class OaHrRecruitPlanEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4307425412404390282L;

	/**
	 * 计划名称
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
	/**
	 * 计划渠道(数据字典)
	 */
	@TableField("CANAL_TYPE")
	@QueryField(queryColumn="CANAL_TYPE", condition=QueryCondition.eq)
	private String canalType;
	
	/**
	 * 计划状态
	 */
	@TableField("STATUS")
	private String status;
	
	/**
	 * 计划人数
	 */
	@TableField("PLAN_NUM")
	private Integer planNum;
	
	/**
	 * 开始时间
	 */
	@TableField("START_DATE")
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	@TableField("END_TIME")
	private Date endDate;
	
	/**
	 * 预算费用
	 */
	@TableField("MONEY")
	private BigDecimal money;
	
	/**
	 * 附件
	 */
	@TableField("ATTACHMENT")
	private String attachment;
	
	/**
	 * 说明
	 */
	@TableField("DISCRIPTION")
	private String discription;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
}

