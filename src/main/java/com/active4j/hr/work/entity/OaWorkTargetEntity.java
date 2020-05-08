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
 * @title OaWorkTargetEntity.java
 * @description 目标管理
 * @time 2020年4月8日 下午2:55:04
 * @author 麻木神
 * @version 1.0
 */
@TableName("OA_WORK_TARGET")
@Getter
@Setter
public class OaWorkTargetEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1862603376923506858L;

	// 目标内容
	@TableField("CONTENT")
	@QueryField(condition=QueryCondition.like, queryColumn="CONTENT")
	private String content;

	// 目标量化数据
	@TableField("DATA_RECORD")
	private int dataRecord;

	// 已完成数据
	@TableField("FINISH_RECORD")
	private int finishRecord;

	@TableField("UNIT")
	private String unit;

	// 目标类型 公司目标：0 部门目标 ：1 个人目标：2
	@TableField("TYPE")
	@QueryField(condition=QueryCondition.eq, queryColumn="TYPE")
	private String type;

	// 目标类别 年度目标 季度目标 月度目标 周目标 每日目标
	@TableField("CATEGORY")
	@QueryField(condition=QueryCondition.eq, queryColumn="CATEGORY")
	private String category;

	// 状态 0：新建 1：进行中 2：完成 3：放弃
	@TableField("STATUS")
	@QueryField(condition=QueryCondition.eq, queryColumn="STATUS")
	private String status;

	// 我的目标
	@TableField("USER_ID")
	private String userId;

	@TableField("USER_NAME")
	private String userName;

	// 创建用户ID
	@TableField("CREATE_USER_ID")
	private String createUserId;

	@TableField("CREATE_USER_NAME")
	private String createUserName;

	// 参与人
	@TableField("PARTICIPANTIDS")
	private String participantIds;

	@TableField("PARTICIPANTS")
	private String participants;

	// 部门
	@TableField("DEPART_ID")
	private String departId;

	@TableField("DEPART_NAME")
	private String departName;

	// 目标期限
	@TableField("START_DATE")
	private Date startDate;

	@TableField("END_DATE")
	private Date endDate;

	// 备注
	@TableField("MEMO")
	private String memo;

	@TableField("PARENT_TARGET_ID")
	private String parentTargetId;
}
