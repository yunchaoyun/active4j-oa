package com.active4j.hr.system.entity;

import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @title SysMessageEntity.java
 * @description 系统消息实体
 * @time 2020年4月3日 上午10:48:55
 * @author 麻木神
 * @version 1.0
 */

@TableName("sys_message")
@Getter
@Setter
public class SysMessageEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6640724465050143378L;

	// 谁发出的，暂时默认系统消息
	@TableField("SENDER")
	private String sender;

	// 消息内容
	@TableField("CONTENT")
	private String content;

	// 发送时间
	@TableField("SEND_TIME")
	private Date sendTime;

	// 接收人
	@TableField("USER_ID")
	private String userId;

	// 状态 是否已读 0：未读 1：已读
	@TableField("STATUS")
	private String status;

	// 是否已经提示
	@TableField("TIP")
	private String tip;

}
