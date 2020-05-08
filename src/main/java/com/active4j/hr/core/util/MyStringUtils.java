package com.active4j.hr.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义字符串工具类
 * 
 * @author teli_
 *
 */
public class MyStringUtils extends StringUtils {

	/**
	 * 是否包含字符串
	 * 
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(StringUtils.trim(s))) {
					return true;
				}
			}
		}
		return false;
	}
}
