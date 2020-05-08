package com.active4j.hr.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.active4j.hr.core.web.tag.model.tree.TSRoleTreeData;
import com.active4j.hr.system.entity.SysOperationEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysRoleFunctionEntity;
import com.active4j.hr.system.model.KeyValueModel;
import com.active4j.hr.system.service.SysOperationService;
import com.active4j.hr.system.service.SysRoleFunctionService;
import com.active4j.hr.system.service.SysRoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SysRoleController.java
 * @description 
		  系统管理  角色管理
 * @time  2020年2月1日 下午7:44:11
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysOperationService sysOperationService;
	
	@Autowired
	private SysRoleFunctionService sysRoleFunctionService;

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/role/roleList");
	}
	
	/**
	 * 菜单列表--树形结构
	 */
	@RequestMapping("/treeDataGrid")
	public void treeDataGrid(HttpServletRequest request, HttpServletResponse response, TreeDataGrid dataGrid) {
		// 生成树形表格菜单的集合
		List<TSRoleTreeData> lstDatas = sysRoleService.getTreeRoleList();

		dataGrid.setPage(1);
		dataGrid.setResults(lstDatas);
		dataGrid.setRows(1000);
		dataGrid.setTotal(lstDatas.size());
		dataGrid.setTotalPage(1);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(SysRoleEntity role, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/role/role");
		//菜单的树形结构
		List<KeyValueModel> lstTrees = new ArrayList<KeyValueModel>();
		List<SysRoleEntity> lstParents = sysRoleService.getParentRoles();
		if(null != lstParents && lstParents.size() > 0) {
			for(SysRoleEntity f : lstParents) {
				KeyValueModel keyValue = new KeyValueModel();
				keyValue.setKey(f.getRoleName());
				keyValue.setValue(f.getId());
				lstTrees.add(keyValue);
				//目前系统只要支持一级菜单和二级菜单，如果以后需要三级或者更多级菜单，取消下面注释
				List<SysRoleEntity> lstChildren = sysRoleService.getChildRolesByParentId(f.getId());
				getRoleTreeKeyValue(lstChildren, lstTrees, "　　");
			}
		}
		view.addObject("lstTrees", lstTrees);
		
		//编辑 获取菜单
		if(StringUtils.isNotEmpty(role.getId())) {
			role = sysRoleService.getById(role.getId());
			view.addObject("role", role);
		}
		//是否有父级菜单
		if(StringUtils.isNotEmpty(role.getParentId())) {
			role.setLevel(1);
			view.addObject("role", role);
		}
		
		return view;
	}

	private void getRoleTreeKeyValue(List<SysRoleEntity> lstRoles, List<KeyValueModel> lstTrees, String tag) {
		if(null != lstRoles && lstRoles.size() > 0) {
			for(SysRoleEntity f : lstRoles) {
				KeyValueModel keyValue = new KeyValueModel();
				keyValue.setKey(tag + f.getRoleName());
				keyValue.setValue(f.getId());
				lstTrees.add(keyValue);
				List<SysRoleEntity> lstChildren = sysRoleService.getChildRolesByParentId(f.getId());
				getRoleTreeKeyValue(lstChildren, lstTrees, "　　　　");
			}
		}
	}
	
	/**
	 * 角色保存
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@Log(type = LogType.save, name = "保存角色信息", memo = "新增或编辑保存了角色信息")
	public AjaxJson save(SysRoleEntity role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(0 == role.getLevel()) {
				role.setParentId(null);
			}
			
			if(StringUtils.isNotEmpty(role.getParentId())) {
				role.setLevel(sysRoleService.getById(role.getParentId()).getLevel() + 1);
			}
			
			if(StringUtils.isNotEmpty(role.getParentId()) && StringUtils.equals(role.getParentId(), role.getId())) {
				j.setSuccess(false);
				j.setMsg("上级角色不能选择自己，请重新选择");
				return j;
			}
			
			if (StringUtils.isNotEmpty(role.getId())) {
				
				SysRoleEntity tmp = sysRoleService.getById(role.getId());
				MyBeanUtils.copyBeanNotNull2Bean(role, tmp);
				if(0 == tmp.getLevel()) {
					tmp.setParentId(null);
				}
				sysRoleService.saveOrUpdate(tmp);
				
			}else {
				sysRoleService.save(role);
			}
			
		}catch(Exception e) {
			log.error("保存角色信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存角色错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 角色删除
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除角色信息", memo = "删除了角色信息")
	public AjaxJson del(SysRoleEntity role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isNotEmpty(role.getId())) {
				//删除之前校验是否存在子菜单
				List<SysRoleEntity> lstRoles = sysRoleService.getChildRolesByParentId(role.getId());
				if(null != lstRoles && lstRoles.size() > 0) {
					j.setSuccess(false);
					j.setMsg("该角色下存在子级角色，不能直接删除");
					return j;
				}
				
				sysRoleService.deleteMenu(role);
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
	 * 更新权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateAuthority")
	@ResponseBody
	@Log(type = LogType.update, name = "更新权限", memo = "更新了权限配置")
	public AjaxJson updateAuthority(String roleId, String rolefunctions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//保存权限配置
			sysRoleService.saveRoleMenu(roleId, rolefunctions);
			
			j.setMsg("权限更新成功");			
		}catch (Exception e){
			log.error("权限配置报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("权限配置保存错误");
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	获取菜单下按钮
	 * @return AjaxJson
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:31:33
	 */
	@RequestMapping("/getOperationListForFunction")
	@ResponseBody
	public AjaxJson getOperationListForFunction(HttpServletRequest request, String functionId, String roleId) {
		AjaxJson j = new AjaxJson();
		try {
			
			List<SysOperationEntity> operationList = sysOperationService.getOperationsByFunctionId(functionId);
			List<SysOperationEntity> lstTmp = new ArrayList<SysOperationEntity>();
			for(SysOperationEntity tSOperation : operationList) {
				SysOperationEntity t = new SysOperationEntity();
				t.setCode(tSOperation.getCode());
				t.setName(tSOperation.getName());
				lstTmp.add(t);
			}
			
			//获取有权限的按钮
			Set<String> operationCodes = sysRoleService.getOperationCodesByRoleIdAndFunctionId(roleId, functionId);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("operationList", lstTmp);
			map.put("operationCodes", operationCodes);
			j.setAttributes(map);
		}catch(Exception e) {
			log.error("获取菜单下按钮报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("获取菜单下按钮错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 更新按钮权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperation")
	@ResponseBody
	@Log(type = LogType.update, name = "更新按钮权限", memo = "更新了按钮权限配置")
	public AjaxJson updateOperation(String roleId, String functionId, String operationcodes, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			SysRoleFunctionEntity roleFunction = sysRoleService.getRoleFunctionByRoleIdAndFunctionId(roleId, functionId);
			if(null != roleFunction) {
				roleFunction.setOperation(operationcodes);
				sysRoleFunctionService.saveOrUpdate(roleFunction);
			}
		}catch(Exception e) {
			log.error("保存按钮权限报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存按钮权限错误");
			e.printStackTrace();
		}
		
		return j;
	}
}
