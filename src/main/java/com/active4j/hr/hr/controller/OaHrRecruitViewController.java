package com.active4j.hr.hr.controller;

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
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrRecruitViewModel;
import com.active4j.hr.hr.entity.OaHrRecruitCVEntity;
import com.active4j.hr.hr.entity.OaHrRecruitViewEntity;
import com.active4j.hr.hr.service.OaHrRecruitCVService;
import com.active4j.hr.hr.service.OaHrRecruitViewService;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrRecruitViewController.java
 * @description 
		招聘管理-面试记录
 * @time  2020年4月22日 下午2:44:13
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/recruit/view")
public class OaHrRecruitViewController extends BaseController{
	
	@Autowired
	private OaHrRecruitViewService oaHrRecruitViewService;
	
	@Autowired
	private OaHrRecruitCVService oaHrRecruitCVService;

	private static final String prefix_page = "oa/hr/recruit/view/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "viewlist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转选择简历页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月22日 下午2:45:14
	 */
	@RequestMapping("/selectCv")
	public ModelAndView selectNeed(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "selectCv");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param viewEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrRecruitViewModel oaHrRecruitViewModel, String cvName, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		IPage<OaHrRecruitViewModel> lstResult = oaHrRecruitViewService.selectPageView(dataGrid, cvName);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param viewEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrRecruitViewEntity viewEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(viewEntity.getId())) {
			//获取面试记录实体
			OaHrRecruitViewEntity view = oaHrRecruitViewService.getById(viewEntity.getId());
			req.setAttribute("view", view);
			//获取简历实体
			OaHrRecruitCVEntity cv = oaHrRecruitCVService.getById(view.getCvId());
			req.setAttribute("cv", cv);
		}
		
		//面试方式
		List<SysDicValueEntity> viewTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_VIEW_TYPE);
		req.setAttribute("viewTypes", viewTypes);
				
		return new ModelAndView(prefix_page + "viewadd");
	}
	
	/**
	 * 保存
	 * @param cvId
	 * @param viewEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String cvId, OaHrRecruitViewEntity viewEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//面试内容
			if(StringUtils.isEmpty(viewEntity.getContent())) {
				j.setMsg("请填写面试内容！");
				j.setSuccess(false);
				return j;
			}
			
			//面试意见
			if(StringUtils.isEmpty(viewEntity.getSuggestion())) {
				j.setMsg("请填写面试意见！");
				j.setSuccess(false);
				return j;
			}
			
			//简历选择
			if(StringUtils.isEmpty(cvId)) {
				j.setMsg("请选择简历！");
				j.setSuccess(false);
				return j;
			}
			
			OaHrRecruitCVEntity cvEntity = oaHrRecruitCVService.getById(cvId);
			if(null == cvEntity) {
				j.setMsg("不存在该简历，请重新选择！");
				j.setSuccess(false);
				return j;
			}
			//简历id赋值
			viewEntity.setCvId(cvId);
			
			if(StringUtils.isNotEmpty(viewEntity.getId())) {
				OaHrRecruitViewEntity t = oaHrRecruitViewService.getById(viewEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(viewEntity, t);
				//编辑保存
				oaHrRecruitViewService.saveOrUpdate(t);
				
			} else {
				//新增保存
				oaHrRecruitViewService.save(viewEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存面试记录数据错误");
			j.setSuccess(false);
			log.error("保存面试记录数据错误，错误信息：{}", e.getMessage());
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
				oaHrRecruitViewService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除面试记录数据错误");
			log.error("删除面试记录数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
}
