package com.active4j.hr.work.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.work.domain.OaWorkTaskStatusDomain;
import com.active4j.hr.work.entity.OaWorkTaskCommentsEntity;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.entity.OaWorkTaskExcuteEntity;
import com.active4j.hr.work.service.OaWorkTaskCommentsService;
import com.active4j.hr.work.service.OaWorkTaskExcuteService;
import com.active4j.hr.work.service.OaWorkTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkTaskController.java
 * @description 
		  工作任务
 * @time  2020年4月7日 下午3:40:07
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/task")
@Slf4j
public class OaWorkTaskController extends BaseController {

	@Autowired
	private OaWorkTaskService oaWorkTaskService;
	@Autowired
	private OaWorkTaskCommentsService oaWorkTaskCommentsService;
	@Autowired
	private OaWorkTaskExcuteService oaWorkTaskExcuteService;
	
	/**
	 * 我的任务
	 * @return
	 */
	@RequestMapping("/mytasklist")
	public ModelAndView mytasklist(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/mytasklist");
		
		String userId = ShiroUtils.getSessionUserId();
		List<OaWorkTaskStatusDomain> lstStatus = oaWorkTaskService.queryOaWorkTaskStatusStat(userId);
		List<OaWorkTaskStatusDomain> lstData = new ArrayList<OaWorkTaskStatusDomain>();
		int total = 0;
		//名称的赋值
		if(null != lstStatus && lstStatus.size() > 0) {
			for(OaWorkTaskStatusDomain status : lstStatus) {
				OaWorkTaskStatusDomain domain = new OaWorkTaskStatusDomain();
				domain.setValue(status.getValue());
				domain.setNum(status.getNum());
				if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NEW)) {
					domain.setName("新建");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_DOING)){
					domain.setName("进行中");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_FINISH)){
					domain.setName("完成");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NO)){
					domain.setName("取消");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_GIVEUP)){
					domain.setName("放弃");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_EX)){
					domain.setName("超期");
				}
				lstData.add(domain);
				Integer in = Integer.parseInt(status.getNum());
				total += in;
			}
		}
		
		checkAllStatus(lstData);
		
		view.addObject("lstData", lstData);
		view.addObject("totalNum", total);
		return view;
	}
	
	/**
	 * 我创建的任务
	 * @return
	 */
	@RequestMapping("/mycreatetasklist")
	public ModelAndView mycreatetasklist(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/mycreatetasklist");
		
		String userId = ShiroUtils.getSessionUserId();
		List<OaWorkTaskStatusDomain> lstStatus = oaWorkTaskService.queryOaWorkTaskStatusStat(userId);
		List<OaWorkTaskStatusDomain> lstData = new ArrayList<OaWorkTaskStatusDomain>();
		int total = 0;
		//名称的赋值
		if(null != lstStatus && lstStatus.size() > 0) {
			for(OaWorkTaskStatusDomain status : lstStatus) {
				OaWorkTaskStatusDomain domain = new OaWorkTaskStatusDomain();
				domain.setValue(status.getValue());
				domain.setNum(status.getNum());
				if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NEW)) {
					domain.setName("新建");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_DOING)){
					domain.setName("进行中");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_FINISH)){
					domain.setName("完成");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NO)){
					domain.setName("取消");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_GIVEUP)){
					domain.setName("放弃");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_EX)){
					domain.setName("超期");
				}
				lstData.add(domain);
				Integer in = Integer.parseInt(status.getNum());
				total += in;
			}
		}
		
		checkAllStatus(lstData);
		
		view.addObject("lstData", lstData);
		view.addObject("totalNum", total);
		return view;
	}
	
	
	/**
	 * 我参与的任务
	 * @return
	 */
	@RequestMapping("/mypartintasklist")
	public ModelAndView mypartintasklist(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/mypartintasklist");
		
		String userId = ShiroUtils.getSessionUserId();
		List<OaWorkTaskStatusDomain> lstStatus = oaWorkTaskService.queryOaWorkTaskStatusStat(userId);
		List<OaWorkTaskStatusDomain> lstData = new ArrayList<OaWorkTaskStatusDomain>();
		int total = 0;
		//名称的赋值
		if(null != lstStatus && lstStatus.size() > 0) {
			for(OaWorkTaskStatusDomain status : lstStatus) {
				OaWorkTaskStatusDomain domain = new OaWorkTaskStatusDomain();
				domain.setValue(status.getValue());
				domain.setNum(status.getNum());
				if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NEW)) {
					domain.setName("新建");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_DOING)){
					domain.setName("进行中");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_FINISH)){
					domain.setName("完成");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NO)){
					domain.setName("取消");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_GIVEUP)){
					domain.setName("放弃");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_EX)){
					domain.setName("超期");
				}
				lstData.add(domain);
				Integer in = Integer.parseInt(status.getNum());
				total += in;
			}
		}
		
		checkAllStatus(lstData);
		
		view.addObject("lstData", lstData);
		view.addObject("totalNum", total);
		return view;
	}
	
	/**
	 * 我监控的任务
	 * @return
	 */
	@RequestMapping("/mymonitorlist")
	public ModelAndView mymonitorlist(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/mymonitortasklist");
		
		String userId = ShiroUtils.getSessionUserId();
		List<OaWorkTaskStatusDomain> lstStatus = oaWorkTaskService.queryOaWorkTaskStatusStat(userId);
		List<OaWorkTaskStatusDomain> lstData = new ArrayList<OaWorkTaskStatusDomain>();
		int total = 0;
		//名称的赋值
		if(null != lstStatus && lstStatus.size() > 0) {
			for(OaWorkTaskStatusDomain status : lstStatus) {
				OaWorkTaskStatusDomain domain = new OaWorkTaskStatusDomain();
				domain.setValue(status.getValue());
				domain.setNum(status.getNum());
				if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NEW)) {
					domain.setName("新建");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_DOING)){
					domain.setName("进行中");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_FINISH)){
					domain.setName("完成");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_NO)){
					domain.setName("取消");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_GIVEUP)){
					domain.setName("放弃");
				}else if(StringUtils.equals(status.getValue(), GlobalConstant.OA_WORK_TASK_STATUS_EX)){
					domain.setName("超期");
				}
				lstData.add(domain);
				Integer in = Integer.parseInt(status.getNum());
				total += in;
			}
		}
		
		checkAllStatus(lstData);
		
		view.addObject("lstData", lstData);
		view.addObject("totalNum", total);
		return view;
	}
	
	/**
	 * 查询数据  个人工作任务
	 * @param oaWorkTaskEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/mytaskdatagrid")
	public void mytaskdatagrid(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkTaskEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTaskEntity, request.getParameterMap(), dataGrid);
		queryWrapper.eq("USER_ID", ShiroUtils.getSessionUserId());
		// 执行查询
		IPage<OaWorkTaskEntity> lstResult = oaWorkTaskService.page(new Page<OaWorkTaskEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
	
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 查询数据  个人工作任务
	 * @param oaWorkTaskEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/mycreatetaskdatagrid")
	public void mycreatetaskdatagrid(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkTaskEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTaskEntity, request.getParameterMap(), dataGrid);
		queryWrapper.eq("APPOINT_USER_ID", ShiroUtils.getSessionUserId());
		// 执行查询
		IPage<OaWorkTaskEntity> lstResult = oaWorkTaskService.page(new Page<OaWorkTaskEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
	
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 查询数据  个人工作任务
	 * @param oaWorkTaskEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/mypartintaskdatagrid")
	public void mypartintaskdatagrid(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkTaskEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTaskEntity, request.getParameterMap(), dataGrid);
		queryWrapper.like("USER_ID", ShiroUtils.getSessionUserId());
		// 执行查询
		IPage<OaWorkTaskEntity> lstResult = oaWorkTaskService.page(new Page<OaWorkTaskEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
	
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 查询数据  个人工作任务
	 * @param oaWorkTaskEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/mymonitortaskdatagrid")
	public void mymonitortaskdatagrid(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkTaskEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTaskEntity, request.getParameterMap(), dataGrid);
		queryWrapper.eq("MONITOR_USER_ID", ShiroUtils.getSessionUserId());
		// 执行查询
		IPage<OaWorkTaskEntity> lstResult = oaWorkTaskService.page(new Page<OaWorkTaskEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
	
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面  -- 任务
	 * @param oaWorkTaskEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/task");
		
		String userId = ShiroUtils.getSessionUserId();
		String userName = ShiroUtils.getSessionUser().getRealName();
		view.addObject("appointUserId", userId);
		view.addObject("appointUserName", userName);
		view.addObject("userId", userId);
		view.addObject("userName", userName);
		view.addObject("monitorUserId", userId);
		view.addObject("monitorUserName", userName);
		
		//上级任务
		List<OaWorkTaskEntity> lstTasks = oaWorkTaskService.findOaWorkTaskByUserId(userId);
		view.addObject("lstTasks", lstTasks);
		
		if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())) {
			oaWorkTaskEntity = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
			view.addObject("task", oaWorkTaskEntity);
			
			view.addObject("appointUserId", oaWorkTaskEntity.getAppointUserId());
			view.addObject("appointUserName", oaWorkTaskEntity.getAppointUserName());
			view.addObject("userId", oaWorkTaskEntity.getUserId());
			view.addObject("userName", oaWorkTaskEntity.getUserName());
			view.addObject("monitorUserId", oaWorkTaskEntity.getMonitorUserId());
			view.addObject("monitorUserName", oaWorkTaskEntity.getMonitorUserName());
			
			if(null != lstTasks && lstTasks.size() > 0) {
				Iterator<OaWorkTaskEntity> itLst = lstTasks.iterator();
				while(itLst.hasNext()) {
					OaWorkTaskEntity t = itLst.next();
					if(StringUtils.equals(oaWorkTaskEntity.getId(), t.getId())) {
						itLst.remove();
					}
				}
			}
		}
		
		return view;
	}
	
	/**
	 * 保存方法
	 * @param oaWorkPlanEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(oaWorkTaskEntity.getTitle())){
				j.setMsg("任务标题不能为空");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkTaskEntity.getContent())) {
				j.setMsg("任务内容不能为空");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkTaskEntity.getAppointUserId())) {
				j.setMsg("任务分配人不能为空");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkTaskEntity.getMonitorUserId())) {
				j.setMsg("任务监控人不能为空");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkTaskEntity.getUserId())) {
				j.setMsg("任务责任人不能为空");
				j.setSuccess(false);
				return j;
			}
			
			if(null == oaWorkTaskEntity.getStartTime() || null == oaWorkTaskEntity.getEndTime()) {
				j.setMsg("任务时间不能为空");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkTaskEntity.getId())) {
				oaWorkTaskEntity.setStatus(GlobalConstant.OA_WORK_TASK_STATUS_NEW); //状态-新建
				oaWorkTaskEntity.setProgress(0);
				
				oaWorkTaskService.save(oaWorkTaskEntity);
				
			}else{
				
				OaWorkTaskEntity tmp = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
				if(!StringUtils.equals(tmp.getStatus(), GlobalConstant.OA_WORK_TASK_STATUS_NEW)) {
					j.setSuccess(false);
					j.setMsg("当前任务状态不支持修改");
					return j;
				}
				if(!StringUtils.equals(tmp.getAppointUserId(), ShiroUtils.getSessionUserId())) {
					j.setSuccess(false);
					j.setMsg("不是本人分配的任务不支持修改");
					return j;
				}
				
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkTaskEntity, tmp);
				
				
				oaWorkTaskService.saveOrUpdate(tmp);
				
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("任务保存失败，错误信息:{}",e);
		}
		return j;
	}
	
	/**
	 * 查看执行
	 * @param oaWorkPlanEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/view");
		
		if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())) {
			oaWorkTaskEntity = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
			view.addObject("task", oaWorkTaskEntity);
		}
		
		return view;
	}
	
	/**
	 * 获取任务数据，树形视图
	 * @param req
	 * @return
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public AjaxJson getTarget(String id, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		
		OaWorkTaskEntity task = oaWorkTaskService.getById(id);
		
		//先查询顶级任务
		while(StringUtils.isNotEmpty(task.getParentTaskId())) {
			task = oaWorkTaskService.getById(task.getParentTaskId());
		}
		
		List<OaWorkTaskEntity> lstTasks = new ArrayList<OaWorkTaskEntity>();
		lstTasks.add(task);
		
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		taskContact(lstTasks, sb, id);
		sb = sb.append("]");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", sb.toString());
		j.setAttributes(map);
		
		return j;
	}
	
	/**
	 * 获取任务
	 * @param req
	 * @return
	 */
	@RequestMapping("/getTask")
	@ResponseBody
	public AjaxJson getTask(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		OaWorkTaskEntity task = oaWorkTaskService.getById(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("task", task);
		j.setAttributes(map);
		
		
		return j;
	}
	
	/**
	 * 任务状态的修改
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/doChange")
	@ResponseBody
	public AjaxJson doChange(OaWorkTaskEntity oaWorkTaskEntity, String status, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())) {
				OaWorkTaskEntity tmp = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
				tmp.setStatus(status);
				oaWorkTaskService.saveOrUpdate(tmp, status);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("修改任务状态报错，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 跳转到添加回复内容
	 * @param oaWorkTaskEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addRecord")
	public ModelAndView addRecord(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/commentsadd");
		
		if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())){
			oaWorkTaskEntity = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
			view.addObject("task", oaWorkTaskEntity);
		}
		
		return view;
	}
	
	/**
	 * 保存任务回复
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveComments")
	@ResponseBody
	public AjaxJson saveComments(String content, String taskId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(content)){
				j.setMsg("回复内容不能为空");
				j.setSuccess(false);
				return j;
			}
			OaWorkTaskCommentsEntity oaWorkTaskComments = new OaWorkTaskCommentsEntity();
			oaWorkTaskComments.setContent(content);
			oaWorkTaskComments.setReplyDate(DateUtils.getDate());
			oaWorkTaskComments.setOaWorkTaskId(taskId);
			ActiveUser user = ShiroUtils.getSessionUser();
			oaWorkTaskComments.setUserHeadImg(user.getHeadImgUrl());
			oaWorkTaskComments.setUserId(user.getId());
			oaWorkTaskComments.setUserName(user.getRealName());
			
			oaWorkTaskCommentsService.save(oaWorkTaskComments);
			
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存任务评价报错，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 获取任务回复内容
	 * @param oaWorkTaskEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getComments")
	@ResponseBody
	public AjaxJson getComments(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())) {
				
				List<OaWorkTaskCommentsEntity> lstExcutes = oaWorkTaskService.findOaWorkTaskComments(oaWorkTaskEntity);
				
				//排序
				Collections.sort(lstExcutes);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lstExcutes", lstExcutes);
				
				j.setAttributes(map);
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("获取任务评价报错，错误信息:{}", e);
		}
		return j;
	}
	
	/**
	 * 跳转到添加执行
	 * @param oaWorkTaskEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addExcute")
	public ModelAndView addExcute(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/task/excuteadd");
		
		if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())){
			oaWorkTaskEntity = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
			view.addObject("task", oaWorkTaskEntity);
		}
		
		return view;
	}
	
	/**
	 * 保存任务回复
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveExcute")
	@ResponseBody
	public AjaxJson saveExcute(OaWorkTaskExcuteEntity oaWorkTaskExcuteEntity, String taskId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(null == oaWorkTaskExcuteEntity.getStartTime() || null == oaWorkTaskExcuteEntity.getEndTime()) {
				j.setMsg("执行时间不能为空");
				j.setSuccess(false);
				return j;
			}
			
			OaWorkTaskEntity oaWorkTaskEntity = oaWorkTaskService.getById(taskId);
			if(StringUtils.equals(oaWorkTaskEntity.getStatus(), GlobalConstant.OA_WORK_TASK_STATUS_NEW)) {
				j.setMsg("请先开始该任务");
				j.setSuccess(false);
				return j;
			}
			
			
			ActiveUser user = ShiroUtils.getSessionUser();
			oaWorkTaskExcuteEntity.setUserHeadImg(user.getHeadImgUrl());
			oaWorkTaskExcuteEntity.setUserId(user.getId());
			oaWorkTaskExcuteEntity.setUserName(user.getRealName());
			oaWorkTaskExcuteEntity.setOaWorkTaskId(taskId);
			
			oaWorkTaskExcuteService.save(oaWorkTaskExcuteEntity, oaWorkTaskEntity);
			
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存任务执行报错，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 获取任务执行内容
	 * @param oaWorkTaskEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getExcutes")
	@ResponseBody
	public AjaxJson getExcutes(OaWorkTaskEntity oaWorkTaskEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(oaWorkTaskEntity.getId())) {
				oaWorkTaskEntity = oaWorkTaskService.getById(oaWorkTaskEntity.getId());
				
				List<OaWorkTaskExcuteEntity> lstExcutes = oaWorkTaskService.findOaWorkTaskExcute(oaWorkTaskEntity);
				
				//排序
				Collections.sort(lstExcutes);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lstExcutes", lstExcutes);
				
				j.setAttributes(map);
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("始化任务执行记录失败，错误信息:", e);
		}
		return j;
	}
	
	/**
	 * 递归拼接
	 * @param lstTasks
	 * @param sb
	 */
	private void taskContact(List<OaWorkTaskEntity> lstTasks, StringBuffer sb, String id) {
		if(null != lstTasks && lstTasks.size() > 0) {
			for(int i = 0; i < lstTasks.size(); i++) {
				OaWorkTaskEntity task = lstTasks.get(i);
				//查询所有岗位
				sb = sb.append("{").append("text:").append("\"").append(task.getTitle()).append("\",").append("id:").append("\"").append(task.getId()).append("\"");
				if(StringUtils.equals(id, task.getId())) {
					sb = sb.append(", state:{selected: true} ");
				}
				
				List<OaWorkTaskEntity> lstOaWorkTask = oaWorkTaskService.findChildrenTask(task.getId());
				if(null != lstOaWorkTask && lstOaWorkTask.size() > 0) {
					//根据type过滤数据
					sb = sb.append(", nodes: [");
					taskContact(lstOaWorkTask, sb, id);
					sb.append("]");
				}
				
				if(i == lstTasks.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
	}
	
	
	
	
	private List<OaWorkTaskStatusDomain> checkAllStatus(List<OaWorkTaskStatusDomain> lstData) {
		OaWorkTaskStatusDomain domain = new OaWorkTaskStatusDomain();
		domain.setValue(GlobalConstant.OA_WORK_TASK_STATUS_NEW);
		domain.setName("新建");
		if(!lstData.contains(domain)) {
			domain.setNum("0");
			lstData.add(domain);
		}
		
		domain = new OaWorkTaskStatusDomain();
		domain.setValue(GlobalConstant.OA_WORK_TASK_STATUS_DOING);
		domain.setName("进行中");
		if(!lstData.contains(domain)) {
			domain.setNum("0");
			lstData.add(domain);
		}
		
		domain = new OaWorkTaskStatusDomain();
		domain.setValue(GlobalConstant.OA_WORK_TASK_STATUS_FINISH);
		domain.setName("完成");
		if(!lstData.contains(domain)) {
			domain.setNum("0");
			lstData.add(domain);
		}
		
		domain = new OaWorkTaskStatusDomain();
		domain.setValue(GlobalConstant.OA_WORK_TASK_STATUS_NO);
		domain.setName("取消");
		if(!lstData.contains(domain)) {
			domain.setNum("0");
			lstData.add(domain);
		}
		
		domain = new OaWorkTaskStatusDomain();
		domain.setValue(GlobalConstant.OA_WORK_TASK_STATUS_GIVEUP);
		domain.setName("放弃");
		if(!lstData.contains(domain)) {
			domain.setNum("0");
			lstData.add(domain);
		}
		
		domain = new OaWorkTaskStatusDomain();
		domain.setValue(GlobalConstant.OA_WORK_TASK_STATUS_EX);
		domain.setName("超期");
		if(!lstData.contains(domain)) {
			domain.setNum("0");
			lstData.add(domain);
		}
		
		return lstData;
	}
	
}
