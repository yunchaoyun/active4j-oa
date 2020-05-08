package com.active4j.hr.activiti.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.entity.WorkflowFormCategoryEntity;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.service.WorkflowFormCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title WorkflowFormController.java
 * @description 表单
 * @time 2020年4月18日 下午10:18:12
 * @author 麻木神
 * @version 1.0
 */
@Controller
@RequestMapping("/wf/form/form")
@Slf4j
public class WorkflowFormController extends BaseController {

	@Autowired
	private WorkflowFormCategoryService workflowFormCategoryService;
	
	@Autowired
	private WorkflowFormService workflowFormService;

	/**
	 * 表单列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {

		// 获取流程类别数据
		List<WorkflowFormCategoryEntity> lstCatogorys = workflowFormCategoryService.list();

		request.setAttribute("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));

		return new ModelAndView("activiti/form/form/formlist");
	}

	/**
	 * 查询数据
	 * 
	 * @param workflowFormEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(WorkflowFormEntity workflowFormEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowFormEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowFormEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<WorkflowFormEntity> lstResult = workflowFormService.page(new Page<WorkflowFormEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param formCategoryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(WorkflowFormEntity workflowFormEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/form/form/form");
		
		//获取表单类别数据
		List<WorkflowFormCategoryEntity> lstCatogorys = workflowFormCategoryService.list();
		//列表排序
		Collections.sort(lstCatogorys);
		view.addObject("lstCatogorys", lstCatogorys);
		
		if(StringUtils.isNotEmpty(workflowFormEntity.getId())) {
			workflowFormEntity = workflowFormService.getById(workflowFormEntity.getId());
			request.setAttribute("form", workflowFormEntity);
		}
		
		return view;
	}
	
	/**
	 * 保存动作
	 * @param formEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(WorkflowFormEntity WorkflowFormEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(WorkflowFormEntity.getName())) {
				j.setSuccess(false);
				j.setMsg("表单名称不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(WorkflowFormEntity.getCode())) {
				j.setSuccess(false);
				j.setMsg("表单标识不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(WorkflowFormEntity.getType())) {
				j.setSuccess(false);
				j.setMsg("表单类型不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(WorkflowFormEntity.getCategoryId())) {
				j.setSuccess(false);
				j.setMsg("表单类别不能为空");
				return j;
			}
			
			if(StringUtils.equals(WorkflowFormEntity.getType(), "0") && StringUtils.isEmpty(WorkflowFormEntity.getPath())) {
				j.setSuccess(false);
				j.setMsg("系统表单，路径配置不能为空");
				return j;
			}
			
			
			if(StringUtils.isEmpty(WorkflowFormEntity.getId())) {
				workflowFormService.save(WorkflowFormEntity);
			}else{
				//编辑保存
				WorkflowFormEntity tmp = workflowFormService.getById(WorkflowFormEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(WorkflowFormEntity, tmp);
				workflowFormService.saveOrUpdate(tmp);
			}
		}catch(Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("表单保存报错，错误信息:{}", e);
		}
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(WorkflowFormEntity workflowFormEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(workflowFormEntity.getId())) {
				workflowFormService.removeById(workflowFormEntity.getId());
			}
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("表单删除报错，错误信息:{}", e);
		}
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转表单构建页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月26日 上午10:04:19
	 */
	@RequestMapping("/builder")
	public ModelAndView builder() {
		ModelAndView view = new ModelAndView("activiti/form/form/builder");
		
		return view;
	}
}
