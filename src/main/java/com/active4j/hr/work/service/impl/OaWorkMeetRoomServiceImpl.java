package com.active4j.hr.work.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.work.dao.OaWorkMeetRoomDao;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
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
@Service("oaWorkMeetRoomService")
@Transactional
public class OaWorkMeetRoomServiceImpl extends ServiceImpl<OaWorkMeetRoomDao, OaWorkMeetRoomEntity> implements OaWorkMeetRoomService {
	
	/**
	 * 
	 * @description
	 *  	查看可用会议室
	 * @return List<OaWorkMeetRoomEntity>
	 * @author 麻木神
	 * @time 2020年4月6日 上午10:32:25
	 */
	public List<OaWorkMeetRoomEntity> findNormalMeetRoom(){
		QueryWrapper<OaWorkMeetRoomEntity> queryWrapper = new QueryWrapper<OaWorkMeetRoomEntity>();
		queryWrapper.eq("STATUS", GlobalConstant.OA_WORK_MEET_ROOM_STATUS_NORMAL);
		return this.list(queryWrapper);
	}
}
