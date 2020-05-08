package com.active4j.hr.work.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkMeetRoomBooksDao;
import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomBooksService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaWorkDialyServiceImpl.java
 * @description 
		  工作中心 会议室类型
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkMeetRoomBooksService")
@Transactional
public class OaWorkMeetRoomBooksServiceImpl extends ServiceImpl<OaWorkMeetRoomBooksDao, OaWorkMeetRoomBooksEntity> implements OaWorkMeetRoomBooksService {
	
	/**
	 * 查看会议室的预定
	 */
	public List<OaWorkMeetRoomBooksEntity> findMeetBooks(OaWorkMeetRoomEntity oaWorkMeetRoomEntity){
		QueryWrapper<OaWorkMeetRoomBooksEntity> queryWrapper = new QueryWrapper<OaWorkMeetRoomBooksEntity>();
		queryWrapper.eq("MEET_ROOM_ID", oaWorkMeetRoomEntity.getId());
		
		return this.list(queryWrapper);
		
	}
	
	
	/**
	 * @return List<OaWorkMeetRoomBooksEntity>
	 * @author 麻木神
	 * @time 2020年4月6日 上午10:50:34
	 */
	public List<OaWorkMeetRoomBooksEntity> findMeetBooks(String roomId, String strDate) {
		QueryWrapper<OaWorkMeetRoomBooksEntity> queryWrapper = new QueryWrapper<OaWorkMeetRoomBooksEntity>();
		queryWrapper.eq("MEET_ROOM_ID", roomId);
		queryWrapper.eq("STR_BOOK_DATE", strDate);
		
		return this.list(queryWrapper);
	}
}
