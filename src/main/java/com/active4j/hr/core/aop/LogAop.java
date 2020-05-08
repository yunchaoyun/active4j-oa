package com.active4j.hr.core.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.threadpool.ThreadPoolManager;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.IpUtil;
import com.active4j.hr.system.entity.SysLogEntity;
import com.active4j.hr.system.service.SysLogService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title LogAop.java
 * @description 
		  spring aop   系统日志
 * @time  2019年12月3日 上午10:15:12
 * @author chenxl
 * @version 1.0
 */
@Component
@Aspect
@Slf4j
public class LogAop {
	
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 配置织入点
	 */
	@Pointcut(value = "@annotation(com.active4j.hr.core.annotation.Log)")
	public void cutService() {
    }

	@Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if(!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();
        
        //如果获取不到用户名，则记录为system
        String userName = "system";
        if(ShiroUtils.hasLogin()) {
        	userName = ShiroUtils.getSessionUserName();
        }
        
        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();
        
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        //获取操作类型，名称，备注
        Log annotation = currentMethod.getAnnotation(Log.class);
        LogType type = annotation.type();
	    String name = annotation.name();
	    String memo = annotation.memo();
	    
	    //获取request作用域
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpServletRequest request = attr.getRequest();
	    //得到UserAgent对象
	    UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	    //获得浏览器信息
	    Browser browser = userAgent.getBrowser();
	    //获得IP地址
        String ip = IpUtil.getIpAddr(request);
	    
	    //日志实体赋值
	    SysLogEntity sysLog = new SysLogEntity();
	    sysLog.setUserName(userName);
	    sysLog.setName(name);
	    sysLog.setType(getLogType(type));
	    sysLog.setOperatorTime(DateUtils.getNow());
	    sysLog.setBroswer(browser.toString());
	    sysLog.setIp(ip);
	    sysLog.setClassName(className);
	    sysLog.setMethodName(methodName);
	    sysLog.setParams(sb.toString());
	    sysLog.setMemo(memo);
	    
	    //多线程异步执行
	    ThreadPoolManager.me().execute(() -> {
	    	//新增日志
	    	sysLogService.addLog(sysLog);
	    }); 
    }
    
    private String getLogType(LogType logType) {
    	String type = "";
    	switch (logType) {
		case login:
			type = "1";
			break;
		case logout:
			type = "2";
			break;
		case save:
			type = "3";
			break;
		case insert:
			type = "4";
			break;
		case del:
			type = "5";
			break;
		case update:
			type = "6";
			break;
		case timer:
			type = "7";
			break;
		case normal:
			type = "8";
			break;
		case abnormal:
			type = "9";
			break;
		default:
			break;
		}
    	
    	return type;
    }
	
}
