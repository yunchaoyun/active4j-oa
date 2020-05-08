package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrUserPactDao;
import com.active4j.hr.hr.entity.OaHrUserPactEntity;
import com.active4j.hr.hr.service.OaHrUserPactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserPactServiceImpl.java
 * @description 
		人力资源-人事合同
 * @time  2020年4月15日 下午5:02:36
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserPactService")
@Transactional
public class OaHrUserPactServiceImpl extends ServiceImpl<OaHrUserPactDao, OaHrUserPactEntity> implements OaHrUserPactService {


}
