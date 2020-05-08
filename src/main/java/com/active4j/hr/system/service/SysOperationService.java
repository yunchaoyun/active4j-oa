package com.active4j.hr.system.service;

import java.util.List;

import com.active4j.hr.system.entity.SysOperationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysOperationService extends IService<SysOperationEntity> {

	
	/**
	 * 
	 * @description
	 *  	根据菜单ID 获取该菜单下的按钮
	 * @return List<SysOperationEntity>
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:34:37
	 */
	public List<SysOperationEntity> getOperationsByFunctionId(String functionId);

	

}
