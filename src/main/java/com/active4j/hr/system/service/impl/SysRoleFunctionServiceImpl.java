package com.active4j.hr.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.system.dao.SysRoleFunctionDao;
import com.active4j.hr.system.entity.SysRoleFunctionEntity;
import com.active4j.hr.system.service.SysRoleFunctionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 角色菜单管理service类
 * @author teli_
 *
 */
@Service("sysRoleFunctionService")
@Transactional
public class SysRoleFunctionServiceImpl extends ServiceImpl<SysRoleFunctionDao, SysRoleFunctionEntity> implements SysRoleFunctionService {
	

}
