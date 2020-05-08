package com.active4j.hr.system.controller;

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
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.TagUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.core.web.tag.model.TreeDataGrid;
import com.active4j.hr.core.web.tag.model.tree.TSFunctionTreeData;
import com.active4j.hr.system.entity.SysFunctionEntity;
import com.active4j.hr.system.entity.SysOperationEntity;
import com.active4j.hr.system.model.KeyValueModel;
import com.active4j.hr.system.service.SysFunctionService;
import com.active4j.hr.system.service.SysOperationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SysFunctionController.java
 * @description 
		  系统管理  菜单管理
 * @time  2020年1月16日 下午4:37:26
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/sys/menu")
@Slf4j
public class SysFunctionController extends BaseController{

	@Autowired
	private SysFunctionService sysFunctionService;
	
	@Autowired
	private SysOperationService sysOperationService;
	
	/**
	 * 菜单列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("system/function/functionList");
	}
	
	
	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("operation")
	public ModelAndView operation(HttpServletRequest request, String functionId) {
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operationList");
	}
	
	
	/**
	 * 菜单列表--树形结构
	 */
	@RequestMapping("/treeDataGrid")
	public void treeDataGrid(HttpServletRequest request, HttpServletResponse response, TreeDataGrid dataGrid) {
		// 生成树形表格菜单的集合
		List<TSFunctionTreeData> lstDatas = sysFunctionService.getTreeFunctionList();

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
	 *  	按钮表格的显示
	 * @return void
	 * @author 麻木神
	 * @time 2020年1月19日 下午3:09:45
	 */
	@RequestMapping("/opdategrid")
	public void opdategrid(SysOperationEntity sysOperationEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		if(StringUtils.isEmpty(sysOperationEntity.getFunctionId())) {
			sysOperationEntity.setFunctionId("-1");
		}
		
		//拼接查询条件
		QueryWrapper<SysOperationEntity> queryWrapper = QueryUtils.installQueryWrapper(sysOperationEntity, request.getParameterMap(), dataGrid);
		
		//执行查询
		IPage<SysOperationEntity> lstResult = sysOperationService.page(new Page<SysOperationEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(SysFunctionEntity function, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/function/function");
		
		//菜单的树形结构
		List<KeyValueModel> lstTrees = new ArrayList<KeyValueModel>();
		List<SysFunctionEntity> lstParents = sysFunctionService.getParentFunctions();
		if(null != lstParents && lstParents.size() > 0) {
			for(SysFunctionEntity f : lstParents) {
				KeyValueModel keyValue = new KeyValueModel();
				keyValue.setKey(f.getName());
				keyValue.setValue(f.getId());
				lstTrees.add(keyValue);
				//目前系统只要支持一级菜单和二级菜单，如果以后需要三级或者更多级菜单，取消下面注释
				List<SysFunctionEntity> lstChildren = sysFunctionService.getChildFunctionsByParentId(f.getId());
				getMenuTreeKeyValue(lstChildren, lstTrees, "　　");
			}
		}
		view.addObject("lstTrees", lstTrees);
		
		//编辑 获取菜单
		if(StringUtils.isNotEmpty(function.getId())) {
			function = sysFunctionService.getById(function.getId());
			view.addObject("function", function);
		}
		//是否有父级菜单
		if(StringUtils.isNotEmpty(function.getParentId())) {
			function.setLevel(1);
			view.addObject("function", function);
		}
		
		
		return view;
	}
	
	
	/**
	 * 菜单保存
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存菜单信息", memo = "新增或编辑保存了菜单信息")
	public AjaxJson save(SysFunctionEntity function, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(0 == function.getLevel()) {
				function.setParentId(null);
			}
			
			if(StringUtils.isNotEmpty(function.getParentId())) {
				function.setLevel(sysFunctionService.getById(function.getParentId()).getLevel() + 1);
			}
			
			if(StringUtils.isNotEmpty(function.getParentId()) && StringUtils.equals(function.getParentId(), function.getId())) {
				j.setSuccess(false);
				j.setMsg("上级目录不能选择自己，请重新选择");
				return j;
			}
			
			if (StringUtils.isNotEmpty(function.getId())) {
				
				SysFunctionEntity tmp = sysFunctionService.getById(function.getId());
				MyBeanUtils.copyBeanNotNull2Bean(function, tmp);
				if(0 == tmp.getLevel()) {
					tmp.setParentId(null);
				}
				sysFunctionService.saveOrUpdate(tmp);
				
			}else {
				sysFunctionService.save(function);
			}
			
		}catch(Exception e) {
			log.error("保存菜单信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存菜单错误");
			e.printStackTrace();
		}
		
		
		return j;
	}
	
	
	/**
	 * 菜单删除
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除菜单信息", memo = "删除了菜单信息")
	public AjaxJson del(SysFunctionEntity function, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isNotEmpty(function.getId())) {
				//删除之前校验是否存在子菜单
				List<SysFunctionEntity> lstMenus = sysFunctionService.getChildMenusByMenu(function);
				if(null != lstMenus && lstMenus.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该菜单下存在子级菜单，不能直接删除");
					return j;
				}
				
				sysFunctionService.deleteMenu(function);
			}
			
		}catch(Exception e) {
			log.error("删除菜单信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除菜单错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 获取菜单
	 * @param lstMenus
	 * @param lstTrees
	 */
	private void getMenuTreeKeyValue(List<SysFunctionEntity> lstMenus, List<KeyValueModel> lstTrees, String tag) {
		if(null != lstMenus && lstMenus.size() > 0) {
			for(SysFunctionEntity function : lstMenus) {
				KeyValueModel keyValue = new KeyValueModel();
				keyValue.setKey(tag + function.getName());
				keyValue.setValue(function.getId());
				lstTrees.add(keyValue);
				List<SysFunctionEntity> lstChildren = sysFunctionService.getChildFunctionsByParentId(function.getId());
				getMenuTreeKeyValue(lstChildren, lstTrees, "　　　　");
			}
		}
	}
	
	/**
	 * 
	 * @description
	 *  	跳转到新增编辑按钮页面
	 * @return ModelAndView
	 * @author 麻木神
	 * @time 2020年1月29日 下午2:52:09
	 */
	@RequestMapping("/addorupdateop")
	public ModelAndView addorupdateop(SysOperationEntity sysOperationEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/function/operation");
		
		if(StringUtils.isNotEmpty(sysOperationEntity.getId())) {
			sysOperationEntity = sysOperationService.getById(sysOperationEntity.getId());
			view.addObject("operation", sysOperationEntity);
		}
		
		view.addObject("functionId", sysOperationEntity.getFunctionId());
		return view;
	}
	
	
	/**
	 * 操作录入
	 * @param ids
	 * @return
	 */
	@RequestMapping("/saveop")
	@ResponseBody
	@Log(type = LogType.save, name = "保存菜单按钮", memo = "新增或编辑保存了菜单按钮")
	public AjaxJson saveop(SysOperationEntity operation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isEmpty(operation.getFunctionId())) {
				j.setSuccess(false);
				j.setMsg("请选择相应菜单添加按钮");
				return j;
			}
			
			if(StringUtils.isEmpty(operation.getId())) {
				sysOperationService.save(operation);
			}else {
				SysOperationEntity tmp = sysOperationService.getById(operation.getId());
				MyBeanUtils.copyBeanNotNull2Bean(operation, tmp);
				sysOperationService.saveOrUpdate(tmp);		
			}
			
		}catch(Exception e) {
			log.error("保存按钮信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存按钮错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 删除操作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delop")
	@ResponseBody
	@Log(type = LogType.del, name = "删除菜单按钮", memo = "删除了菜单按钮")
	public AjaxJson delop(SysOperationEntity operation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isNotEmpty(operation.getId())) {
				sysOperationService.removeById(operation.getId());
			}
			
		}catch(Exception e) {
			log.error("删除按钮信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除按钮错误");
			e.printStackTrace();
		}
		
		return j;
	}
}
