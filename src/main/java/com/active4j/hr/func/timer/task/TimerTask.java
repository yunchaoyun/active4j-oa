package com.active4j.hr.func.timer.task;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title TimerTask.java
 * @description 
		定时任务调度测试
 * @time  2019年12月10日 下午2:42:13
 * @author guyp
 * @version 1.0
 */
@Component("timerTask")
@Slf4j
public class TimerTask {
	
	/**
	 * 
	 * @description
	 *  	执行多参方法
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2019年12月10日 下午10:22:33
	 */
    public void taskMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info("执行多参任务： 字符串类型：{}，布尔类型：{}，长整型：{}，浮点型：{}，整型：{}", s, b, l, d, i);
    }

    /**
     * 
     * @description
     *  	执行有参方法
     * @params
     * @return void
     * @author guyp
     * @time 2019年12月10日 下午10:23:29
     */
    public void taskParams(String params) {
    	 log.info("执行有参任务开始：" + params);
    }

    /**
     * 
     * @description
     *  	执行无参方法
     * @params
     * @return void
     * @author guyp
     * @time 2019年12月10日 下午10:23:45
     */
    public void taskNoParams() {
    	 log.info("执行无参任务");
    }
}
