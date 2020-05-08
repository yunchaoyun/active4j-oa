package com.active4j.hr.func.timer.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.core.util.SpringUtils;
import com.active4j.hr.func.timer.entity.QuartzJobEntity;

/**
 * 
 * @title JobInvokeUtil.java
 * @description 
		任务执行工具
 * @time  2019年12月10日 下午2:34:39
 * @author guyp
 * @version 1.0
 */
public class QuartzJobInvokeUtil {
	
	/**
	 * 
	 * @description
	 *  	执行方法
	 * @params
	 *      job 定时任务
	 * @return void
	 * @author guyp
	 * @time 2019年12月10日 下午2:24:13
	 */
    public static void invokeMethod(QuartzJobEntity job) throws Exception {
        String invokeParams = job.getInvokeParams();
        String beanName = getBeanName(invokeParams);
        String methodName = getMethodName(invokeParams);
        List<Object[]> methodParams = getMethodParams(invokeParams);
        //判断包名
        if(!isValidClassName(beanName)) {
            Object bean = SpringUtils.getBean(beanName);
            invokeMethod(bean, methodName, methodParams);
        }else {
            Object bean = Class.forName(beanName).newInstance();
            invokeMethod(bean, methodName, methodParams);
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    /**
     * 
     * @description
     *  	调用任务方法
     * @params
     *      bean 目标对象
     *      methodName 方法名称
     *      methodParams 方法参数
     * @return void
     * @author guyp
     * @time 2019年12月10日 下午2:24:36
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if(null != methodParams && methodParams.size() > 0) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        }else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 
     * @description
     *  	校验是否为为class包名
     * @params
     * @return boolean
     * @author guyp
     * @time 2019年12月10日 下午2:26:15
     */
    public static boolean isValidClassName(String invokeParams) {
        return StringUtils.countMatches(invokeParams, ".") > 1;
    }

    /**
     * 
     * @description
     *  	获取bean名称
     * @params
     *      invokeParams 目标字符串
     * @return String
     * @author guyp
     * @time 2019年12月10日 下午2:33:48
     */
    public static String getBeanName(String invokeParams) {
        String beanName = StringUtils.substringBefore(invokeParams, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 
     * @description
     *  	获取bean方法
     * @params
     *      invokeParams 目标字符串
     * @return String
     * @author guyp
     * @time 2019年12月10日 下午2:33:27
     */
    public static String getMethodName(String invokeParams) {
        String methodName = StringUtils.substringBefore(invokeParams, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 
     * @description
     *  	获取method方法参数相关列表
     * @params
     *      invokeParams 目标字符串
     * @return List<Object[]>
     * @author guyp
     * @time 2019年12月10日 下午2:33:04
     */
    public static List<Object[]> getMethodParams(String invokeParams) {
        String methodStr = StringUtils.substringBetween(invokeParams, "(", ")");
        if(StringUtils.isEmpty(methodStr)) {
            return null;
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> classs = new LinkedList<>();
        for(int i = 0; i < methodParams.length; i++) {
            String str = StringUtils.trimToEmpty(methodParams[i]);
            // String字符串类型，包含'
            if(StringUtils.contains(str, "'")) {
                classs.add(new Object[] { StringUtils.replace(str, "'", ""), String.class });
            }
            // boolean布尔类型，true或者false
            else if(StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
                classs.add(new Object[] { Boolean.valueOf(str), Boolean.class });
            }
            // long长整形，包含L
            else if(StringUtils.containsIgnoreCase(str, "L")) {
                classs.add(new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class });
            }
            // double浮点类型，包含D
            else if(StringUtils.containsIgnoreCase(str, "D")) {
                classs.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class });
            }
            // 其他类型归类为整型
            else {
                classs.add(new Object[] { Integer.valueOf(str), Integer.class });
            }
        }
        return classs;
    }
    
    /**
     * 
     * @description
     *  	获取参数类型
     * @params
     *      methodParams 参数相关列表
     * @return Class<?>[]
     * @author guyp
     * @time 2019年12月10日 下午2:28:42
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classs = new Class<?>[methodParams.size()];
        int index = 0;
        for(Object[] os : methodParams) {
            classs[index] = (Class<?>) os[1];
            index++;
        }
        return classs;
    }

    /**
     * 
     * @description
     *  	获取参数值
     * @params
     *      methodParams 参数相关列表
     * @return Object[]
     * @author guyp
     * @time 2019年12月10日 下午2:28:24
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classs = new Object[methodParams.size()];
        int index = 0;
        for(Object[] os : methodParams) {
        	classs[index] = (Object) os[0];
            index++;
        }
        return classs;
    }
}
