package com.active4j.hr.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


/**
 * 验证码
 * @author teli_
 *
 */
@Configuration
@ConfigurationProperties(prefix = "vercode")
@Data
public class VerCodeProperties {

	
	private int randCodeLength;
	
	private String randCodeType;
}
