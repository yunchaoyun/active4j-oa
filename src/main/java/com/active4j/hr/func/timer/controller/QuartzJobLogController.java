package com.active4j.hr.func.timer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.active4j.hr.func.timer.entity.QuartzJobLogEntity;
import com.active4j.hr.func.timer.model.QuartzJobLogModel;
import com.active4j.hr.func.timer.service.QuartzJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title QuartzJobLogController.java
 * @description 
		定时任务日志管理
 * @time  2020年4月7日 下午4:05:45
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/func/timer/joblog")
@Slf4j
public class QuartzJobLogController extends BaseController {
	
	@Autowired
	private QuartzJobLogService quartzJobLogService;

	private static final String prefix_page = "func/timer/joblog/";
	
	/**
	 * 
	 * @description
	 *  	跳转列表页
	 * @params
	 * @return String
	 * @author guyp
	 * @time 2019年12月10日 上午10:15:00
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView(prefix_page + "jobloglist");
		
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
	public void datagrid(QuartzJobLogEntity quartzJobLogEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//拼接查询条件
		QueryWrapper<QuartzJobLogEntity> queryWrapper = QueryUtils.installQueryWrapper(quartzJobLogEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<QuartzJobLogEntity> lstResult = quartzJobLogService.page(new Page<QuartzJobLogEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	查看
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月7日 下午4:07:36
	 */
	@RequestMapping("/view")
	public ModelAndView view(QuartzJobLogEntity quartzJobLogEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("func/timer/joblog/view");
		
		if(StringUtils.isNotEmpty(quartzJobLogEntity.getId())) {
			QuartzJobLogModel model = quartzJobLogService.getLogDetailModel(quartzJobLogEntity.getId());
			view.addObject("log", model);
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	删除定时任务日志
	 * @params
	 *      id 任务日志id
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月11日 下午4:20:08
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除定时任务日志", memo = "删除了定时任务日志")
	public AjaxJson del(String id) {
		AjaxJson j = new AjaxJson();
		try {
			//ids校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择一条记录");
			}
			//删除定时任务日志
			quartzJobLogService.removeById(id);
		}catch(Exception e) {
			log.error("删除定时任务日志报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除定时任务日志异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	清空定时任务日志
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2019年12月12日 上午10:57:33
	 */
	@RequestMapping("/empty")
	@ResponseBody
	@Log(type = LogType.del, name = "清空定时任务日志", memo = "清空了所有定时任务日志")
	public AjaxJson empty() {
		AjaxJson j = new AjaxJson();
		try {
			//清空定时任务日志
			quartzJobLogService.remove(new QueryWrapper<QuartzJobLogEntity>());
		}catch(Exception e) {
			log.error("清空定时任务日志报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("清空定时任务日志异常");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
