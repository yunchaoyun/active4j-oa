package com.active4j.hr.work.service;

import java.util.List;

import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkMeetRoomService extends IService<OaWorkMeetRoomEntity> {
	
	/**
	 * 
	 * @description
	 *  	查看可用会议室
	 * @return List<OaWorkMeetRoomEntity>
	 * @author 麻木神
	 * @time 2020年4月6日 上午10:32:25
	 */
	public List<OaWorkMeetRoomEntity> findNormalMeetRoom();
}
