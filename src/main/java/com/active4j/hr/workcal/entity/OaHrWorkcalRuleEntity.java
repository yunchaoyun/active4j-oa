package com.active4j.hr.workcal.entity;

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
 * @title OaHrWorkcalRuleEntity.java
 * @description 
		考勤管理-工作日历规则设置
 * @time  2020年4月20日 上午10:02:00
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_workcal_rule")
@Getter
@Setter
public class OaHrWorkcalRuleEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -232388749966622558L;

	/**
	 * 年
	 */
	@TableField("YEAR")
	@QueryField(queryColumn="YEAR", condition=QueryCondition.eq)
	private String year;
	
	/**
	 * 日期字符串
	 */
	@TableField("CURRENT_STR_DATE")
	private String currentStrDate;
	
	/**
	 * 日期
	 */
	@TableField("CURRENT_DATE_TIME")
	@QueryField(queryColumn="CURRENT_DATE_TIME", condition=QueryCondition.range)
	private Date currentDateTime;
	
	/**
	 * 日期的类型，0：工作日    1：法定假日  2：公司假期  3：双休日
	 */
	@TableField("TYPE")
	@QueryField(queryColumn="TYPE", condition=QueryCondition.eq)
	private String type;
	
	/**
	 * 是否需要签到
	 */
	@TableField("NEED_SIGN")
	private Boolean needSign;
	
	/**
	 * 星期
	 */
	@TableField("WEEK_STR")
	private String weekStr;
	
	/**
	 * 上班时间
	 */
	@TableField("START_TIME")
	private String startTime;
	
	/**
	 * 下班时间
	 */
	@TableField("END_TIME")
	private String endTime;
	
	/**
	 * 
	 */
	@TableField("STR_YEAR_MONTH")
	private String strYearMonth;;
	
}

