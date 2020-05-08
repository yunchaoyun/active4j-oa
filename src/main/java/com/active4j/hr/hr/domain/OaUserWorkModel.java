package com.active4j.hr.hr.domain;

import java.util.List;

import com.active4j.hr.hr.entity.OaHrUserWorkEntity;

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
public class OaUserWorkModel {

	private List<OaHrUserWorkEntity> works;
	
}
