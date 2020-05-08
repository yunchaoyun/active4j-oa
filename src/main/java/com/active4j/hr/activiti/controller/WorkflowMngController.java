package com.active4j.hr.activiti.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.domain.FormJsonData;
import com.active4j.hr.activiti.entity.WorkflowAuthEntity;
import com.active4j.hr.activiti.entity.WorkflowCategoryEntity;
import com.active4j.hr.activiti.entity.WorkflowFormCategoryEntity;
import com.active4j.hr.activiti.entity.WorkflowFormEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowAuthService;
import com.active4j.hr.activiti.service.WorkflowCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormCategoryService;
import com.active4j.hr.activiti.service.WorkflowFormService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title WorkflowMngController.java
 * @description 流程任务
 * @time 2020年4月20日 下午1:44:51
 * @author 麻木神
 * @version 1.0
 */
@Controller
@RequestMapping("wf/flow/mng")
@Slf4j
public class WorkflowMngController extends BaseController {

	@Autowired
	private WorkflowCategoryService workflowCategoryService;

	@Autowired
	private WorkflowMngService workflowMngService;
	
	@Autowired
	private WorkflowFormCategoryService workflowFormCategoryService;
	
	@Autowired
	private WorkflowFormService workflowFormService;
	
	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private WorkflowAuthService workflowAuthService;
	
	
	
