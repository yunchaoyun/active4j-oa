package com.active4j.hr.core.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @title WebMVCConfig.java
 * @description webmvc相关配置 解决swagger页面404
 * @time 2019年12月31日 上午10:28:46
 * @author 麻木神
 * @version 1.0
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

  
}
