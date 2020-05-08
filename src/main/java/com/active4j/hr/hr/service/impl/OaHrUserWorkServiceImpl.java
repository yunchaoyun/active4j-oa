package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrUserWorkDao;
import com.active4j.hr.hr.entity.OaHrUserWorkEntity;
import com.active4j.hr.hr.service.OaHrUserWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserWorkServiceImpl.java
 * @description 
		人力资源-工作信息
 * @time  2020年4月13日 上午11:08:49
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserWorkService")
@Transactional
public class OaHrUserWorkServiceImpl extends ServiceImpl<OaHrUserWorkDao, OaHrUserWorkEntity> implements OaHrUserWorkService {

}
