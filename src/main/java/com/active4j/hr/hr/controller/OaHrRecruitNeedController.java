package com.active4j.hr.hr.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.entity.OaHrRecruitNeedEntity;
import com.active4j.hr.hr.entity.OaHrRecruitPlanNeedEntity;
import com.active4j.hr.hr.service.OaHrRecruitNeedService;
import com.active4j.hr.hr.service.OaHrRecruitPlanNeedService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrRecruitNeedController.java
 * @description 
		招聘管理-招聘需求管理
 * @time  2020年4月21日 下午4:26:41
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/recruit/need")
public class OaHrRecruitNeedController extends BaseController{

	@Autowired
	private OaHrRecruitNeedService oaHrRecruitNeedService;
	
	@Autowired
	private OaHrRecruitPlanNeedService oaHrRecruitPlanNeedService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	private static final String prefix_page = "oa/hr/recruit/need/";
	
	/**
	 * 
	 * @description
	 *  	跳转到招聘需求页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月21日 下午4:07:51
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "needlist");
		
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
	public void datagrid(OaHrRecruitNeedEntity oaHrRecruitNeedEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//拼接查询条件
		QueryWrapper<OaHrRecruitNeedEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrRecruitNeedEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrRecruitNeedEntity> lstResult = oaHrRecruitNeedService.page(new Page<OaHrRecruitNeedEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	跳转新增编辑页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月21日 下午4:15:39
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrRecruitNeedEntity needEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(needEntity.getId())) {
			OaHrRecruitNeedEntity need = oaHrRecruitNeedService.getById(needEntity.getId());
			req.setAttribute("need", need);		
		}
		
		return new ModelAndView(prefix_page + "needadd");
	}
	
	/**
	 * 
	 * @description
	 *  	保存方法
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月21日 下午4:18:59
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaHrRecruitNeedEntity needEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//部门Id不能为空
			if(StringUtils.isEmpty(needEntity.getDepartId())) {
				j.setMsg("请选择需求部门!");
				j.setSuccess(false);
				return j;
			}
			
			//需求职位
			if(StringUtils.isEmpty(needEntity.getNeedJob())) {
				j.setMsg("请填写需求职位!");
				j.setSuccess(false);
				return j;
			}
			
			//需求人数
			if(null == needEntity.getNeedNum()) {
				j.setMsg("请填写需求人数!");
				j.setSuccess(false);
				return j;
			}
			
			try {
				Integer.valueOf(needEntity.getNeedNum());
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("请填写数字！");
				return j;
			}
			
			//看看部门存不存在
			SysDeptEntity depart = sysDeptService.getById(needEntity.getDepartId());
			if(null == depart) {
				j.setMsg("部门选择错误！");
				j.setSuccess(false);
				return j;
			}
			//编号和部门名赋值
			needEntity.setDepartCode(depart.getDeptNo());
			needEntity.setDepartName(depart.getName());
			
			if(StringUtils.isNotEmpty(needEntity.getId())) {
				//编辑保存
				OaHrRecruitNeedEntity t = oaHrRecruitNeedService.getById(needEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(needEntity, t);
				
				oaHrRecruitNeedService.saveOrUpdate(t);
			} else {
				//新增保存
				//时间赋值
				needEntity.setPutDate(new Date());
				oaHrRecruitNeedService.save(needEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存招聘需求信息错误");
			j.setSuccess(false);
			log.error("保存招聘需求信息错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月21日 下午4:26:08
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try{
			if(StringUtils.isNotEmpty(id)) {
				//将公共表中的需求移除
				QueryWrapper<OaHrRecruitPlanNeedEntity> queryPlanNeed = new QueryWrapper<OaHrRecruitPlanNeedEntity>();
				queryPlanNeed.eq("NEED_ID", id);
				oaHrRecruitPlanNeedService.remove(queryPlanNeed);
				//删除
				oaHrRecruitNeedService.removeById(id);
			}
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除招聘需求错误");
			log.error("删除招聘需求错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
}
