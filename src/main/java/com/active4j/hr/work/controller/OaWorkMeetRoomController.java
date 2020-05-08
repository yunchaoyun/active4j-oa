package com.active4j.hr.work.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.work.domain.OaWorkBookRoomDomain;
import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomBooksService;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkMeetTypeController.java
 * @description 工作中心 会议室
 * @time 2020年4月6日 上午9:42:03
 * @author 麻木神
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/work/meetRoom")
@Slf4j
public class OaWorkMeetRoomController extends BaseController {

	@Autowired
	private OaWorkMeetRoomService oaWorkMeetRoomService;
	
	@Autowired
	private OaWorkMeetRoomBooksService oaWorkMeetRoomBooksService;

	/**
	 * 会议室列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("oa/work/meetroom/meetroomlist");
	}

	/**
	 * 查询数据
	 * 
	 * @param oaWorkMeetRoomEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkMeetRoomEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkMeetRoomEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<OaWorkMeetRoomEntity> lstResult = oaWorkMeetRoomService.page(new Page<OaWorkMeetRoomEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
	
		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param oaWorkMeetRoomEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meetroom/meetroom");
		
		if(StringUtils.isNotEmpty(oaWorkMeetRoomEntity.getId())) {
			oaWorkMeetRoomEntity = oaWorkMeetRoomService.getById(oaWorkMeetRoomEntity.getId());
			view.addObject("meet", oaWorkMeetRoomEntity);
		}
		
		return view;
	}
	
	/**
	 * 预定会议室
	 * @param oaWorkMeetRoomEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/bookview")
	public ModelAndView bookview(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, String currentDate, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meetroom/roombooks");
		
		if(StringUtils.isNotEmpty(oaWorkMeetRoomEntity.getId())) {
			oaWorkMeetRoomEntity = oaWorkMeetRoomService.getById(oaWorkMeetRoomEntity.getId());
			view.addObject("meetRoomName", oaWorkMeetRoomEntity.getName());
			view.addObject("meetRoomId", oaWorkMeetRoomEntity.getId());
			if(StringUtils.isNotEmpty(currentDate)) {
				Date bookDate = DateUtils.str2Date(currentDate, DateUtils.SDF_YYYY_MM_DD);
				view.addObject("bookDate", bookDate);
			}
		}
		
		return view;
	}
	
	/**
	 * 预定会议室
	 * @param oaWorkMeetRoomEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meetroom/viewbooks");
		view.addObject("lstBooks", "-1");
		if(StringUtils.isNotEmpty(oaWorkMeetRoomEntity.getId())) {
			oaWorkMeetRoomEntity = oaWorkMeetRoomService.getById(oaWorkMeetRoomEntity.getId());
			view.addObject("roomName", oaWorkMeetRoomEntity.getName());
			view.addObject("roomId", oaWorkMeetRoomEntity.getId());
			
			List<OaWorkMeetRoomBooksEntity> lstBooks = oaWorkMeetRoomBooksService.findMeetBooks(oaWorkMeetRoomEntity);
			List<OaWorkBookRoomDomain> lstBookDoamins = new ArrayList<OaWorkBookRoomDomain>();
			if(null != lstBooks && lstBooks.size() > 0) {
				for(OaWorkMeetRoomBooksEntity book : lstBooks) {
					OaWorkBookRoomDomain domain = new OaWorkBookRoomDomain();
					domain.setId(book.getId());
					domain.setTitle("预定人:" + book.getCreateName());
					String startTime = DateUtils.date2Str(book.getStartDate(), DateUtils.SDF_HHMM);
					String endTime = DateUtils.date2Str(book.getEndDate(), DateUtils.SDF_HHMM);
					domain.setStart(book.getStrBookDate() + " " + startTime);
					domain.setEnd(book.getStrBookDate() + " " + endTime);
					lstBookDoamins.add(domain);
				}
				view.addObject("lstBooks", JSON.toJSONString(lstBookDoamins));
			}
		}
		
		//查询可用的会议室
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", lstRooms);
		
		return view;
	}
	
	@RequestMapping("/getRoomView")
	@ResponseBody
	public AjaxJson getRoomView(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(StringUtils.isNotEmpty(oaWorkMeetRoomEntity.getId())) {
				oaWorkMeetRoomEntity = oaWorkMeetRoomService.getById(oaWorkMeetRoomEntity.getId());
				map.put("roomName", oaWorkMeetRoomEntity.getName());
				
				List<OaWorkMeetRoomBooksEntity> lstBooks = oaWorkMeetRoomBooksService.findMeetBooks(oaWorkMeetRoomEntity);
				List<OaWorkBookRoomDomain> lstBookDoamins = new ArrayList<OaWorkBookRoomDomain>();
				if(null != lstBooks && lstBooks.size() > 0) {
					for(OaWorkMeetRoomBooksEntity book : lstBooks) {
						OaWorkBookRoomDomain domain = new OaWorkBookRoomDomain();
						domain.setId(book.getId());
						domain.setTitle("预定人:" + book.getCreateName());
						String startTime = DateUtils.date2Str(book.getStartDate(), DateUtils.SDF_HHMM);
						String endTime = DateUtils.date2Str(book.getEndDate(), DateUtils.SDF_HHMM);
						domain.setStart(book.getStrBookDate() + " " + startTime);
						domain.setEnd(book.getStrBookDate() + " " + endTime);
						lstBookDoamins.add(domain);
					}
				}
				map.put("lstBooks", lstBookDoamins);
			}
			
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("查询会议室预定失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 保存方法
	 * @param oaWorkMeetRoomEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(oaWorkMeetRoomEntity.getName())) {
				j.setSuccess(false);
				j.setMsg("名称不能为空!");
				return j;
			}
			
			if(StringUtils.isEmpty(oaWorkMeetRoomEntity.getId())) {
				//新增方法
				oaWorkMeetRoomService.save(oaWorkMeetRoomEntity);
			}else {
				//编辑方法
				OaWorkMeetRoomEntity tmp = oaWorkMeetRoomService.getById(oaWorkMeetRoomEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkMeetRoomEntity, tmp);
				oaWorkMeetRoomService.saveOrUpdate(tmp);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("保存会议室失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	/**
	 * 删除
	 * @param oaWorkMeetRoomEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(OaWorkMeetRoomEntity oaWorkMeetRoomEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkMeetRoomEntity.getId())) {
				oaWorkMeetRoomService.removeById(oaWorkMeetRoomEntity.getId());
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("删除会议室失败，错误信息:{}", e);
		}
		return j;
	}
}
