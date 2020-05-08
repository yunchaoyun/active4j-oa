package com.active4j.hr.work.service;

import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkMeetService extends IService<OaWorkMeetEntity> {
	
	/**
	 * 是否生成日程的会议保存方法  新增
	 * @param oaWorkMeetEntity
	 * @param partIn
	 */
	public void save(OaWorkMeetEntity oaWorkMeetEntity, boolean partIn, boolean isTip);

	
	/**
	 * 是否生成日程的会议保存方法  编辑
	 * @param oaWorkMeetEntity
	 * @param partIn
	 */
	public void saveOrUpdate(OaWorkMeetEntity tmp, boolean partIn, boolean isTip);
	
	/**
	 * 删除会议，是否连同删除日程信息和提醒信息
	 * @param oaWorkMeetEntity
	 * @param flag
	 */
	public void delete(OaWorkMeetEntity oaWorkMeetEntity, boolean flag);

}
