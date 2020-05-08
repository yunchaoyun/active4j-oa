package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrUserPayDao;
import com.active4j.hr.hr.entity.OaHrUserPayEntity;
import com.active4j.hr.hr.service.OaHrUserPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserPayServiceImpl.java
 * @description 
		人力资源-成本信息
 * @time  2020年4月13日 上午11:04:38
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserPayService")
@Transactional
public class OaHrUserPayServiceImpl extends ServiceImpl<OaHrUserPayDao, OaHrUserPayEntity> implements OaHrUserPayService {

}
