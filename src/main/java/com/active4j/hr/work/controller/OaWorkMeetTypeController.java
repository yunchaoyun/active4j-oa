package com.active4j.hr.work.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.work.entity.OaWorkMeetTypeEntity;
import com.active4j.hr.work.service.OaWorkMeetTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkMeetTypeController.java
 * @description 
		  工作中心  会议类型
 * @time  2020年4月6日 上午9:42:03
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/meetType")
@Slf4j
public class OaWorkMeetTypeController extends BaseController {

	@Autowired
	private OaWorkMeetTypeService oaWorkMeetTypeService;
	
	/**
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("oa/work/meettype/meettypelist");
	}
	
	
	/**
	 * 查询数据
	 * @param oaWorkMeetTypeEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkMeetTypeEntity oaWorkMeetTypeEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaWorkMeetTypeEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkMeetTypeEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaWorkMeetTypeEntity> lstResult = oaWorkMeetTypeService.page(new Page<OaWorkMeetTypeEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param oaWorkMeetTypeEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkMeetTypeEntity oaWorkMeetTypeEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meettype/meettype");
		
		if(StringUtils.isNotEmpty(oaWorkMeetTypeEntity.getId())) {
			oaWorkMeetTypeEntity = oaWorkMeetTypeService.getById(oaWorkMeetTypeEntity.getId());
			view.addObject("type", oaWorkMeetTypeEntity);
		}
		
		return view;
	}
	
	/**
	 * 保存方法
	 * @param oaWorkMeetTypeEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "新增或保存会议类型", memo = "新增或保存会议类型")
	public AjaxJson save(@Validated OaWorkMeetTypeEntity oaWorkMeetTypeEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(StringUtils.isEmpty(oaWorkMeetTypeEntity.getId())) {
				//新增方法
				oaWorkMeetTypeService.save(oaWorkMeetTypeEntity);
				
			}else {
				//编辑方法
				OaWorkMeetTypeEntity tmp = oaWorkMeetTypeService.getById(oaWorkMeetTypeEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkMeetTypeEntity, tmp);
				oaWorkMeetTypeService.saveOrUpdate(tmp);
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("保存会议类型失败，错误信息:{}", e);
		}
		
		return j;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	@Log(type = LogType.del, name = "删除会议类型", memo = "删除会议类型")
	public AjaxJson delete(OaWorkMeetTypeEntity oaWorkMeetTypeEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkMeetTypeEntity.getId())) {
				oaWorkMeetTypeService.removeById(oaWorkMeetTypeEntity.getId());
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除会议类型失败，错误信息:{}", e);
		}
		
		return j;
	}
}
