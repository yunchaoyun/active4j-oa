package com.active4j.hr.hr.controller;

import java.util.ArrayList;
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
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrUserYearHolidayModel;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.entity.OaHrYearHolidayEntity;
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
 * @title OaHrYearHolidayController.java
 * @description 
		年假维护
 * @time  2020年4月24日 下午1:58:00
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/yearholiday")
public class OaHrYearHolidayController extends BaseController{

	@Autowired
	private OaHrYearHolidayService oaHrYearHolidayService;
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	private static final String prefix_page = "oa/hr/year/";
	
	/**
	 * 跳转到年假界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "yearholidaylist");
		
		return view;
	}
	
	
	/**
	 * 年假统计界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/statlist")
	public ModelAndView statlist(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "yearholidaystatlist");
		
		List<OaHrUserYearHolidayModel> lstResult = oaHrYearHolidayService.queryYearHolidayModel();
		
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
	public void datagrid(OaHrYearHolidayEntity oaHrYearHolidayEntity, String deptId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		oaHrYearHolidayEntity.setDepartId(deptId);
		//拼接查询条件
		QueryWrapper<OaHrYearHolidayEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrYearHolidayEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrYearHolidayEntity> lstResult = oaHrYearHolidayService.page(new Page<OaHrYearHolidayEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
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
		ModelAndView view = new ModelAndView(prefix_page + "yearholidayadd");
		
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
		ModelAndView view = new ModelAndView(prefix_page + "yearholidayedit");
		
		if(StringUtils.isNotEmpty(id)) {
			OaHrYearHolidayEntity time = oaHrYearHolidayService.getById(id);
			view.addObject("time", time);
			
		}
		
		return view;
	}

	
	/**
	 * 保存年假数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public AjaxJson saveAdd(String[] UList, String year, String days, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(null == UList || UList.length <= 0) {
				j.setMsg("请选择用户!");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(year)) {
				j.setSuccess(false);
				j.setMsg("请填写年假年份");
				return j;
			}
			
			
			Double d = Double.valueOf(days);
			
			List<OaHrYearHolidayEntity> lstTimes = new ArrayList<OaHrYearHolidayEntity>();
			for(String id : UList) {
				OaHrUserEntity user = oaHrUserService.getById(id);
				if(null == user) {
					continue;
				}
				
				OaHrYearHolidayEntity time = new OaHrYearHolidayEntity();
				time.setUserName(user.getRealName());
				time.setUserId(user.getId());
				
				//获取部门信息
				SysDeptEntity depart = sysDeptService.getById(user.getDeptId());
				time.setDepartCode(depart.getDeptNo());
				time.setDepartName(depart.getName());
				time.setDepartId(depart.getId());
				
				time.setOptType(GlobalConstant.OPT_TYPE_ADD); //增加
				
				time.setYear(year);
				time.setDays(d);
				time.setMemo(memo);
				
				lstTimes.add(time);
			}
			//批量保存
			oaHrYearHolidayService.saveBatch(lstTimes);
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存年假数据错误");
			log.error("保存年假数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
	/**
	 * 保存年假数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveEdit")
	@ResponseBody
	public AjaxJson saveEdit(String id, String year, String days, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(year)) {
				j.setSuccess(false);
				j.setMsg("请填写年假年份");
				return j;
			}
			
			Double d = Double.valueOf(days);
			
			OaHrYearHolidayEntity time = oaHrYearHolidayService.getById(id);
			time.setYear(year);;
			time.setDays(d);
			time.setMemo(memo);
			oaHrYearHolidayService.saveOrUpdate(time);
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存年假数据错误");
			log.error("保存年假数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
	/**
	 * 删除年假数据
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
				oaHrYearHolidayService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除年假数据错误");
			log.error("删除年假数据错误，错误原因：{}", e.getMessage());
		}
		
		
		return j;
	}
}
