package com.active4j.hr.hr.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrUserOverTimeModel;
import com.active4j.hr.hr.entity.OaHrOverTimeEntity;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.service.OaHrOverTimeService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrOverTimeController.java
 * @description 
		加班维护管理
 * @time  2020年4月24日 上午10:00:39
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/overtime")
public class OaHrOverTimeController extends BaseController{

	@Autowired
	private OaHrOverTimeService oaHrOverTimeService;
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	private static final String prefix_page = "oa/hr/overtime/";
	
	/**
	 * 加班记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "overtimelist");
		
		return view;
	}
	
	/**
	 * 加班统计界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/statlist")
	public ModelAndView statlist(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "overtimestatlist");
		
		List<OaHrUserOverTimeModel> lstResult = oaHrOverTimeService.queryOverTimeModel();
		
		view.addObject("lstResult", lstResult);
		
		return view;
	}
	
	
	/**
	 * 查询数据
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrOverTimeEntity oaHrOverTimeEntity, String deptId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		oaHrOverTimeEntity.setDepartId(deptId);
		//拼接查询条件
		QueryWrapper<OaHrOverTimeEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrOverTimeEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrOverTimeEntity> lstResult = oaHrOverTimeService.page(new Page<OaHrOverTimeEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "overtimeadd");
		
		//查询按用户分配权限使用的树形结构
		String userTreeStr = oaHrUserService.getCompanyOfOaUser(null);
		view.addObject("userTreeStr", userTreeStr);
		
		return view;
	}
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(String id, HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "overtimeedit");
		
		if(StringUtils.isNotEmpty(id)) {
			//获取加班实体
			OaHrOverTimeEntity time = oaHrOverTimeService.getById(id);
			view.addObject("time", time);
		}
		
		return view;
	}
	
	
	/**
	 * 保存加班数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public AjaxJson saveAdd(String[] UList, String type, String startTime, String endTime, String hours, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(null == UList || UList.length <= 0) {
				j.setMsg("请选择用户!");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(hours)) {
				j.setSuccess(false);
				j.setMsg("请填写完整的时间");
				return j;
			}
			
			Date startDate = DateUtils.str2Date(startTime, DateUtils.SDF_YYYY_MM_DD_HH_MM);
			Date endDate = DateUtils.str2Date(endTime, DateUtils.SDF_YYYY_MM_DD_HH_MM);
			
			Double d = Double.valueOf(hours);
			
			List<OaHrOverTimeEntity> lstTimes = new ArrayList<OaHrOverTimeEntity>();
			for(String id : UList) {
				OaHrUserEntity user = oaHrUserService.getById(id);
				if(null == user) {
					continue;
				}
				
				OaHrOverTimeEntity time = new OaHrOverTimeEntity();
				time.setUserName(user.getRealName());
				time.setUserId(user.getId());
				
				//获取部门信息
				SysDeptEntity depart = sysDeptService.getById(user.getDeptId());
				time.setDepartCode(depart.getDeptNo());
				time.setDepartName(depart.getName());
				time.setDepartId(depart.getId());
				time.setOptType(GlobalConstant.OPT_TYPE_ADD); //增加
				time.setStartTime(startDate);
				time.setEndTime(endDate);
				time.setType(type);
				time.setHours(d);
				time.setMemo(memo);
				
				lstTimes.add(time);
			}
			//批量保存
			oaHrOverTimeService.saveBatch(lstTimes);
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存加班数据错误");
			log.error("保存加班数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
	/**
	 * 保存加班数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveEdit")
	@ResponseBody
	public AjaxJson saveEdit(String id, String type, String startTime, String endTime, String hours, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(hours)) {
				j.setSuccess(false);
				j.setMsg("请填写完整的时间");
				return j;
			}
			
			Date startDate = DateUtils.str2Date(startTime, DateUtils.SDF_YYYY_MM_DD_HH_MM);
			Date endDate = DateUtils.str2Date(endTime, DateUtils.SDF_YYYY_MM_DD_HH_MM);
			
			Double d = Double.valueOf(hours);
			
			OaHrOverTimeEntity time = oaHrOverTimeService.getById(id);
			time.setType(type);
			time.setStartTime(startDate);
			time.setEndTime(endDate);
			time.setHours(d);
			time.setMemo(memo);
			oaHrOverTimeService.saveOrUpdate(time);
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存加班数据错误");
			log.error("保存加班数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 删除加班数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(id)) {
				//删除
				oaHrOverTimeService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除加班数据错误");
			log.error("删除加班数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
}
