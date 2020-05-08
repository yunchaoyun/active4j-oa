package com.active4j.hr.core.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * @title RedisPropertiesLoader.java
 * @description 
		  redis属性加载器    集成shiro时使用
 * @time  2019年12月17日 下午2:58:24
 * @author 麻木神
 * @version 1.0
*/
@ConfigurationProperties(prefix="spring.redis")
@Configuration
@ConditionalOnProperty(prefix="spring.redis",value={"host", "port", "password", "timeout", "database"}, matchIfMissing=false)
@Getter
@Setter
public class RedisPropertiesLoader {

	private String host;
	
	private int port;
	
	private String password;
	
	private int timeout;
	
	private int database;
	
}
