package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrUserBankInfoEntity.java
 * @description 
		薪资管理-员工银行卡信息实体
 * @time  2020年4月20日 下午4:48:10
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user_bank_info")
@Getter
@Setter
public class OaHrUserBankInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5359713135836800874L;

	/**
	 * 银行卡号
	 */
	@TableField("CARD_NO")
	private String cardNo;
	
	/**
	 * 开户行
	 */
	@TableField("BANK")
	private String bank;
	
	/**
	 * 银行卡的图片
	 */
	@TableField("CARD_IMG")
	private String cardImg;

	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
}

