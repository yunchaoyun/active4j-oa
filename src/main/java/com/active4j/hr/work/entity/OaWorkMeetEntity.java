package com.active4j.hr.work.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkMeetEntity.java
 * @description 会议管理实体
 * @time 2020年4月7日 上午9:13:34
 * @author 麻木神
 * @version 1.0
 */
@TableName("OA_WORK_MEET")
@Getter
@Setter
public class OaWorkMeetEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6657162259532040734L;

	// 会议类型
	@TableField("TYPE")
	private String type;

	// 会议名称
	@TableField("NAME")
	private String name;

	// 召集人
	@TableField("CALL_USER_NAME")
	private String callUserName;

	@TableField("CALL_USER_ID")
	private String callUserId;

	// 参与人
	@TableField("PARTICIPANTIDS")
	private String participantIds;

	@TableField("PARTICIPANTS")
	private String participants;

	// 会议地点
	@TableField("MEET_ROOM_ID")
	private String meetRoomId;

	// 会议内容
	@TableField("CONTENT")
	private String content;

	// 会议时间
	@TableField("MEETING_TIME")
	private Date meetingTime;

	@TableField("TO_TIME")
	private Date toTime;

	// 备注
	@TableField("MEMO")
	private String memo;

	// 是否启用会议提醒
	@TableField("TIP")
	private boolean tip;

	// 提醒方式
	@TableField("TIP_TYPE")
	private int tipType;

	// 提醒时间
	@TableField("TIP_TIME")
	private Date tipTime;
}
