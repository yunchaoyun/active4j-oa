package com.active4j.hr.core.util;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * 获取UUID唯一主键
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
