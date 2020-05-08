package com.active4j.hr.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.system.dao.SysDicValueDao;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.service.SysDicValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 *  数据字典service类
 * @author teli_
 *
 */
@Service("sysDicValueService")
@Transactional
public class SysDicValueServiceImpl extends ServiceImpl<SysDicValueDao, SysDicValueEntity> implements SysDicValueService {

	

}
