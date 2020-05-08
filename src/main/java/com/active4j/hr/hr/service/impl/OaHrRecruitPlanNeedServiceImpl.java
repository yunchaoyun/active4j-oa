package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrRecruitPlanNeedDao;
import com.active4j.hr.hr.entity.OaHrRecruitPlanNeedEntity;
import com.active4j.hr.hr.service.OaHrRecruitPlanNeedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitPlanServiceImpl.java
 * @description 
		招聘计划管理 - 需求中间
 * @time  2020年4月21日 下午5:26:38
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitPlanNeedService")
@Transactional
public class OaHrRecruitPlanNeedServiceImpl extends ServiceImpl<OaHrRecruitPlanNeedDao, OaHrRecruitPlanNeedEntity> implements OaHrRecruitPlanNeedService {

}
