package com.active4j.hr.work.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.work.domain.OaWorkTaskStatusDomain;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @title OaWorkTaskDao.java
 * @description 
		  任务管理
 * @time  2020年4月6日 上午9:22:19
 * @author 麻木神
 * @version 1.0
*/
public interface OaWorkTaskDao extends BaseMapper<OaWorkTaskEntity>{

	public List<OaWorkTaskStatusDomain> queryOaWorkTaskStatusStat(@Param("userId") String userId);
	
}
