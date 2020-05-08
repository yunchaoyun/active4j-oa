package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title FlowContractApprovalEntity.java
 * @description 
		行政 合同审批
 * @time  2020年4月28日 下午5:47:41
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
@TableName("flow_contract_approval")
public class FlowContractApprovalEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4803959880129070637L;

	/**
	 * 合同名称
	 */
	@TableField("CONTRACT_NAME")
	private String contractName;
	
	/**
	 * 金钱
	 */
	@TableField("MONEY")
	private Double money ;
	
}
