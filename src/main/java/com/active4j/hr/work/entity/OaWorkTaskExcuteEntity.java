package com.active4j.hr.work.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkTaskExcuteEntity.java
 * @description 
		  工作任务执行记录
 * @time  2020年4月7日 下午3:17:32
 * @author 麻木神
 * @version 1.0
*/
@TableName("OA_WORK_TASK_EXCUTE")
@Getter
@Setter
public class OaWorkTaskExcuteEntity extends BaseEntity implements Comparable<OaWorkTaskExcuteEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 739207634100031630L;

	@TableField("USER_ID")
	private String userId;
	
	@TableField("USER_NAME")
	private String userName;
	
	@TableField("USER_HEAD_IMG")
	private String userHeadImg;
	
	@TableField("START_TIME")
	private Date startTime;
	
	@TableField("END_TIME")
	private Date endTime;
	
	//进度
	@TableField("PROGRESS")
	private int progress;
	
	//说明
	@TableField("DESCRIPTION")
	private String description;
	
	@TableField("OA_WORK_TASK_ID")
	private String OaWorkTaskId;
	
	@Override
	public int compareTo(OaWorkTaskExcuteEntity o) {
		return o.getCreateDate().compareTo(this.getCreateDate());
	}
}
