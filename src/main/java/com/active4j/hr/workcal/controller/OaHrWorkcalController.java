package com.active4j.hr.workcal.controller;

import java.util.ArrayList;
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
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.workcal.entity.OaHrWorkcalRuleEntity;
import com.active4j.hr.workcal.service.OaHrWorkcalRuleService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrWorkcalController.java
 * @description 
		考勤管理-工作日历管理
 * @time  2020年4月20日 上午10:30:37
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/hr/workcal")
@Slf4j
public class OaHrWorkcalController extends BaseController {
	
	@Autowired
	private OaHrWorkcalRuleService oaHrWorkcalRuleService;
	
	private static final String prefix_page = "oa/hr/workcal/";
	
	/**
	 * 
	 * @description
	 *  	跳转到列表页
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月10日 下午2:33:22
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView(prefix_page + "workcalrulelist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	获取表格数据
	 * @return void
	 * @author guyp
	 * @time 2020年4月10日 下午2:34:52
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrWorkcalRuleEntity oaHrWorkcalRuleEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//拼接查询条件
		QueryWrapper<OaHrWorkcalRuleEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrWorkcalRuleEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrWorkcalRuleEntity> lstResult = oaHrWorkcalRuleService.page(new Page<OaHrWorkcalRuleEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	跳转日历视图
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月20日 上午10:44:28
	 */
	@RequestMapping("/goInitYear")
	public ModelAndView goInitYear(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "inityear");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	保存当前年考勤规则数据
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月20日 下午1:39:40
	 */
	@RequestMapping("/saveInitYear")
	@ResponseBody
	public AjaxJson saveInitYear(String year, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try{
			if(StringUtils.isEmpty(year)) {
				j.setSuccess(false);
				j.setMsg("年份不能为空");
				return j;
			}
			
			//初始化
			oaHrWorkcalRuleService.initYearRule(year);
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg("保存考勤规则失败");
			log.error("保存初始化考勤规则失败，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	 保存当前年考勤规则数据
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月20日 上午11:22:24
	 */
	@RequestMapping("/saveEditTime")
	@ResponseBody
	public AjaxJson saveEditTime(String editDate_begin, String editDate_end, String startTime, String endTime, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(editDate_begin)) {
				j.setSuccess(false);
				j.setMsg("修改日期范围不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(editDate_end)) {
				j.setSuccess(false);
				j.setMsg("修改日期范围不能为空");
				return j;
			}
			
			QueryWrapper<OaHrWorkcalRuleEntity> queryWrapper = new QueryWrapper<OaHrWorkcalRuleEntity>();
			queryWrapper.ge("CURRENT_DATE_TIME", editDate_begin);
			queryWrapper.le("CURRENT_DATE_TIME", editDate_end);
			List<OaHrWorkcalRuleEntity> lstRules = oaHrWorkcalRuleService.list(queryWrapper);
			
			List<OaHrWorkcalRuleEntity> saveLst = new ArrayList<OaHrWorkcalRuleEntity>();
			if(null != lstRules && lstRules.size() > 0) {
				for(OaHrWorkcalRuleEntity rule : lstRules) {
					if(StringUtils.isNotEmpty(startTime)) {
						rule.setStartTime(startTime);
					}
					if(StringUtils.isNotEmpty(endTime)) {
						rule.setEndTime(endTime);
					}
					saveLst.add(rule);
				}
			}
			//批量保存
			oaHrWorkcalRuleService.saveOrUpdateBatch(saveLst);
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg("修改考勤日历规则时间失败");
			log.error("修改考勤日历规则时间失败，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转编辑时间
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月20日 上午11:23:53
	 */
	@RequestMapping("/goEditTime")
	public ModelAndView goEditTime(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "edittime");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转编辑类型
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月20日 上午11:24:23
	 */
	@RequestMapping("/goEditType")
	public ModelAndView goEditType(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "edittype");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	日历视图
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月20日 下午1:44:55
	 */
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "workcalview");
		
		//查询当前年
		String currentYear = DateUtils.getCurrentYear();
		view.addObject("currentYear", currentYear);
		
		//查询系统中的年份配置
		List<String> lstYears = oaHrWorkcalRuleService.getYearGroups();
		view.addObject("lstYears", lstYears);
		
		
		List<Map<String, String>> workdays = new ArrayList<Map<String, String>>();
		List<Map<String, String>> holidays = new ArrayList<Map<String, String>>();
		List<Map<String, String>> extrdays = new ArrayList<Map<String, String>>();
		List<Map<String, String>> offdays = new ArrayList<Map<String, String>>();
		//查询当前年的工作日历配置
		QueryWrapper<OaHrWorkcalRuleEntity> queryWrapper = new QueryWrapper<OaHrWorkcalRuleEntity>();
		queryWrapper.ge("YEAR", currentYear);
		List<OaHrWorkcalRuleEntity> lstRules = oaHrWorkcalRuleService.list(queryWrapper);
		if(null != lstRules && lstRules.size() > 0) {
			for(OaHrWorkcalRuleEntity r : lstRules) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("date", DateUtils.date2Str(r.getCurrentDateTime(), DateUtils.SDF_YYYYMMDD));
				map.put("name", r.getWeekStr());
				if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_WORKDAY)) {
					//工作日
					workdays.add(map);
				} else if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_HOLIDAY)) {
					//法定假日
					holidays.add(map);
				} else if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_SPECIAL)) {
					//公司假期
					extrdays.add(map);
				} else if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_OFF)) {
					//公司调休
					offdays.add(map);
				}
			}
		}
		String workdaysJson = JSON.toJSONString(workdays);
		String holidaysJson = JSON.toJSONString(holidays);
		String extrdaysJson = JSON.toJSONString(extrdays);
		String offdaysJson = JSON.toJSONString(offdays);
		view.addObject("workdays", workdaysJson);
		view.addObject("holidays", holidaysJson);
		view.addObject("extrdays", extrdaysJson);
		view.addObject("offdays", offdaysJson);
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	获取当前年考勤规则数据
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月20日 下午1:49:48
	 */
	@RequestMapping("/getYearData")
	@ResponseBody
	public AjaxJson getYearData(String year, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try{
			Map<String, Object> mapResult = new HashMap<String, Object>();
			
			List<Map<String, String>> workdays = new ArrayList<Map<String, String>>();
			List<Map<String, String>> holidays = new ArrayList<Map<String, String>>();
			List<Map<String, String>> extrdays = new ArrayList<Map<String, String>>();
			List<Map<String, String>> offdays = new ArrayList<Map<String, String>>();
			//查询当前年的工作日历配置
			QueryWrapper<OaHrWorkcalRuleEntity> queryWrapper = new QueryWrapper<OaHrWorkcalRuleEntity>();
			queryWrapper.ge("YEAR", year);
			List<OaHrWorkcalRuleEntity> lstRules = oaHrWorkcalRuleService.list(queryWrapper);
			if(null != lstRules && lstRules.size() > 0) {
				for(OaHrWorkcalRuleEntity r : lstRules) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("date", DateUtils.date2Str(r.getCurrentDateTime(), DateUtils.SDF_YYYYMMDD));
					map.put("name", r.getWeekStr());
					if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_WORKDAY)) {
						//工作日
						workdays.add(map);
					} else if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_HOLIDAY)) {
						//法定假日
						holidays.add(map);
					} else if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_SPECIAL)) {
						extrdays.add(map);
					} else if(StringUtils.equals(r.getType(), GlobalConstant.OA_WORKCAL_TYPE_OFF)){
						offdays.add(map);
					}
				}
			}
			
			mapResult.put("workdays", workdays);
			mapResult.put("holidays", holidays);
			mapResult.put("extrdays", extrdays);
			mapResult.put("offdays", extrdays);
			
			j.setAttributes(mapResult);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg("获取考勤规则失败");
			log.error("获取考勤规则失败，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
}
