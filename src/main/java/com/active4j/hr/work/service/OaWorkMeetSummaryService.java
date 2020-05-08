package com.active4j.hr.work.service;

import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.active4j.hr.work.entity.OaWorkMeetSummaryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkMeetSummaryService extends IService<OaWorkMeetSummaryEntity> {
	
	/**
	 * 
	 * @description
	 *  	根据会议 获取会议纪要
	 * @return OaWorkMeetSummaryEntity
	 * @author 麻木神
	 * @time 2020年4月7日 上午9:57:35
	 */
	public OaWorkMeetSummaryEntity findMeetSummary(OaWorkMeetEntity oaWorkMeetEntity);
	
}
