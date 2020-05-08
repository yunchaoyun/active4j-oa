package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrTrainCourseCateDao;
import com.active4j.hr.hr.entity.OaHrTrainCourseCateEntity;
import com.active4j.hr.hr.service.OaHrTrainCourseCateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrTrainCourseCateServiceImpl.java
 * @description 
		课程类别管理
 * @time  2020年4月23日 上午10:16:33
 * @author guyp
 * @version 1.0
 */
@Service("oaHrTrainCourseCateService")
@Transactional
public class OaHrTrainCourseCateServiceImpl extends ServiceImpl<OaHrTrainCourseCateDao, OaHrTrainCourseCateEntity> implements OaHrTrainCourseCateService {

}
