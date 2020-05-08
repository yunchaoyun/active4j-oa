package com.active4j.hr.work.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class OaWorkBookRoomDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2800166008926473765L;

	private String id;
	
	private String title;
	
	private String start;
	
	private String end;

	
	
}
