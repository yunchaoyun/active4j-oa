package com.active4j.hr.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * @title ResponseUtil.java
 * @description 输出响应工具类
 * @time 2020年1月27日 下午9:15:02
 * @author 麻木神
 * @version 1.0
 */
@Slf4j
public class ResponseUtil {

	/**
	 * 返回json格式数据
	 * 
	 * @param response
	 * @param result
	 */
	public static void writeJson(HttpServletResponse response, String result) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			log.error("往客户端写出数据报错，错误信息:" + e.getMessage());
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 
	 * @description
	 *  	将表格数据输出到前端
	 * @return void
	 * @author 麻木神
	 * @time 2020年1月27日 下午11:34:48
	 */
	public static <T> void writeJson(HttpServletResponse response, DataGrid dataGrid, IPage<T> lstResult) {
		writeJson(response, dataGrid, lstResult, null);
	}

	public static <T> void writeJson(HttpServletResponse response, DataGrid dataGrid, IPage<T> lstResult, String footer) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		try {

			String result = null;

			if (StringUtils.isNotEmpty(footer)) {
				result = listtojson(dataGrid.getField().split(","), lstResult.getTotal(), lstResult.getSize(), lstResult.getRecords(), footer);
			} else {
				result = listtojson(dataGrid.getField().split(","), lstResult.getTotal(), lstResult.getSize(), lstResult.getRecords(), null);
			}

			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error("往客户端写出数据报错，错误信息:" + e.getMessage());
			log.error(e.getMessage(), e);
		}
	}
	
	public static <T> void writeJson(HttpServletResponse response, DataGrid dataGrid, List<T> lstResult) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		try {
			String result = listtojson(dataGrid.getField().split(","), lstResult.size(), 1, lstResult, null);

			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error("往客户端写出数据报错，错误信息:" + e.getMessage());
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 循环LIST对象拼接jqgrid格式的JSON数据
	 * @param fields
	 * @param total
	 * @param list
	 */
	private static String listtojson(String[] fields, long total, long totalPage, List<?> list, String footers) throws Exception {
		Object[] values = new Object[fields.length];
		StringBuffer jsonTemp = new StringBuffer();
		jsonTemp.append("{\"total\":" + total + ", \"totalPage\":" + totalPage + ",\"rows\":[");
		int i;
		String fieldName;
		for (int j = 0; j < list.size(); ++j) {
			jsonTemp.append("{\"state\":\"closed\",");
			for (i = 0; i < fields.length; ++i) {
				fieldName = fields[i].toString();
				if (list.get(j) instanceof Map)
					values[i] = ((Map<?, ?>) list.get(j)).get(fieldName);
				else {
					values[i] = fieldNametoValues(fieldName, list.get(j));
				}
				jsonTemp.append("\"" + fieldName + "\"" + ":\"" + String.valueOf(values[i]).replace("\"", "\\\"") + "\"");
				if (i != fields.length - 1) {
					jsonTemp.append(",");
				}
			}
			if (j != list.size() - 1)
				jsonTemp.append("},");
			else {
				jsonTemp.append("}");
			}
		}
		jsonTemp.append("]");
		if (footers != null) {
			jsonTemp.append(",");
			jsonTemp.append("\"footer\":\"" + footers + "\"");
		}
		jsonTemp.append("}");
		return jsonTemp.toString();
	}

	
	/**
	 * 
	 * 获取对象内对应字段的值
	 * @param fields
	 */
	public static Object fieldNametoValues(String FiledName, Object o){
		Object value = "";
		String fieldName = "";
		String childFieldName = null;
		ReflectHelper reflectHelper=new ReflectHelper(o);
		if (FiledName.indexOf("_") == -1) {
			if(FiledName.indexOf(".") == -1){
				fieldName = FiledName;
			}else{
				fieldName = FiledName.substring(0, FiledName.indexOf("."));//外键字段引用名
				childFieldName = FiledName.substring(FiledName.indexOf(".") + 1);//外键字段名
			}
		} else {
			fieldName = FiledName.substring(0, FiledName.indexOf("_"));//外键字段引用名
			childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);//外键字段名
		}
		value = reflectHelper.getMethodValue(fieldName)==null?"":reflectHelper.getMethodValue(fieldName);
		//如果是日期格式，这边格式化一下
		if(value instanceof java.util.Date) {
			Date tmp = (Date)value;
			value = DateUtils.date2Str(tmp, DateUtils.SDF_YYYY_MM_DD_HH_MM_SS);
		}
		if (value !=""&&value != null && (FiledName.indexOf("_") != -1||FiledName.indexOf(".") != -1)) {
			value = fieldNametoValues(childFieldName, value);
		}
		if(value != "" && value != null) {
			value = value.toString().replaceAll("\r\n", "");
		}
		return value;
	}

}
