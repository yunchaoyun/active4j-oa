package com.active4j.hr.activiti.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title WorkflowCategoryEntity.java
 * @description 工作流 类别
 * @time 2020年4月16日 下午2:38:27
 * @author 麻木神
 * @version 1.0
 */
@TableName("wf_category")
@Getter
@Setter
public class WorkflowCategoryEntity extends BaseEntity implements Comparable<WorkflowCategoryEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4558321048020058311L;

	// 类别名称
	@TableField("NAME")
	@QueryField(condition=QueryCondition.eq, queryColumn="NAME")
	private String name;

	// 排序
	@TableField("SORT")
	private Integer sort;

	// 备注
	@TableField("MEMO")
	private String memo;
	
	@Override
	public int compareTo(WorkflowCategoryEntity o) {
		return this.getSort() - o.getSort();
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof WorkflowCategoryEntity)) {
			return false;
		}
		
		WorkflowCategoryEntity w = (WorkflowCategoryEntity)obj;
		
		
		return this.getId().equals(w.getId());
	}
}
