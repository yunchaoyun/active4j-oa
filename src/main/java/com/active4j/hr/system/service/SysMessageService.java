package com.active4j.hr.system.service;

import com.active4j.hr.system.entity.SysMessageEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysMessageService extends IService<SysMessageEntity> {
	
	
	 /**
	  * 使用线程池 发送消息
	  */
	 public void sendMessage(SysMessageEntity message);
	
}
