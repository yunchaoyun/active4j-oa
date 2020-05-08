package com.active4j.hr.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.system.dao.SysUserDao;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysFunctionEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.entity.SysUserRoleEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.model.MenuModel;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 用户管理service类
 * @author teli_
 *
 */
@Service("sysUserService")
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	
	/**
	 * 
	 * @description
	 *  	根据用户ID获取用户所在部门 注意是部门
	 * @return SysDeptEntity
	 * @author 麻木神
	 * @time 2020年4月8日 下午9:03:57
	 */
	public SysDeptEntity getUserDepart(String userId) {
		SysDeptEntity dept = null;
		SysUserEntity user = getById(userId);
		if(StringUtils.isNotEmpty(user.getDeptId())) {
			String deptId = user.getDeptId();
			for(int i = 0; i < 10; i++) {
				if(StringUtils.isNotEmpty(deptId)) {
					dept = sysDeptService.getById(deptId);
					if(StringUtils.equals(dept.getType(), "2")) {
						break;
					}else {
						deptId = dept.getParentId();
					}
				}else {
					break;
				}
				
			}
		}
		return dept;
	}
	
	
	/**
	 * 根据用户名取得用户
	 * @param userName
	 * @return
	 */
	public SysUserEntity getUserByUseName(String userName) {
		QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>();
		queryWrapper.eq("USER_NAME", userName);
		List<SysUserEntity> lstUserRoles = this.list(queryWrapper);
		
		if(null != lstUserRoles && lstUserRoles.size() > 0) {
			return lstUserRoles.get(0);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @description
	 *  	根据部门获取用户
	 * @return List<SysUserEntity>
	 * @author 麻木神
	 * @time 2020年4月7日 上午10:52:54
	 */
	public List<SysUserEntity> findUsersByDept(SysDeptEntity sysDeptEntity) {
		QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>();
		queryWrapper.eq("DEPT_ID", sysDeptEntity.getId());
		return this.list(queryWrapper);
	}
	
	/**
	 * 根据用户信息  组装shiro session用户
	 * @param user
	 * @return
	 */
	public ActiveUser getActiveUserByUser(SysUserEntity user) {
		//session user
		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(user.getId());
		activeUser.setRealName(user.getRealName());
		activeUser.setUserName(user.getUserName());
		activeUser.setHeadImgUrl(user.getHeadImgUrl());
		activeUser.setDeptName(SystemUtils.getDeptNameById(user.getDeptId()));
		activeUser.setAdmin(StringUtils.equals(user.getAdmin(), "true") ? true : false);
		
		/**
		 * 可见菜单跟权限的赋值
		 */
		List<SysFunctionEntity> lstMenus = this.findMenuByUserId(user.getId());
		
		//查询用户角色
		List<SysRoleEntity> lstRoles = this.baseMapper.findRolesByUserId(user.getId());
		
		//查询所有有权限的按钮
		List<String> lstBtns = this.baseMapper.findOperationByUserId(user.getId());
		
		/**
		 * 构造可见菜单的显示形式字符串
		 */
		activeUser.setMenus(getMenus(lstMenus));
		/**
		 * 权限角色
		 */
		activeUser.setRoles(getAuthRoles(lstRoles));
		
		/**
		 * 权限菜单按钮
		 */
		activeUser.setPermissions(getAuthMenus(lstMenus, lstBtns));
		
		return activeUser;
	}
	
	/**
	 * 
	 * @description
	 *  	菜单权限
	 * @return Set<String>
	 * @author 麻木神
	 * @time 2020年2月2日 下午10:34:33
	 */
	private Set<String> getAuthMenus(List<SysFunctionEntity> lstMenus, List<String> lstBtns) {
		Set<String> st = new HashSet<String>();
		//菜单
		if(null != lstMenus && lstMenus.size() > 0) {
			for(SysFunctionEntity function : lstMenus) {
				st.add(function.getUrl());
			}
		}
		//按钮
		if(null != lstBtns && lstBtns.size() > 0) {
			for(String strBtns : lstBtns) {
				String[] strs = strBtns.split(",");
				for(String str : strs) {
					st.add(str);
				}
			}
		}
		return st;
	}
	
	/**
	 * 
	 * @description
	 *  	角色权限
	 * @return Set<String>
	 * @author 麻木神
	 * @time 2020年2月2日 下午10:39:27
	 */
	public Set<String> getAuthRoles(List<SysRoleEntity> lstRoles) {
		Set<String> st = new HashSet<String>();
		if(null != lstRoles && lstRoles.size() > 0) {
			for(SysRoleEntity role : lstRoles) {
				st.add(role.getRoleCode());
			}
		}
		
		return st;
	}
	

	/**
	 * 根据角色可见菜单，组装系统菜单栏
	 * @param lstMenus
	 * @return
	 */
	private List<MenuModel> getMenuModels(List<SysFunctionEntity> lstMenus) {
		List<MenuModel> lstModels = new ArrayList<MenuModel>();
		if(null != lstMenus && lstMenus.size() > 0) {
			//取最顶层菜单
			if(null != lstMenus && lstMenus.size() > 0) {
				Iterator<SysFunctionEntity> itMenus = lstMenus.iterator();
				while(itMenus.hasNext()) {
					SysFunctionEntity m = itMenus.next();
					if(StringUtils.isEmpty(m.getParentId())) {
						lstModels.add(getMenuModel(m));
						itMenus.remove();
					}
				}
			}
		}
		if(lstModels.size() > 0) {
			//排序
			Collections.sort(lstModels, (m1, m2)-> m1.getOrderNo() - m2.getOrderNo());
			//取子菜单
			for(MenuModel model : lstModels) {
				List<MenuModel> firstLst = new ArrayList<MenuModel>();
				Iterator<SysFunctionEntity> itMenus = lstMenus.iterator();
				while(itMenus.hasNext()) {
					SysFunctionEntity m = itMenus.next();
					if(StringUtils.equals(m.getParentId(), model.getId())) {
						firstLst.add(getMenuModel(m));
						itMenus.remove();
					}
				}
				if(firstLst.size() > 0) {
					//排序
					Collections.sort(firstLst, (m1, m2)-> m1.getOrderNo() - m2.getOrderNo());
					model.setHasChildren(true);
					model.setChildren(firstLst);
					
					//取子菜单
					for(MenuModel secondM : firstLst) {
						List<MenuModel> secondLst = new ArrayList<MenuModel>();
						
						Iterator<SysFunctionEntity> itMenus2 = lstMenus.iterator();
						while(itMenus2.hasNext()) {
							SysFunctionEntity m = itMenus2.next();
							if(StringUtils.equals(m.getParentId(), secondM.getId())) {
								secondLst.add(getMenuModel(m));
								itMenus2.remove();
							}
						}
						if(secondLst.size() > 0) {
							Collections.sort(secondLst, (m1, m2)-> m1.getOrderNo() - m2.getOrderNo());
							secondM.setHasChildren(true);
							secondM.setChildren(secondLst);
						}
					}
				}
			}
		}
		
		return lstModels;
	}
	
	private MenuModel getMenuModel(SysFunctionEntity m) {
		MenuModel menu = new MenuModel();
		menu.setId(m.getId());
		menu.setTitle(m.getName());
		menu.setOrderNo(m.getOrderNo());
		menu.setIcon(m.getIcon());
		menu.setUrl(m.getUrl());
		
		return menu;
	}
	
	
	private String getMenus(List<SysFunctionEntity> lst) {
		StringBuffer menuString = new StringBuffer();
		
		List<MenuModel> lstMenus = getMenuModels(lst);
		for(MenuModel menu : lstMenus) {
			
			menuString.append("<li>");
			//一级菜单
			menuString.append("<a href=" + (StringUtils.isEmpty(menu.getUrl()) ? "" : menu.getUrl()) + ">");
			menuString.append(menu.getIcon());
			menuString.append("<span class=\"nav-label\">" + menu.getTitle() + "</span>");
			
			//如果有二级菜单
			if(menu.isHasChildren()) {
				menuString.append("<span class=\"fa arrow\"></span>");
			}
			
			menuString.append("</a>");
			
			//如果有二级菜单
			if(menu.isHasChildren()) {
				menuString.append("<ul class=\"nav nav-second-level\">");
				List<MenuModel> lstChildren = menu.getChildren();
				for(MenuModel m2 : lstChildren) {
					menuString.append("<li>");
					
					if(m2.isHasChildren()) {
						//如果存在三级菜单
						//存在三级菜单
						menuString.append("<a class=\"J_menuItem\" href=" + m2.getUrl() + ">" + m2.getTitle() + "<span class=\"fa arrow\"></span></a>");
						
						menuString.append("<ul class=\"nav nav-third-level\">");
						
						//三级菜单
						List<MenuModel> lstChildren2 = m2.getChildren();
						
						for(MenuModel m3 : lstChildren2) {
							menuString.append("<li><a class=\"J_menuItem\" href=" + m3.getUrl() + ">" + m3.getTitle() + "</a></li>");
						}
						
						menuString.append("</ul>");
						
					}else {
						//没有三级菜单
						menuString.append("<a class=\"J_menuItem\" href=" + m2.getUrl() + ">" + m2.getTitle() + "</a>");
					}
					
					menuString.append("</li>");
				}
				menuString.append(" </ul>");
			}
			menuString.append("</li>");
		}
		return menuString.toString();
	}
	
	
	/**
	 * 
	 * @description
	 *  	用户的保存
	 * @return void
	 * @author 麻木神
	 * @time 2020年1月28日 下午10:19:49
	 */
	public void saveUser(SysUserEntity user, String[] roleIds) {
		super.save(user);
		
		if(null != roleIds) {
			for(String roleId : roleIds) {
				SysUserRoleEntity userRole = new SysUserRoleEntity();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				sysUserRoleService.save(userRole);
			}
		}
	}
	
	

	/**
	 * 
	 * @description
	 *  	根据用户ID 获取用户所有菜单
	 * @params
	 *      userId 用户ID
	 * @return List<SysMenuEntity>
	 * @author guyp
	 * @time 2020年1月3日 下午1:18:08
	 */
	public List<SysFunctionEntity> findMenuByUserId(String userId) {
		return this.baseMapper.findMenuByUserId(userId);
	}
	
	
	/**
	 * 
	 * @description
	 *  	获取当前用户的下属用户ID
	 * @return List<String>
	 * @author 麻木神
	 * @time 2020年4月3日 下午4:22:41
	 */
	public List<String> getUnderUserIds(String userId) {
		//查询用户所属角色
		List<SysRoleEntity> lstRoles = this.getUserRoleByUserId(userId);
		
		if(null != lstRoles && lstRoles.size() > 0) {
			return this.baseMapper.getUnderUserIds(lstRoles.stream().map(r -> r.getId()).collect(Collectors.toList()));
		}else {
			return null;
		}
		
	}
	
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取用户所属角色集合
	 * @params
	 *      userId  用户ID
	 * @return List<SysRoleEntity>
	 * @author 麻木神
	 * @time 2020年1月28日 下午11:40:01
	 */
	public List<SysRoleEntity> getUserRoleByUserId(String userId){
		List<SysRoleEntity> lstRoles = new ArrayList<SysRoleEntity>();
		QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<SysUserRoleEntity>();
		queryWrapper.eq("USER_ID", userId);
		List<SysUserRoleEntity> lstUserRoles = sysUserRoleService.list(queryWrapper);
		if(null != lstUserRoles && lstUserRoles.size() > 0) {
			for(SysUserRoleEntity userRole : lstUserRoles) {
				lstRoles.add(sysRoleService.getById(userRole.getRoleId()));
			}
		}
		
		return lstRoles;
	}
	
	/**
	 * 
	 * @description
	 *  	编辑用户
	 * @return void
	 * @author 麻木神
	 * @time 2020年1月29日 上午12:26:43
	 */
	public void saveOrUpdateUser(SysUserEntity user, String[] roleIds) {
		
		//先删除之前的角色
		QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<SysUserRoleEntity>();
		queryWrapper.eq("USER_ID", user.getId());
		sysUserRoleService.remove(queryWrapper);
		
		//保存用户角色
		if(null != roleIds) {
			for(String roleId : roleIds) {
				SysUserRoleEntity userRole = new SysUserRoleEntity();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				sysUserRoleService.save(userRole);
			}
		}
		
		//修改用户信息
		super.saveOrUpdate(user);
	}

	/**
	 * 
	 * @description
	 *  	根据id查询用户个人资料
	 * @params
	 * @return SysUserModel
	 * @author guyp
	 * @time 2020年2月8日 下午12:36:47
	 */
	public SysUserModel getInfoByUserId(String userId) {
		return this.baseMapper.findInfoByUserId(userId);
	}

	/**
	 * 
	 * @description
	 *  	删除关联的用户信息，然后删除用户
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2020年2月8日 下午4:25:56
	 */
	public void delete(String userId) {
		//先删除角色
		QueryWrapper<SysUserRoleEntity> queryRole = new QueryWrapper<SysUserRoleEntity>();
		queryRole.eq("USER_ID", userId);
		sysUserRoleService.remove(queryRole);
		
		//删除用户
		this.removeById(userId);
		
	}
}
