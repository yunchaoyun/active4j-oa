package com.active4j.hr.hr.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;


@TableName("oa_hr_job")
@Getter
@Setter
public class OaHrJobEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4841843156447677596L;

	/**
	 * 岗位编号
	 */
	@TableField("JOB_NO")
	private String jobNo;
	
	/**
	 * 岗位名称  全称
	 */
	@TableField("JOB_NAME")
	private String jobName;
	
	/**
	 * 上级岗位ID
	 */
	@TableField("PARENT_ID")
	private String parentId;
	
	/**
	 * 岗位排序
	 */
	@TableField("LEVEL")
	private int level;
	
	/**
	 * 岗位描述
	 */
	@TableField("DESCRIPTION")
	private String description;
	
	/**
	 * 入职要求
	 */
	@TableField("REQUIRE_STR")
	private String requireStr;
}

