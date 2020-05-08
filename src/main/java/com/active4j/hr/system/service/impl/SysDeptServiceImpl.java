package com.active4j.hr.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.tree.TSDepartTreeData;
import com.active4j.hr.system.dao.SysDeptDao;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 部门管理service类
 * 
 * @author teli_
 *
 */
@Service("sysDeptService")
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 
	 * @description 获取所有顶级部门
	 * @return List<SysDeptEntity>
	 * @author 麻木神
	 * @time 2020年1月28日 下午3:13:14
	 */
	public List<SysDeptEntity> getParentDepts() {
		// 先查父级菜单 注意排序
		QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<SysDeptEntity>();
		queryWrapper.isNull("PARENT_ID");
		queryWrapper.orderByAsc("LEVEL");
		List<SysDeptEntity> lstDeparts = this.list(queryWrapper);

		return lstDeparts;
	}

	/**
	 * @description 根据部门ID 获取子部门
	 * @return List<SysDeptEntity>
	 * @author 麻木神
	 * @time 2020年1月28日 下午3:35:12
	 */
	public List<SysDeptEntity> getChildDeptsByDeptId(String deptId) {
		QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<SysDeptEntity>();
		queryWrapper.eq("PARENT_ID", deptId);
		queryWrapper.orderByAsc("LEVEL");
		List<SysDeptEntity> lstDeparts = this.list(queryWrapper);

		return lstDeparts;

	}

	/**
	 * 
	 * @description 表格的树形显示
	 * @return List<TSDepartTreeData>
	 * @author 麻木神
	 * @time 2020年1月29日 下午4:15:50
	 */
	public List<TSDepartTreeData> getTreeDepartList() {
		// 生成树形表格菜单的集合
		List<TSDepartTreeData> lstDatas = new ArrayList<TSDepartTreeData>();
		
		// 先查父级菜单 注意排序
		List<SysDeptEntity> lstDeparts = getParentDepts();
		
		//递归显示所有部门
		getTSDepartTreeList(null, lstDeparts, lstDatas);
		
		return lstDatas;
	}

	private void getTSDepartTreeList(SysDeptEntity parentDepart, List<SysDeptEntity> lstChildren, List<TSDepartTreeData> lstTreeData) {
		if (null != lstChildren && lstChildren.size() >= 0) {
			// 这里集合要进行排序
			Collections.sort(lstChildren, (s1, s2) -> s1.getLevel() - s2.getLevel());
			for (SysDeptEntity dept : lstChildren) {
				TSDepartTreeData treeData = new TSDepartTreeData();
				treeData.setId(dept.getId());
				treeData.setDepartName(dept.getName());
				treeData.setDepartNo(dept.getDeptNo());
				treeData.setType(dept.getType());
				treeData.setExpanded(false);
				treeData.setLoaded(true);
				if (null == parentDepart) {
					treeData.setParentId(null);
				} else {
					treeData.setParentId(parentDepart.getId());
				}

				treeData.setLevel(String.valueOf(dept.getLevel())); // 层次
				// 获取子菜单
				List<SysDeptEntity> lstChildren2 = getChildDeptsByDeptId(dept.getId());
				
				if (null != lstChildren2 && lstChildren2.size() > 0) {
					treeData.setLeaf(false);
				} else {
					treeData.setLeaf(true);
				}
				lstTreeData.add(treeData);

				getTSDepartTreeList(dept, lstChildren2, lstTreeData);

			}
		}
	}
	
	/**
	 * 
	 * @description
	 *  	根据部门id获取用户
	 * @params
	 * @return List<SysUserEntity>
	 * @author guyp
	 * @time 2020年4月9日 下午4:27:12
	 */
	public List<SysUserEntity> getUsersByDept(String deptId) {
		QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>();
		queryWrapper.eq("DEPT_ID", deptId);
		List<SysUserEntity> lstUsers = sysUserService.list(queryWrapper);
		
		return lstUsers;
	}
}
