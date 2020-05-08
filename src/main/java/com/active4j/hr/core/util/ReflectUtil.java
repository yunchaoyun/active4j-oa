package com.active4j.hr.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射工具类
 * @author teli_
 *
 */
@Slf4j
public class ReflectUtil {
	
	/**
	 * 反射出来的基本类型
	 */
	public static final String TYPE_String = "class java.lang.String";
	public static final String TYPE_Integer = "class java.lang.Integer";
	public static final String TYPE_Double = "class java.lang.Double";
	public static final String TYPE_Boolean = "class java.lang.Boolean";
	public static final String TYPE_Date = "class java.util.Date";
	public static final String Type_Short = "class java.lang.Short";
	public static final String BASE_TYPE_Boolean = "boolean";
	public static final String BASE_TYPE_Integer = "int";
	public static final String BASE_TYPE_Long = "long";
	
	
	public static Object getValueByField(Field field, Object sourceObj) {
		
		Object result = null;
		try {
			//如果类型是string的
			if(StringUtils.equals(field.getGenericType().toString(), TYPE_String)) {
				
				// 拿到该属性的gettet方法  
	           
	            Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  

	            String val = (String) m.invoke(sourceObj);// 调用getter方法获取属性值  
	            if (StringUtils.isNotEmpty(val)) {  
	               result = val;
	            }  
			}
			
			  // 如果类型是Integer  
			else if (StringUtils.equals(field.getGenericType().toString(), TYPE_Integer)) {  
                Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  
                Integer val = (Integer) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;
                }  

            }  

            // 如果类型是Double  
			else if (StringUtils.equals(field.getGenericType().toString(), TYPE_Double)) {  
                Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  
                Double val = (Double) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val; 
                }  

            }  

            // 如果类型是Boolean 是封装类  
			else if (StringUtils.equals(field.getGenericType().toString(), TYPE_Boolean)) {  
                Method m = (Method) sourceObj.getClass().getMethod(field.getName());  
                Boolean val = (Boolean) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;
                }  

            }  

            // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的  
            // 反射找不到getter的具体名  
			else if (StringUtils.equals(field.getGenericType().toString(), BASE_TYPE_Boolean)) {  
                Method m = (Method) sourceObj.getClass().getMethod(field.getName());  
                Boolean val = (Boolean) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;
                }  

            }  
            // 如果类型是Date  
            else if (StringUtils.equals(field.getGenericType().toString(), TYPE_Date)) {  
                Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  
                Date val = (Date) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;  
                }  

            }  
            // 如果类型是Short  
            else if (StringUtils.equals(field.getGenericType().toString(), Type_Short)) {  
                Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  
                Short val = (Short) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;  
                }  

            }  
			// 如果是int类型
            else if (StringUtils.equals(field.getGenericType().toString(), BASE_TYPE_Integer)) {  
                Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  
                Integer val = (Integer) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;
                }  

            }  
			
			// 如果是long类型
            else if (StringUtils.equals(field.getGenericType().toString(), BASE_TYPE_Long)) {  
                Method m = (Method) sourceObj.getClass().getMethod("get" + getMethodName(field.getName()));  
                Long val = (Long) m.invoke(sourceObj);  
                if (val != null) {  
                	result = val;
                }  

            }  
			
            // 如果还需要其他的类型请自己做扩展
		}catch(Exception e) {
			log.error("通过反射获取字段值出错，错误信息:" + e.getMessage());
			e.printStackTrace();
		}
	
		
		
		return result;
	}
	
	
	// 把一个字符串的第一个字母大写、效率是最高的、  
    private static String getMethodName(String fildeName) throws Exception{  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  
    }
	
}
