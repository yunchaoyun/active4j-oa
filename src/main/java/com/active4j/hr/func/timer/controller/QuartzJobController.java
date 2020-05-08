package com.active4j.hr.func.timer.controller;

import java.util.List;

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
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.timer.entity.QuartzJobEntity;
import com.active4j.hr.func.timer.model.QuartzJobModel;
import com.active4j.hr.func.timer.service.QuartzJobService;
import com.active4j.hr.func.timer.util.CronUtils;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title QuartzJobController.java
 * @description 
		定时任务管理
 * @time  2020年4月7日 上午10:34:59
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/func/timer/job")
@Slf4j
public class QuartzJobController extends BaseController {
	
	@Autowired
	private QuartzJobService quartzJobService;

	private static final String prefix_page = "func/timer/job/";
	
	/**
	 * 
	 * @description
	 *  	跳转列表页
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月3日 下午3:16:28
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView(prefix_page + "joblist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	获取表格数据
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2019年12月10日 上午10:20:42
	 */
	@RequestMapping("/datagrid")
	public void datagrid(QuartzJobEntity quartzJobEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//拼接查询条件
		QueryWrapper<QuartzJobEntity> queryWrapper = QueryUtils.installQueryWrapper(quartzJobEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<QuartzJobEntity> lstResult = quartzJobService.page(new Page<QuartzJobEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	新增或编辑页面
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月7日 上午10:36:19
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(QuartzJobEntity quartzJobEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("func/timer/job/job");
		
		if(StringUtils.isNotEmpty(quartzJobEntity.getId())) {
			//编辑
			quartzJobEntity = quartzJobService.getById(quartzJobEntity.getId());
			view.addObject("job", quartzJobEntity);
		}
		
		//查询数据字典，数据查看权限
		List<SysDicValueEntity> lstDics = SystemUtils.getDictionaryLst("func_timer_job_group");
		view.addObject("jobGroup", lstDics);
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	查看
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月7日 上午10:41:42
	 */
	@RequestMapping("/view")
	public ModelAndView view(QuartzJobEntity quartzJobEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("func/timer/job/view");
		
		if(StringUtils.isNotEmpty(quartzJobEntity.getId())) {
			QuartzJobModel model = quartzJobService.getJobDetailModel(quartzJobEntity.getId());
			view.addObject("job", model);
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	保存定时任务
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月11日 下午3:27:40
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存定时任务", memo = "新增或编辑保存了定时任务")
	public AjaxJson save(@Validated QuartzJobEntity quartzJobEntity)  {
		AjaxJson j = new AjaxJson();
		
		try{
			//这里使用了@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理，所以不再做字段为空和长度校验，具体校验规则请见是实体字段
			//校验cron表达式
			if(!CronUtils.isValid(quartzJobEntity.getCronExpression())) {
				j.setSuccess(false);
				j.setMsg("请输入正确的cron表达式");
				return j;
			}
			
			//保存定时任务
			j = quartzJobService.saveJob(quartzJobEntity);
		}catch(Exception e) {
			log.error("保存定时任务报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存定时任务异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除定时任务
	 * @params
	 *      ids 任务ids
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月11日 下午4:20:08
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除定时任务", memo = "删除了定时任务")
	public AjaxJson del(String id) {
		AjaxJson j = new AjaxJson();
		try {
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择一条记录");
			}
			//删除定时任务
			quartzJobService.delJob(id);
		}catch(Exception e) {
			log.error("删除定时任务报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除定时任务异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	启用定时任务
	 * @params
	 *      id 任务id
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月11日 下午1:49:44
	 */
	@RequestMapping("/start")
	@ResponseBody
	@Log(type = LogType.update, name = "启用定时任务", memo = "启用了定时任务")
	public AjaxJson start(String id) {
		AjaxJson j = new AjaxJson();
		try {
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("该任务不存在，请重新选择");
				return j;
			}
			//启用任务
			j = quartzJobService.startJob(id);
		}catch(Exception e) {
			log.error("启用定时任务报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("启用定时任务异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	立即执行一次定时任务
	 * @params
	 *      id 任务id
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月12日 上午12:51:33
	 */
	@RequestMapping("/one")
	@ResponseBody
	@Log(type = LogType.save, name = "执行定时任务", memo = "立即执行了一次定时任务")
	public AjaxJson one(String id) {
		AjaxJson j = new AjaxJson();
		try {
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("该任务不存在，请重新选择");
				return j;
			}
			//启用任务
			if(!quartzJobService.runAJobNow(id)) {
				j.setSuccess(false);
				j.setMsg("立即执行一次任务失败");
				return j;
			}
		}catch(Exception e) {
			log.error("立即执行一次定时任务报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("启立即执行一次定时任务异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	暂停定时任务
	 * @params
	 *      id 任务id
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月11日 下午1:50:38
	 */
	@RequestMapping("/pause")
	@ResponseBody
	@Log(type = LogType.update, name = "暂停定时任务", memo = "暂停了定时任务")
	public AjaxJson pause(String id) {
		AjaxJson j = new AjaxJson();
		try {
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("该任务不存在，请重新选择");
				return j;
			}
			//暂停任务
			j = quartzJobService.pauseJob(id);
		}catch(Exception e) {
			log.error("暂停定时任务报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("暂停定时任务异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
