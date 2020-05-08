package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrSpecialTimeDao;
import com.active4j.hr.hr.entity.OaHrSpecialTimeEntity;
import com.active4j.hr.hr.service.OaHrSpecialTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrSpecialTimeServiceImpl.java
 * @description 
		特殊考勤维护管理
 * @time  2020年4月24日 下午2:35:27
 * @author guyp
 * @version 1.0
 */
@Service("oaHrSpecialTimeService")
@Transactional
public class OaHrSpecialTimeServiceImpl extends ServiceImpl<OaHrSpecialTimeDao, OaHrSpecialTimeEntity> implements OaHrSpecialTimeService {


}
