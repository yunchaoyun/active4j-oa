package com.active4j.hr.activiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
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
 * 流程类别
 * 
 * @author chenxl
 *
 */
@Controller
@RequestMapping("/wf/flow/category")
@Slf4j
public class WorkflowCategoryController extends BaseController {

	@Autowired
	private WorkflowCategoryService workflowCategoryService;

	/**
	 * 流程类别列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("activiti/category/categorylist");
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
	public void datagrid(WorkflowCategoryEntity workflowCategoryEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowCategoryEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowCategoryEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<WorkflowCategoryEntity> lstResult = workflowCategoryService.page(new Page<WorkflowCategoryEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}

	/**
	 * 跳转到新增编辑页面
	 * 
	 * @param workflowCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(WorkflowCategoryEntity workflowCategoryEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/category/category");

		if (StringUtils.isNotEmpty(workflowCategoryEntity.getId())) {
			
			workflowCategoryEntity = workflowCategoryService.getById(workflowCategoryEntity.getId());
			view.addObject("category", workflowCategoryEntity);
		}

		return view;
	}

	/**
	 * 保存方法
	 * 
	 * @param workflowCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(WorkflowCategoryEntity workflowCategoryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			if (StringUtils.isEmpty(workflowCategoryEntity.getName())) {
				j.setSuccess(false);
				j.setMsg("类别名称不能为空!");
				return j;
			}
			if (null == workflowCategoryEntity.getSort()) {
				j.setSuccess(false);
				j.setMsg("类别排序不能为空!");
				return j;
			}

			if (StringUtils.isEmpty(workflowCategoryEntity.getId())) {
				// 新增方法
				workflowCategoryService.save(workflowCategoryEntity);
			} else {
				// 编辑方法
				WorkflowCategoryEntity tmp = workflowCategoryService.getById(workflowCategoryEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(workflowCategoryEntity, tmp);
				workflowCategoryService.saveOrUpdate(tmp);
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存流程类别失败，错误信息:{}",e);
		}

		return j;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(WorkflowCategoryEntity workflowCategoryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			if (StringUtils.isNotEmpty(workflowCategoryEntity.getId())) {
				workflowCategoryService.removeById(workflowCategoryEntity.getId());
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除流程类别失败，错误信息:{}",e);
		}

		return j;
	}
}
