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
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.entity.OaHrTrainCourseEntity;
import com.active4j.hr.hr.entity.OaHrTrainForceEntity;
import com.active4j.hr.hr.entity.OaHrTrainPlanCourseEntity;
import com.active4j.hr.hr.entity.OaHrTrainPlanEntity;
import com.active4j.hr.hr.service.OaHrTrainCourseService;
import com.active4j.hr.hr.service.OaHrTrainForceService;
import com.active4j.hr.hr.service.OaHrTrainPlanCourseService;
import com.active4j.hr.hr.service.OaHrTrainPlanService;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrTrainPlanController.java
 * @description 
		课程计划管理
 * @time  2020年4月23日 上午11:26:56
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/train/plan")
public class OaHrTrainPlanController extends BaseController{
	
	@Autowired
	private OaHrTrainCourseService oaHrTrainCourseService;
	
	@Autowired
	private OaHrTrainPlanService oaHrTrainPlanService;
	
	@Autowired
	private OaHrTrainForceService oaHrTrainForceService;
	
	@Autowired
	private OaHrTrainPlanCourseService oaHrTrainPlanCourseService;

	private static final String prefix_page = "oa/hr/train/plan/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "planlist");
		
		return view;
	}
	
	/**
	 * 选择课程
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectCourse")
	public ModelAndView selectNeed(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "selectcourse");
		
		return view;
	}
	
	
	/**
	 * 查询数据
	 * @param planEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrTrainPlanEntity oaHrTrainPlanEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaHrTrainPlanEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrTrainPlanEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrTrainPlanEntity> lstResult = oaHrTrainPlanService.page(new Page<OaHrTrainPlanEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param planEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrTrainPlanEntity planEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(planEntity.getId())) {
			//获取计划实体
			OaHrTrainPlanEntity plan = oaHrTrainPlanService.getById(planEntity.getId());
			req.setAttribute("plan", plan);
			
			//获得课程
			QueryWrapper<OaHrTrainPlanCourseEntity> queryPlanCourse = new QueryWrapper<OaHrTrainPlanCourseEntity>();
			queryPlanCourse.eq("PLAN_ID", plan.getId());
			List<OaHrTrainPlanCourseEntity> lstPlanCourses = oaHrTrainPlanCourseService.list(queryPlanCourse);
//			List<OaTrainCourseEntity> courses = plan.getCourses();
			
			String courseIds = "";
			String courseNames = "";
			if(null != lstPlanCourses && lstPlanCourses.size() > 0) {
				for (OaHrTrainPlanCourseEntity planCourse : lstPlanCourses) {
					if(null != planCourse) {
						OaHrTrainCourseEntity course = oaHrTrainCourseService.getById(planCourse.getCourseId());
						courseIds += course.getId() + ",";
						courseNames += course.getName() + ",";
					}
					
				}
			}
			req.setAttribute("courseIds", courseIds);
			req.setAttribute("courseNames", courseNames);
		}
		
		//培训周期
		List<SysDicValueEntity> plancycles = SystemUtils.getDictionaryLst(SysConstant.DIC_PLAN_CYCLE);
		req.setAttribute("plancycles", plancycles);
		
		return new ModelAndView(prefix_page + "planadd");
	}
	
	/**
	 * 保存
	 * @param courseIds
	 * @param planEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String courseIds, OaHrTrainPlanEntity planEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//课程选择
			if(StringUtils.isEmpty(courseIds)) {
				j.setMsg("请选择课程！");
				j.setSuccess(false);
				return j;
			}
			
			//计划名称
			if(StringUtils.isEmpty(planEntity.getName())) {
				j.setMsg("请填写计划名称！");
				j.setSuccess(false);
				return j;
			}
			
			//得到课程
			List<OaHrTrainCourseEntity> courses = new ArrayList<OaHrTrainCourseEntity>();
			for(String courseId : courseIds.split(",")) {
				OaHrTrainCourseEntity course = oaHrTrainCourseService.getById(courseId);
				
				if(null == course) {
					continue;
				}
				courses.add(course);
			}
			//编辑保存
			if(StringUtils.isNotEmpty(planEntity.getId())) {
				OaHrTrainPlanEntity t = oaHrTrainPlanService.getById(planEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(planEntity, t);
				//保存计划实体
				oaHrTrainPlanService.saveOrUpdate(t);
				//将公共表中的计划移除重新保存
				QueryWrapper<OaHrTrainPlanCourseEntity> queryPlanCourse = new QueryWrapper<OaHrTrainPlanCourseEntity>();
				queryPlanCourse.eq("PLAN_ID", planEntity.getId());
				oaHrTrainPlanCourseService.remove(queryPlanCourse);
				//重新保存中间表
				if(null != courses && courses.size() > 0) {
					List<OaHrTrainPlanCourseEntity> lstPcs = new ArrayList<OaHrTrainPlanCourseEntity>();
					for(OaHrTrainCourseEntity course : courses) {
						OaHrTrainPlanCourseEntity pc = new OaHrTrainPlanCourseEntity();
						pc.setPlanId(planEntity.getId());
						pc.setCourseId(course.getId());
						lstPcs.add(pc);
					}
					//批量保存中间表
					oaHrTrainPlanCourseService.saveBatch(lstPcs);
				}
				
			} else {
				//新增保存计划
				oaHrTrainPlanService.save(planEntity);
				//新增保存中间表
				if(null != courses && courses.size() > 0) {
					List<OaHrTrainPlanCourseEntity> lstPcs = new ArrayList<OaHrTrainPlanCourseEntity>();
					for(OaHrTrainCourseEntity course : courses) {
						OaHrTrainPlanCourseEntity pc = new OaHrTrainPlanCourseEntity();
						pc.setPlanId(planEntity.getId());
						pc.setCourseId(course.getId());
						lstPcs.add(pc);
					}
					//批量保存中间表
					oaHrTrainPlanCourseService.saveBatch(lstPcs);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存培训计划数据错误");
			j.setSuccess(false);
			log.error("保存培训计划数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	TODO
	 * @params
	 *      TODO
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月23日 下午2:19:34
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择一条记录");
				return j;
			}
			
			//看看实施表中有没有关联的
			QueryWrapper<OaHrTrainForceEntity> queryForce = new QueryWrapper<OaHrTrainForceEntity>();
			queryForce.eq("PLAN_ID", id);
			List<OaHrTrainForceEntity> lstForces = oaHrTrainForceService.list(queryForce);
			if(null != lstForces && lstForces.size() > 0) {
				j.setSuccess(false);
				j.setMsg("该计划存在于实施信息中，请移除后再试");
				return j;
			}
			
			//将公共表中的计划移除
			QueryWrapper<OaHrTrainPlanCourseEntity> queryPlanCourse = new QueryWrapper<OaHrTrainPlanCourseEntity>();
			queryPlanCourse.eq("PLAN_ID", id);
			oaHrTrainPlanCourseService.remove(queryPlanCourse);
			//删除计划实体
			oaHrTrainPlanService.removeById(id);
			
		}catch(Exception e){
			log.error("删除培训计划数据报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除培训计划数据错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	
}
