package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title SysOperationEntity.java
 * @description 
		  系统管理  菜单配置下 按钮
 * @time  2020年1月15日 上午11:14:11
 * @author 麻木神
 * @version 1.0
*/
@TableName("sys_operation")
@Getter
@Setter
public class SysOperationEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2918581471233044293L;

	/**
	 * 操作名称
	 */
	@TableField("NAME")
	private String name;
	
	/**
	 * 操作代码
	 */
	@TableField("CODE")
	private String code;
	
	/**
	 * 关联菜单
	 */
	@TableField("FUNCTION_ID")
	@QueryField(queryColumn="FUNCTION_ID", condition=QueryCondition.eq)
	private String functionId;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
	
}
