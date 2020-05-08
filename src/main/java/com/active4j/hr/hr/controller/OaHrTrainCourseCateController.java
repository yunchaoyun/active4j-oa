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
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.entity.OaHrTrainCourseCateEntity;
import com.active4j.hr.hr.entity.OaHrTrainCourseEntity;
import com.active4j.hr.hr.service.OaHrTrainCourseCateService;
import com.active4j.hr.hr.service.OaHrTrainCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrTrainCourseCateController.java
 * @description 
		课程类别管理
 * @time  2020年4月23日 上午10:22:27
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/train/course/cate")
public class OaHrTrainCourseCateController extends BaseController{
	
	@Autowired
	private OaHrTrainCourseCateService oaHrTrainCourseCateService;
	
	@Autowired
	private OaHrTrainCourseService oaHrTrainCourseService;
	
	private static final String prefix_page = "oa/hr/train/course/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "coursecatelist");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param cateEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrTrainCourseCateEntity oaHrTrainCourseCateEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaHrTrainCourseCateEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrTrainCourseCateEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrTrainCourseCateEntity> lstResult = oaHrTrainCourseCateService.page(new Page<OaHrTrainCourseCateEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param cateEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrTrainCourseCateEntity cateEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(cateEntity.getId())) {
			//获取课程类别实体
			OaHrTrainCourseCateEntity cate = oaHrTrainCourseCateService.getById(cateEntity.getId());
			req.setAttribute("cate", cate);
		}
		
		return new ModelAndView(prefix_page + "coursecateadd");
	}
	
	/**
	 * 保存
	 * @param cateEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaHrTrainCourseCateEntity cateEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//类别名称
			if(StringUtils.isEmpty(cateEntity.getName())) {
				j.setMsg("请填写类别名称！");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isNotEmpty(cateEntity.getId())) {
				//获取课程类别数据
				OaHrTrainCourseCateEntity t = oaHrTrainCourseCateService.getById(cateEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(cateEntity, t);
				//编辑保存
				oaHrTrainCourseCateService.saveOrUpdate(t);
				
			} else {
				//新增保存
				oaHrTrainCourseCateService.save(cateEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存课程类别数据错误");
			j.setSuccess(false);
			log.error("保存课程类别数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 删除课程类别	
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
				//先看看课程表有没有关联的
				QueryWrapper<OaHrTrainCourseEntity> queryCourse = new QueryWrapper<OaHrTrainCourseEntity>();
				queryCourse.eq("CATE_ID", id);
				List<OaHrTrainCourseEntity> lstCourses = oaHrTrainCourseService.list(queryCourse);
				if(null != lstCourses && lstCourses.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该类别下存在课程，请移除后再试");
					return j;
				}
				//删除
				oaHrTrainCourseCateService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除课程类别数据错误");
			log.error("删除课程类别数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
}
