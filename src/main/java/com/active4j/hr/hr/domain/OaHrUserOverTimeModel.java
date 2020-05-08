package com.active4j.hr.hr.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrUserOverTimeModel.java
 * @description 
		人事加班model
 * @time  2020年4月24日 上午10:05:25
 * @author guyp
 * @version 1.0
 */
@Setter
@Getter
public class OaHrUserOverTimeModel {
	
	private String id;
	
	private String realname;
	
	private String departname;
	
	private String total;
	
	private String workday;
	
	private String weekend;
	
	private String holiday;
	
	private String used;
	
	private String sheng;
	
}
