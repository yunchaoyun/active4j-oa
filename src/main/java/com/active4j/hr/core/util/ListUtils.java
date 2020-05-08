package com.active4j.hr.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @title ListUtils.java
 * @time  2020年1月25日 下午9:44:39
 * @author 麻木神
 * @version 1.0
*/
public class ListUtils {

	
	/**
     * 方法描述:  值替换工具
     * 作    者： zym
     * @param objList
     * @param perFieldName
     * @param sufFieldName
     * @return 格式：old_new,old2_new2
     * 返回类型： String
     */
	public static String listToReplaceStr(List<?> objList, String perFieldName, String sufFieldName){
		List<String> strList = new ArrayList<String>();
		for (Object object : objList) {
			String perStr = null;
			String sufStr = null;
			try {
				perStr = (String)PropertyUtils.getProperty(object, perFieldName);
				sufStr = (String)PropertyUtils.getProperty(object, sufFieldName);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			strList.add(perStr + "_" +sufStr);
		}
		return StringUtils.join(strList, ",");
	}
}
