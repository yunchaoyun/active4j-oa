package com.active4j.hr.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 客户端请求request 工具类
 * 
 * @author teli_
 *
 */
public class RequestUtil {

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * 获取request
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * 判断客户端
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		String uri = request.getRequestURI();
		if (MyStringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
			return true;
		}

		String ajax = request.getParameter("__ajax");
		if (MyStringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
			return true;
		}
		return false;
	}

}
