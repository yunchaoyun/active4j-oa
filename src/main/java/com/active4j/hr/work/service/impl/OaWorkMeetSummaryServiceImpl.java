package com.active4j.hr.work.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkMeetSummaryDao;
import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.active4j.hr.work.entity.OaWorkMeetSummaryEntity;
import com.active4j.hr.work.service.OaWorkMeetSummaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaWorkDialyServiceImpl.java
 * @description 
		  工作中心 会议
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkMeetSummaryService")
@Transactional
public class OaWorkMeetSummaryServiceImpl extends ServiceImpl<OaWorkMeetSummaryDao, OaWorkMeetSummaryEntity> implements OaWorkMeetSummaryService {
	
	/**
	 * 
	 * @description
	 *  	根据会议 获取会议纪要
	 * @return OaWorkMeetSummaryEntity
	 * @author 麻木神
	 * @time 2020年4月7日 上午9:57:35
	 */
	public OaWorkMeetSummaryEntity findMeetSummary(OaWorkMeetEntity oaWorkMeetEntity) {
		QueryWrapper<OaWorkMeetSummaryEntity> queryWrapper = new QueryWrapper<OaWorkMeetSummaryEntity>();
		queryWrapper.eq("OA_MEET_ID", oaWorkMeetEntity.getId());
		
		return this.getOne(queryWrapper);
	}
}
