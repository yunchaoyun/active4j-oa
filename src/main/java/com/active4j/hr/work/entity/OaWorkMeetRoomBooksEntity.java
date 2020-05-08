package com.active4j.hr.work.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkMeetRoomBooksEntity.java
 * @description 会议预定管理
 * @time 2020年4月6日 上午10:04:56
 * @author 麻木神
 * @version 1.0
 */
@TableName("OA_WORK_MEET_ROOM_BOOKS")
@Getter
@Setter
public class OaWorkMeetRoomBooksEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1567309320677404774L;

	@TableField("USER_NAME")
	private String userName;

	@TableField("USER_ID")
	private String userId;

	@TableField("BOOK_DATE")
	@QueryField(condition=QueryCondition.eq, queryColumn="BOOK_DATE")
	private Date bookDate;

	@TableField("STR_BOOK_DATE")
	private String strBookDate;

	@TableField("START_DATE")
	private Date startDate;

	@TableField("END_DATE")
	private Date endDate;

	@TableField("MEMO")
	private String memo;
	
	@TableField("MEET_ROOM_ID")
	@QueryField(condition=QueryCondition.eq, queryColumn="MEET_ROOM_ID")
	private String meetRoomId;

}
