package com.active4j.hr.activiti.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @title UtilMisc.java
 * @description 流程图绘制工具类
 * @time 2020年4月24日 下午9:57:51
 * @author 麻木神
 * @version 1.0
 */
public class UtilMisc {
	public static <V, V1 extends V, V2 extends V> Map<String, V> toMap(String name1, V1 value1, String name2, V2 value2) {
		return populateMap(new HashMap<String, V>(), name1, value1, name2, value2);
	}

	@SuppressWarnings("unchecked")
	private static <K, V> Map<String, V> populateMap(Map<String, V> map, Object... data) {
		for (int i = 0; i < data.length;) {
			map.put((String) data[i++], (V) data[i++]);
		}
		return map;
	}
}
