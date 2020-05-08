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
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.entity.OaHrRecruitBackupEntity;
import com.active4j.hr.hr.service.OaHrRecruitBackupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrRecruitBackupController.java
 * @description 
		后备资源库管理
 * @time  2020年4月22日 下午5:14:19
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/recruit/backup")
public class OaHrRecruitBackupController extends BaseController{
	
	@Autowired
	private OaHrRecruitBackupService oaHrRecruitBackupService;

	private static final String prefix_page = "oa/hr/recruit/backup/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "backuplist");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param backupEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrRecruitBackupEntity oaHrRecruitBackupEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaHrRecruitBackupEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrRecruitBackupEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrRecruitBackupEntity> lstResult = oaHrRecruitBackupService.page(new Page<OaHrRecruitBackupEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 删除记录
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
				oaHrRecruitBackupService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除后备库数据错误");
			log.error("删除后备库数据错误，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	
}
