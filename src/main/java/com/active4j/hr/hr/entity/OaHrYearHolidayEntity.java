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
 * @title OaHrYearHolidayEntity.java
 * @description 
		年假维护实体
 * @time  2020年4月24日 上午9:36:00
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_year_holiday")
@Getter
@Setter
public class OaHrYearHolidayEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8223547825142391005L;

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
	 * 哪一年
	 */
	@TableField("YEAR")
	private String year;
	
	/**
	 * 操作类型   增加还是减少
	 */
	@TableField("OPT_TYPE")
	private String optType;
	
	/**
	 * 合计多少天
	 */
	@TableField("DAYS")
	private Double days;
		
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
}

