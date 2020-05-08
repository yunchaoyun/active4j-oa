package com.active4j.hr.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.tree.TSRoleTreeData;
import com.active4j.hr.system.dao.SysRoleDao;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysRoleFunctionEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.entity.SysUserRoleEntity;
import com.active4j.hr.system.service.SysRoleFunctionService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 角色管理 service类
 * @author teli_
 *
 */
@Service("sysRoleService")
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	
	@Autowired
	private SysRoleFunctionService sysRoleFunctionService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 
	 * @description
	 *  	根据角色的名称 查询用户
	 * @return List<SysUserEntity>
	 * @author 麻木神
	 * @time 2020年4月23日 下午9:05:35
	 */
	public List<SysUserEntity> findUserByRoleName(String roleName){
		List<SysUserEntity> lstUsers = new ArrayList<SysUserEntity>();
		
		QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<SysRoleEntity>();
		queryWrapper.eq("ROLE_NAME", roleName);
		List<SysRoleEntity> lstRoles = this.list(queryWrapper);
		if(null != lstRoles && lstRoles.size() > 0) {
			SysRoleEntity role = lstRoles.get(0);
			
			QueryWrapper<SysUserRoleEntity> queryWrapperUser = new QueryWrapper<SysUserRoleEntity>();
			queryWrapperUser.eq("ROLE_ID", role.getId());
			List<SysUserRoleEntity> lstUserRoles = sysUserRoleService.list(queryWrapperUser);
			if(null != lstUserRoles && lstUserRoles.size() > 0) {
				for(SysUserRoleEntity userRole : lstUserRoles) {
					lstUsers.add(sysUserService.getById(userRole.getUserId()));
				}
			}
		}
		return lstUsers;
	}
	
	/**
	 * 
	 * @description
	 *  	获取所有父级角色
	 * @return List<SysRoleEntity>
	 * @author 麻木神
	 * @time 2020年2月1日 下午8:04:09
	 */
	public List<SysRoleEntity> getParentRoles() {
		// 先查父级菜单 注意排序
		QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<SysRoleEntity>();
		queryWrapper.isNull("PARENT_ID");
		queryWrapper.orderByAsc("ORDER_NO");
		List<SysRoleEntity> lstRoles = this.list(queryWrapper);
		
		return lstRoles;
	}
	
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
	public List<SysRoleEntity> getChildRolesByParentId(String roleId) {
		QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<SysRoleEntity>();
		queryWrapper.eq("PARENT_ID", roleId);
		queryWrapper.orderByAsc("ORDER_NO");
		List<SysRoleEntity> lstChildren = this.list(queryWrapper);
		
		return lstChildren;
	}
	
	/**
	 * 
	 * @description
	 *  	构建树形菜单显示的表格
	 * @return List<TSRoleTreeData>
	 * @author 麻木神
	 * @time 2020年2月1日 下午7:59:46
	 */
	public List<TSRoleTreeData> getTreeRoleList(){
		// 生成树形表格菜单的集合
		List<TSRoleTreeData> lstDatas = new ArrayList<TSRoleTreeData>();

		// 先查父级菜单 注意排序
		List<SysRoleEntity> lstMenus = getParentRoles();
		
		getTSRoleTreeList(null, lstMenus, lstDatas);
		
		return lstDatas;
	}
	
	
	/**
	 * 
	 * @description
	 *  	删除角色
	 * @return void
	 * @author 麻木神
	 * @time 2020年2月1日 下午8:52:23
	 */
	public void deleteMenu(SysRoleEntity role) {
		QueryWrapper<SysRoleFunctionEntity> queryWrapper = new QueryWrapper<SysRoleFunctionEntity>();
		queryWrapper.eq("ROLE_ID", role.getId());
		sysRoleFunctionService.remove(queryWrapper);
		
		this.removeById(role.getId());
	}
	
	/**
	 * 
	 * @description
	 *  	根据角色 获取菜单信息
	 * @return List<SysRoleFunctionEntity>
	 * @author 麻木神
	 * @time 2020年2月1日 下午9:11:16
	 */
	public List<SysRoleFunctionEntity> getRoleFunctionList(SysRoleEntity role){
		QueryWrapper<SysRoleFunctionEntity> queryWrapper = new QueryWrapper<SysRoleFunctionEntity>();
		queryWrapper.eq("ROLE_ID", role.getId());
		
		return sysRoleFunctionService.list(queryWrapper);
	}

	/**
	 * 递归的方式，根据菜单的父子关系，生成树形结构
	 * 
	 * @param parent
	 * @param lstChildren
	 */
	private void getTSRoleTreeList(SysRoleEntity parent, List<SysRoleEntity> lstChildren, List<TSRoleTreeData> lstTreeData) {
		if (null != lstChildren && lstChildren.size() >= 0) {
			// 这里集合要进行排序
			Collections.sort(lstChildren, (s1, s2) -> s1.getOrderNo() -s2.getOrderNo());
			for (SysRoleEntity tsRole : lstChildren) {
				TSRoleTreeData treeData = new TSRoleTreeData();
				treeData.setId(tsRole.getId());
				treeData.setRoleName(tsRole.getRoleName());
				treeData.setOrderNo(tsRole.getOrderNo()); 
				treeData.setRoleCode(tsRole.getRoleCode());
				treeData.setDescription(tsRole.getDescription());
				treeData.setExpanded(false);
				treeData.setLoaded(true);
				if (null == parent) {
					treeData.setParentId(null);
				} else {
					treeData.setParentId(parent.getId());
				}

				treeData.setLevel(String.valueOf(tsRole.getLevel())); // 层次
				// 获取子菜单
				List<SysRoleEntity> lstChildren2 = getChildRolesByParentId(tsRole.getId());
				
				if (null != lstChildren2 && lstChildren2.size() > 0) {
					treeData.setLeaf(false);
				} else {
					treeData.setLeaf(true);
				}
				lstTreeData.add(treeData);

				getTSRoleTreeList(tsRole, lstChildren2, lstTreeData);

			}
		}
	}
	
	
	/**
	 * 配置权限
	 * @param roleId
	 * @param roleMenuIds
	 */
	public void saveRoleMenu(String roleId, String roleMenuIds) {
		
		//删除之前的配置
		QueryWrapper<SysRoleFunctionEntity> queryWrapper = new QueryWrapper<SysRoleFunctionEntity>();
		queryWrapper.eq("ROLE_ID", roleId);
		sysRoleFunctionService.remove(queryWrapper);
		
		if(StringUtils.isNotEmpty(roleMenuIds)) {
			//重新保存
			String[] ids = roleMenuIds.split(",");
			if(null != ids && ids.length > 0) {
				for(String menuId : ids) {
					SysRoleFunctionEntity roleMenu = new SysRoleFunctionEntity();
					roleMenu.setMenuId(menuId);
					roleMenu.setRoleId(roleId);
					sysRoleFunctionService.save(roleMenu);
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @description
	 *  	获取配置了权限的按钮集合
	 * @return Set<String>
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:39:23
	 */
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId){
		Set<String> operationCodes = new HashSet<String>();
		QueryWrapper<SysRoleFunctionEntity> queryWrapper = new QueryWrapper<SysRoleFunctionEntity>();
		queryWrapper.eq("ROLE_ID", roleId);
		queryWrapper.eq("MENU_ID", functionId);
		List<SysRoleFunctionEntity> lstRoleFunctions = sysRoleFunctionService.list(queryWrapper);
		if(null != lstRoleFunctions && lstRoleFunctions.size() > 0) {
			SysRoleFunctionEntity tsRoleFunction = lstRoleFunctions.get(0);
			if (StringUtils.isNotEmpty(tsRoleFunction.getOperation())) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		
		return operationCodes;
	}
	
	/**
	 * 
	 * @description
	 *  	获取有权限菜单
	 * @return SysRoleFunctionEntity
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:49:43
	 */
	public SysRoleFunctionEntity getRoleFunctionByRoleIdAndFunctionId(String roleId, String functionId) {
		QueryWrapper<SysRoleFunctionEntity> queryWrapper = new QueryWrapper<SysRoleFunctionEntity>();
		queryWrapper.eq("ROLE_ID", roleId);
		queryWrapper.eq("MENU_ID", functionId);
		List<SysRoleFunctionEntity> lstRoleFunctions = sysRoleFunctionService.list(queryWrapper);
		if(null != lstRoleFunctions && lstRoleFunctions.size() > 0) {
			return lstRoleFunctions.get(0);
		}
		
		return null;
	}
	
}
