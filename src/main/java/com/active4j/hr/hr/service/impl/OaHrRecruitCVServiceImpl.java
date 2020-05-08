package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrRecruitCVDao;
import com.active4j.hr.hr.entity.OaHrRecruitCVEntity;
import com.active4j.hr.hr.service.OaHrRecruitCVService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitCVServiceImpl.java
 * @description 
		招聘管理-简历库
 * @time  2020年4月22日 下午1:25:45
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitCVService")
@Transactional
public class OaHrRecruitCVServiceImpl extends ServiceImpl<OaHrRecruitCVDao, OaHrRecruitCVEntity> implements OaHrRecruitCVService {

}
