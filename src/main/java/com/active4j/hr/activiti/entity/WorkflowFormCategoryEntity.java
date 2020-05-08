package com.active4j.hr.activiti.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title WorkflowFormCategory.java
 * @description 表单类别
 * @time 2020年4月18日 下午3:44:27
 * @author 麻木神
 * @version 1.0
 */
@TableName("wf_form_category")
@Getter
@Setter
public class WorkflowFormCategoryEntity extends BaseEntity implements Comparable<WorkflowFormCategoryEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5987634688959321835L;

	// 类别名称
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.like)
	private String name;

	// 排序
	@TableField("SORT")
	private Integer sort;

	// 备注
	@TableField("MEMO")
	private String memo;

	@Override
	public int compareTo(WorkflowFormCategoryEntity o) {

		return this.getSort() - o.getSort();
	}

}
