package com.active4j.hr.work.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkMeetSummaryEntity.java
 * @description 会议纪要实体
 * @time 2020年4月7日 上午9:26:56
 * @author 麻木神
 * @version 1.0
 */
@TableName("OA_WORK_MEET_SUMMARY")
@Getter
@Setter
public class OaWorkMeetSummaryEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4655588567333393873L;

	@TableField("NAME")
	private String name;

	@TableField("START_TIME")
	private Date startTime;

	@TableField("END_TIME")
	private Date endTime;

	// 参与人
	@TableField("PARTICIPANTIDS")
	private String participantIds;

	@TableField("PARTICIPANTS")
	private String participants;

	// 缺席人
	@TableField("NO_PARTICIPANTIDS")
	private String noParticipantIds;

	@TableField("NO_PARTICIPANTS")
	private String noParticipants;

	// 会议地点
	@TableField("MEET_ROOM_ID")
	private String meetRoomId;

	// 会议记要内容
	@TableField("CONTENT")
	private String content;

	// 备注
	@TableField("MEMO")
	private String memo;
	
	@TableField("OA_MEET_ID")
	private String oaMeetId;
}
