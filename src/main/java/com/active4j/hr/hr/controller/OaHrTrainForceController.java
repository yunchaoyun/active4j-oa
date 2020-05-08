package com.active4j.hr.hr.controller;

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
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrTrainForceModel;
import com.active4j.hr.hr.entity.OaHrTrainForceEntity;
import com.active4j.hr.hr.entity.OaHrTrainPlanEntity;
import com.active4j.hr.hr.service.OaHrTrainForceService;
import com.active4j.hr.hr.service.OaHrTrainPlanService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrTrainForceController.java
 * @description 
		培训实施管理
 * @time  2020年4月23日 上午11:16:41
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/train/force")
public class OaHrTrainForceController extends BaseController{
	
	@Autowired
	private OaHrTrainPlanService oaHrTrainPlanService;
	
	@Autowired
	private OaHrTrainForceService oaHrTrainForceService;
	
	private static final String prefix_page = "oa/hr/train/force/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "forcelist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转选择计划页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月23日 上午11:17:51
	 */
	@RequestMapping("/selectPlan")
	public ModelAndView selectNeed(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "selectPlan");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param forceEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrTrainForceModel oaHrTrainForceModel, String planName, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		IPage<OaHrTrainForceModel> lstResult = oaHrTrainForceService.selectPageForce(dataGrid, planName);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param forceEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrTrainForceEntity forceEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(forceEntity.getId())) {
			//获取实施实体
			OaHrTrainForceEntity force = oaHrTrainForceService.getById(forceEntity.getId());
			req.setAttribute("force", force);
			//获取计划实体
			OaHrTrainPlanEntity plan = oaHrTrainPlanService.getById(force.getPlanId());
			req.setAttribute("plan", plan);
			
		}
				
		return new ModelAndView(prefix_page + "forceadd");
	}
	
	/**
	 * 保存
	 * @param planId
	 * @param viewEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String planId, OaHrTrainForceEntity forceEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//计划选择
			if(StringUtils.isEmpty(planId)) {
				j.setMsg("请选择培训计划！");
				j.setSuccess(false);
				return j;
			}
			
			OaHrTrainPlanEntity planEntity = oaHrTrainPlanService.getById(planId);
			if(null == planEntity) {
				j.setMsg("不存在该培训计划，请重新选择！");
				j.setSuccess(false);
				return j;
			}
			
			//计划id赋值
			forceEntity.setPlanId(planId);
			
			if(StringUtils.isNotEmpty(forceEntity.getId())) {
				OaHrTrainForceEntity t = oaHrTrainForceService.getById(forceEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(forceEntity, t);
				//编辑保存
				oaHrTrainForceService.saveOrUpdate(t);
				
			} else {
				//新增保存
				oaHrTrainForceService.save(forceEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存培训实施数据错误");
			j.setSuccess(false);
			log.error("保存培训实施数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 删除面试记录
	 * @param id
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
				oaHrTrainForceService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除培训实施数据错误");
			log.error("删除培训实施数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
}
