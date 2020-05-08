package com.active4j.hr.hr.domain;

import java.util.List;

import com.active4j.hr.hr.entity.OaHrUserStudyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaUserStudyModel.java
 * @description 
		人事信息-教育经历list
 * @time  2020年4月14日 上午10:21:44
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
public class OaUserStudyModel {

	private List<OaHrUserStudyEntity> studys;
	
}
