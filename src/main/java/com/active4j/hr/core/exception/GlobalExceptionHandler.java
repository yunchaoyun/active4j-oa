package com.active4j.hr.core.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.util.RequestUtil;
import com.active4j.hr.core.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @title GlobalExceptionHandler.java
 * @description 
		全局异常管理
 * @time 2019年12月11日 下午5:09:21
 * @author 麻木神
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	
	/**
	 * 
	 * @description
	 *  	实体字段校验  校验不通过抛出异常
	 * @return void
	 * @author 麻木神
	 * @time 2019年12月11日 下午5:25:57
	 */
	@ExceptionHandler(BindException.class)
	public void methodArgumentNotValidException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
		//打印异常信息
		log.error(ex.getMessage());
		//返回前端
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();
		if(null != errors && errors.size() > 0) {
			//构造返回前端json字符串
			AjaxJson j = new AjaxJson();
			j.setSuccess(false);
			j.setMsg(errors.get(0).getDefaultMessage());
			ResponseUtil.writeJson(response, j.toJsonStr());
		}
	}

	
	@ExceptionHandler(UnauthorizedException.class)
	public ModelAndView unauthorizedException(UnauthorizedException ex, HttpServletRequest request, HttpServletResponse response) {
		//判断返回页面还是json
		if(RequestUtil.isAjaxRequest(request)) {
			//对于ajax异步请求，需要返回json结果
			ResponseUtil.writeJson(response, AjaxJson.getErrorStr(GlobalConstant.Err_Msg_No_Auth));
			return null;
		}else {
			//直接返回异常页面
			ModelAndView mv = new ModelAndView("common/403");
			return mv;
		}
	}
		
	
	@ExceptionHandler(Exception.class)
	public ModelAndView exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		//判断返回页面还是json
		if(RequestUtil.isAjaxRequest(request)) {
			//对于ajax异步请求，需要返回json结果
			ResponseUtil.writeJson(response, AjaxJson.getErrorStr(GlobalConstant.Err_Msg_All));
			log.error("ajax全局异常, 异常信息:{}", ex.getMessage());
			return null;
		}else {
			//直接返回异常页面
			ModelAndView mv = new ModelAndView("common/500");
			log.error("mv全局异常, 异常信息:{}", ex.getMessage());
			return mv;
		}
	}
	
	
}
