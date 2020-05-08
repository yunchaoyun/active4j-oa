package com.active4j.hr.core.threadpool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.active4j.hr.core.beanutil.ApplicationContextUtil;

/**
 * @title ThreadPoolManager.java
 * @description 
		  线程池执行类  单例
 * @time  2019年12月4日 下午5:04:26
 * @author 麻木神
 * @version 1.0
*/
public class ThreadPoolManager {

	private ThreadPoolManager() {}
	
	
	private static ThreadPoolManager me = new ThreadPoolManager();
	
	//返回单例
	public static ThreadPoolManager me() {
		return me;
	}
	
	//线程池
	private ThreadPoolTaskExecutor threadPoolExecutor = ApplicationContextUtil.getContext().getBean(ThreadPoolTaskExecutor.class);
	
	//交给线程池执行
	public void execute(Runnable runnable) {
		threadPoolExecutor.execute(runnable);
	}
	
	/**
	 * 
	 * @description
	 *  	关闭线程池，不再接受新的任务，之前提交的任务等执行结束再关闭线程池
	 * @return void
	 * @author 麻木神
	 * @time 2019年12月4日 下午5:28:08
	 */
	public void shutdown() {
		threadPoolExecutor.shutdown();
	}
}
