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
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.SystemUtils;
import com.active4j.hr.work.domain.KeyAndValueDomain;
import com.active4j.hr.work.entity.OaWorkMeetTypeEntity;
import com.active4j.hr.work.entity.OaWorkTargetEntity;
import com.active4j.hr.work.entity.OaWorkTargetExcuteEntity;
import com.active4j.hr.work.service.OaWorkTargetExcuteService;
import com.active4j.hr.work.service.OaWorkTargetService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkTargetController.java
 * @description 
		  目标管理
 * @time  2020年4月8日 下午3:19:44
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/target")
@Slf4j
public class OaWorkTargetController extends BaseController {

	@Autowired
	private OaWorkTargetService oaWorkTargetService;
	@Autowired
	private OaWorkTargetExcuteService oaWorkTargetExcuteService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 我的目标
	 * @return
	 */
	@RequestMapping("/mytargetlist")
	public ModelAndView myTargetList() {
		ModelAndView view = new ModelAndView("oa/work/target/mytargetlist");
		
		List<SysDicValueEntity> lstCategoryTypes = SystemUtils.getDictionaryLst("oatargetcategory");
		if(null != lstCategoryTypes && lstCategoryTypes.size() > 0) {
			List<KeyAndValueDomain> lstCategorys = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstCategoryTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstCategorys.add(keyValue);
			}
			view.addObject("lstCategorys", lstCategorys);
		}
		
