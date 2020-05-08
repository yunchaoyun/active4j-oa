package com.active4j.hr.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.system.dao.SysOperationDao;
import com.active4j.hr.system.entity.SysOperationEntity;
import com.active4j.hr.system.service.SysOperationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 菜单管理-按钮service类
 * @author teli_
 *
 */
@Service("sysOperationService")
@Transactional
public class SysOperationServiceImpl extends ServiceImpl<SysOperationDao, SysOperationEntity> implements SysOperationService {
	
	/**
	 * 
	 * @description
	 *  	根据菜单ID 获取该菜单下的按钮
	 * @return List<SysOperationEntity>
	 * @author 麻木神
	 * @time 2020年2月2日 下午4:34:37
	 */
	public List<SysOperationEntity> getOperationsByFunctionId(String functionId) {
		QueryWrapper<SysOperationEntity> queryWrapper = new QueryWrapper<SysOperationEntity>();
		queryWrapper.eq("FUNCTION_ID", functionId);
		
		return this.list(queryWrapper);
	}
}
