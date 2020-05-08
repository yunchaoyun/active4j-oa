package com.active4j.hr.activiti.biz.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title FlowSignetApprovalEntity.java
 * @description 
		行政 用章审批
 * @time  2020年4月28日 下午2:18:38
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
@TableName("flow_signet_approval")
public class FlowSignetApprovalEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3196298665491421486L;

	/**
	 * 开始日期
	 */
	@TableField("START_DAY")
	private Date startDay;
	
	/**
	 * 结束日期
	 */
	@TableField("END_DAY")
	private Date endDay;
	
	/**
	 * 理由
	 */
	@TableField("REASON")
	private String reason;
	
}
