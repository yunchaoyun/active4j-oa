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
import com.active4j.hr.hr.entity.OaHrOverTimeEntity;
import com.active4j.hr.hr.entity.OaHrSpecialTimeEntity;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.entity.OaHrYearHolidayEntity;
import com.active4j.hr.hr.service.OaHrOverTimeService;
import com.active4j.hr.hr.service.OaHrSpecialTimeService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.active4j.hr.hr.service.OaHrYearHolidayService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrSpecialTimeController.java
 * @description 
		特殊考勤维护管理
 * @time  2020年4月24日 下午2:38:24
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/specialtime")
public class OaHrSpecialTimeController extends BaseController{

	@Autowired
	private OaHrSpecialTimeService oaHrSpecialTimeService;
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	@Autowired
	private OaHrOverTimeService oaHrOverTimeService;
	
	@Autowired
	private OaHrYearHolidayService oaHrYearHolidayService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	private static final String prefix_page = "oa/hr/time/";
	
	/**
	 * 打卡记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "specialtimelist");
		
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
	public void datagrid(OaHrSpecialTimeEntity oaHrSpecialTimeEntity, String deptId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		oaHrSpecialTimeEntity.setDepartId(deptId);
		//拼接查询条件
		QueryWrapper<OaHrSpecialTimeEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrSpecialTimeEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrSpecialTimeEntity> lstResult = oaHrSpecialTimeService.page(new Page<OaHrSpecialTimeEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
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
		ModelAndView view = new ModelAndView(prefix_page + "specialtimeadd");
		
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
		ModelAndView view = new ModelAndView(prefix_page + "specialtimeedit");
		
		if(StringUtils.isNotEmpty(id)) {
			OaHrSpecialTimeEntity time = oaHrSpecialTimeService.getById(id);
			view.addObject("time", time);
		}
		
		return view;
	}
	
	
	/**
	 * 保存特殊考勤数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public AjaxJson saveAdd(String[] UList, String type, String startTime, String endTime, String hours, String memo, boolean generate, boolean isYear, HttpServletRequest request) {
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
			
			List<OaHrSpecialTimeEntity> lstTimes = new ArrayList<OaHrSpecialTimeEntity>();
			for(String id : UList) {
				OaHrUserEntity user = oaHrUserService.getById(id);
				if(null == user) {
					continue;
				}
				
				OaHrSpecialTimeEntity time = new OaHrSpecialTimeEntity();
				time.setUserName(user.getRealName());
				time.setUserId(user.getId());
				
				//获取部门信息
				SysDeptEntity depart = sysDeptService.getById(user.getDeptId());
				time.setDepartCode(depart.getDeptNo());
				time.setDepartName(depart.getName());
				time.setDepartId(depart.getId());
				time.setStartTime(startDate);
				time.setEndTime(endDate);
				time.setType(type);
				time.setHours(d);
				time.setMemo(memo);
				
				//处理调休的情况，要对冲加班数据
				if(generate) {
					OaHrOverTimeEntity over = new OaHrOverTimeEntity();
					over.setUserName(user.getRealName());
					over.setUserId(user.getId());
					over.setDepartCode(depart.getDeptNo());
					over.setDepartName(depart.getName());
					over.setDepartId(depart.getId());
					over.setStartTime(startDate);
					over.setEndTime(endDate);
					over.setType(GlobalConstant.OA_OVER_TIME_TYPE_OFF); //调休
					over.setHours(d);
					over.setMemo("调休扣除");
					over.setOptType(GlobalConstant.OPT_TYPE_SUB);
					
					oaHrOverTimeService.save(over);
					//维护一下ID 以便数据修改
					time.setOverTimeId(over.getId());
				}
				
				//处理年假的情况，扣除年假
				if(isYear) {
					OaHrYearHolidayEntity year = new OaHrYearHolidayEntity();
					year.setUserName(user.getRealName());
					year.setUserId(user.getId());
					year.setDepartCode(depart.getDeptNo());
					year.setDepartName(depart.getName());
					year.setDepartId(depart.getId());
					year.setOptType(GlobalConstant.OPT_TYPE_SUB);
					year.setMemo("年假扣除");
					year.setDays(d);
					year.setYear(DateUtils.date2Str(DateUtils.SDF_YYYY));
					
					oaHrYearHolidayService.save(year);
					//维护一下ID 以便数据修改
					time.setYearHolidayId(year.getId());
				}
			
				lstTimes.add(time);
				
			}
			//保存特殊考勤
			oaHrSpecialTimeService.saveBatch(lstTimes);
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存特殊考勤数据错误");
			log.error("保存特殊考勤数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
	/**
	 * 保存特殊考勤数据
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
			
			OaHrSpecialTimeEntity time = oaHrSpecialTimeService.getById(id);
			time.setType(type);
			time.setStartTime(startDate);
			time.setEndTime(endDate);
			time.setHours(d);
			time.setMemo(memo);
			oaHrSpecialTimeService.saveOrUpdate(time);
			
			if(StringUtils.isNotEmpty(time.getOverTimeId())) {
				OaHrOverTimeEntity over = oaHrOverTimeService.getById(time.getOverTimeId());
				over.setType(type);
				over.setStartTime(startDate);
				over.setEndTime(endDate);
				over.setHours(d);
				oaHrOverTimeService.saveOrUpdate(over);
			}
			
			if(StringUtils.isNotEmpty(time.getYearHolidayId())) {
				OaHrYearHolidayEntity year = oaHrYearHolidayService.getById(time.getYearHolidayId());
				year.setDays(d);
				oaHrYearHolidayService.saveOrUpdate(year);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存特殊考勤数据错误");
			log.error("保存特殊考勤数据错误", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 删除特殊考勤数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(id)) {
				oaHrSpecialTimeService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除特殊考勤数据错误");
			log.error("删除特殊考勤错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
}
