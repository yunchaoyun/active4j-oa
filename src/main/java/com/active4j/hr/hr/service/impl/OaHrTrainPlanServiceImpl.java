package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrTrainPlanDao;
import com.active4j.hr.hr.entity.OaHrTrainPlanEntity;
import com.active4j.hr.hr.service.OaHrTrainPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrTrainPlanServiceImpl.java
 * @description 
		课程计划管理
 * @time  2020年4月23日 上午10:15:01
 * @author guyp
 * @version 1.0
 */
@Service("oaHrTrainPlanService")
@Transactional
public class OaHrTrainPlanServiceImpl extends ServiceImpl<OaHrTrainPlanDao, OaHrTrainPlanEntity> implements OaHrTrainPlanService {

}
