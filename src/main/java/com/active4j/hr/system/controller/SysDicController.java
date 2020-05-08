package com.active4j.hr.system.controller;

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
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.web.tag.TagUtil;
import com.active4j.hr.core.web.tag.model.TreeDataGrid;
import com.active4j.hr.core.web.tag.model.tree.TSDicTreeData;
import com.active4j.hr.system.entity.SysDicEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.service.SysDicService;
import com.active4j.hr.system.service.SysDicValueService;
import com.active4j.hr.system.service.SystemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title SysTypeController.java
 * @description 
		系统管理  数据字典
 * @time  2020年2月7日 上午10:59:39
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/dic")
@Slf4j
public class SysDicController extends BaseController {
	
	@Autowired
	private SysDicService sysDicService;
	
	@Autowired
	private SysDicValueService sysDicValueService;
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 菜单列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("system/dic/dicList");
	}
	
	/**
	 * 
	 * @description
	 *  	数据字典列表--树形结构
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2020年2月7日 下午5:26:26
	 */
	@RequestMapping("/treeDataGrid")
	public void treeDataGrid(HttpServletRequest request, HttpServletResponse response, TreeDataGrid dataGrid) {
		// 生成树形表格菜单的集合
		List<TSDicTreeData> lstDatas = sysDicService.getTreeDicList();

		dataGrid.setPage(1);
		dataGrid.setResults(lstDatas);
		dataGrid.setRows(1000);
		dataGrid.setTotal(lstDatas.size());
		dataGrid.setTotalPage(1);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 
	 * @description
	 *  	跳转新增编辑页面
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年2月7日 下午5:09:44
	 */
	@RequestMapping("/add")
	public ModelAndView add(String id, HttpServletRequest req) {
		ModelAndView view = null;
		
		if(StringUtils.isEmpty(id)) {
			view = new ModelAndView("system/dic/dic");
		}else {
			if(id.startsWith("G")) {
				//字典
				view = new ModelAndView("system/dic/dic");
				String tmpId = id.substring(1);
				//字典的实体
				SysDicEntity dic = sysDicService.getById(tmpId);
				view.addObject("dic", dic);
			}else {
				//字典值
				view = new ModelAndView("system/dic/dicval");
				String tmpId = id.substring(1);
				//值的实体
				SysDicValueEntity dicValue = sysDicValueService.getById(tmpId);
				view.addObject("dicValue", dicValue);
				
				SysDicEntity dic = sysDicService.getById(dicValue.getParentId());
				view.addObject("name", dic.getName());
				view.addObject("parentId", dic.getId());
			}
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转新增字典值页面
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年2月7日 下午7:01:32
	 */
	@RequestMapping("/addval")
	public ModelAndView addVal(String id, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/dic/dicval");
		String tmpId = id.substring(1);
		if(id.startsWith("G")) {
			//查字典
			SysDicEntity dic = sysDicService.getById(tmpId);
			view.addObject("parentId", dic.getId());
		}else {
			//查字典值
			SysDicValueEntity value = sysDicValueService.getById(tmpId);
			view.addObject("parentId", value.getParentId());
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	保存数据字典
	 * @params
	 *      SysDicEntity 字典实体
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月8日 上午9:47:50
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存数据字典", memo = "新增或编辑保存了数据字典")
	public AjaxJson save(SysDicEntity sysDicEntity, String oldCode, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isEmpty(sysDicEntity.getId())) {
				//判断是否存在该字典编码
				if(sysDicService.haveCode(sysDicEntity.getCode())) {
					j.setSuccess(false);
					j.setMsg("已存在该字典编码，请重新输入");
					return j;
				}
				
				//新增保存
				sysDicService.save(sysDicEntity);
				//刷新一下数据字典
				systemService.initDic();
			}else {
				//判断是否存在该字典编码
				if(!StringUtils.equals(oldCode, sysDicEntity.getCode())) {
					if(sysDicService.haveCode(sysDicEntity.getCode())) {
						j.setSuccess(false);
						j.setMsg("已存在该字典编码，请重新输入");
						return j;
					}
				}
				
				//编辑保存
				SysDicEntity tmp = sysDicService.getById(sysDicEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(sysDicEntity, tmp);
				sysDicService.saveOrUpdate(tmp);
				//刷新一下数据字典
				systemService.initDic();
			}
		}catch(Exception e) {
			log.error("保存数据字典报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存数据字典错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	保存字典值
	 * @params
	 *      SysDicValueEntity 字典值实体
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月8日 上午9:49:54
	 */
	@RequestMapping("/saveval")
	@ResponseBody
	@Log(type = LogType.save, name = "保存字典值", memo = "新增或编辑保存了字典值")
	public AjaxJson saveVal(SysDicValueEntity sysDicValueEntity, String parentId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			//后端校验
			if(StringUtils.isEmpty(parentId)) {
				j.setSuccess(false);
				j.setMsg("请选择字典");
				return j;
			}
			//数据字典赋值
			sysDicValueEntity.setParentId(parentId);
			
			if(StringUtils.isEmpty(sysDicValueEntity.getId())) {
				//新增保存
				sysDicValueService.save(sysDicValueEntity);
				//刷新一下数据字典
				systemService.initDic();
			}else {
				//编辑保存
				SysDicValueEntity tmp = sysDicValueService.getById(sysDicValueEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(sysDicValueEntity, tmp);
				sysDicValueService.saveOrUpdate(tmp);
				//刷新一下数据字典
				systemService.initDic();
			}
		}catch(Exception e) {
			log.error("保存字典值报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存字典值错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除数据字典
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月8日 上午10:32:09
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除数据字典信息", memo = "删除了数据字典信息")
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择需要删除的数据字典");
				return j;
			}
			
			String tmpId = id.substring(1);
			if(id.startsWith("G")) {
				//判断是否存在子值
				QueryWrapper<SysDicValueEntity> queryWrapper = new QueryWrapper<SysDicValueEntity>();
				queryWrapper.eq("PARENT_ID", tmpId);
				List<SysDicValueEntity> lstDicValues = sysDicValueService.list(queryWrapper);
				if(lstDicValues.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该字典下存在子值，请先删除字典值");
					return j;
				}
				
				//字典的删除
				sysDicService.removeById(tmpId);
			}else {
				//值的删除
				sysDicValueService.removeById(tmpId);
			}
			
			//刷新一下数据字典
			systemService.initDic();
		}catch(Exception e) {
			log.error("删除数据字典信息出错，错误信息：{}", e.getMessage() );
			j.setSuccess(false);
			e.printStackTrace();
		}
		
		return j;
	}
}
