package com.active4j.hr.work.controller;

import java.util.List;

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
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.entity.OaWorkMeetSummaryEntity;
import com.active4j.hr.work.entity.OaWorkMeetTypeEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.active4j.hr.work.service.OaWorkMeetService;
import com.active4j.hr.work.service.OaWorkMeetSummaryService;
import com.active4j.hr.work.service.OaWorkMeetTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkMeetController.java
 * @description 会议管理
 * @time 2020年4月7日 上午9:07:03
 * @author 麻木神
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/work/meet")
@Slf4j
public class OaWorkMeetController extends BaseController {

	@Autowired
	private OaWorkMeetRoomService oaWorkMeetRoomService;

	@Autowired
	private OaWorkMeetTypeService oaWorkMeetTypeService;
	
	@Autowired
	private OaWorkMeetService oaWorkMeetService;
	
	@Autowired
	private OaWorkMeetSummaryService oaWorkMeetSummaryService;

	/**
	 * 
	 * @description 列表显示
	 * @return ModelAndView
	 * @author 麻木神
	 * @time 2020年4月7日 上午9:07:54
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meet/meetlist");

		// 会议室查询
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", ListUtils.listToReplaceStr(lstRooms, "name", "id"));

		// 会议类型查询
		List<OaWorkMeetTypeEntity> lstTypes = oaWorkMeetTypeService.list();
		view.addObject("lstTypes", ListUtils.listToReplaceStr(lstTypes, "name", "id"));

		return view;
	}

	/**
	 * 查询数据
	 * 
	 * @param oaWorkMeetTypeEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkMeetEntity oaWorkMeetEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkMeetEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkMeetEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<OaWorkMeetEntity> lstResult = oaWorkMeetService.page(new Page<OaWorkMeetEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param oaWorkMeetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(OaWorkMeetEntity oaWorkMeetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meet/meetadd");
		
		if(StringUtils.isEmpty(oaWorkMeetEntity.getId())) {
			//赋值召集人
			String callUserName = ShiroUtils.getSessionUser().getRealName();
			String callUserId = ShiroUtils.getSessionUserId();
			view.addObject("callUserName", callUserName);
			view.addObject("callUserId", callUserId);
		}
		
		//会议地点
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", lstRooms);
		
		//会议类型
		List<OaWorkMeetTypeEntity> lstTypes = oaWorkMeetTypeService.list();
		view.addObject("lstTypes", lstTypes);
		
		return view;
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param oaWorkMeetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView update(OaWorkMeetEntity oaWorkMeetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meet/meetupdate");
		
		if(StringUtils.isNotEmpty(oaWorkMeetEntity.getId())) {
			oaWorkMeetEntity = oaWorkMeetService.getById(oaWorkMeetEntity.getId());
			view.addObject("meet", oaWorkMeetEntity);
		}
		
		//会议地点
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", lstRooms);
		
		//会议类型
		List<OaWorkMeetTypeEntity> lstTypes = oaWorkMeetTypeService.list();
		view.addObject("lstTypes", lstTypes);
		
		return view;
	}
	
	/**
	 * 创建会议纪要
	 * @param oaWorkMeetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/createSummary")
	public ModelAndView createSummary(OaWorkMeetEntity oaWorkMeetEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meet/summary");
		
		//会议地点
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", lstRooms);
		
		oaWorkMeetEntity = oaWorkMeetService.getById(oaWorkMeetEntity.getId());
		view.addObject("oaMeetId", oaWorkMeetEntity.getId());
		
		try {
			OaWorkMeetSummaryEntity summary = oaWorkMeetSummaryService.findMeetSummary(oaWorkMeetEntity);
			
			if(null != summary) {
				view.addObject("summary", summary);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return view;
	}
	
	/**
	 * 保存方法
	 * @param oaWorkMeetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkMeetEntity oaWorkMeetEntity, boolean partIn, int[] tmpTipType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(oaWorkMeetEntity.getName())) {
				j.setSuccess(false);
				j.setMsg("名称不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetEntity.getParticipantIds())) {
				j.setSuccess(false);
				j.setMsg("会议参与人不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetEntity.getMeetRoomId())) {
				j.setSuccess(false);
				j.setMsg("会议地点不能为空!");
				return j;
			}
			
			if(null == oaWorkMeetEntity.getMeetingTime()) {
				j.setSuccess(false);
				j.setMsg("会议时间不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetEntity.getContent())) {
				j.setSuccess(false);
				j.setMsg("会议内容不能为空!");
				return j;
			}
			
			if(oaWorkMeetEntity.isTip() && null == oaWorkMeetEntity.getTipTime()) {
				j.setSuccess(false);
				j.setMsg("提醒时间不能为空!");
				return j;
			}
			
			if(oaWorkMeetEntity.isTip() && null != oaWorkMeetEntity.getTipTime() && oaWorkMeetEntity.getTipTime().before(DateUtils.getDate())) {
				j.setSuccess(false);
				j.setMsg("提醒时间必须在当前时间之后");
				return j;
			}
			
			if(oaWorkMeetEntity.isTip() && null == tmpTipType) {
				j.setSuccess(false);
				j.setMsg("提醒方式不能为空!");
				return j;
			}
			
			//提醒方式的处理
			int total = 0;
			if(null != tmpTipType && oaWorkMeetEntity.isTip()) {
				for(int i : tmpTipType) {
					total += i;
				}
			}
			
			oaWorkMeetEntity.setTipType(total);
			
			if(StringUtils.isEmpty(oaWorkMeetEntity.getId())) {
				//新增
				oaWorkMeetService.save(oaWorkMeetEntity, partIn, oaWorkMeetEntity.isTip());
				
			}else{
				OaWorkMeetEntity tmp = oaWorkMeetService.getById(oaWorkMeetEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkMeetEntity, tmp);
				
				//编辑
				oaWorkMeetService.saveOrUpdate(tmp, partIn, oaWorkMeetEntity.isTip());
				
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存会议失败，错误信息{}", e);
		}
		
		return j;
	}
	
	/**
	 * 保存会议纪要
	 * @param oaWorkMeetSummaryEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSummary")
	@ResponseBody
	public AjaxJson saveSummary(OaWorkMeetSummaryEntity oaWorkMeetSummaryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(oaWorkMeetSummaryEntity.getOaMeetId())) {
				j.setSuccess(false);
				j.setMsg("会议不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetSummaryEntity.getName())) {
				j.setSuccess(false);
				j.setMsg("会议议题不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetSummaryEntity.getMeetRoomId())) {
				j.setSuccess(false);
				j.setMsg("会议地点不能为空!");
				return j;
			}
			
			if(null == oaWorkMeetSummaryEntity.getStartTime() || null == oaWorkMeetSummaryEntity.getEndTime()) {
				j.setSuccess(false);
				j.setMsg("会议时间不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetSummaryEntity.getContent())) {
				j.setSuccess(false);
				j.setMsg("会议内容不能为空!");
				return j;
			}
			
			
			if(StringUtils.isEmpty(oaWorkMeetSummaryEntity.getId())) {
				//新增
				oaWorkMeetSummaryService.save(oaWorkMeetSummaryEntity);
				
			}else{
				OaWorkMeetSummaryEntity tmp = oaWorkMeetSummaryService.getById(oaWorkMeetSummaryEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkMeetSummaryEntity, tmp);
				
				oaWorkMeetSummaryService.saveOrUpdate(tmp);
				
			}
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存会议纪要失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 删除
	 * @param oaWorkMeetEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(OaWorkMeetEntity oaWorkMeetEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkMeetEntity.getId())) {
				oaWorkMeetEntity = oaWorkMeetService.getById(oaWorkMeetEntity.getId());
				
				//不是自己预订的会议室不能删除
				if(!StringUtils.equals(oaWorkMeetEntity.getCallUserId(), ShiroUtils.getSessionUserId())) {
					j.setSuccess(false);
					j.setMsg("不是自己创建的会议不能删除");
					return j;
				}
				
				oaWorkMeetService.delete(oaWorkMeetEntity, true);
				
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除会议失败，错误信息:{}", e);
		}
		
		return j;
	}
}
