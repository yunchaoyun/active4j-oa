package com.active4j.hr.hr.domain;

import com.active4j.hr.hr.entity.OaHrRecruitViewEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrRecruitViewModel.java
 * @description 
		面试记录model
 * @time  2020年4月22日 下午2:49:56
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
public class OaHrRecruitViewModel extends OaHrRecruitViewEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5582121245971552500L;
	
	/**
	 * 姓名
	 */
	private String cvName;
	
	/**
	 * 性别
	 */
	private String cvSex;
	
	/**
	 * 职位
	 */
	private String cvJob;
	
}
