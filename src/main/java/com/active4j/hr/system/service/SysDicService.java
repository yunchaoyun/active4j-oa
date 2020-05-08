package com.active4j.hr.system.service;

import java.util.List;

import com.active4j.hr.core.web.tag.model.tree.TSDicTreeData;
import com.active4j.hr.system.entity.SysDicEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysDicService extends IService<SysDicEntity> {

	/**
	 * 
	 * @description
	 *  	构建树形表格显示的菜单
	 * @params
	 * @return List<TSDicTreeData>
	 * @author guyp
	 * @time 2020年2月7日 下午12:47:08
	 */
	public List<TSDicTreeData> getTreeDicList();
	
	/**
	 * 
	 * @description
	 *  	判断是否存在相同的数据字典编码
	 * @return boolean
	 * @author guyp
	 * @time 2020年2月8日 上午10:09:58
	 */
	public boolean haveCode(String code);
}
