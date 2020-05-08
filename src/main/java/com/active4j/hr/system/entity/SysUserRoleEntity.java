package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户跟角色  多对多表
 * @author teli_
 *
 */
@TableName("sys_user_role")
@Getter
@Setter
public class SysUserRoleEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2437600856369796846L;

	/**
	 * 关联用户表 ID
	 */
	@TableField("USER_ID")
	private String userId;
	
	/**
	 * 关联角色表 ID
	 */
	@TableField("ROLE_ID")
	private String roleId;
}
