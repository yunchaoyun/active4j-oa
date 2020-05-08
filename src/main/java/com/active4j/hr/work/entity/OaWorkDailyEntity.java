package com.active4j.hr.work.entity;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkDailyEntity.java
 * @description 工作中心 日报
 * @time 2020年4月3日 下午1:55:31
 * @author 麻木神
 * @version 1.0
 */
@TableName("oa_work_daily")
@Getter
@Setter
public class OaWorkDailyEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3066688406364129545L;

	// 日报标题
	@TableField("TITLE")
	@NotEmpty(message = "请填写日报标题")
	@QueryField(queryColumn="TITLE", condition=QueryCondition.eq)
	private String title;

	// 日报内容
	@TableField("CONTENT")
	@NotEmpty(message = "请填写日报内容")
	private String content;

	// 日报状态 （0:草稿； 1：已提交）
	@TableField("STATUS")
	@QueryField(queryColumn="STATUS", condition=QueryCondition.eq)
	private String status;

	// 提交日期
	@TableField("SUBMIT_DATE")
	private Date submitDate;

	// String提交日期
	@TableField("STR_SUBMIT_DATE")
	private String strSubmitDate;

	// userId
	@TableField("USER_ID")
	private String userId;

}