		List<SysDicValueEntity> lstStatusTypes = SystemUtils.getDictionaryLst("workplanstatus");
		if(null != lstStatusTypes && lstStatusTypes.size() > 0) {
			List<KeyAndValueDomain> lstStatus = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstStatusTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstStatus.add(keyValue);
			}
			view.addObject("lstStatus", lstStatus);
		}
		
		return view;
	}
	
	/**
	 * 我的目标，获取数据
	 * @param oaInfoNewsEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getData")
	public void getData(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			//拼接查询条件
			QueryWrapper<OaWorkTargetEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTargetEntity, request.getParameterMap(), dataGrid);
			
			List<OaWorkTargetEntity> lstResult = oaWorkTargetService.findOaWorkTarget(ShiroUtils.getSessionUserId(), GlobalConstant.OA_WORK_PLAN_TYPE_PERSON, queryWrapper);
			
			//输出结果
			ResponseUtil.writeJson(response, dataGrid, lstResult);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 我创建的目标，获取数据
	 * @param oaInfoNewsEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCreateData")
	public void getCreateData(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			//拼接查询条件
			QueryWrapper<OaWorkTargetEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTargetEntity, request.getParameterMap(), dataGrid);
			
			List<OaWorkTargetEntity> lstResult = oaWorkTargetService.findOaWorkTargetCreate(ShiroUtils.getSessionUserId(), GlobalConstant.OA_WORK_PLAN_TYPE_PERSON, queryWrapper);
			
			//输出结果
			ResponseUtil.writeJson(response, dataGrid, lstResult);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 我参与的目标，获取数据
	 * @param oaInfoNewsEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPartinData")
	public void getPartinData(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			//拼接查询条件
			QueryWrapper<OaWorkTargetEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTargetEntity, request.getParameterMap(), dataGrid);
			
			List<OaWorkTargetEntity> lstResult = oaWorkTargetService.findOaWorkTargetPart(ShiroUtils.getSessionUserId(), GlobalConstant.OA_WORK_PLAN_TYPE_PERSON, queryWrapper);
			
			//输出结果
			ResponseUtil.writeJson(response, dataGrid, lstResult);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 部门目标，获取数据
	 * @param oaInfoNewsEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDepartData")
	public void getDepartData(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			//拼接查询条件
			QueryWrapper<OaWorkTargetEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTargetEntity, request.getParameterMap(), dataGrid);
			
			SysDeptEntity dept = sysUserService.getUserDepart(ShiroUtils.getSessionUserId());
			
			List<OaWorkTargetEntity> lstResult = oaWorkTargetService.findOaWorkTargetDepart(dept.getId(), GlobalConstant.OA_WORK_PLAN_TYPE_DEPART, queryWrapper);
			
			//输出结果
			ResponseUtil.writeJson(response, dataGrid, lstResult);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 公司目标，获取数据
	 * @param oaInfoNewsEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getComData")
	public void getComData(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			//拼接查询条件
			QueryWrapper<OaWorkTargetEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkTargetEntity, request.getParameterMap(), dataGrid);
			
			List<OaWorkTargetEntity> lstResult = oaWorkTargetService.findOaWorkTargetComp(GlobalConstant.OA_WORK_PLAN_TYPE_COMPANY, queryWrapper);
			
			//输出结果
			ResponseUtil.writeJson(response, dataGrid, lstResult);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取目标数据，树形视图
	 * @param req
	 * @return
	 */
	@RequestMapping("/getTarget")
	@ResponseBody
	public AjaxJson getTarget(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		OaWorkTargetEntity target = oaWorkTargetService.getById(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("target", target);
		j.setAttributes(map);
		return j;
	}
	
	/**
	 * 查看执行
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addRecord")
	public ModelAndView addRecord(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/target/viewadd");
		
		if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())){
			oaWorkTargetEntity = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
			view.addObject("target", oaWorkTargetEntity);
		}
		
		return view;
	}
	
	/**
	 * 获取目标数据，树形视图
	 * @param req
	 * @return
	 */
	@RequestMapping("/getTreeData")
	@ResponseBody
	public AjaxJson getTreeData(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		OaWorkTargetEntity target = oaWorkTargetService.getById(id);
		
		List<OaWorkTargetEntity> lstTargets = new ArrayList<OaWorkTargetEntity>();
		lstTargets.add(target);
		
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		targetContact(lstTargets, sb);
		sb = sb.append("]");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", sb.toString());
		j.setAttributes(map);
		
		return j;
	}
	
	private void targetContact(List<OaWorkTargetEntity> lstTargets, StringBuffer sb) {
		if(null != lstTargets && lstTargets.size() > 0) {
			for(int i = 0; i < lstTargets.size(); i++) {
				OaWorkTargetEntity target = lstTargets.get(i);
				//查询所有岗位
				sb = sb.append("{").append("text:").append("\"").append(target.getContent()).append("\",").append("id:").append("\"").append(target.getId()).append("\"");
				List<OaWorkTargetEntity> lstChildren = oaWorkTargetService.findChildrenTarget(target);
				if(null != lstChildren && lstChildren.size() > 0) {
					//根据type过滤数据
					sb = sb.append(", nodes: [");
					targetContact(lstChildren, sb);
					sb.append("]");
				}
				if(i == lstTargets.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
	}
	
	
	/**
	 * 我创建的目标
	 * @return
	 */
	@RequestMapping("/mycreatelist")
	public ModelAndView myCreateTargetList() {
		ModelAndView view = new ModelAndView("oa/work/target/mycreatelist");
		
		List<SysDicValueEntity> lstCategoryTypes = SystemUtils.getDictionaryLst("oatargetcategory");
		if(null != lstCategoryTypes && lstCategoryTypes.size() > 0) {
			List<KeyAndValueDomain> lstCategorys = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstCategoryTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstCategorys.add(keyValue);
			}
			view.addObject("lstCategorys", lstCategorys);
		}
		
		List<SysDicValueEntity> lstStatusTypes = SystemUtils.getDictionaryLst("workplanstatus");
		if(null != lstStatusTypes && lstStatusTypes.size() > 0) {
			List<KeyAndValueDomain> lstStatus = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstStatusTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstStatus.add(keyValue);
			}
			view.addObject("lstStatus", lstStatus);
		}
		
		return view;
	}
	
	/**
	 * 我参与的目标
	 * @return
	 */
	@RequestMapping("/mypartinlist")
	public ModelAndView myPartinList() {
		ModelAndView view = new ModelAndView("oa/work/target/mypartinlist");
		List<SysDicValueEntity> lstCategoryTypes = SystemUtils.getDictionaryLst("oatargetcategory");
		if(null != lstCategoryTypes && lstCategoryTypes.size() > 0) {
			List<KeyAndValueDomain> lstCategorys = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstCategoryTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstCategorys.add(keyValue);
			}
			view.addObject("lstCategorys", lstCategorys);
		}
		
		List<SysDicValueEntity> lstStatusTypes = SystemUtils.getDictionaryLst("workplanstatus");
		if(null != lstStatusTypes && lstStatusTypes.size() > 0) {
			List<KeyAndValueDomain> lstStatus = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstStatusTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstStatus.add(keyValue);
			}
			view.addObject("lstStatus", lstStatus);
		}
		return view;
	}
	
	/**
	 * 部门目标
	 * @return
	 */
	@RequestMapping("/departlist")
	public ModelAndView departlist() {
		ModelAndView view = new ModelAndView("oa/work/target/departlist");
		List<SysDicValueEntity> lstCategoryTypes = SystemUtils.getDictionaryLst("oatargetcategory");
		if(null != lstCategoryTypes && lstCategoryTypes.size() > 0) {
			List<KeyAndValueDomain> lstCategorys = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstCategoryTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstCategorys.add(keyValue);
			}
			view.addObject("lstCategorys", lstCategorys);
		}
		
		List<SysDicValueEntity> lstStatusTypes = SystemUtils.getDictionaryLst("workplanstatus");
		if(null != lstStatusTypes && lstStatusTypes.size() > 0) {
			List<KeyAndValueDomain> lstStatus = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstStatusTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstStatus.add(keyValue);
			}
			view.addObject("lstStatus", lstStatus);
		}
		return view;
	}
	
	/**
	 * 公司目标
	 * @return
	 */
	@RequestMapping("/companylist")
	public ModelAndView companylist() {
		ModelAndView view = new ModelAndView("oa/work/target/companylist");
		List<SysDicValueEntity> lstCategoryTypes = SystemUtils.getDictionaryLst("oatargetcategory");
		if(null != lstCategoryTypes && lstCategoryTypes.size() > 0) {
			List<KeyAndValueDomain> lstCategorys = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstCategoryTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstCategorys.add(keyValue);
			}
			view.addObject("lstCategorys", lstCategorys);
		}
		
		List<SysDicValueEntity> lstStatusTypes = SystemUtils.getDictionaryLst("workplanstatus");
		if(null != lstStatusTypes && lstStatusTypes.size() > 0) {
			List<KeyAndValueDomain> lstStatus = new ArrayList<KeyAndValueDomain>();
			for(SysDicValueEntity type : lstStatusTypes) {
				KeyAndValueDomain keyValue = new KeyAndValueDomain();
				keyValue.setKey(type.getValue());
				keyValue.setValue(type.getLabel());
				lstStatus.add(keyValue);
			}
			view.addObject("lstStatus", lstStatus);
		}
		return view;
	}
	
	/**
	 * 新增页面
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/target/target");
		
		List<OaWorkTargetEntity> lstTargets = oaWorkTargetService.findOaWorkTargetStatus("2", ShiroUtils.getSessionUserId());
		view.addObject("lstTargets", lstTargets);
		
		if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())) {
			oaWorkTargetEntity = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
			view.addObject("target", oaWorkTargetEntity);
			
			if(null != lstTargets && lstTargets.size() > 0) {
				Iterator<OaWorkTargetEntity> itLst = lstTargets.iterator();
				while(itLst.hasNext()) {
					OaWorkTargetEntity t = itLst.next();
					if(StringUtils.equals(oaWorkTargetEntity.getId(), t.getId())) {
						itLst.remove();
					}
				}
			}
		}
		return view;
	}
	
	/**
	 * 新增页面
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdatedepart")
	public ModelAndView addorupdatedepart(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/target/targetdepart");
		
		SysDeptEntity dept = sysUserService.getUserDepart(ShiroUtils.getSessionUserId());
		List<OaWorkTargetEntity> lstTargets = oaWorkTargetService.findOaWorkTargetStatusDepart("1", dept.getId());
		view.addObject("lstTargets", lstTargets);
		
		if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())) {
			oaWorkTargetEntity = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
			view.addObject("target", oaWorkTargetEntity);
			if(null != lstTargets && lstTargets.size() > 0) {
				Iterator<OaWorkTargetEntity> itLst = lstTargets.iterator();
				while(itLst.hasNext()) {
					OaWorkTargetEntity t = itLst.next();
					if(StringUtils.equals(oaWorkTargetEntity.getId(), t.getId())) {
						itLst.remove();
					}
				}
			}
		}
		return view;
	}
	
	/**
	 * 新增页面
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdatecom")
	public ModelAndView addorupdatecom(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/target/targetcom");
		
		List<OaWorkTargetEntity> lstTargets = oaWorkTargetService.findOaWorkTargetStatus("0");
		view.addObject("lstTargets", lstTargets);
		
		if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())) {
			oaWorkTargetEntity = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
			view.addObject("target", oaWorkTargetEntity);
			if(null != lstTargets && lstTargets.size() > 0) {
				Iterator<OaWorkTargetEntity> itLst = lstTargets.iterator();
				while(itLst.hasNext()) {
					OaWorkTargetEntity t = itLst.next();
					if(StringUtils.equals(oaWorkTargetEntity.getId(), t.getId())) {
						itLst.remove();
					}
				}
			}
		}
		return view;
	}
	
	/**
	 * 查看执行
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/target/excute");
		
		if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())) {
			oaWorkTargetEntity = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
			view.addObject("target", oaWorkTargetEntity);
		}
		
		return view;
	}
	
	/**
	 * 目标状态的修改
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/doChange")
	@ResponseBody
	public AjaxJson doChange(OaWorkTargetEntity oaWorkTargetEntity, String status, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())) {
				OaWorkTargetEntity tmp = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
				tmp.setStatus(status);
				oaWorkTargetService.saveOrUpdate(tmp, status);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("目标保存失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 保存目标的跟踪回复
	 * @param oaWorkPlanExcuteEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveExcute")
	@ResponseBody
	public AjaxJson saveExcute(OaWorkTargetExcuteEntity oaWorkTargetExcuteEntity, String targetId, int finishData, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(oaWorkTargetExcuteEntity.getContent())) {
				j.setSuccess(false);
				j.setMsg("情况说明不能为空");
				return j;
			}
			
			OaWorkTargetEntity target = oaWorkTargetService.getById(targetId);
			
			ActiveUser user = ShiroUtils.getSessionUser();
			oaWorkTargetExcuteEntity.setUserId(user.getId());
			oaWorkTargetExcuteEntity.setUserHeadImg(user.getHeadImgUrl());
			oaWorkTargetExcuteEntity.setUserName(user.getRealName());
			oaWorkTargetExcuteEntity.setFinishRecord(finishData);
			oaWorkTargetExcuteEntity.setUnit(target.getUnit());
			
			oaWorkTargetExcuteService.save(target, oaWorkTargetExcuteEntity, finishData);
			
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("目标执行保存失败，错误信息:{}", e) ;
		}
		
		return j;
	}
	
	/**
	 * 保存方法
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkTargetEntity oaWorkTargetEntity, String parentTargetId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{

			if(StringUtils.isEmpty(oaWorkTargetEntity.getContent())) {
				j.setSuccess(false);
				j.setMsg("目标内容不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkTargetEntity.getId())) {
				oaWorkTargetEntity.setStatus(GlobalConstant.OA_WORK_TARGET_STATUS_NEW); //新建状态
				oaWorkTargetEntity.setUserId(ShiroUtils.getSessionUserId());
				oaWorkTargetEntity.setUserName(ShiroUtils.getSessionUser().getRealName());
				if(StringUtils.isNotEmpty(oaWorkTargetEntity.getParticipantIds())) {
					oaWorkTargetEntity.setCreateUserId(ShiroUtils.getSessionUserId());
					oaWorkTargetEntity.setCreateUserName(ShiroUtils.getSessionUser().getRealName());
				}
				//部门
				SysDeptEntity dept = sysUserService.getUserDepart(ShiroUtils.getSessionUserId());
				oaWorkTargetEntity.setDepartId(dept.getId());
				oaWorkTargetEntity.setDepartName(dept.getName());
				
				if(StringUtils.isNotEmpty(parentTargetId)) {
					oaWorkTargetEntity.setParentTargetId(parentTargetId);
				}else {
					oaWorkTargetEntity.setParentTargetId(null);
				}
				
				//新增方法
				oaWorkTargetService.save(oaWorkTargetEntity);
				
				
			}else {
				OaWorkTargetEntity tmp = oaWorkTargetService.getById(oaWorkTargetEntity.getId());
				if(!StringUtils.equals(tmp.getStatus(), GlobalConstant.OA_WORK_TARGET_STATUS_NEW)) {
					j.setSuccess(false);
					j.setMsg("当前目标状态不支持修改");
					return j;
				}
				
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkTargetEntity, tmp);
				if(StringUtils.isNotEmpty(tmp.getParticipantIds())) {
					oaWorkTargetEntity.setCreateUserId(ShiroUtils.getSessionUserId());
					oaWorkTargetEntity.setCreateUserName(ShiroUtils.getSessionUser().getRealName());
				}
				
				if(StringUtils.isNotEmpty(parentTargetId)) {
					oaWorkTargetEntity.setParentTargetId(parentTargetId);
				}else {
					oaWorkTargetEntity.setParentTargetId(null);
				}
				
				oaWorkTargetService.saveOrUpdate(tmp);
				
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("目标保存失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	@RequestMapping("/getRecord")
	@ResponseBody
	public AjaxJson getRecord(OaWorkTargetEntity oaWorkTargetEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkTargetEntity.getId())) {
				List<OaWorkTargetExcuteEntity> lstExcutes = oaWorkTargetExcuteService.findOaWorkTargetExcute(oaWorkTargetEntity);
				//排序
				Collections.sort(lstExcutes);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lstExcutes", lstExcutes);
				
				j.setAttributes(map);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("初始化目标记录失败，错误信息:{}", e);
		}
		return j;
	}
	
	/**
	 * 目标的图标形式表示
	 * @param oaWorkTargetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/getChartData")
	@ResponseBody
	public AjaxJson getChartData(OaWorkTargetEntity oaWorkTargetEntity,String select, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		
		try{
			List<OaWorkTargetEntity> lstTargets = new ArrayList<OaWorkTargetEntity>();
			if(StringUtils.equals("0", select)) {
				lstTargets = oaWorkTargetService.findOaWorkTargetChart("2", ShiroUtils.getSessionUserId(), "0");
			}else if(StringUtils.equals("1", select)) {
				lstTargets = oaWorkTargetService.findOaWorkTargetChart("2", ShiroUtils.getSessionUserId(), "1");
			}else if(StringUtils.equals("2", select)) {
				lstTargets = oaWorkTargetService.findOaWorkTargetChart("2", ShiroUtils.getSessionUserId(), "2");
				
			}else if(StringUtils.equals("3", select)) {
				SysDeptEntity dept = sysUserService.getUserDepart(ShiroUtils.getSessionUserId());
				if(null == dept) {
					lstTargets = oaWorkTargetService.findOaWorkTargetChart("1", "-1", "3");
				}else {
					lstTargets = oaWorkTargetService.findOaWorkTargetChart("1", dept.getId(), "3");
				}
			}else if(StringUtils.equals("4", select)){
				lstTargets = oaWorkTargetService.findOaWorkTargetChart("0", "", "4");
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			StringBuffer sb = new StringBuffer();
			sb = sb.append("[");
			chartContact(lstTargets, sb);
			sb = sb.append("]");
			
			map.put("data", sb.toString());
			
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("获取我的目标图标数据失败，错误信息:", e);
		}
		
		return j;
	}
	
	private void chartContact(List<OaWorkTargetEntity> lstTargets, StringBuffer sb) {
		if(null != lstTargets && lstTargets.size() > 0) {
			for(int i = 0; i < lstTargets.size(); i++) {
				OaWorkTargetEntity target = lstTargets.get(i);
				if(StringUtils.equals(target.getStatus(), GlobalConstant.OA_WORK_TARGET_STATUS_NEW) || StringUtils.equals(target.getStatus(), GlobalConstant.OA_WORK_TARGET_STATUS_DOING)){
					//查询所有岗位
					
					if(target.getDataRecord() > 0) {
						int result = target.getFinishRecord() * 100 / target.getDataRecord() ;
						sb = sb.append("{").append("name:").append("\"").append(target.getContent()+"(" + result + "%}").append("\",").append("id:").append("\"").append(target.getId()).append("\"");
					}else {
						sb = sb.append("{").append("name:").append("\"").append(target.getContent()).append("\",").append("id:").append("\"").append(target.getId()).append("\"");
					}
					//根据目标类别，展示目标的大小
					if(StringUtils.equals(target.getCategory(), GlobalConstant.OA_WORK_TARGET_CATEGORY_YEAR)) {
						sb = sb.append(",value:").append("100");
					}else if(StringUtils.equals(target.getCategory(), GlobalConstant.OA_WORK_TARGET_CATEGORY_SESSION)){
						sb = sb.append(",value:").append("90");
					}else if(StringUtils.equals(target.getCategory(), GlobalConstant.OA_WORK_TARGET_CATEGORY_MONTH)){
						sb = sb.append(",value:").append("80");
					}else if(StringUtils.equals(target.getCategory(), GlobalConstant.OA_WORK_TARGET_CATEGORY_WEEK)){
						sb = sb.append(",value:").append("70");
					}else if(StringUtils.equals(target.getCategory(), GlobalConstant.OA_WORK_TARGET_CATEGORY_DAY)){
						sb = sb.append(",value:").append("60");
					}
					
					List<OaWorkTargetEntity> lstChildren = oaWorkTargetService.findChildrenTarget(target);
					
					if(null != lstChildren && lstChildren.size() > 0) {
						//根据type过滤数据
						sb = sb.append(", children: [");
						chartContact(lstChildren, sb);
						sb.append("]");
						
					}
					
					if(i == lstTargets.size() - 1) {
						sb = sb.append("}");
					}else {
						sb = sb.append("},");
					}
				}
			}
		}
	}
}
