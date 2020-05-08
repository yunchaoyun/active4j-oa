package com.active4j.hr.system.service;

import java.util.List;
import java.util.Set;

import com.active4j.hr.core.web.tag.model.tree.TSRoleTreeData;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysRoleFunctionEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysRoleService extends IService<SysRoleEntity> {
	
	/**
	 * 
	 * @description
	 *  	根据角色的名称 查询用户
	 * @return List<SysUserEntity>
	 * @author 麻木神
	 * @time 2020年4月23日 下午9:05:35
	 */
	public List<SysUserEntity> findUserByRoleName(String roleName);

	/**
	 * 
	 * @description
	 *  	构建树形菜单显示的表格
	 * @return List<TSRoleTreeData>
	 * @author 麻木神
	 * @time 2020年2月1日 下午7:59:46
	 */
	public List<TSRoleTreeData> getTreeRoleList();
	
	/**
	 * 
	 * @description
	 *  	获取所有父级角色
	 * @return List<SysRoleEntity>
	 * @author 麻木神
	 * @time 2020年2月1日 下午8:04:09
	 */
	public List<SysRoleEntity> getParentRoles();
	
	
	/**
	 * 
	 * @description
	 *  	根据角色ID 获取子角色
	 * @params
	 *      角色ID
	 * @return List<SysRoleEntity>
	 * @author 麻木神
	 * @time 2020年2月1日 下午8:04:09
	 */
	public List<SysRoleEntity> getChildRolesByParentId(String roleId);
	
	
	/**
	 * 
	 * @description
	 *  	删除角色
	 * @return void
	 * @author 麻木神
	 * @time 2020年2月1日 下午8:52:23
	 */
	public void deleteMenu(SysRoleEntity role);
	
	/**
	 * 
	 * @description
	 *  	根据角色 获取菜单信息
	 * @return List<SysRoleFunctionEntity>
	 * @author 麻木神
	 * @time 2020年2月1日 下午9:11:16
	 */
	public List<SysRoleFunctionEntity> getRoleFunctionList(SysRoleEntity role);
	
	
	/**
	 * 配置权限
	 * @param roleId
	 * @param roleMenuIds
	 */
	public void saveRoleMenu(String roleId, String roleMenuIds);

	/**
	 * 
	 * @description
	 *  	获取配置了权限的按钮集合
	 * @return Set<String>
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:39:23
	 */
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId);
	
	
	/**
	 * 
	 * @description
	 *  	获取有权限菜单
	 * @return SysRoleFunctionEntity
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:49:43
	 */
	public SysRoleFunctionEntity getRoleFunctionByRoleIdAndFunctionId(String roleId, String functionId);
}
