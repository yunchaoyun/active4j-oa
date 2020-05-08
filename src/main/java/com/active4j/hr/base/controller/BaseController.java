package com.active4j.hr.base.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.active4j.hr.core.web.DateConvertEditor;
import com.active4j.hr.core.web.IntegerConvertEditor;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础controller
 * @author teli_
 *
 */
@Slf4j
public class BaseController {
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(int.class, new IntegerConvertEditor());
	}
	
	
}
