package com.active4j.hr.work.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkMeetTypeDao;
import com.active4j.hr.work.entity.OaWorkMeetTypeEntity;
import com.active4j.hr.work.service.OaWorkMeetTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaWorkDialyServiceImpl.java
 * @description 
		  工作中心 会议类型
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkMeetTypeService")
@Transactional
public class OaWorkMeetTypeServiceImpl extends ServiceImpl<OaWorkMeetTypeDao, OaWorkMeetTypeEntity> implements OaWorkMeetTypeService {
	
	
}
