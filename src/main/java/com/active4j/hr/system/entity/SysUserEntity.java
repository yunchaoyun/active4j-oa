package com.active4j.hr.system.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title SysUserEntity.java
 * @description 
		  系统管理 用户实体
 * @time  2020年1月15日 上午10:33:20
 * @author 麻木神
 * @version 1.0
*/
@TableName("sys_user")
@Getter
@Setter
public class SysUserEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8089937508571356155L;

	/**
	 * 用户名
	 */
	@TableField("USER_NAME")
	@QueryField(queryColumn="USER_NAME", condition=QueryCondition.eq)
	private String userName;
	
	/**
	 * 真实姓名
	 */
	@TableField("REAL_NAME")
	@QueryField(queryColumn="REAL_NAME", condition=QueryCondition.like)
	private String realName;
	
	/**
	 * 是否管理员   true:是     false：否
	 */
	@TableField("ADMIN")
	@QueryField(queryColumn="ADMIN", condition=QueryCondition.eq)
	private String admin;
	
	/**
	 *  密码
	 */
	@TableField("PASSWORD")
	private String password;
	
	/**
	 * 盐
	 */
	@TableField("SALT")
	private String salt;
	
	/**
	 * 用户状态
	 */
	@TableField("STATUS")
	@QueryField(queryColumn="STATUS", condition=QueryCondition.eq)
	private String status;
	
	/**
	 * 头像
	 */
	@TableField("HEAD_IMG_URL")
	private String headImgUrl;
	
	/**
	 * 电子邮箱
	 */
	@TableField("EMAIL")
	private String email;
	
	/**
	 * 手机号
	 */
	@TableField("TEL_NO")
	private String telNo;
	
	/**
	 * 所属部门
	 */
	@TableField("DEPT_ID")
	@QueryField(queryColumn="DEPT_ID", condition=QueryCondition.eq)
	private String deptId;
	
	/**
	 * 备注
	 */
	@TableField("MEMO")
	private String memo;
	
	
}
