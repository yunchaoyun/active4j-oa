package com.active4j.hr.hr.domain;

import com.active4j.hr.hr.entity.OaHrUserBankInfoEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaUserWorkModel.java
 * @description 
		人事信息-工作经历list
 * @time  2020年4月14日 上午10:48:56
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
public class OaUserBankInfoModel extends OaHrUserBankInfoEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7736117561798916332L;

	/**
	 * 员工姓名
	 */
	private String oaUserName;
	
	/**
	 * 部门名称
	 */
	private String oaUserDepartName;
}
