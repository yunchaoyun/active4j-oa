package com.active4j.hr.hr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.hr.entity.OaHrJobEntity;
import com.active4j.hr.hr.service.OaHrJobService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrJobController.java
 * @description 
		oa 岗位管理
 * @time  2020年4月9日 下午3:46:44
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/hr/job")
@Slf4j
public class OaHrJobController extends BaseController {
	
	@Autowired
	private OaHrJobService oaHrJobService;
	
	/**
	 * 
	 * @description
	 *  	 组织架构-部门信息跳转
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月9日 上午9:47:20
	 */
	@RequestMapping("/oajob")
	public ModelAndView oaJob(HttpServletRequest req) {
		
		return new ModelAndView("oa/hr/organization/oajob");
	}
	
	/**
	 * 
	 * @description
	 *  	获取岗位信息
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月9日 下午2:27:43
	 */
	@RequestMapping("/getJob")
	@ResponseBody
	public AjaxJson getJob(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(id)) {
				Map<String, Object> map = new HashMap<String, Object>();
				//获取岗位信息
				OaHrJobEntity job = oaHrJobService.getById(id);
				map.put("job", job);
				//获取父岗位信息
				if(null != job.getParentId()){
					OaHrJobEntity parent = oaHrJobService.getById(job.getParentId());
					map.put("parentName", parent.getJobName());
					map.put("parentId", parent.getId());
				}
				
				j.setAttributes(map);
			}
		}catch(Exception e) {
			log.error("获取岗位信息报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("获取岗位信息错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	岗位保存
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月9日 下午4:18:11
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存部门信息", memo = "新增或编辑保存了部门信息")
	public AjaxJson save(OaHrJobEntity job, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isEmpty(job.getParentId())) {
				job.setLevel(0);
				job.setParentId(null);
			}else {
				OaHrJobEntity parent = oaHrJobService.getById(job.getParentId());
				job.setLevel(parent.getLevel() + 1);
			}
			
			if(StringUtils.isNotEmpty(job.getParentId()) && StringUtils.equals(job.getParentId(), job.getId())) {
				j.setSuccess(false);
				j.setMsg("上级岗位不能选择自己，请重新选择");
				return j;
			}
			
			if (StringUtils.isNotEmpty(job.getId())) {
				//编辑保存
				OaHrJobEntity tmp = oaHrJobService.getById(job.getId());
				MyBeanUtils.copyBeanNotNull2Bean(job, tmp);
				if(StringUtils.isEmpty(job.getParentId())) {
					tmp.setParentId(null);
				}
				oaHrJobService.saveOrUpdate(tmp);
			}else {
				//新增保存
				oaHrJobService.save(job);
			}
		}catch(Exception e) {
			log.error("保存岗位信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存岗位错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除岗位
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月9日 下午4:16:54
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除岗位信息", memo = "删除了岗位信息")
	public AjaxJson del(OaHrJobEntity job, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try {
			if(StringUtils.isNotEmpty(job.getId())) {
				
				List<OaHrJobEntity> lstJobs = oaHrJobService.getChildJobsByJobId(job.getId());
				if(null != lstJobs && lstJobs.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该岗位下存在子岗位，不能直接删除");
					return j;
				}
				//删除岗位
				oaHrJobService.removeById(job.getId());
			}
			
		}catch(Exception e) {
			log.error("删除岗位信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除岗位错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
