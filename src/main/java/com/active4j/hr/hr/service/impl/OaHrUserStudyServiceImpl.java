package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrUserStudyDao;
import com.active4j.hr.hr.entity.OaHrUserStudyEntity;
import com.active4j.hr.hr.service.OaHrUserStudyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserStudyServiceImpl.java
 * @description 
		人力资源-教育信息
 * @time  2020年4月13日 上午11:06:28
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserStudyService")
@Transactional
public class OaHrUserStudyServiceImpl extends ServiceImpl<OaHrUserStudyDao, OaHrUserStudyEntity> implements OaHrUserStudyService {

}
