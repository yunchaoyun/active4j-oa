package com.active4j.hr.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysLogEntity;
import com.active4j.hr.system.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 
 * @title SysLogController.java
 * @description 
		 系统管理  日志管理
 * @time  2020年2月4日 上午9:44:07
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
	
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 
	 * @description
	 *  	菜单列表页面跳转
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年2月4日 上午9:45:26
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView("system/log/loglist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	表格数据显示
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2020年2月4日 上午9:45:39
	 */
	@RequestMapping("/datagrid")
	public void datagrid(SysLogEntity sysLogEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<SysLogEntity> queryWrapper = QueryUtils.installQueryWrapper(sysLogEntity, request.getParameterMap(), dataGrid);
		
		//执行查询
		IPage<SysLogEntity> lstResult = sysLogService.page(new Page<SysLogEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
}
