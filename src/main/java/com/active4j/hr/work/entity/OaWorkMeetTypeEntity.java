package com.active4j.hr.work.entity;

import javax.validation.constraints.NotEmpty;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkMeetTypeEntity.java
 * @description 
		  工作中心 会议类型
 * @time  2020年4月6日 上午9:18:56
 * @author 麻木神
 * @version 1.0
*/
@TableName("OA_WORK_MEET_TYPE")
@Getter
@Setter
public class OaWorkMeetTypeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5177409132013481599L;

	@TableField("NAME")
	@NotEmpty(message = "类型名称不能为空")
	private String name;
	
	@TableField("MEMO")
	private String memo;
	
}
