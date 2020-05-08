package com.active4j.hr.work.domain;

public class OaWorkTaskStatusDomain {

	private String name;
	
	private String value;
	
	private String num;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof OaWorkTaskStatusDomain)) {
			return false;
		}
		
		OaWorkTaskStatusDomain tmp = (OaWorkTaskStatusDomain)obj;
		
		return this.value.equals(tmp.getValue());
	}
	
	
}
