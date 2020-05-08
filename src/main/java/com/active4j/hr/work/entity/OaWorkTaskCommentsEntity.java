package com.active4j.hr.work.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkTaskComments.java
 * @description 
		  任务评价
 * @time  2020年4月7日 下午3:16:41
 * @author 麻木神
 * @version 1.0
*/
@TableName("OA_WORK_TASK_COMMENTS")
@Getter
@Setter
public class OaWorkTaskCommentsEntity extends BaseEntity implements Comparable<OaWorkTaskCommentsEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7587848140789271257L;

	@TableField("USER_ID")
	private String userId;
	
	@TableField("USER_NAME")
	private String userName;
	
	@TableField("USER_HEAD_IMG")
	private String userHeadImg;
	
	@TableField("CONTENT")
	private String content;
	
	@TableField("REPLY_DATE")
	private Date replyDate;
	
	@TableField("OA_WORK_TASK_ID")
	private String OaWorkTaskId;
	
	@Override
	public int compareTo(OaWorkTaskCommentsEntity o) {
		return o.getReplyDate().compareTo(this.getReplyDate());
	}
}
