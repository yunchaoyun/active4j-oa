package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrRecruitNeedDao;
import com.active4j.hr.hr.entity.OaHrRecruitNeedEntity;
import com.active4j.hr.hr.service.OaHrRecruitNeedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitNeedServiceImpl.java
 * @desc 招聘管理-招聘需求
 * @time  2020年4月21日 下午4:05:28
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitNeedService")
@Transactional
public class OaHrRecruitNeedServiceImpl extends ServiceImpl<OaHrRecruitNeedDao, OaHrRecruitNeedEntity> implements OaHrRecruitNeedService {

}
