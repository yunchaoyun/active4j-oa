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
 * @title OaHrOverTimeEntity.java
 * @description 
		加班维护实体
 * @time  2020年4月24日 上午9:33:48
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_over_time")
@Getter
@Setter
public class OaHrOverTimeEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1261942915635388376L;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
	
	/**
	 * 用户名
	 */
	@TableField("USER_NAME")
	@QueryField(queryColumn="USER_NAME", condition=QueryCondition.eq)
	private String userName;
	
	/**
	 * 用户代码
	 */
	@TableField("USER_CODE")
	private String userCode;
	
	/**
	 * 部门id
	 */
	@TableField("DEPART_ID")
	@QueryField(queryColumn="DEPART_ID", condition=QueryCondition.eq)
	private String departId;
	
	/**
	 * 部门名称
	 */
	@TableField("DEPART_NAME")
	private String departName;
	
	/**
	 * 部门代码
	 */
	@TableField("DEPART_CODE")
	private String departCode;
	
	/**
	 * 类型
	 */
	@TableField("TYPE")
	private String type;
	
	/**
	 * 操作类型   增加还是减少
	 */
	@TableField("OPT_TYPE")
	private String optType;
	
	/**
	 * 开始时间
	 */
	@TableField("START_TIME")
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	@TableField("END_TIME")
	private Date endTime;
	
	/**
	 * 合计多少小时
	 */
	@TableField("HOURS")
	private Double hours;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
}

