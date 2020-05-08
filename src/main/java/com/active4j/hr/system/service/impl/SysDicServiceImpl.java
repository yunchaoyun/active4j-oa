package com.active4j.hr.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.tree.TSDicTreeData;
import com.active4j.hr.system.dao.SysDicDao;
import com.active4j.hr.system.entity.SysDicEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.service.SysDicService;
import com.active4j.hr.system.service.SysDicValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 数据字典管理service类
 * @author teli_
 *
 */
@Service("sysDicService")
@Transactional
public class SysDicServiceImpl extends ServiceImpl<SysDicDao, SysDicEntity> implements SysDicService {

	@Autowired
	private SysDicValueService sysDicValueService;
	
	/**
	 * 
	 * @description
	 *  	构建树形表格显示的字典
	 * @params
	 * @return List<TSDicTreeData>
	 * @author guyp
	 * @time 2020年2月7日 下午12:47:08
	 */
	public List<TSDicTreeData> getTreeDicList() {
		// 生成树形表格字典的集合
		List<TSDicTreeData> lstDatas = new ArrayList<TSDicTreeData>();

		// 先查父级字典 注意排序
		List<SysDicEntity> lstDics = getParentDics();
		
		getTSDicTreeList(lstDics, lstDatas);
		
		return lstDatas;
	}
	
	/**
	 * 
	 * @description
	 *  	查询顶级字典
	 * @params
	 * @return List<SysDicEntity>
	 * @author guyp
	 * @time 2020年2月7日 下午1:16:56
	 */
	public List<SysDicEntity> getParentDics() {
		// 先查父级字典 注意排序
		QueryWrapper<SysDicEntity> queryWrapper = new QueryWrapper<SysDicEntity>();
		queryWrapper.orderByDesc("CREATE_DATE");
		List<SysDicEntity> lstDics = this.list(queryWrapper);
		
		return lstDics;
	}
	
	/**
	 * 
	 * @description
	 *  	根据字典ID 获取子字典
	 * @params
	 * @return List<SysDicValueEntity>
	 * @author guyp
	 * @time 2020年2月7日 下午4:25:44
	 */
	public List<SysDicValueEntity> getChildDicsByParentId(String dicId) {
		QueryWrapper<SysDicValueEntity> queryWrapper = new QueryWrapper<SysDicValueEntity>();
		queryWrapper.eq("PARENT_ID", dicId);
		queryWrapper.orderByDesc("CREATE_DATE");
		List<SysDicValueEntity> lstChildren = sysDicValueService.list(queryWrapper);
		
		return lstChildren;
	}

	/**
	 * 
	 * @description
	 *  	根据字典的父子关系，生成树形结构
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2020年2月7日 下午4:59:01
	 */
	private void getTSDicTreeList(List<SysDicEntity> lstChildren, List<TSDicTreeData> lstTreeData) {
		if (null != lstChildren && lstChildren.size() >= 0) {
			//父级字典树形赋值
			for (SysDicEntity tsDic : lstChildren) {
				TSDicTreeData treeData = new TSDicTreeData();
				treeData.setId("G" + tsDic.getId());
				treeData.setDicName(tsDic.getName());
				treeData.setDicCode(tsDic.getCode());
				treeData.setParentId(null);
				treeData.setLoaded(true);
				treeData.setLevel("0");
				
				// 获取子菜单
				List<SysDicValueEntity> lstChildren2 = getChildDicsByParentId(tsDic.getId());
				
				if (null != lstChildren2 && lstChildren2.size() > 0) {
					treeData.setLeaf(false);
				} else {
					treeData.setLeaf(true);
				}
				lstTreeData.add(treeData);
				
				//子字典树形赋值
				getTSDicValueTreeList(tsDic, lstChildren2, lstTreeData);

			}
		}
	}
	
	/**
	 * 
	 * @description
	 *  	根据字典的父关系，生成子字典树形结构
	 * @params
	 * @return void
	 * @author guyp
	 * @time 2020年2月7日 下午5:00:25
	 */
	private void getTSDicValueTreeList(SysDicEntity parent, List<SysDicValueEntity> lstChildren, List<TSDicTreeData> lstTreeData) {
		if (null != lstChildren && lstChildren.size() >= 0) {
			//子字典值赋值
			for (SysDicValueEntity tsDicValue : lstChildren) {
				TSDicTreeData treeData = new TSDicTreeData();
				treeData.setId("T" + tsDicValue.getId());
				treeData.setDicName(tsDicValue.getLabel());
				treeData.setDicCode(tsDicValue.getValue());
				treeData.setLevel("1");
				treeData.setLoaded(true);
				treeData.setLeaf(true);
				
				if (null == parent) {
					treeData.setParentId(null);
				} else {
					treeData.setParentId("G" + parent.getId());
				}

				lstTreeData.add(treeData);

			}
		}
	}

	/**
	 * 
	 * @description
	 *  	判断是否存在相同的数据字典编码
	 * @return boolean
	 * @author guyp
	 * @time 2020年2月8日 上午10:09:58
	 */
	public boolean haveCode(String code) {
		// 查询code值
		QueryWrapper<SysDicEntity> queryWrapper = new QueryWrapper<SysDicEntity>();
		queryWrapper.eq("CODE", code);
		List<SysDicEntity> lstDics = this.list(queryWrapper);
		//存在返回true
		if(lstDics.size() > 0) {
			return true;
		}
		return false;
	}

}
