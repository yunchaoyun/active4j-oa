package com.active4j.hr.core.web.tag;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ReflectHelper;
import com.active4j.hr.core.util.oConvertUtils;
import com.active4j.hr.core.web.tag.model.ComboBox;
import com.active4j.hr.core.web.tag.model.TreeDataGrid;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 类描述：标签工具类
 * 
 */
public class TagUtil {
	/**
	 * <b>Summary: </b> getFiled(获得实体Bean中所有属性)
	 * 
	 * @param objClass
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Field[] getFiled(Class<?> objClass) throws ClassNotFoundException {
		Field[] field = null;
		if (objClass != null) {
			Class<?> class1 = Class.forName(objClass.getName());
			field = class1.getDeclaredFields();// 这里便是获得实体Bean中所有属性的方法
			return field;
		} else {
			return field;
		}
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

	/**
	 * 对象转数组
	 * @param fields
	 * @param o
	 * @param stack
	 * @return
	 * @throws Exception
	 */
	protected static Object[] field2Values(String[] fields, Object o) throws Exception {
		Object[] values = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].toString();
			values[i] = fieldNametoValues(fieldName, o);
		}
		return values;
	}
	/**
	 * 循环LIST对象拼接jqgrid格式的JSON数据
	 * @param fields
	 * @param total
	 * @param list
	 */
	private static String listtojson(String[] fields, int total, int totalPage, List<?> list, String footers) throws Exception {
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
	 * 计算指定列的合计
	 * @param filed 字段名
	 * @param list 列表数据
	 * @return
	 */
	private static Object getTotalValue(String fieldName, List list) {
		Double sum = 0D;
		try {
			for (int j = 0; j < list.size(); j++) {
				Double v = 0d;
				String vstr = String.valueOf(fieldNametoValues(fieldName, list.get(j)));
				if (!StringUtils.isEmpty(vstr)) {
					v = Double.valueOf(vstr);
				} else {

				}
				sum += v;
			}
		} catch (Exception e) {
			return "";
		}
		return sum;
	}
	/**
	 * 循环LIST对象拼接自动完成控件数据
	 * @param fields
	 * @param total
	 * @param list
	 * @throws Exception 
	 */
	public static String getAutoList(Autocomplete autocomplete, List list) throws Exception {
		String field = autocomplete.getLabelField() + "," + autocomplete.getValueField();
		String[] fields = field.split(",");
		Object[] values = new Object[fields.length];
		StringBuffer jsonTemp = new StringBuffer();
		jsonTemp.append("{\"totalResultsCount\":\"1\",\"geonames\":[");
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				jsonTemp.append("{'nodate':'yes',");
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i].toString();
					values[i] = fieldNametoValues(fieldName, list.get(j));
					jsonTemp.append("\"").append(fieldName).append("\"").append(":\"").append(values[i]).append("\"");
					if (i != fields.length - 1) {
						jsonTemp.append(",");
					}
				}
				if (j != list.size() - 1) {
					jsonTemp.append("},");
				} else {
					jsonTemp.append("}");
				}
			}
		} else {
			jsonTemp.append("{'nodate':'数据不存在'}");
		}
		jsonTemp.append("]}");
		return JSONObject.toJSONString(jsonTemp).toString();
	}
	/**
	 * 循环LIST对象拼接DATATABLE格式的JSON数据
	 * @param fields
	 * @param total
	 * @param list
	 */
	private static String datatable(String field, int total, List list) throws Exception {
		String[] fields = field.split(",");
		Object[] values = new Object[fields.length];
		StringBuffer jsonTemp = new StringBuffer();
		jsonTemp.append("{\"iTotalDisplayRecords\":" + total + ",\"iTotalRecords\":" + total + ",\"aaData\":[");
		for (int j = 0; j < list.size(); j++) {
			jsonTemp.append("{");
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].toString();
				values[i] = fieldNametoValues(fieldName, list.get(j));
				jsonTemp.append("\"" + fieldName + "\"" + ":\"" + values[i] + "\"");
				if (i != fields.length - 1) {
					jsonTemp.append(",");
				}
			}
			if (j != list.size() - 1) {
				jsonTemp.append("},");
			} else {
				jsonTemp.append("}");
			}
		}
		jsonTemp.append("]}");
		return jsonTemp.toString();
	}
	
	/**
	 * 返回列表JSONObject对象
	 * @param field
	 * @param dataGrid
	 * @return
	 */
	private static JSONObject getJson(TreeDataGrid dg) {
		JSONObject jObject = null;
		try {
			if(StringUtils.isNotEmpty(dg.getFooter())){
				jObject = JSONObject.parseObject(listtojson(dg.getField().split(","), dg.getTotal(), dg.getTotalPage(), dg.getResults(),dg.getFooter()));
			}else{
				jObject = JSONObject.parseObject(listtojson(dg.getField().split(","), dg.getTotal(), dg.getTotalPage(), dg.getResults(),null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObject;

	}



	/**
	 * 获取指定字段类型 <b>Summary: </b> getColumnType(请用一句话描述这个方法的作用)
	 * 
	 * @param fileName
	 * @param fields
	 * @return
	 */
	public static String getColumnType(String fileName, Field[] fields) {
		String type = "";
		if (fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName(); // 获取属性的名字
				String filedType = fields[i].getGenericType().toString(); // 获取属性的类型
				if (fileName.equals(name)) {
					if (filedType.equals("class java.lang.Integer")) {
						filedType = "int";
						type = filedType;
					}else if (filedType.equals("class java.lang.Short")) {
						filedType = "short";
						type = filedType;
					}else if (filedType.equals("class java.lang.Double")) {
						filedType = "double";
						type = filedType;
					}else if (filedType.equals("class java.util.Date")) {
						filedType = "date";
						type = filedType;
					}else if (filedType.equals("class java.lang.String")) {
						filedType = "string";
						type = filedType;
					}else if (filedType.equals("class java.sql.Timestamp")) {
						filedType = "Timestamp";
						type = filedType;
					}else if (filedType.equals("class java.lang.Character")) {
						filedType = "character";
						type = filedType;
					}else if (filedType.equals("class java.lang.Boolean")) {
						filedType = "boolean";
						type = filedType;
					}else if (filedType.equals("class java.lang.Long")) {
						filedType = "long";
						type = filedType;
					}

				}
			}
		}
		return type;
	}

	/**
	 * 
	 * <b>Summary: </b> getSortColumnIndex(获取指定字段索引)
	 * 
	 * @param fileName
	 * @param fieldString
	 * @return
	 */
	protected static String getSortColumnIndex(String fileName, String[] fieldString) {
		String index = "";
		if (fieldString.length > 0) {
			for (int i = 0; i < fieldString.length; i++) {
				if (fileName.equals(fieldString[i])) {
					int j = i + 1;
					index = oConvertUtils.getString(j);
				}
			}
		}
		return index;

	}


	/**
	 * 控件类型：easyui
	 * 返回datagrid JSON数据
	 * @param response
	 * @param dataGrid
	 */
	public static void datagrid(HttpServletResponse response,TreeDataGrid dg) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		response.setCharacterEncoding("utf-8");
		JSONObject object = TagUtil.getJson(dg);
		try {
			PrintWriter pw=response.getWriter();
			pw.write(object.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据模型生成JSON
	 * @param all 全部对象
	 * @param in  已拥有的对象
	 * @param comboBox 模型
	 * @return
	 */
	public static List<ComboBox> getComboBox(List all, List in, ComboBox comboBox) {
		List<ComboBox> comboxBoxs = new ArrayList<ComboBox>();
		String[] fields = new String[] { comboBox.getId(), comboBox.getText() };
		Object[] values = new Object[fields.length];
		for (Object node : all) {
			ComboBox box = new ComboBox();
			ReflectHelper reflectHelper=new ReflectHelper(node);
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].toString();
				values[i] = reflectHelper.getMethodValue(fieldName);
			}
			box.setId(values[0].toString());
			box.setText(values[1].toString());
			if (in != null) {
				for (Object node1 : in) {
					ReflectHelper reflectHelper2=new ReflectHelper(node);
					if (node1 != null) {
						String fieldName = fields[0].toString();
						String	test = reflectHelper2.getMethodValue(fieldName).toString();
						if (values[0].toString().equals(test)) {
							box.setSelected(true);
						}
					}
				}
			}
			comboxBoxs.add(box);
		}
		return comboxBoxs;

	}
	/**
	 * 获取自定义函数名
	 * 
	 * @param functionname
	 * @return
	 */
	public static String getFunction(String functionname) {
		int index = functionname.indexOf("(");
		if (index == -1) {
			return functionname;
		} else {
			return functionname.substring(0, functionname.indexOf("("));
		}
	}

	/**
	 * 获取自定义函数的参数
	 * 
	 * @param functionname
	 * @return
	 */
	public static String getFunParams(String functionname) {
		int index = functionname.indexOf("(");
		String param = "";
		if (index != -1) {
			String testparam = functionname.substring(
					functionname.indexOf("(") + 1, functionname.length() - 1);
			if (StringUtils.isNotEmpty(testparam)) {
				String[] params = testparam.split(",");
				int i = 0;
				for (String string : params) {
					i++;
					if(i == params.length) {
						param += (string.indexOf("{") != -1) ? ("'\"+"
								+ string.substring(1, string.length() - 1) + "+\"',")
								: ("'\"+rowObject." + string + "+\"'");
					}else {
						param += (string.indexOf("{") != -1) ? ("'\"+"
								+ string.substring(1, string.length() - 1) + "+\"',")
								: ("'\"+rowObject." + string + "+\"',");
					}
				}
			}
		}
		// += "'\"+index+\"'";// 传出行索引号参数
		return param;
	}
}
