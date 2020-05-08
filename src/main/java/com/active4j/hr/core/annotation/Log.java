package com.active4j.hr.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.active4j.hr.core.model.LogType;

/**
 * 自定义日志注解
 * 
 * @author 38943
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Inherited
public @interface Log {
	// 类型
	LogType type();

	// 名称
	String name() default "";

	// 备注
	String memo() default "";

}