	/**
	 * 流程类别列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {

		// 获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		request.setAttribute("categoryReplace", ListUtils.listToReplaceStr(lstCatogorys, "name", "id"));
		
		//表格里表单名称的
		List<WorkflowFormEntity> lstForms = workflowFormService.list();
		request.setAttribute("formReplace", ListUtils.listToReplaceStr(lstForms, "name", "id"));

		return new ModelAndView("activiti/mng/mnglist");
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
	public void datagrid(WorkflowMngEntity workflowMngEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<WorkflowMngEntity> queryWrapper = QueryUtils.installQueryWrapper(workflowMngEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<WorkflowMngEntity> lstResult = workflowMngService.page(new Page<WorkflowMngEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(WorkflowMngEntity workflowMngEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/mng/mng");
		
		//获取流程类别数据
		List<WorkflowCategoryEntity> lstCatogorys = workflowCategoryService.list();
		//列表排序
		Collections.sort(lstCatogorys);
		view.addObject("lstCatogorys", lstCatogorys);
		
		//获取表单类别数据
		List<WorkflowFormCategoryEntity> lstForms = workflowFormCategoryService.list();
		//列表排序
		Collections.sort(lstForms);
		view.addObject("lstForms", lstForms);
		
		//查询系统流程定义
		List<ProcessDefinition> lstProcess = workflowService.findProcessDefineList();
		view.addObject("lstProcess", lstProcess);
		
		Set<String> lstRoleIds = new HashSet<String>();
		Set<String> lstUserIds = new HashSet<String>();
		//实体赋值
		if(StringUtils.isNotEmpty(workflowMngEntity.getId())) {
			WorkflowMngEntity mng = workflowMngService.getById(workflowMngEntity.getId());
			view.addObject("mng", mng);
			
			//流程实例
			if(StringUtils.isNotEmpty(mng.getProcessDefineId())) {
				view.addObject("firstId", mng.getProcessDefineId());
				
				view.addObject("pros", mng.getProcessDefineId());
			}
			
			//权限表的查询
			List<WorkflowAuthEntity> lstAuths = workflowAuthService.findWorkflowAuthByFlowId(mng.getId());
			if(null != lstAuths && lstAuths.size() > 0) {
				for(WorkflowAuthEntity auth : lstAuths) {
					//角色类型
					if(StringUtils.equals("R", auth.getType())) {
						lstRoleIds.add(auth.getURId());
					}else if(StringUtils.equals("U", auth.getType())) {
						lstUserIds.add(auth.getURId());
					}
					
				}
			}
			
			
			//查询按角色分配权限使用的树形结构
			String roleTreeStr = SystemUtils.getRoleTree(lstRoleIds);
			view.addObject("roleTreeStr", roleTreeStr);
			
			//查询按用户分配权限使用的树形结构
			String userTreeStr = SystemUtils.getCompanyOfUser(lstUserIds);
			view.addObject("userTreeStr", userTreeStr);
			
			//表单信息
			WorkflowFormEntity form = workflowFormService.getById(mng.getFormId());
			view.addObject("form", form);
			
			WorkflowFormCategoryEntity formC = workflowFormCategoryService.getById(form.getCategoryId());
			
			view.addObject("formC", formC);
			
			List<WorkflowFormEntity> lstFs = workflowFormService.findWorkflowForm(formC.getId());
			
			view.addObject("lstFs", lstFs);
			
		}else {
			
			//查询按角色分配权限使用的树形结构
			String roleTreeStr = SystemUtils.getRoleTree(lstRoleIds);
			view.addObject("roleTreeStr", roleTreeStr);
			
			//查询按用户分配权限使用的树形结构
			String userTreeStr = SystemUtils.getCompanyOfUser(lstUserIds);
			view.addObject("userTreeStr", userTreeStr);
		}
		
		return view;
	}
	
	@RequestMapping("/getForms") 
	@ResponseBody
	public AjaxJson getForms(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(id)) {
				WorkflowFormCategoryEntity category = workflowFormCategoryService.getById(id);
				if(null != category) {
					List<WorkflowFormEntity> lstForms = workflowFormService.findWorkflowForm(category.getId());
					List<FormJsonData> forms = new ArrayList<FormJsonData>();
					for(WorkflowFormEntity f : lstForms) {
						FormJsonData fjd = new FormJsonData();
						fjd.setId(f.getId());
						fjd.setName(f.getName());
						forms.add(fjd);
						
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("forms", forms);
					j.setAttributes(map);
							
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			j.setMsg("数据获取失败，请联系管理员!");
			j.setSuccess(false);;
		}
		
		return j;
	}
	
	/**
	 * 保存动作
	 * @param request
	 * @return
	 */
	@RequestMapping("/save") 
	public ModelAndView save(String id, String workflowNo, String name, String categoryId, String memo, String isAll, String[] RList, String[] UList, String formId, String processId, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("system/common/success");
		try{
			if(StringUtils.isEmpty(workflowNo)) {
				view = new ModelAndView("system/common/warning");
				view.addObject("message", "流程编号不能为空");
				return view;
			}
			
			if(StringUtils.isEmpty(name)) {
				view = new ModelAndView("system/common/warning");
				view.addObject("message", "流程名称不能为空");
				return view;
			}
			
			if(StringUtils.isEmpty(categoryId)) {
				view = new ModelAndView("system/common/warning");
				view.addObject("message", "流程类别不能为空");
				return view;
			}
			
			if(StringUtils.isEmpty(isAll)) {
				view = new ModelAndView("system/common/warning");
				view.addObject("message", "权限指定类型不能为空");
				return view;
			}
			
			if(StringUtils.isEmpty(processId)) {
				view = new ModelAndView("system/common/warning");
				view.addObject("message", "流程不能为空");
				return view;
			}
			
			if(StringUtils.isEmpty(formId)) {
				view = new ModelAndView("system/common/warning");
				view.addObject("message", "流程关联的表单不能为空");
				return view;
			}
			
			if(StringUtils.isEmpty(id)) {
				//新增保存
				WorkflowMngEntity workflow = new WorkflowMngEntity();
				workflow.setWorkflowNo(workflowNo);
				workflow.setName(name);
				workflow.setMemo(memo);
				//流程关键字段的处理
				String[] ps = processId.split("##");
				//流程定义
				workflow.setProcessDefineId(ps[0]);
				//流程key
				workflow.setProcessKey(ps[1]);
				//部署ID
				workflow.setDeployId(ps[2]);
				//类别处理
				workflow.setCategoryId(categoryId);
				//流程状态  0:正常  1:停用: 2:过期
				workflow.setStatus("0");
				//isAll  0:所有人 1：指定人
				workflow.setType(isAll); //流程权限类型
				//表单处理
				workflow.setFormId(formId);
				//保存
				workflowMngService.saveWorkflowMng(workflow, RList, UList);
				
			}else{
				//编辑保存
				WorkflowMngEntity workflow = workflowMngService.getById(id);
				workflow.setWorkflowNo(workflowNo);
				workflow.setName(name);
				workflow.setMemo(memo);
				//流程关键字段的处理
				String[] ps = processId.split("##");
				//流程定义
				workflow.setProcessDefineId(ps[0]);
				//流程key
				workflow.setProcessKey(ps[1]);
				//部署ID
				workflow.setDeployId(ps[2]);
				//类别处理
				workflow.setCategoryId(categoryId);
				//isAll  0:所有人 1：指定人
				workflow.setType(isAll); //流程权限类型
				
				//表单处理
				workflow.setFormId(formId);
				
				//保存
				workflowMngService.saveOrUpdateWorkflowMng(workflow, RList, UList);
			}
		}catch(Exception e) {
			view = new ModelAndView("system/common/warning");
			view.addObject("message", GlobalConstant.ERROR_MSG);
			log.error("流程管理保存报错，错误信息:{}", e);
		}
		
		return view;
	}
	
