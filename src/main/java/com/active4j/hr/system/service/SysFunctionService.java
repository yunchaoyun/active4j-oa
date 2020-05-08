package com.active4j.hr.system.service;

import java.util.List;

import com.active4j.hr.core.web.tag.model.tree.TSFunctionTreeData;
import com.active4j.hr.system.entity.SysFunctionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysFunctionService extends IService<SysFunctionEntity> {
	
	
	/**
	 * 
	 * @description
	 *  	查询顶级菜单
	 * @return List<SysFunctionEntity>
	 * @author 麻木神
	 * @time 2020年1月19日 上午9:29:21
	 */
	public List<SysFunctionEntity> getParentFunctions();
	
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
	public List<SysFunctionEntity> getChildFunctionsByParentId(String functionId);
	
	/**
	 * 根据指定菜单，获取子级菜单
	 * @param menu
	 * @return
	 */
	public List<SysFunctionEntity> getChildMenusByMenu(SysFunctionEntity menu);
	
	/**
	 * 删除指定菜单
	 * @param menu
	 */
	public void deleteMenu(SysFunctionEntity menu);

	
	/**
	 * 
	 * @description
	 *  	构建树形表格显示的菜单
	 * @return List<TSFunctionTreeData>
	 * @author 麻木神
	 * @time 2020年1月16日 下午9:43:23
	 */
	public List<TSFunctionTreeData> getTreeFunctionList();
	
}
