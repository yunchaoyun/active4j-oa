package com.active4j.hr.core.init;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.active4j.hr.core.threadpool.ThreadPoolManager;
import com.active4j.hr.system.service.SystemService;

import lombok.extern.slf4j.Slf4j;
/**
 * 初始化启动执行
 * @author teli_
 *
 */
@Slf4j
@Configuration
public class CommonStartConfig implements InitializingBean, DisposableBean {
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * spring初始化执行
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("---------------初始化启动执行---------------");
		
		//系统初始化数据字典
		systemService.initDic();
		//系统初始化部门数据
		systemService.initDeparts();
		
	}

	
	/**
	 * 容器销毁执行
	 */
	@Override
	public void destroy() throws Exception {
		log.info("---------------容器销毁启动执行---------------");
		
		//关闭线程池
		ThreadPoolManager.me().shutdown();
		
		log.info("---------------容器销毁启动执行完成---------------");
	}
	

}
