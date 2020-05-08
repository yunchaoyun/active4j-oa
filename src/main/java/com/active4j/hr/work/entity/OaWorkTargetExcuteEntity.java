package com.active4j.hr.work.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title OaWorkTargetExcuteEntity.java
 * @description 目标执行
 * @time 2020年4月8日 下午2:56:08
 * @author 麻木神
 * @version 1.0
 */
@TableName("OA_WORK_TARGET_EXCUTE")
@Getter
@Setter
public class OaWorkTargetExcuteEntity extends BaseEntity implements Comparable<OaWorkTargetExcuteEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8737544739151848696L;

	@TableField("USER_ID")
	private String userId;

	@TableField("USER_NAME")
	private String userName;

	@TableField("USER_HEAD_IMG")
	private String userHeadImg;

	@TableField("CONTENT")
	private String content;

	// 已完成数据
	@TableField("FINISH_RECORD")
	private int finishRecord;

	@TableField("UNIT")
	private String unit;

	@TableField("MEMO")
	private String memo;

	@TableField("OA_WORK_TARGET_ID")
	private String oaWorkTargetId;

	@Override
	public int compareTo(OaWorkTargetExcuteEntity o) {
		return o.getCreateDate().compareTo(this.getCreateDate());
	}
}
