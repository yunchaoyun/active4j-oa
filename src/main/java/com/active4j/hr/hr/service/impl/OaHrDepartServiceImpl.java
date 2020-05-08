package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.service.OaHrDepartService;
import com.active4j.hr.system.dao.SysDeptDao;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrDepartServiceImpl.java
 * @description 
		人力资源-部门管理
 * @time  2020年4月9日 下午3:53:46
 * @author guyp
 * @version 1.0
 */
@Service("oaHrDepartService")
@Transactional
public class OaHrDepartServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements OaHrDepartService {

}
