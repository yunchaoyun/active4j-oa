package com.active4j.hr.activiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.entity.WorkflowFormCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowFormCategoryService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title BaseController.java
 * @description 流程表单类别
 * @time 2020年4月18日 下午10:09:47
 * @author 麻木神
 * @version 1.0
 */
@Controller
@RequestMapping("/wf/form/category")
@Slf4j
public class WorkflowFormCategoryController extends BaseController {

	@Autowired
	private WorkflowFormCategoryService workflowFormCategoryService;

	/**
	 * 流程类别列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("activiti/form/category/categorylist");
	}

	/**
	 * 查询数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(WorkflowFormCategoryEntity workflowFormCategoryEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowFormCategoryEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowFormCategoryEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<WorkflowFormCategoryEntity> lstResult = workflowFormCategoryService.page(new Page<WorkflowFormCategoryEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param workflowCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(WorkflowFormCategoryEntity workflowFormCategoryEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/form/category/category");
		
		if(StringUtils.isNotEmpty(workflowFormCategoryEntity.getId())) {
			workflowFormCategoryEntity = workflowFormCategoryService.getById(workflowFormCategoryEntity.getId());
			view.addObject("category", workflowFormCategoryEntity);
		}
		
		return view;
	}
	
	/**
	 * 保存方法
	 * @param workflowCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(WorkflowFormCategoryEntity workflowFormCategoryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(workflowFormCategoryEntity.getName())) {
				j.setSuccess(false);
				j.setMsg("类别名称不能为空!");
				return j;
			}
			
			if(null == workflowFormCategoryEntity.getSort()) {
				j.setSuccess(false);
				j.setMsg("类别排序不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(workflowFormCategoryEntity.getId())) {
				//新增方法
				workflowFormCategoryService.save(workflowFormCategoryEntity);
				
			}else {
				//编辑方法
				WorkflowFormCategoryEntity tmp = workflowFormCategoryService.getById(workflowFormCategoryEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(workflowFormCategoryEntity, tmp);
				workflowFormCategoryService.saveOrUpdate(tmp);
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存表单类别失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(WorkflowFormCategoryEntity workflowFormCategoryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(workflowFormCategoryEntity.getId())) {
				workflowFormCategoryService.removeById(workflowFormCategoryEntity.getId());
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除表单类别失败，错误信息:{}", e);
		}
		return j;
	}
}
