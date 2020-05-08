package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrRecruitBackupDao;
import com.active4j.hr.hr.entity.OaHrRecruitBackupEntity;
import com.active4j.hr.hr.service.OaHrRecruitBackupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitBackupServiceImpl.java
 * @description 
		招聘管理-后备资源库
 * @time  2020年4月22日 下午5:13:09
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitBackupService")
@Transactional
public class OaHrRecruitBackupServiceImpl extends ServiceImpl<OaHrRecruitBackupDao, OaHrRecruitBackupEntity> implements OaHrRecruitBackupService {

}
