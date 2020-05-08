package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title FlowCostApprovalEntity.java
 * @description 
		  费用审批
 * @time  2020年4月22日 下午3:44:49
 * @author 麻木神
 * @version 1.0
*/
@TableName("flow_cost_approval")
@Getter
@Setter
public class FlowCostApprovalEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8292009108338340458L;
	
	@TableField("AMOUNT")
	private double amount;
	
	@TableField("DEPT_ID")
	private String deptId;
	
	@TableField("REASON")
	private String reason;
	
	@TableField("MONTH")
	private String month;
	

}
