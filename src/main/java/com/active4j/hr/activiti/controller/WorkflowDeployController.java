package com.active4j.hr.activiti.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * @title WorkflowDeployController.java
 * @description 
		  流程部署
 * @time  2020年4月17日 下午3:04:20
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/wf/flow/deploy")
@Slf4j
public class WorkflowDeployController extends BaseController {

	@Autowired
	private WorkflowService workflowService;
	
	/**
	 * 跳转到工作流部署列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView("activiti/deploy/deploylist");
		
		return view;
	}
	
	/**
	 * 跳转到流程定义列表
	 * @return
	 */
	@RequestMapping("/listDef")
	public ModelAndView listDef() {
		ModelAndView view = new ModelAndView("activiti/define/definelist");
		
		return view;
	}
	
	
	/**
	 * 查询流程列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		IPage<Deployment> lstResult = workflowService.findDeployList(dataGrid);
		
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 删除流程部署
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params="delete") 
	@ResponseBody
	public AjaxJson delete(String id, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			workflowService.deleteDeploy(id);
		
		}catch(Exception e) {
			j.setMsg(GlobalConstant.ERROR_MSG);
			j.setSuccess(false);
			log.error("删除流程部署报错，错误信息：", e);
		}
		
		return j;
	}
	
	/**
	 * 新增流程部署
	 * @param file
	 * @param name
	 * @param category
	 * @param request
	 * @return
	 */
	@RequestMapping("/deploy") 
	@ResponseBody
	public AjaxJson newDeploy(@RequestParam(value = "file") MultipartFile file, String name, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(null == file) {
				j.setSuccess(false);
				j.setMsg("上传文件不能为空!");
				return j;
			}
			 
			if(StringUtils.isEmpty(name)) {
				j.setSuccess(false);
				j.setMsg("流程名称不能为空!");
				return j;
			}
			
			if(!StringUtils.endsWith(file.getOriginalFilename(), ".zip")) {
				j.setSuccess(false);
				j.setMsg("部署流程文件必须是zip文件!");
				return j;
			}
			
			workflowService.saveNewDeploy(name, file.getInputStream());
			
			j.setSuccess(true);
			j.setMsg("部署成功");
			
		}catch(Exception e) {
			j.setMsg("上传失败，请联系管理员");
			j.setSuccess(false);
			log.error("部署流程报错，错误信息：{}", e);
		}
		return j;
	}
	
	
	/**
	 * 查询流程定义列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagridDef")
	public void datagridDef(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//流程名称
		String name = request.getParameter("name");
		//是否显示最新版本
		String isLastest = request.getParameter("isLastest");
		Boolean lastest = false;
		if(StringUtils.isNotEmpty(isLastest)) {
			lastest = Boolean.valueOf(isLastest);
		}
		
		IPage<ProcessDefinition> lstResult = workflowService.findProcessListByName(name, lastest, dataGrid);
		
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到查看流程图页面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/viewImage") 
	public ModelAndView viewImage(String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("activiti/define/flowimage");
		
		view.addObject("id", id);
		
		return view;
	}
	
	/**
	 * 查看流程图
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/image") 
	public void image(String id, HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isNotEmpty(id)) {
			//获取流程图文件流
			InputStream in = workflowService.findWorkflowImage(id);
			try {
				OutputStream out = response.getOutputStream();
				for(int b=-1;(b=in.read())!=-1;){
					out.write(b);
				}
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/imageProcess") 
	public void imageProcess(String id, HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isNoneBlank(id)) {
			//获取流程图文件流
			InputStream in = workflowService.findImageProcess(id);
			try {
				byte[] b = new byte[1024];
				int len;
				while ((len = in.read(b, 0, 1024)) != -1) {
					response.getOutputStream().write(b, 0, len);
				}
				response.getOutputStream().flush();
				response.getOutputStream().close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 删除流程部署
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteDef") 
	@ResponseBody
	public AjaxJson deleteDef(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			workflowService.deleteDefine(id);
		}catch(Exception e) {
			j.setMsg(GlobalConstant.ERROR_MSG);
			j.setSuccess(false);
			log.error("删除流程定义，错误信息：{}", e);
		}
		return j;
	}
	
	/**
	 * 删除流程部署  -- 包括所有实例，历史记录
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteAll") 
	@ResponseBody
	public AjaxJson deleteAll(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			workflowService.deleteAll(id);
		
		}catch(Exception e) {
			j.setMsg(GlobalConstant.ERROR_MSG);
			j.setSuccess(false);
			log.error("彻底删除流程定义，错误信息：{}", e);
		}
		
		return j;
	}
	
}
