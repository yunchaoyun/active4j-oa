package com.active4j.hr.func.timer.system;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @title FuncSystemTimerTaskService.java
 * @description 
		系统固定定时任务
 * @time  2019年12月12日 下午1:49:30
 * @author guyp
 * @version 1.0
 */
@Service
@Transactional
@Slf4j
public class SystemTimerTaskService {

	/**
	 * 
	 * @description
	 *  	系统固定定时任务，需要在启动类添加开启定时任务的注解@EnableScheduling，不支持集群
	 *  	此处指列举cron参数，该参数接收一个cron表达式，cron表达式是一个字符串，字符串以5或6个空格隔开，分开共6或7个域，每一个域代表一个含义。
	 *  	如有其余系统固定任务可以继续添加方法
	 * @params
	 *      0 0 1 * * ? 每天凌晨1点执行
	 * @return void
	 * @author guyp
	 * @time 2019年12月12日 下午3:13:46
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void taskCron() {
		 log.info("系统固定定时任务方式开始，当前时间：{}", DateUtils.getDate2Str(new Date()));
	}
	
}
