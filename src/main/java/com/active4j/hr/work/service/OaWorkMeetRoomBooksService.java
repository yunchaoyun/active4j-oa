package com.active4j.hr.work.service;

import java.util.List;

import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkMeetRoomBooksService extends IService<OaWorkMeetRoomBooksEntity> {

	/**
	 * 查看会议室的预定
	 */
	public List<OaWorkMeetRoomBooksEntity> findMeetBooks(OaWorkMeetRoomEntity oaWorkMeetRoomEntity);
	
	/**
	 * 
	 * @return List<OaWorkMeetRoomBooksEntity>
	 * @author 麻木神
	 * @time 2020年4月6日 上午10:50:34
	 */
	public List<OaWorkMeetRoomBooksEntity> findMeetBooks(String roomId, String strDate);

}
