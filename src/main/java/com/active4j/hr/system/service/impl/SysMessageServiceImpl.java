package com.active4j.hr.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.threadpool.ThreadPoolManager;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.system.dao.SysMessageDao;
import com.active4j.hr.system.entity.SysMessageEntity;
import com.active4j.hr.system.service.SysMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title SysMessageServiceImpl.java
 * @description 
		  系统消息管理
 * @time  2020年4月3日 上午10:52:54
 * @author 麻木神
 * @version 1.0
 */
@Service("sysMessageService")
@Transactional
public class SysMessageServiceImpl extends ServiceImpl<SysMessageDao, SysMessageEntity> implements SysMessageService {
	

	 /**
	  * 使用线程池 发送消息
	  */
	 public void sendMessage(SysMessageEntity message) {
			//基础变量赋值
		 message.setCreateDate(DateUtils.getDate());
		 message.setCreateName("system");
		 
		 ThreadPoolManager.me().execute(() -> {
			 this.save(message);
		 });
	 }
	
}
