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
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.util.SystemUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaHrDepartController.java
 * @description 
		oa 部门管理
 * @time  2020年4月9日 上午9:10:55
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/hr/depart")
@Slf4j
public class OaHrDepartController extends BaseController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 
	 * @description
	 *  	 组织架构-部门信息跳转
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月9日 上午9:47:20
	 */
	@RequestMapping("/oadepart")
	public ModelAndView oaDepart(HttpServletRequest req) {
		
		//查询数据字典
		List<SysDicValueEntity> types = SystemUtils.getDictionaryLst(SysConstant.DIC_DEPART_TYPE);
		req.setAttribute("types", types);
		
		return new ModelAndView("oa/hr/organization/oadepart");
	}
	
	/**
	 * 
	 * @description
	 *  	获取部门信息
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月9日 下午2:27:43
	 */
	@RequestMapping("/getDepart")
	@ResponseBody
	public AjaxJson getDepart(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isNotEmpty(id)) {
				Map<String, Object> map = new HashMap<String, Object>();
				//获取部门信息
				SysDeptEntity depart = sysDeptService.getById(id);
				map.put("depart", depart);
				//获取父部门信息
				if(null != depart.getParentId()){
					SysDeptEntity parent = sysDeptService.getById(depart.getParentId());
					map.put("parentName", parent.getName());
					map.put("parentId", parent.getId());
				}
				
				j.setAttributes(map);
			}
		}catch(Exception e) {
			log.error("获取部门信息报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("获取部门信息错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
