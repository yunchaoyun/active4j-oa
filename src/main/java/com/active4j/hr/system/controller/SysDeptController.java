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

import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.TagUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.core.web.tag.model.TreeDataGrid;
import com.active4j.hr.core.web.tag.model.tree.TSDepartTreeData;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SysDeptController.java
 * @description 
		  系统管理  部门管理
 * @time  2020年1月29日 下午4:10:09
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("system/depart/departList");
	}
	
	
	/**
	 * 菜单列表--树形结构
	 */
	@RequestMapping("/departTreeGrid")
	public void treeDataGrid(HttpServletRequest request, HttpServletResponse response, TreeDataGrid dataGrid) {
		// 生成树形表格菜单的集合
		List<TSDepartTreeData> lstDatas = sysDeptService.getTreeDepartList();

		dataGrid.setPage(1);
		dataGrid.setResults(lstDatas);
		dataGrid.setRows(1000);
		dataGrid.setTotal(lstDatas.size());
		dataGrid.setTotalPage(1);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 跳转到部门的新增页面
	 * @param depart
	 * @param req
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(SysDeptEntity depart, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/depart/depart");
		
		//获取部门类型的数据字典
		List<SysDicValueEntity> lstTypes = SystemUtils.getDictionaryLst("tsdepart");
		view.addObject("types", lstTypes);
		
		return view;
	}
	
	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/update")
	public ModelAndView update(SysDeptEntity depart, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/depart/depart");
		
		//获取部门类型的数据字典
		List<SysDicValueEntity> lstTypes = SystemUtils.getDictionaryLst("tsdepart");
		view.addObject("types", lstTypes);
		
		depart = sysDeptService.getById(depart.getId());
		view.addObject("depart", depart);
		
		SysDeptEntity parentDept = sysDeptService.getById(depart.getParentId());
		if(null != parentDept) {
			view.addObject("parentDepartName", parentDept.getName());
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	部门保存
	 * @return AjaxJson
	 * @author 麻木神
	 * @time 2020年2月1日 下午7:08:22
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存部门信息", memo = "新增或编辑保存了部门信息")
	public AjaxJson save(SysDeptEntity depart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isEmpty(depart.getParentId())) {
				depart.setLevel(0);
				depart.setParentId(null);
			}else {
				SysDeptEntity parent = sysDeptService.getById(depart.getParentId());
				depart.setLevel(parent.getLevel() + 1);
			}
			
			if(StringUtils.isNotEmpty(depart.getParentId()) && StringUtils.equals(depart.getParentId(), depart.getId())) {
				j.setSuccess(false);
				j.setMsg("上级部门不能选择自己，请重新选择");
				return j;
			}
			
			if (StringUtils.isNotEmpty(depart.getId())) {
				//编辑保存
				SysDeptEntity tmp = sysDeptService.getById(depart.getId());
				MyBeanUtils.copyBeanNotNull2Bean(depart, tmp);
				if(StringUtils.isEmpty(depart.getParentId())) {
					tmp.setParentId(null);
				}
				sysDeptService.saveOrUpdate(tmp);
			}else {
				//新增保存
				sysDeptService.save(depart);
			}
		}catch(Exception e) {
			log.error("保存部门信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存部门错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 删除部门
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除部门信息", memo = "删除了部门信息")
	public AjaxJson del(SysDeptEntity depart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try {
			if(StringUtils.isNotEmpty(depart.getId())) {
				//查询部门下面的用户，存在用户则不能删除该部门
				List<SysUserEntity> lstUsers = sysDeptService.getUsersByDept(depart.getId());
				if(null != lstUsers && lstUsers.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该部门下存在用户，不能直接删除");
					return j;
				}
				
				List<SysDeptEntity> lstDepts = sysDeptService.getChildDeptsByDeptId(depart.getId());
				if(null != lstDepts && lstDepts.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该部门下存在子部门，不能直接删除");
					return j;
				}
			
				//删除部门
				sysDeptService.removeById(depart.getId());
			}
			
		}catch(Exception e) {
			log.error("删除部门信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除部门错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	部门下人员列表
	 * @return void
	 * @author 麻木神
	 * @time 2020年2月1日 下午7:15:33
	 */
	@RequestMapping("/userDatagrid")
	public void userDatagrid(SysUserEntity sysUserEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) { 
		if(StringUtils.isEmpty(sysUserEntity.getDeptId())) {
			sysUserEntity.setDeptId("-1");
		}
		
		//拼接查询条件
		QueryWrapper<SysUserEntity> queryWrapper = QueryUtils.installQueryWrapper(sysUserEntity, request.getParameterMap(), dataGrid);
		
		//执行查询
		IPage<SysUserEntity> lstResult = sysUserService.page(new Page<SysUserEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
}
