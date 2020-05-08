package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrUserPayEntity.java
 * @description 
		人力资源-人员成本信息实体
 * @time  2020年4月13日 上午10:00:35
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user_pay")
@Getter
@Setter
public class OaHrUserPayEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8481520092943310004L;
	
	/**
	 * 成本编号
	 */
	@TableField("PAY_NO")
	private String payNo;

	/**
	 * 成本类型
	 */
	@TableField("PAY_TYPE")
	private String payType;
	
	/**
	 * 基本工资
	 */
	@TableField("BASE_MONEY")
	private Double baseMoney;
	
	/**
	 * 绩效工资
	 */
	@TableField("PERFORM_MONEY")
	private Double performMoney;
	
	/**
	 * 公积金号
	 */
	@TableField("FUND_NO")
	private String fundNo;
	
	/**
	 * 公司承担养老保险
	 */
	@TableField("COMPANY_AGED")
	private Double companyAged;
	
	/**
	 * 个人承担养老保险
	 */
	@TableField("PERSONAL_AGED")
	private Double personalAged;
	
	/**
	 * 公司承担公积金
	 */
	@TableField("COMPANY_FUND")
	private Double companyFund;
	
	/**
	 * 个人承担公积金
	 */
	@TableField("PERSONAL_FUND")
	private Double personalFund;
	
	/**
	 * 公司承担失业金
	 */
	@TableField("COMPANY_UN_EMPLOY")
	private Double companyUnEmploy;
	
	/**
	 * 个人承担失业金
	 */
	@TableField("PERSONAL_UN_EMPLOY")
	private Double personalUnemploy;
	
	/**
	 * 公司承担医疗保险
	 */
	@TableField("COMPANY_MEDICAL")
	private Double companyMedical;
	
	/**
	 * 个人承担医疗保险
	 */
	@TableField("PERSONAL_MEDICAL")
	private Double personalMedical;
	
	/**
	 * 生育保险
	 */
	@TableField("FERTILITY")
	private Double fertility;
	
	/**
	 * 工伤保险
	 */
	@TableField("OCCU_INJURY")
	private Double occuInjury;
	
	/**
	 * 工资开户行
	 */
	@TableField("PAY_BANK")
	private String payBank;
	
	/**
	 * 工资账号
	 */
	@TableField("WAGE_NO")
	private String wageNo;
	
	/**
	 * 社保缴纳地
	 */
	@TableField("SOCIAL_AREA")
	private String socialArea;
	
	/**
	 * 社保号码
	 */
	@TableField("SOCIAL_NO")
	private String socialNo;
	
	/**
	 * 人事用户id
	 */
	@TableField("USER_ID")
	private String userId;
}

