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
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.hr.domain.OaHrTrainCourseModel;
import com.active4j.hr.hr.entity.OaHrTrainCourseCateEntity;
import com.active4j.hr.hr.entity.OaHrTrainCourseEntity;
import com.active4j.hr.hr.entity.OaHrTrainPlanCourseEntity;
import com.active4j.hr.hr.service.OaHrTrainCourseCateService;
import com.active4j.hr.hr.service.OaHrTrainCourseService;
import com.active4j.hr.hr.service.OaHrTrainPlanCourseService;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrTrainCourseController.java
 * @description 
		课程管理
 * @time  2020年4月23日 上午10:45:23
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/train/course")
public class OaHrTrainCourseController extends BaseController{
	
	@Autowired
	private OaHrTrainCourseCateService oaHrTrainCourseCateService;
	
	@Autowired
	private OaHrTrainCourseService oaHrTrainCourseService;
	
	@Autowired
	private OaHrTrainPlanCourseService oaHrTrainPlanCourseService;
	
	@Autowired
	private UploadAttachmentService uploadAttachmentService;

	private static final String prefix_page = "oa/hr/train/course/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "courselist");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param courseEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrTrainCourseModel oaHrTrainCourseModel, String cateId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		IPage<OaHrTrainCourseModel> lstResult = oaHrTrainCourseService.selectPageCourse(dataGrid, cateId);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param courseEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrTrainCourseEntity courseEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(courseEntity.getId())) {
			//获取课程实体
			OaHrTrainCourseEntity course = oaHrTrainCourseService.getById(courseEntity.getId());
			req.setAttribute("course", course);
			//获取类别实体
			OaHrTrainCourseCateEntity cate = oaHrTrainCourseCateService.getById(course.getCateId());
			req.setAttribute("cate", cate);
			
			//获取附件名
			UploadAttachmentEntity attachment = uploadAttachmentService.getById(course.getAttachment());
			if(null != attachment) {
				req.setAttribute("attachment", attachment.getName());
			}
		}
		
		//授课方式
		List<SysDicValueEntity> courseTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_COURSE_TYPE);
		req.setAttribute("courseTypes", courseTypes);
		
		//课程类别
		List<OaHrTrainCourseCateEntity> cates = oaHrTrainCourseCateService.list();
		req.setAttribute("cates", cates);
		
		return new ModelAndView(prefix_page + "courseadd");
	}
	
	/**
	 * 保存
	 * @param courseEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String cateId, OaHrTrainCourseEntity courseEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//课程类别
			if(StringUtils.isEmpty(cateId)) {
				j.setMsg("请选择课程类别！");
				j.setSuccess(false);
				return j;
			}
			
			//课程名称
			if(StringUtils.isEmpty(courseEntity.getName())) {
				j.setMsg("请填写课程名称！");
				j.setSuccess(false);
				return j;
			}
			
			OaHrTrainCourseCateEntity cateEntity = oaHrTrainCourseCateService.getById(cateId);
			if(null == cateEntity) {
				j.setMsg("不存在该课程类别，请重新选择！");
				j.setSuccess(false);
				return j;
			}
			//课程类别id赋值
			courseEntity.setCateId(cateId);
			
			if(StringUtils.isNotEmpty(courseEntity.getId())) {
				OaHrTrainCourseEntity t = oaHrTrainCourseService.getById(courseEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(courseEntity, t);
				//编辑保存
				oaHrTrainCourseService.saveOrUpdate(t);
				
			} else {
				//新增保存
				oaHrTrainCourseService.save(courseEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存课程数据错误");
			j.setSuccess(false);
			log.error("保存课程数据错误，错误原因：{}", e.getMessage());
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
				//删除关联表中的数据
				QueryWrapper<OaHrTrainPlanCourseEntity> queryPlanCourse = new QueryWrapper<OaHrTrainPlanCourseEntity>();
				queryPlanCourse.eq("COURSE_ID", id);
				oaHrTrainPlanCourseService.remove(queryPlanCourse);
				//删除课程
				oaHrTrainCourseService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除课程数据错误");
			log.error("删除课程数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
}
