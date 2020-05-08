package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrRecruitPlanDao;
import com.active4j.hr.hr.entity.OaHrRecruitPlanEntity;
import com.active4j.hr.hr.service.OaHrRecruitPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitPlanServiceImpl.java
 * @description 
		招聘计划管理
 * @time  2020年4月21日 下午5:26:38
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitPlanService")
@Transactional
public class OaHrRecruitPlanServiceImpl extends ServiceImpl<OaHrRecruitPlanDao, OaHrRecruitPlanEntity> implements OaHrRecruitPlanService {

}
