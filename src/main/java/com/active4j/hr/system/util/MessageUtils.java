package com.active4j.hr.system.util;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.entity.SysMessageEntity;
import com.active4j.hr.system.service.SysMessageService;

/**
 * @title MessageUtils.java
 * @description 
		  系统消息发送 工具类
 * @time  2020年4月3日 下午12:14:54
 * @author 麻木神
 * @version 1.0
*/
public class MessageUtils {

	private static SysMessageService sysMessageService = ApplicationContextUtil.getContext().getBean(SysMessageService.class);
	
	/**
	 * 
	 * @description
	 *  	放系统消息
	 * @return void
	 * @author 麻木神
	 * @time 2020年4月4日 下午9:37:41
	 */
	public static void SendSysMessage(String userId, String content) {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(content)) {
			return;
		}
		
		SysMessageEntity msg = new SysMessageEntity();
		msg.setContent(content);
		msg.setSender(GlobalConstant.SYS_MSG_SENDER);
		msg.setStatus(GlobalConstant.SYS_MSG_NO_READ);
		msg.setSendTime(DateUtils.getDate());
		msg.setUserId(userId);
		msg.setTip("0");
		
		sysMessageService.sendMessage(msg);
	}
	
}
