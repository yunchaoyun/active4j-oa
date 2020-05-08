package com.active4j.hr.work.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import com.active4j.hr.work.domain.UnderEmployerDailyDomain;
import com.active4j.hr.work.entity.OaWorkDailyEntity;
import com.active4j.hr.work.service.OaWorkDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title OaWorkDialyController.java
 * @description 
		  工作中心  日报管理
 * @time  2020年4月3日 下午2:25:41
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/daily")
@Slf4j
public class OaWorkDailyController extends BaseController {
	
	
	@Autowired
	private OaWorkDailyService oaWorkDailyService;
	
	@Autowired
	private SysUserService sysUserService;

	
	/**
	 * 我的日报
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("oa/work/daily/mydailylist");
		
		return view;
	}
	
	
	/**
	 * 获取下属人员
	 * @param req
	 * @return
	 */
	@RequestMapping("/unlist")
	public ModelAndView unlist(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("oa/work/daily/sudndailylist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	表格数据显示
	 * @return void
	 * @time 2020年2月4日 上午9:45:39
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkDailyEntity oaWorkDailyEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaWorkDailyEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkDailyEntity, request.getParameterMap(), dataGrid);
		queryWrapper.eq("USER_ID", ShiroUtils.getSessionUserId());
		
		//执行查询
		IPage<OaWorkDailyEntity> lstResult = oaWorkDailyService.page(new Page<OaWorkDailyEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	
	/**
	 * 新增查看
	 * @param oaWorkDaily
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkDailyEntity oaWorkDaily, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/daily/mydaily");
		
		if(StringUtils.isNotEmpty(oaWorkDaily.getId())) {
			oaWorkDaily = oaWorkDailyService.getById(oaWorkDaily.getId());
			view.addObject("oaWorkDaily", oaWorkDaily);
		}
		
		return view;
	}
	
	/**
	 * 删除草稿
	 * @param oaWorkDaily
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@Log(type = LogType.del, name = "删除日报信息", memo = "删除日报信息")
	public AjaxJson delete(OaWorkDailyEntity oaWorkDaily, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkDaily.getId())) {
				
				oaWorkDaily = oaWorkDailyService.getById(oaWorkDaily.getId());
				
				if(StringUtils.equals(GlobalConstant.OAWORK_DAILY_STATUS_SUBMIT, oaWorkDaily.getStatus())){
					j.setMsg("该日报已提交，不能删除！");
					j.setSuccess(false);
					return j;
				}
				
				oaWorkDailyService.removeById(oaWorkDaily.getId());
				
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("删除日报草稿报错，错误信息：{}", e);
		}
		
		return j;
	}
	
	
	/**
	 * 保存方法
	 * @param oaWorkDaily
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存日报信息", memo = "新增或编辑保存了日报信息")
	public AjaxJson save(@Validated OaWorkDailyEntity oaWorkDaily, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isNotEmpty(oaWorkDaily.getId())) {
				OaWorkDailyEntity t = oaWorkDailyService.getById(oaWorkDaily.getId());
				
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkDaily, t);
				oaWorkDailyService.saveOrUpdate(t);
			} else {
				
				OaWorkDailyEntity tmp = oaWorkDailyService.getOaWorkDaily(ShiroUtils.getSessionUserId(), DateUtils.date2Str(DateUtils.SDF_YYYY_MM_DD));
				if(null != tmp) {
					j.setSuccess(false);
					j.setMsg("当天日报已经提交");
					return j;
				}
				
				//新增方法
				oaWorkDaily.setUserId(ShiroUtils.getSessionUserId());
				oaWorkDaily.setSubmitDate(DateUtils.getDate());
				oaWorkDaily.setStrSubmitDate(DateUtils.date2Str(DateUtils.SDF_YYYY_MM_DD));
				//初始状态都设为草稿
				oaWorkDaily.setStatus(GlobalConstant.OAWORK_DAILY_STATUS_DRAFT);
				oaWorkDailyService.save(oaWorkDaily);
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("日报保存出错！");
			log.error("保存日报信息报错，错误信息:{}", e);
		}
		
		return j;
		
	}
	
	/**
	 * 提交草稿
	 * @param oaWorkDaily
	 * @param request
	 * @return
	 */
	@RequestMapping("/doSubmit")
	@ResponseBody
	@Log(type = LogType.update, name = "提交日报信息", memo = "提交日报信息")
	public AjaxJson doSubmit(OaWorkDailyEntity oaWorkDaily, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkDaily.getId())) {
				
				oaWorkDaily = oaWorkDailyService.getById(oaWorkDaily.getId());
				
				if(StringUtils.equals(GlobalConstant.OAWORK_DAILY_STATUS_SUBMIT, oaWorkDaily.getStatus())){
					j.setMsg("该日报已提交，请勿重复提交！");
					j.setSuccess(false);
					return j;
				}
				
				oaWorkDaily.setStatus(GlobalConstant.OAWORK_DAILY_STATUS_SUBMIT);
				
				oaWorkDailyService.saveOrUpdate(oaWorkDaily);
				
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("提交日报报错，错误信息:{}", e);
		}
		
		return j;
	}
	
	
	/**
	 * 获取下属人员列表
	 * @return
	 */
	@RequestMapping("/getUnderData")
	@ResponseBody
	public AjaxJson getUnderData(String strdate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		if(StringUtils.isEmpty(strdate)) {
			j.setSuccess(false);
			j.setMsg("请选择日期！");
			return j;
		}
		
		try {
			List<String> lstIds = sysUserService.getUnderUserIds(ShiroUtils.getSessionUserId());
			List<UnderEmployerDailyDomain> lstDatas = new ArrayList<UnderEmployerDailyDomain>();
			if(null != lstIds && lstIds.size() > 0) {
				for(String userId : lstIds) {
					UnderEmployerDailyDomain domain = new UnderEmployerDailyDomain();
					domain.setId(userId);
					domain.setName(sysUserService.getById(userId).getRealName());
					
					OaWorkDailyEntity daily = oaWorkDailyService.getOaWorkDaily(userId, strdate);
					if(null == daily) {
						domain.setDailyStatus("0");
					}else {
						domain.setDailyStatus("1");
					}
					domain.setStrDate(strdate);
					
					lstDatas.add(domain);
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lstData", lstDatas);
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg("获取数据出错");
			log.error("获取下属人员日报出错，错误信息:{}", e);
		}
		return j;
	}
	
	/**
	 * 提醒
	 * @param id
	 * @param strdate
	 * @param request
	 * @return
	 */
	@RequestMapping("/doWarn")
	@ResponseBody
	public AjaxJson doWarn(String id, String strdate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		if(StringUtils.isEmpty(id)) {
			j.setSuccess(false);
			j.setMsg("不存在该用户！");
			return j;
		}
		
		if(StringUtils.isEmpty(strdate)) {
			j.setSuccess(false);
			j.setMsg("请选择日期！");
			return j;
		}
		
		try{
			j.setMsg("提醒成功！");
			
			MessageUtils.SendSysMessage(id, "你："+strdate+"当天的日报未提交，请尽快提交！");
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("提醒下属日报报错，错误信息：{}", e);
		}
		
		return j;
	}
	
	
	/**
	 * 获取用户日报列表
	 * @param id
	 * @param strdate
	 * @param request
	 * @return
	 */
	@RequestMapping("/goView")
	@ResponseBody
	public AjaxJson goView(String id, String strdate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		if(StringUtils.isEmpty(id)) {
			j.setSuccess(false);
			j.setMsg("不存在该用户！");
			return j;
		}
		
		if(StringUtils.isEmpty(strdate)) {
			j.setSuccess(false);
			j.setMsg("请选择日期！");
			return j;
		}
		
		try{
			
			OaWorkDailyEntity dailyEntity = oaWorkDailyService.getOaWorkDaily(id, strdate);
			List<UnderEmployerDailyDomain> lstDatas = new ArrayList<UnderEmployerDailyDomain>();
			UnderEmployerDailyDomain d = new UnderEmployerDailyDomain();
			d.setDailyId(dailyEntity.getId());
			d.setDailyTitle(dailyEntity.getTitle());
			d.setSubmitDate(DateUtils.date2Str(dailyEntity.getSubmitDate(),DateUtils.SDF_YYYY_MM_DD_HH_MM_SS));
			d.setSubmitName(sysUserService.getById(dailyEntity.getUserId()).getRealName());
			d.setContent(dailyEntity.getContent());
			
			lstDatas.add(d);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lstData", lstDatas);
			j.setAttributes(map);
			
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.ERROR_MSG);
			log.error("查看下属日报报错，错误信息:{}", e);
		}
		
		return j;
	}
}
