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
 * @title OaHrUserPactEntity.java
 * @description 
		人力资源-人事合同
 * @time  2020年4月15日 上午10:58:49
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user_pact")
@Getter
@Setter
public class OaHrUserPactEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8011752846825579836L;

	/**
	 * 工号
	 */
	@TableField("USER_NO")
	@QueryField(queryColumn="USER_NO", condition=QueryCondition.eq)
	private String userNo;

	/**
	 * 姓名
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
	/**
	 * 合同编号
	 */
	@TableField("PACT_NO")
	private String pactNo;
	
	/**
	 * 合同类型
	 */
	@TableField("PACT_TYPE")
	private String pactType;
	
	/**
	 * 是否签订培训协议
	 */
	@TableField("CAN_TRAIN_PACT")
	private String canTrainPact;
	
	/**
	 * 有无固定期限合同
	 */
	@TableField("CAN_GU_DING")
	private String canGuDing;
	
	/**
	 * 生效日期
	 */
	@TableField("EFFECT_DATE")
	private Date effectDate;
	
	/**
	 * 失效日期
	 */
	@TableField("NO_EFFECT_DATE")
	private Date noEffectDate;
	
	/**
	 * 附件
	 */
	@TableField("ATTACHMENT")
	private String attachment;
	
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

