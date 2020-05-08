package com.active4j.hr.activiti.biz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title FlowBaseController.java
 * @description 
		  流程办理中心
 * @time  2020年4月22日 下午8:12:09
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("flow/biz/my")
@Slf4j
public class FlowBaseController extends BaseController {

	@Autowired
	private WorkflowBaseService workflowBaseService;
	
	@Autowired
	private WorkflowCategoryService workflowCategoryService;
	
	@Autowired
	private WorkflowMngService workflowMngService;
	
	@Autowired
	private WorkflowFormService workflowFormService;
	
	@Autowired
	private WorkflowService workflowService;
	
	/**
	 * 跳转到我的流程草稿页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("flow/my/mydraftlist");
		
		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
		
		return view;
	}
	
	/**
	 * 跳转到我的申请流程页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/listApply")
	public ModelAndView listApply(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("flow/my/myapplylist");
		
		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
		
		return view;
	}
	
	/**
	 * 跳转到我的申请流程页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/listFinish")
	public ModelAndView listFinish(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("flow/my/myfinishlist");
		
		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
		
		return view;
	}
	
	
	/**
	 * 查询数据  -- 我的草稿
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowBaseEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowBaseEntity, request.getParameterMap(), dataGrid);
		queryWrapper.eq("STATUS", "0"); //草稿
		queryWrapper.eq("USER_NAME", ShiroUtils.getSessionUserName()); //当前用户
		// 执行查询
		IPage<WorkflowBaseEntity> lstResult = workflowBaseService.page(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 查询数据  -- 我的申请
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagridApply")
	public void datagridApply(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowBaseEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowBaseEntity, request.getParameterMap(), dataGrid);
		queryWrapper.in("STATUS", new Object[] {"1", "2", "5"}); 
		queryWrapper.eq("USER_NAME", ShiroUtils.getSessionUserName()); //当前用户
		// 执行查询
		IPage<WorkflowBaseEntity> lstResult = workflowBaseService.page(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
		
	}
	
	/**
	 * 查询数据  -- 我的办结
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagridFinish")
	public void datagridFinish(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowBaseEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowBaseEntity, request.getParameterMap(), dataGrid);
		queryWrapper.in("STATUS", new Object[] {"3", "4"}); 
		queryWrapper.eq("USER_NAME", ShiroUtils.getSessionUserName()); //当前用户
		// 执行查询
		IPage<WorkflowBaseEntity> lstResult = workflowBaseService.page(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

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
	public ModelAndView addorupdate(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("");
		
		if(StringUtils.isNotEmpty(workflowBaseEntity.getId())) {
			workflowBaseEntity = workflowBaseService.getById(workflowBaseEntity.getId());
			
			//根据主表中的流程ID，查询流程中心的流程配置
			WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
			
			if(null != workflow) {
				//查询表单
				WorkflowFormEntity form = workflowFormService.getById(workflow.getFormId());
				//系统表单
				if(StringUtils.equals("0", form.getType())) {
					view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=0" + "&workflowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
					return view;
				}
			}
				
		}
		return view;
	}
	
	/**
	 * 查看详情
	 * @param baseActivitiEntity
	 * @param request
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping("/view")
	public ModelAndView view(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) throws ClassNotFoundException {
		ModelAndView view = new ModelAndView("");
		
		if(StringUtils.isNotEmpty(workflowBaseEntity.getId())) {
			workflowBaseEntity = workflowBaseService.getById(workflowBaseEntity.getId());
			
			//根据主表中的流程ID，查询流程中心的流程配置
			WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
			
			
			if(null != workflow) {
				//查询表单
				WorkflowFormEntity form = workflowFormService.getById(workflow.getFormId());
				//系统表单
				if(StringUtils.equals("0", form.getType())) {
					if(StringUtils.equals(workflowBaseEntity.getStatus(), "5")) {
						view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=5" + "&flowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
					}else {
						view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=1" + "&flowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
					}
					return view;
				}
			}
		}
		
		return view;
	}
	
	/**
	 * 查看详情
	 * @param baseActivitiEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/viewImage")
	public ModelAndView viewImage(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("flow/my/flowimage");
		
		view.addObject("businessKey", workflowBaseEntity.getId());
		
		return view;
	}
	
	/**
	 * 跳转到流程删除页面
	 * @param baseActivitiEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/goDelete")
	public ModelAndView goDelete(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("flow/my/delete");
		
		view.addObject("baseId", workflowBaseEntity.getId());
		
		return view;
	}
	
	/**
	 * 删除流程实例
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(WorkflowBaseEntity workflowBaseEntity, String reason, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(reason)){
				j.setSuccess(false);
				j.setMsg("删除理由不能为空!");
				return j;
			}
			
			if(StringUtils.isNotEmpty(workflowBaseEntity.getId())) {
				workflowService.deleteProcessInstranceByBusinessKey(workflowBaseEntity.getId(), reason);
				
				workflowBaseEntity = workflowBaseService.getById(workflowBaseEntity.getId());
				
				//根据主表中的流程ID，查询流程中心的流程配置
				WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
				
				if(null != workflow) {
					//查询表单
					WorkflowFormEntity form = workflowFormService.getById(workflow.getFormId());
					//系统表单
					if(StringUtils.equals("0", form.getType())) {
						String url = StringUtils.substringBefore(form.getPath(), "go") + "del?businessId=" + workflowBaseEntity.getBusinessId();
						j.setObj(StringUtils.substring(url, 1));
					}
				}
				
				workflowBaseService.removeById(workflowBaseEntity.getId());
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除流程实例失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 删除草稿流程
	 * @param workflowBaseEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(workflowBaseEntity.getId())){
				
				workflowBaseEntity = workflowBaseService.getById(workflowBaseEntity.getId());
				
				//根据主表中的流程ID，查询流程中心的流程配置
				WorkflowMngEntity workflow = workflowMngService.getById(workflowBaseEntity.getWorkflowId());
				
				if(null != workflow) {
					//查询表单
					WorkflowFormEntity form = workflowFormService.getById(workflow.getFormId());
					//系统表单
					if(StringUtils.equals("0", form.getType())) {
						String url = StringUtils.substringBefore(form.getPath(), "go") + "del?businessId=" + workflowBaseEntity.getBusinessId();
						j.setObj(StringUtils.substring(url, 1));
					}
				}
				
				workflowBaseService.removeById(workflowBaseEntity.getId());
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除流程草稿失败，错误信息:{}", e);
		}
		
		return j;
	}
}
