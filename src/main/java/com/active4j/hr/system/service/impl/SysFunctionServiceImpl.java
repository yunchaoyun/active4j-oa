package com.active4j.hr.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.tree.TSFunctionTreeData;
import com.active4j.hr.system.dao.SysFunctionDao;
import com.active4j.hr.system.entity.SysFunctionEntity;
import com.active4j.hr.system.entity.SysRoleFunctionEntity;
import com.active4j.hr.system.service.SysFunctionService;
import com.active4j.hr.system.service.SysRoleFunctionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 菜单管理service类
 * 
 * @author teli_
 *
 */
@Service("sysFunctionService")
@Transactional
public class SysFunctionServiceImpl extends ServiceImpl<SysFunctionDao, SysFunctionEntity> implements SysFunctionService {
	
	
	@Autowired
	private SysRoleFunctionService sysRoleFunctionService;
	
	/**
	 * 
	 * @description
	 *  	查询顶级菜单
	 * @return List<SysFunctionEntity>
	 * @author 麻木神
	 * @time 2020年1月19日 上午9:29:21
	 */
	public List<SysFunctionEntity> getParentFunctions() {
		// 先查父级菜单 注意排序
		QueryWrapper<SysFunctionEntity> queryWrapper = new QueryWrapper<SysFunctionEntity>();
		queryWrapper.isNull("PARENT_ID");
		queryWrapper.orderByAsc("ORDER_NO");
		List<SysFunctionEntity> lstMenus = this.list(queryWrapper);
		
		return lstMenus;
	}
	
	
	/**
	 * 
	 * @description
	 *  	根据菜单ID 获取子菜单
	 * @params
	 *      菜单ID
	 * @return List<SysFunctionEntity>
	 * @author 麻木神
	 * @time 2020年1月19日 上午10:05:02
	 */
	public List<SysFunctionEntity> getChildFunctionsByParentId(String functionId) {
		QueryWrapper<SysFunctionEntity> queryWrapper = new QueryWrapper<SysFunctionEntity>();
		queryWrapper.eq("PARENT_ID", functionId);
		queryWrapper.orderByAsc("ORDER_NO");
		List<SysFunctionEntity> lstChildren = this.list(queryWrapper);
		
		return lstChildren;
	}
	
	
	/**
	 * 根据指定菜单，获取子级菜单
	 * @param menu
	 * @return
	 */
	public List<SysFunctionEntity> getChildMenusByMenu(SysFunctionEntity menu){
		QueryWrapper<SysFunctionEntity> queryWrapper = new QueryWrapper<SysFunctionEntity>();
		queryWrapper.eq("PARENT_ID", menu.getId());
		return this.list(queryWrapper);
	}

	
	/**
	 * 删除指定菜单
	 * @param menu
	 */
	public void deleteMenu(SysFunctionEntity menu) {
		QueryWrapper<SysRoleFunctionEntity> queryWrapper = new QueryWrapper<SysRoleFunctionEntity>();
		queryWrapper.eq("MENU_ID", menu.getId());
		sysRoleFunctionService.remove(queryWrapper);
		
		this.removeById(menu.getId());
	}
	

	/**
	 * 
	 * @description
	 *  	构建树形表格显示的菜单
	 * @return List<TSFunctionTreeData>
	 * @author 麻木神
	 * @time 2020年1月16日 下午9:43:23
	 */
	public List<TSFunctionTreeData> getTreeFunctionList() {
		// 生成树形表格菜单的集合
		List<TSFunctionTreeData> lstDatas = new ArrayList<TSFunctionTreeData>();

		// 先查父级菜单 注意排序
		List<SysFunctionEntity> lstMenus = getParentFunctions();
		
		getTSFunctionTreeList(null, lstMenus, lstDatas);
		
		return lstDatas;
	}
	
	/**
	 * 递归的方式，根据菜单的父子关系，生成树形结构
	 * 
	 * @param parent
	 * @param lstChildren
	 */
	private void getTSFunctionTreeList(SysFunctionEntity parent, List<SysFunctionEntity> lstChildren, List<TSFunctionTreeData> lstTreeData) {
		if (null != lstChildren && lstChildren.size() >= 0) {
			// 这里集合要进行排序
			Collections.sort(lstChildren, (s1, s2) -> s1.getOrderNo() -s2.getOrderNo());
			for (SysFunctionEntity tsFunction : lstChildren) {
				TSFunctionTreeData treeData = new TSFunctionTreeData();
				treeData.setId(tsFunction.getId());
				treeData.setFunctionName((StringUtils.isEmpty(tsFunction.getIcon()) ? "" : tsFunction.getIcon()) + " " + tsFunction.getName()); // 菜单名
				treeData.setFunctionOrder(tsFunction.getOrderNo()); // 菜单的排序
				treeData.setFunctionUrl(tsFunction.getUrl()); // 菜单的URL
				treeData.setExpanded(false);
				treeData.setLoaded(true);
				if (null == parent) {
					treeData.setParentId(null);
				} else {
					treeData.setParentId(parent.getId());
				}

				treeData.setLevel(String.valueOf(tsFunction.getLevel())); // 层次
				// 获取子菜单
				List<SysFunctionEntity> lstChildren2 = getChildFunctionsByParentId(tsFunction.getId());
				
				if (null != lstChildren2 && lstChildren2.size() > 0) {
					treeData.setLeaf(false);
				} else {
					treeData.setLeaf(true);
				}
				lstTreeData.add(treeData);

				getTSFunctionTreeList(tsFunction, lstChildren2, lstTreeData);

			}
		}
	}

}
