package com.active4j.hr.activiti.util;

import java.util.List;
import java.util.stream.Collectors;

import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;

/**
 * @title WorkflowTaskUtil.java
 * @description 工作流审批 关于审批人的工具类
 * @time 2020年4月23日 下午8:55:23
 * @author 麻木神
 * @version 1.0
 */
public class WorkflowTaskUtil {

	private static SysUserService sysUserService = ApplicationContextUtil.getContext().getBean(SysUserService.class);
	private static SysDeptService sysDeptService = ApplicationContextUtil.getContext().getBean(SysDeptService.class);
	private static SysRoleService sysRoleService = ApplicationContextUtil.getContext().getBean(SysRoleService.class);

	
	/**
	 * 
	 * @description
	 *  	查询申请人的部门经理
	 * @return List<String>
	 * @author 麻木神
	 * @time 2020年4月23日 下午9:29:33
	 */
	public static List<String> getDepartManagerByApplyName(String applyName) {

		// 确定用户
		SysUserEntity user = sysUserService.getUserByUseName(applyName);
		// 部门查询
		SysDeptEntity dept = sysDeptService.getById(user.getDeptId());
		// 确定角色名称
		String roleName = dept.getName() + WorkflowConstant.Str_Dept_Manager;
		// 根据角色名称查询角色
		List<SysUserEntity> lstUsers = sysRoleService.findUserByRoleName(roleName);

		return lstUsers.stream().map(u -> u.getUserName()).collect(Collectors.toList());

	}

	/**
	 * 
	 * @description
	 *  	TODO
	 * @return List<String>
	 * @author 麻木神
	 * @time 2020年4月23日 下午9:29:50
	 */
	public static List<String> getApprovalUserByRoleName(String roleName) {
		// 根据角色名称查询角色
		List<SysUserEntity> lstUsers = sysRoleService.findUserByRoleName(roleName);

		return lstUsers.stream().map(u -> u.getUserName()).collect(Collectors.toList());
	}
}