	/**
	 * 停用流程
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/lock") 
	@ResponseBody
	public AjaxJson lock(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(id)) {
				WorkflowMngEntity workflow = workflowMngService.getById(id);
				if(StringUtils.equals("2", workflow.getStatus())) {
					j.setSuccess(false);
					j.setMsg("过期流程无法停用!");
					return j;
				}
				workflow.setStatus("1"); //停用流程
				workflowMngService.saveOrUpdate(workflow);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			j.setMsg("流程停用失败，请联系管理员!");
			j.setSuccess(false);;
			log.error("流程停用失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 启用流程
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/unLock") 
	@ResponseBody
	public AjaxJson unLock(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(id)) {
				WorkflowMngEntity workflow = workflowMngService.getById(id);
				if(StringUtils.equals("2", workflow.getStatus())) {
					j.setSuccess(false);
					j.setMsg("过期流程无法启用!");
					return j;
				}
				workflow.setStatus("0"); //启用流程
				workflowMngService.saveOrUpdate(workflow);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			j.setMsg("流程启用失败，请联系管理员!");
			j.setSuccess(false);;
			log.error("流程启用失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 新部署流程，需要刷新，变成最新版本
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/startNew") 
	@ResponseBody
	public AjaxJson startNew(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(id)) {
				WorkflowMngEntity workflow = workflowMngService.getById(id);
				if(!StringUtils.equals("2", workflow.getStatus())) {
					j.setSuccess(false);
					j.setMsg("只有过期流程才可以启用最新!");
					return j;
				}
				//改为正常
				workflow.setStatus("0");
				//查询最新流程
				ProcessDefinition pd = workflowService.findNewestProcessDefine(workflow.getProcessKey());
				workflow.setDeployId(pd.getDeploymentId());
				workflow.setProcessDefineId(pd.getId());
				workflow.setProcessKey(pd.getKey());
				workflowMngService.saveOrUpdate(workflow);
				
			}
		}catch(Exception e) {
			j.setMsg("流程启用最新失败，请联系管理员!");
			j.setSuccess(false);;
			log.error("流程启用最新失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 跳转到查看流程图页面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/viewImage") 
	public ModelAndView viewImage(String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/mng/flowimage");
		
		WorkflowMngEntity flow = workflowMngService.getById(id);
		
		view.addObject("id", flow.getProcessDefineId());
		
		return view;
	}
	
	/**
	 * 删除流程
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/del") 
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(id)) {
				workflowMngService.delete(id);
			}
		}catch(Exception e) {
			e.printStackTrace();
			j.setMsg("流程删除失败，请联系管理员!");
			j.setSuccess(false);;
			log.error("流程删除失败，错误信息:{}", e);
		}
		
		return j;
	}
}
