package com.active4j.hr.activiti.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.service.SysUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @title WorkflowCenterController.java
 * @description 
		  工作流中心
 * @time  2020年4月21日 下午3:09:29
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/wf/flow/center")
@Slf4j
public class WorkflowCenterController extends BaseController {

	@Autowired
	private WorkflowMngService workflowMngService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private WorkflowCategoryService workflowCategoryService;
	
	@Autowired
	private WorkflowFormService workflowFormService;
	
	/**
	 * 流程中心
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/center/center");
		
		//返回页面map  有分类，有list，所有采用Map类型  使用TreeMap排序
		Map<WorkflowCategoryEntity, List<WorkflowMngEntity>> mapResult = new TreeMap<WorkflowCategoryEntity, List<WorkflowMngEntity>>();
		
		//当前用户ID
		String userId = ShiroUtils.getSessionUserId();
		
		List<SysRoleEntity> lstRoles = sysUserService.getUserRoleByUserId(userId);
		
		List<String> roleIds = new ArrayList<String>();
		if(null != lstRoles) {
			roleIds = lstRoles.stream().map(d -> d.getId()).collect(Collectors.toList());
		}
		
		List<WorkflowMngEntity> lstWorkflows = workflowMngService.findWorkflowMngByUserIdAndRoleIds(userId, roleIds);
		//设置分组
		mapResult = setGroupOfWorkflow(lstWorkflows);
		
		view.addObject("mapResult", mapResult);
		
		return view;
	}
	
	private Map<WorkflowCategoryEntity, List<WorkflowMngEntity>> setGroupOfWorkflow(List<WorkflowMngEntity> lstWorks) {
		Map<WorkflowCategoryEntity, List<WorkflowMngEntity>> mapResult = new TreeMap<WorkflowCategoryEntity, List<WorkflowMngEntity>>();
		//按类别分组
		if(null != lstWorks && lstWorks.size() > 0) {
			for(WorkflowMngEntity wf : lstWorks) {
				if(null != mapResult.get(workflowCategoryService.getById(wf.getCategoryId()))) {
					List<WorkflowMngEntity> lst = mapResult.get(workflowCategoryService.getById(wf.getCategoryId()));
					lst.add(wf);
					mapResult.put(workflowCategoryService.getById(wf.getCategoryId()), lst);
				}else {
					List<WorkflowMngEntity> lst = new ArrayList<WorkflowMngEntity>();
					lst.add(wf);
					mapResult.put(workflowCategoryService.getById(wf.getCategoryId()), lst);
				}
			}
		}
		
		return mapResult;
	}
	
	/**
	 * 跳转到表单页面，配置过的
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/go")
	public ModelAndView go(String id, String workflowId, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("");
		if(StringUtils.isEmpty(id)) {
			view = new ModelAndView("system/common/warning");
			view.addObject("message", "系统不存在当前表单");
			return view;
		}
		
		WorkflowFormEntity form = workflowFormService.getById(id);
		if(null == form) {
			view = new ModelAndView("system/common/warning");
			view.addObject("message", "系统不存在当前表单");
			return view;
		}
		
		//系统表单
		if(StringUtils.equals("0", form.getType())) {
			view = new ModelAndView("redirect:" + form.getPath() + "?formId=" + id + "&workflowId=" + workflowId);
			return view;
		}
		
		return view;
		
	}
}
