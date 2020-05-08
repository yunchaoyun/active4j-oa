package com.active4j.hr.func.timer.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title CronUtils.java
 * @description 
		cron表达式工具类
 * @time  2019年12月11日 下午10:41:42
 * @author guyp
 * @version 1.0
 */
@Slf4j
public class CronUtils {
	
	/**
	 * 
	 * @description
	 *  	返回一个布尔值表示cron表达式的有效性
	 * @params
	 *      cronExpression cron表达式
	 * @return boolean
	 * @author guyp
	 * @time 2019年12月11日 下午10:31:33
	 */
    public static boolean isValid(String cronExpression) {
    	return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 
     * @description
     *  	返回cron表达式错误的描述，如果表达式有效则返回null
     * @params
     *      cronExpression cron表达式
     * @return String
     * @author guyp
     * @time 2019年12月11日 下午10:34:22
     */
    public static String getInvalidMessage(String cronExpression) {
    	try {
			new CronExpression(cronExpression);
			return null;
		} catch (ParseException e) {
			return e.getMessage();
		}
    }

    /**
     * 
     * @description
     *  	根据cron表达式得到下一次执行的时间
     * @params
     *      cronExpression cron表达式
     * @return Date
     * @author guyp
     * @time 2019年12月11日 下午10:40:31
     */
    public static Date getNextExecution(String cronExpression) {
    	try {
    		if(StringUtils.isEmpty(cronExpression)) {
    			return null;
    		}
    		
			CronExpression cron = new CronExpression(cronExpression);
			return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
		} catch (ParseException e) {
			log.error("根据cron表达式得到下一次执行的时间报错，错误原因：{}", e.getMessage());
			return null;
		}
    }
}
