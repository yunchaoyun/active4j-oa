package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrTrainPlanCourseDao;
import com.active4j.hr.hr.entity.OaHrTrainPlanCourseEntity;
import com.active4j.hr.hr.service.OaHrTrainPlanCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrTrainPlanCourseServiceImpl.java
 * @description 
		课程计划-课程 多对多管理
 * @time  2020年4月23日 上午10:19:11
 * @author guyp
 * @version 1.0
 */
@Service("oaHrTrainPlanCourseService")
@Transactional
public class OaHrTrainPlanCourseServiceImpl extends ServiceImpl<OaHrTrainPlanCourseDao, OaHrTrainPlanCourseEntity> implements OaHrTrainPlanCourseService {

}
