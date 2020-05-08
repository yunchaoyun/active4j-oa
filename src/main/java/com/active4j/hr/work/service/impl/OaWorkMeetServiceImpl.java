package com.active4j.hr.work.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkMeetDao;
import com.active4j.hr.work.entity.OaWorkMeetEntity;
import com.active4j.hr.work.service.OaWorkMeetService;
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
@Service("oaWorkMeetService")
@Transactional
public class OaWorkMeetServiceImpl extends ServiceImpl<OaWorkMeetDao, OaWorkMeetEntity> implements OaWorkMeetService {
	
	
	/**
	 * 是否生成日程的会议保存方法
	 * @param oaWorkMeetEntity
	 * @param partIn
	 */
	public void save(OaWorkMeetEntity oaWorkMeetEntity, boolean partIn, boolean isTip){
		if(partIn) {
			this.save(oaWorkMeetEntity);
			
			//生成日程
			generateSchedule(oaWorkMeetEntity);
			
		}else {
			this.save(oaWorkMeetEntity);
		}
		
		if(isTip) {
			//动态添加会议的定时任务
			
		}
	}
	
	
	/**
	 * 是否生成日程的会议保存方法  编辑
	 * @param oaWorkMeetEntity
	 * @param partIn
	 */
	public void saveOrUpdate(OaWorkMeetEntity oaWorkMeetEntity, boolean partIn, boolean isTip){
		
		this.saveOrUpdate(oaWorkMeetEntity);
		
		//删除日程
		
		if(partIn) {
			//生成日程
			generateSchedule(oaWorkMeetEntity);
		}
		
		//删除定时任务提醒
		
		
		if(isTip) {
			//生成定时任务提醒
			
		}
	}
	
	/**
	 * 保存日程
	 * @param oaWorkMeetEntity
	 */
	private void generateSchedule(OaWorkMeetEntity oaWorkMeetEntity){
		//生成日程
		
	}
	
	
	/**
	 * 删除会议，是否连同删除日程信息和提醒信息
	 * @param oaWorkMeetEntity
	 * @param flag
	 */
	public void delete(OaWorkMeetEntity oaWorkMeetEntity, boolean flag){
		this.removeById(oaWorkMeetEntity.getId());
		
		if(flag){
			//删除日程
			
			//删除定时任务提醒
		}
	}
	
}
