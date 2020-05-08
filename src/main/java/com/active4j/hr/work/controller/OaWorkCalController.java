package com.active4j.hr.work.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.work.domain.ScheduleEventDomain;
import com.active4j.hr.work.domain.UnderEmployerDomain;
import com.active4j.hr.work.entity.OaWorkScheduleEntity;
import com.active4j.hr.work.service.OaWorkCalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkCalController.java
 * @description 
		  日程管理
 * @time  2020年4月7日 下午12:35:37
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/cal")
@Slf4j
public class OaWorkCalController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private OaWorkCalService oaWorkCalService;
	
	/**
	 * 我的日程
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("oa/work/cal/mycallist");
		
		return view;
	}
	
	/**
	 * 我的日程
	 * @param req
	 * @return
	 */
	@RequestMapping("/createlist")
	public ModelAndView createlist(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("oa/work/cal/callist");
		
		return view;
	}
	
	/**
	 * 我的日程
	 * @param req
	 * @return
	 */
	@RequestMapping("/underlist")
	public ModelAndView underlist(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("oa/work/cal/underlist");
		List<String> lstIds = sysUserService.getUnderUserIds(ShiroUtils.getSessionUserId());
		if(lstIds != null && lstIds.size() > 0) {
			view.addObject("ids", lstIds.stream().collect(Collectors.joining(",")));
		}else {
			view.addObject("ids", "");
		}
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param oaWorkMeetTypeEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkScheduleEntity oaWorkScheduleEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaWorkScheduleEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkScheduleEntity, request.getParameterMap(), dataGrid);
		queryWrapper.eq("CREATE_USER_ID", ShiroUtils.getSessionUserId());
		//执行查询
		IPage<OaWorkScheduleEntity> lstResult = oaWorkCalService.page(new Page<OaWorkScheduleEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public AjaxJson getData(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			List<OaWorkScheduleEntity> lstDatas = oaWorkCalService.getOaWorkScheduleByUserId(ShiroUtils.getSessionUserId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lstData", lstDatas);
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("日程获取数据失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 获取下属人员列表
	 * @return
	 */
	@RequestMapping("/getUnderData")
	@ResponseBody
	public AjaxJson getUnderData(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			List<UnderEmployerDomain> lstDatas = new ArrayList<UnderEmployerDomain>();
			List<String> underIds = sysUserService.getUnderUserIds(ShiroUtils.getSessionUserId());
			if(null != underIds && underIds.size() > 0) {
				for(String id : underIds) {
					UnderEmployerDomain d = new UnderEmployerDomain();
					d.setId(id);
					d.setName(sysUserService.getById(id).getRealName());
					lstDatas.add(d);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lstData", lstDatas);
			j.setAttributes(map);
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("日程获取数据失败，错误信息:{}", e);
		}
		return j;
	}
	
	/**
	 * 获取日程表初始化数据
	 * @return
	 */
	@RequestMapping("/getCalEvent")
	@ResponseBody
	public AjaxJson getCalEvent(Date start, Date end, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			//查询日程安排
			List<OaWorkScheduleEntity> lstDatas = oaWorkCalService.getOaWorkScheduleByUserId(ShiroUtils.getSessionUserId(), start, end);
			Map<String, Object> map = new HashMap<String, Object>();
			List<ScheduleEventDomain> lstData = new ArrayList<ScheduleEventDomain>();
			if(null != lstDatas && lstDatas.size() > 0){
				for(OaWorkScheduleEntity s : lstDatas) {
					ScheduleEventDomain event = new ScheduleEventDomain();
					if(null != s.getStartTime()) {
						event.setStartTime(DateUtils.date2Str(s.getStartTime(), DateUtils.SDF_YYYY_MM_DD_HH_MM));
					}else{
						event.setStartTime("");
					}
					
					if(null != s.getEndTime()) {
						event.setEndTime(DateUtils.date2Str(s.getEndTime(), DateUtils.SDF_YYYY_MM_DD_HH_MM));
					}else {
						event.setEndTime("");
					}
					
					event.setId(s.getId());
					event.setTitle(s.getTitle());
					lstData.add(event);
				}
				map.put("lstData", lstData);
			}
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("日程获取数据失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	
	/**
	 * 获取下属日程表初始化数据
	 * @return
	 */
	@RequestMapping("/getUnderCalEvent")
	@ResponseBody
	public AjaxJson getUnderCalEvent(String ids, Date start, Date end, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(ids)) {
				ids = "-1";
			}
			List<OaWorkScheduleEntity> lstDatas = oaWorkCalService.getOaWorkScheduleByUserId(ids.split(","), start, end);
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<ScheduleEventDomain> lstData = new ArrayList<ScheduleEventDomain>();
			if(null != lstDatas && lstDatas.size() > 0){
				for(OaWorkScheduleEntity s : lstDatas) {
					ScheduleEventDomain event = new ScheduleEventDomain();
					if(null != s.getStartTime()) {
						event.setStartTime(DateUtils.date2Str(s.getStartTime(), DateUtils.SDF_YYYY_MM_DD_HH_MM));
					}else{
						event.setStartTime("");
					}
					
					if(null != s.getEndTime()) {
						event.setEndTime(DateUtils.date2Str(s.getEndTime(), DateUtils.SDF_YYYY_MM_DD_HH_MM));
					}else {
						event.setEndTime("");
					}
					
					event.setId(s.getId());
					event.setTitle(s.getTitle());
					lstData.add(event);
				}
			}
			map.put("lstData", lstData);
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("日程获取数据失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 日程表数据变动 更新
	 * @return
	 */
	@RequestMapping("/updateEvent")
	@ResponseBody
	public AjaxJson updateEvent(String id, Date start, Date end, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			//查询日程
			if(StringUtils.isNotEmpty(id)) {
				OaWorkScheduleEntity oaWorkScheduleEntity = oaWorkCalService.getById(id);
				if(null != oaWorkScheduleEntity) {
					oaWorkScheduleEntity.setStartTime(start);
					if(null == end) {
						oaWorkScheduleEntity.setEndTime(DateUtils.addMinute(start, 60));
					}else {
						oaWorkScheduleEntity.setEndTime(end);
					}
					oaWorkScheduleEntity.setStatus(GlobalConstant.OA_WORK_SHEDULE_STATUS_A);
					oaWorkCalService.saveOrUpdate(oaWorkScheduleEntity);
					
				}
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("更新日程数据报错，错误信息：{}",e);
		}
		
		return j;
	}
	
	/**
	 * 新增页面
	 * @param OaWorkCalEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkScheduleEntity oaWorkScheduleEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/cal/cal");
		
		view.addObject("defaultUserId", ShiroUtils.getSessionUserId());
		view.addObject("defaultUserName", ShiroUtils.getSessionUser().getRealName());
		
		if(StringUtils.isNotEmpty(oaWorkScheduleEntity.getId())) {
			oaWorkScheduleEntity = oaWorkCalService.getById(oaWorkScheduleEntity.getId());
			view.addObject("schedule", oaWorkScheduleEntity);
			view.addObject("defaultUserId", oaWorkScheduleEntity.getUserId());
			view.addObject("defaultUserName", oaWorkScheduleEntity.getUserName());
		}
		
		return view;
	}
	
	/**
	 * 新增页面
	 * @param OaWorkCalEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView update(OaWorkScheduleEntity oaWorkScheduleEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/cal/calupdate");
		
		if(StringUtils.isNotEmpty(oaWorkScheduleEntity.getId())) {
			oaWorkScheduleEntity = oaWorkCalService.getById(oaWorkScheduleEntity.getId());
			String businessId = oaWorkScheduleEntity.getBusinessId();
			List<OaWorkScheduleEntity> lstSs = oaWorkCalService.getOaWorkScheduleByBusessionId(businessId);
			StringBuffer sbIds = new StringBuffer();
			StringBuffer sbNames = new StringBuffer();
			for(OaWorkScheduleEntity s : lstSs) {
				sbIds = sbIds.append(s.getUserId()).append(",");
				sbNames = sbNames.append(s.getUserName()).append(",");
			}
			view.addObject("schedule", oaWorkScheduleEntity);
			view.addObject("defaultUserId", sbIds.substring(0, sbIds.length()-1));
			view.addObject("defaultUserName", sbNames.substring(0, sbNames.length() - 1));
		}
		
		return view;
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkScheduleEntity oaWorkScheduleEntity, String participantIds, int[] tmpTipType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(oaWorkScheduleEntity.getTitle())) {
				j.setSuccess(false);
				j.setMsg("日程内容不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(participantIds)) {
				j.setSuccess(false);
				j.setMsg("日程参与人不能为空!");
				return j;
			}
			
			if(oaWorkScheduleEntity.isTip() && null == oaWorkScheduleEntity.getTipTime()) {
				j.setSuccess(false);
				j.setMsg("提醒时间不能为空!");
				return j;
			}
			
			if(oaWorkScheduleEntity.isTip() && null != oaWorkScheduleEntity.getTipTime() && oaWorkScheduleEntity.getTipTime().before(DateUtils.getDate())) {
				j.setSuccess(false);
				j.setMsg("提醒时间必须在当前时间之后");
				return j;
			}
			
			if(oaWorkScheduleEntity.isTip() && null == tmpTipType) {
				j.setSuccess(false);
				j.setMsg("提醒方式不能为空!");
				return j;
			}
			
			//提醒方式的处理
			int total = 0;
			if(null != tmpTipType && oaWorkScheduleEntity.isTip()) {
				for(int i : tmpTipType) {
					total += i;
				}
			}
			
			oaWorkScheduleEntity.setTipType(total);
			
			if(null != oaWorkScheduleEntity.getStartTime() && null != oaWorkScheduleEntity.getEndTime()) {
				oaWorkScheduleEntity.setStatus(GlobalConstant.OA_WORK_SHEDULE_STATUS_A);
			}else {
				oaWorkScheduleEntity.setStatus(GlobalConstant.OA_WORK_SHEDULE_STATUS_NO_A);
			}
			
			if(StringUtils.isEmpty(oaWorkScheduleEntity.getId())) {
				oaWorkScheduleEntity.setType(GlobalConstant.OA_WORK_SHEDULE_TYPE_DEFINE);
				
				String[] userIds = participantIds.split(",");
				String businessId = UUID.randomUUID().toString();
				oaWorkScheduleEntity.setCreateUserId(ShiroUtils.getSessionUserId());
				oaWorkScheduleEntity.setCreateUserName(ShiroUtils.getSessionUser().getRealName());
				for(String id : userIds) {
					OaWorkScheduleEntity tmp = new OaWorkScheduleEntity();
					tmp.setUserId(id);
					SysUserEntity user = sysUserService.getById(id);
					tmp.setUserName(user.getRealName());
					tmp.setBusinessId(businessId);
					//暂时只支持一次性日程
					tmp.setCategory(oaWorkScheduleEntity.getCategory());
					tmp.setCreateUserId(oaWorkScheduleEntity.getCreateUserId());
					tmp.setCreateUserName(oaWorkScheduleEntity.getCreateUserName());
					tmp.setEndTime(oaWorkScheduleEntity.getEndTime());
					tmp.setStartTime(oaWorkScheduleEntity.getStartTime());
					tmp.setStatus(oaWorkScheduleEntity.getStatus());
					tmp.setTip(oaWorkScheduleEntity.isTip());
					tmp.setTipTime(oaWorkScheduleEntity.getTipTime());
					tmp.setType(oaWorkScheduleEntity.getType());
					tmp.setTitle(oaWorkScheduleEntity.getTitle());
					tmp.setTipType(oaWorkScheduleEntity.getTipType());
					oaWorkCalService.saveAndTip(tmp);
					
				}
			} else{
				OaWorkScheduleEntity schedule = oaWorkCalService.getById(oaWorkScheduleEntity.getId());
				if(!StringUtils.equals(ShiroUtils.getSessionUserId(), schedule.getCreateUserId())){
					j.setMsg("不是本人创建的日程不能修改");
					j.setSuccess(false);
					return j;
				}
				oaWorkScheduleEntity.setType(GlobalConstant.OA_WORK_SHEDULE_TYPE_DEFINE);
				String[] userIds = participantIds.split(",");
				String businessId = UUID.randomUUID().toString();
				oaWorkScheduleEntity.setCreateUserId(ShiroUtils.getSessionUserId());
				oaWorkScheduleEntity.setCreateUserName(ShiroUtils.getSessionUser().getRealName());
				for(String id : userIds) {
					OaWorkScheduleEntity tmp = new OaWorkScheduleEntity();
					tmp.setUserId(id);
					SysUserEntity user = sysUserService.getById(id);
					tmp.setUserName(user.getRealName());
					tmp.setBusinessId(businessId);
					//暂时只支持一次性日程
					tmp.setCategory(oaWorkScheduleEntity.getCategory());
					tmp.setCreateUserId(oaWorkScheduleEntity.getCreateUserId());
					tmp.setCreateUserName(oaWorkScheduleEntity.getCreateUserName());
					tmp.setEndTime(oaWorkScheduleEntity.getEndTime());
					tmp.setStartTime(oaWorkScheduleEntity.getStartTime());
					tmp.setStatus(oaWorkScheduleEntity.getStatus());
					tmp.setTip(oaWorkScheduleEntity.isTip());
					tmp.setTipTime(oaWorkScheduleEntity.getTipTime());
					tmp.setType(oaWorkScheduleEntity.getType());
					tmp.setTitle(oaWorkScheduleEntity.getTitle());
					tmp.setTipType(oaWorkScheduleEntity.getTipType());
					oaWorkCalService.saveAndTip(tmp);
				}
				//删除之前的日程，重新创建
				oaWorkCalService.deleteAndTip(schedule);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存日程数据报错，错误信息：{}",e);
		}
		return j;
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping("/saveupdate")
	@ResponseBody
	public AjaxJson saveupdate(OaWorkScheduleEntity oaWorkScheduleEntity, String participantIds, int[] tmpTipType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(oaWorkScheduleEntity.getTitle())) {
				j.setSuccess(false);
				j.setMsg("日程内容不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(participantIds)) {
				j.setSuccess(false);
				j.setMsg("日程参与人不能为空!");
				return j;
			}
			
			if(oaWorkScheduleEntity.isTip() && null == oaWorkScheduleEntity.getTipTime()) {
				j.setSuccess(false);
				j.setMsg("提醒时间不能为空!");
				return j;
			}
			
			if(oaWorkScheduleEntity.isTip() && null != oaWorkScheduleEntity.getTipTime() && oaWorkScheduleEntity.getTipTime().before(DateUtils.getDate())) {
				j.setSuccess(false);
				j.setMsg("提醒时间必须在当前时间之后");
				return j;
			}
			
			if(oaWorkScheduleEntity.isTip() && null == tmpTipType) {
				j.setSuccess(false);
				j.setMsg("提醒方式不能为空!");
				return j;
			}
			
			//提醒方式的处理
			int total = 0;
			if(null != tmpTipType && oaWorkScheduleEntity.isTip()) {
				for(int i : tmpTipType) {
					total += i;
				}
			}
			
			oaWorkScheduleEntity.setTipType(total);
			
			if(null != oaWorkScheduleEntity.getStartTime() && null != oaWorkScheduleEntity.getEndTime()) {
				oaWorkScheduleEntity.setStatus(GlobalConstant.OA_WORK_SHEDULE_STATUS_A);
			}else {
				oaWorkScheduleEntity.setStatus(GlobalConstant.OA_WORK_SHEDULE_STATUS_NO_A);
			}
			
			OaWorkScheduleEntity schedule = oaWorkCalService.getById(oaWorkScheduleEntity.getId());
			if(!StringUtils.equals(ShiroUtils.getSessionUserId(), schedule.getCreateUserId())){
				j.setMsg("不是本人创建的日程不能修改");
				j.setSuccess(false);
				return j;
			}
			
			//批量修改  实际是删除后再修改
			String businessIdOld = schedule.getBusinessId();
			List<OaWorkScheduleEntity> lstSs = oaWorkCalService.getOaWorkScheduleByBusessionId(businessIdOld);
			
			
			oaWorkScheduleEntity.setType(GlobalConstant.OA_WORK_SHEDULE_TYPE_DEFINE);
			String[] userIds = participantIds.split(",");
			String businessId = UUID.randomUUID().toString();
			oaWorkScheduleEntity.setCreateUserId(ShiroUtils.getSessionUserId());
			oaWorkScheduleEntity.setCreateUserName(ShiroUtils.getSessionUser().getRealName());
			for(String id : userIds) {
				OaWorkScheduleEntity tmp = new OaWorkScheduleEntity();
				tmp.setUserId(id);
				SysUserEntity user = sysUserService.getById(id);
				tmp.setUserName(user.getRealName());
				tmp.setBusinessId(businessId);
				//暂时只支持一次性日程
				tmp.setCategory(oaWorkScheduleEntity.getCategory());
				tmp.setCreateUserId(oaWorkScheduleEntity.getCreateUserId());
				tmp.setCreateUserName(oaWorkScheduleEntity.getCreateUserName());
				tmp.setEndTime(oaWorkScheduleEntity.getEndTime());
				tmp.setStartTime(oaWorkScheduleEntity.getStartTime());
				tmp.setStatus(oaWorkScheduleEntity.getStatus());
				tmp.setTip(oaWorkScheduleEntity.isTip());
				tmp.setTipTime(oaWorkScheduleEntity.getTipTime());
				tmp.setType(oaWorkScheduleEntity.getType());
				tmp.setTitle(oaWorkScheduleEntity.getTitle());
				tmp.setTipType(oaWorkScheduleEntity.getTipType());
				oaWorkCalService.saveAndTip(tmp);
				
			}
			
			//删除之前的日程，重新创建
			for(OaWorkScheduleEntity s : lstSs) {
				oaWorkCalService.deleteAndTip(s);
			}
			
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存日程数据报错，错误信息：{}",e);
		}
		
		return j;
	}
	
	/**
	 * 删除日程
	 * @param oaWorkScheduleEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(OaWorkScheduleEntity oaWorkScheduleEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkScheduleEntity.getId())) {
				oaWorkScheduleEntity = oaWorkCalService.getById(oaWorkScheduleEntity.getId());
				if(!StringUtils.equals(ShiroUtils.getSessionUserId(), oaWorkScheduleEntity.getCreateUserId())){
					j.setMsg("不是本人创建的日程不能修改");
					j.setSuccess(false);
					return j;
				}
				oaWorkCalService.deleteAndTip(oaWorkScheduleEntity);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除日程数据报错，错误信息：{}",e);
		}
		
		return j;
	}
}

