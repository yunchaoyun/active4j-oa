package com.active4j.hr.hr.domain;

import com.active4j.hr.hr.entity.OaHrRecruitOfferEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrRecruitOfferModel.java
 * @description 
		offer记录model
 * @time  2020年4月22日 下午4:26:31
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
public class OaHrRecruitOfferModel extends OaHrRecruitOfferEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7888798026175637023L;

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
