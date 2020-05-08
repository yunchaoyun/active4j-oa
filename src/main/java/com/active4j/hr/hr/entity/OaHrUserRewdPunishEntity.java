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
 * @title OaHrUserRewdPunishEntity.java
 * @description 
		人力资源-奖惩记录
 * @time  2020年4月16日 下午4:11:50
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user_rewd_punish")
@Getter
@Setter
public class OaHrUserRewdPunishEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2264069784961349515L;

	/**
	 * 姓名
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
	/**
	 * 奖惩类型
	 */
	@TableField("RP_TYPE")
	@QueryField(queryColumn="RP_TYPE", condition=QueryCondition.eq)
	private String rpType;
	
	/**
	 * 奖惩明目
	 */
	@TableField("ITEMS")
	private String items;
	
	/**
	 * 奖惩日期
	 */
	@TableField("RP_DATE")
	private Date rpDate;
	
	/**
	 * 奖惩金额
	 */
	@TableField("RP_MONEY")
	private Double rpMoney;
	
	/**
	 * 奖惩原因
	 */
	@TableField("RP_DEMO")
	private String rpDemo;
	
	/**
	 * 附件
	 */
	@TableField("ATTACHMENT")
	private String attachment;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
	
}

