package com.active4j.hr.system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title SysUserModel.java
 * @description 
		用户个人资料
 * @time  2020年2月8日 下午12:25:03
 * @author guyp
 * @version 1.0
 */
@Getter
@Setter
public class SysUserModel{
	
	/**
	 * 用户id
	 */
	private String id;

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 头像
	 */
	private String headImgUrl;
	
	/**
	 * 电子邮箱
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String telNo;
	
	/**
	 * 所属部门
	 */
	private String deptName;
	
	/**
	 * 角色
	 */
	private String roleName;
	
	
}
