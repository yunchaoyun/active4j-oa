package com.active4j.hr.core.beanutil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName: ContextHolderUtils
 * 
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;

	}

	public static String getParameter(String field) {
		HttpServletRequest request = getRequest();
		return request.getParameter(field);
	}

}
