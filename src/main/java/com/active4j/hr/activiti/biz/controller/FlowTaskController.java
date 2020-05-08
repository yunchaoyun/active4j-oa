package com.active4j.hr.activiti.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title FlowTaskController.java
 * @description 
		  流程的审批
 * @time  2020年4月24日 下午2:16:13
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("flow/biz/task")
@Slf4j
public class FlowTaskController extends BaseController {

	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private WorkflowBaseService workflowBaseService;
	
	@Autowired
	private WorkflowCategoryService workflowCategoryService;
	
	@Autowired
	private WorkflowMngService workflowMngService;
	
	@Autowired
	private WorkflowFormService workflowFormService;
	
	/**
	 * 跳转到待审批流程页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("flow/task/waittasklist");
		
		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
		
		return view;
	}
	
	/**
	 * 跳转到已审批流程页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/finishlist")
	public ModelAndView finishlist(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("flow/task/finishtasklist");
		
		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
				
		return view;
	}
	
	/**
	 * 跳转到承接审批流程
	 * @param req
	 * @return
	 */
	@RequestMapping("/groupwaittasklist")
	public ModelAndView groupwaittasklist(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("flow/task/groupwaittasklist");
		
		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		view.addObject("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
		
		return view;
	}
	
	/**
	 * 查询数据  -- 我的待办审批
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String startTime = request.getParameter("applyDate_begin");
		String endTime = request.getParameter("applyDate_end");
		
		// 执行查询
		IPage<WorkflowBaseEntity> lstResult = workflowService.findTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 查询数据  -- 我的已办审批
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagridFinish")
	public void datagridFinish(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String startTime = request.getParameter("applyDate_begin");
		String endTime = request.getParameter("applyDate_end");
		// 执行查询
		IPage<WorkflowBaseEntity> lstResult = workflowService.findFinishedTaskByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()), workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName(), WorkflowConstant.Task_Category_approval);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	
	/**
	 * 查询数据  -- 我的待办审批  组任务
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagridGroup")
	public void datagridGroup(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String startTime = request.getParameter("applyDate_begin");
		String endTime = request.getParameter("applyDate_end");
		
		IPage<WorkflowBaseEntity> lstResult = workflowService.findGroupTaskStrsByUserName(new Page<WorkflowBaseEntity>(dataGrid.getPage(), dataGrid.getRows()),workflowBaseEntity, startTime, endTime, ShiroUtils.getSessionUserName());
	
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	
	/**
	 * 直接办理任务
	 * @param baseActivitiEntity
	 * @param request
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping("/approve")
	public ModelAndView approve(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) throws ClassNotFoundException {
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
					view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=2" + "&workflowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
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
	@RequestMapping("/viewApprove")
	public ModelAndView viewApprove(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) throws ClassNotFoundException {
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
					view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=3" + "&workflowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
					return view;
				}
			}
		}
		
		
		return view;
	}
	
	/**
	 * 查看详情  -- 组任务 
	 * @param baseActivitiEntity
	 * @param request
	 * @return
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping("/viewDetail")
	public ModelAndView viewDetail(WorkflowBaseEntity workflowBaseEntity, HttpServletRequest request) throws ClassNotFoundException {
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
					view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=1" + "&workflowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
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
					view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + form.getId() + "&type=1" + "&workflowId=" + workflow.getId() + "&id=" + workflowBaseEntity.getId());
					return view;
				}
			}
		}
		
		
		return view;
	}
	
	
	/**
	 * 根据任务ID，获取连线
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/getTransByTaskId")
	@ResponseBody
	public AjaxJson getTransByTaskId(String taskId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			int count = workflowService.findTaskOutgoByTaskId(taskId);
			map.put("count", count);
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("获取任务连线失败，错误信息{}", e);
		}
		
		return j;
	}
	
	/**
	 * 回退承接审批
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/setApproves")
	@ResponseBody
	public AjaxJson setApproves(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(id)) {
				boolean flag = workflowService.saveBackClaimGroupTaskByBusinessKey(id, ShiroUtils.getSessionUserName());
				if(!flag) {
					j.setSuccess(false);
					j.setMsg("当前任务不是组任务，无法回退");
				}
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("回退承接的审批任务失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 办理承接审批
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/getApproves")
	@ResponseBody
	public AjaxJson getApproves(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(id)) {
				workflowService.saveClaimGroupTaskByBusinessKey(id, ShiroUtils.getSessionUserName());
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("承接审批任务保存失败，错误信息:{}", e);
		}
		
		return j;
	}
}
