package com.active4j.hr.system.model;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class ActiveUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6270912302455345492L;

	private String userName;
	
	private String id;
	
	private String realName;
	
	private String headImgUrl;
	
	private String deptName;
	
	private boolean admin;
	
	private String menus;
	
	private Set<String> roles;
	
	private Set<String> permissions;

}
