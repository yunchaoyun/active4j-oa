package com.active4j.hr.work.entity;

import javax.validation.constraints.NotEmpty;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkMeetRoomEntity.java
 * @description 
		  会议室管理
 * @time  2020年4月6日 上午10:00:33
 * @author 麻木神
 * @version 1.0
*/
@TableName("OA_WORK_MEET_ROOM")
@Getter
@Setter
public class OaWorkMeetRoomEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628143367454955204L;

	@TableField("NAME")
	@NotEmpty(message = "会议室名称不能为空")
	private String name;
	
	@TableField("STATUS")
	@NotEmpty(message = "状态不能为空")
	private String status;
	
	@TableField("PERSONS")
	private int persons;
	
	@TableField("MEMO")
	private String memo;
	
}
