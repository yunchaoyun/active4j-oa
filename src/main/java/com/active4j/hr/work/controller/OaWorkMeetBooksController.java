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
import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomBooksService;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkMeetBooksController.java
 * @description 
		  会议预定
 * @time  2020年4月6日 上午10:39:48
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/meetRoomBooks")
@Slf4j
public class OaWorkMeetBooksController extends BaseController {

	
	@Autowired
	private OaWorkMeetRoomBooksService oaWorkMeetRoomBooksService;
	@Autowired
	private OaWorkMeetRoomService oaWorkMeetRoomService;
	
	/**
	 * 会议室预定列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView("oa/work/meetroom/meetroombookslist");
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", ListUtils.listToReplaceStr(lstRooms, "name", "id"));
		
		String nowStrDate = DateUtils.date2Str(DateUtils.SDF_YYYY_MM_DD);
		view.addObject("nowStrDate", nowStrDate);
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkMeetRoomBooksEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkMeetRoomBooksEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<OaWorkMeetRoomBooksEntity> lstResult = oaWorkMeetRoomBooksService.page(new Page<OaWorkMeetRoomBooksEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meetroom/meetroombooks");
		
		//查询可用的会议室
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", lstRooms);
		
		if(StringUtils.isNotEmpty(oaWorkMeetRoomBooksEntity.getId())) {
			oaWorkMeetRoomBooksEntity = oaWorkMeetRoomBooksService.getById(oaWorkMeetRoomBooksEntity.getId());
			view.addObject("meet", oaWorkMeetRoomBooksEntity);
		}
		
		return view;
	}
	
	
	/**
	 * 保存方法
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, String meetRoomId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//预定人赋值
			oaWorkMeetRoomBooksEntity.setUserName(ShiroUtils.getSessionUser().getRealName());
			oaWorkMeetRoomBooksEntity.setUserId(ShiroUtils.getSessionUserId());
			
			if(null == oaWorkMeetRoomBooksEntity.getBookDate()) {
				j.setSuccess(false);
				j.setMsg("预定日期不能为空");
				return j;
			}

			if(null == oaWorkMeetRoomBooksEntity.getStartDate() || null == oaWorkMeetRoomBooksEntity.getEndDate()) {
				j.setSuccess(false);
				j.setMsg("请填写完整的预定时间");
				return j;
			}
			
			//日期赋值
			oaWorkMeetRoomBooksEntity.setStrBookDate(DateUtils.date2Str(oaWorkMeetRoomBooksEntity.getBookDate(), DateUtils.SDF_YYYY_MM_DD));
			
			//时间的校验，预定的会议室，时间不能重合
			List<OaWorkMeetRoomBooksEntity> lstRooms = oaWorkMeetRoomBooksService.findMeetBooks(meetRoomId, oaWorkMeetRoomBooksEntity.getStrBookDate());
			if(null != lstRooms && lstRooms.size() > 0) {
				for(OaWorkMeetRoomBooksEntity bookRoom : lstRooms) {
					if(oaWorkMeetRoomBooksEntity.getStartDate().after(bookRoom.getStartDate()) && oaWorkMeetRoomBooksEntity.getStartDate().before(bookRoom.getEndDate())) {
						j.setSuccess(false);
						j.setMsg("当前会议室已经被预定");
						return j;
					}
					if(oaWorkMeetRoomBooksEntity.getEndDate().after(bookRoom.getStartDate()) && oaWorkMeetRoomBooksEntity.getEndDate().before(bookRoom.getEndDate())) {
						j.setSuccess(false);
						j.setMsg("当前会议室已经被预定");
						return j;
					}
				}
			} 
			
			if(StringUtils.isEmpty(oaWorkMeetRoomBooksEntity.getId())) {
				if(StringUtils.isNotEmpty(meetRoomId)) {
					OaWorkMeetRoomEntity room = oaWorkMeetRoomService.getById(meetRoomId);
					if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_WORK_MEET_ROOM_STATUS_STOP)) {
						j.setSuccess(false);
						j.setMsg("会议室不可用");
						return j;
					}
					oaWorkMeetRoomBooksEntity.setMeetRoomId(meetRoomId);
				}
				
				//新增方法
				oaWorkMeetRoomBooksService.save(oaWorkMeetRoomBooksEntity);
				
				
			}else {
				//编辑方法
				OaWorkMeetRoomBooksEntity tmp = oaWorkMeetRoomBooksService.getById(oaWorkMeetRoomBooksEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkMeetRoomBooksEntity, tmp);
				
				if(StringUtils.isNotEmpty(meetRoomId)) {
					OaWorkMeetRoomEntity room = oaWorkMeetRoomService.getById(meetRoomId);
					if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_WORK_MEET_ROOM_STATUS_STOP)) {
						j.setSuccess(false);
						j.setMsg("会议室不可用");
						return j;
					}
					tmp.setMeetRoomId(meetRoomId);
				}
				
				oaWorkMeetRoomBooksService.saveOrUpdate(tmp);
				
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("保存会议室预定信息失败，错误信息:{}", e);
		}
		return j;
	}
	
	/**
	 * 删除
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkMeetRoomBooksEntity.getId())) {
				oaWorkMeetRoomBooksEntity = oaWorkMeetRoomBooksService.getById(oaWorkMeetRoomBooksEntity.getId());
				
				//不是自己预订的会议室不能删除
				if(!StringUtils.equals(oaWorkMeetRoomBooksEntity.getUserId(), ShiroUtils.getSessionUserId())) {
					j.setSuccess(false);
					j.setMsg("不是自己预订的会议室不能删除");
					return j;
				}
				oaWorkMeetRoomBooksService.removeById(oaWorkMeetRoomBooksEntity.getId());
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("删除会议室预定信息失败，错误信息:{}", e);
		}
		
		return j;
	}
}
