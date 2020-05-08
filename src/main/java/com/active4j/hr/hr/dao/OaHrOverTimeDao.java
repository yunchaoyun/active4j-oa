package com.active4j.hr.hr.dao;

import java.util.List;

import com.active4j.hr.hr.domain.OaHrUserOverTimeModel;
import com.active4j.hr.hr.entity.OaHrOverTimeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface OaHrOverTimeDao extends BaseMapper<OaHrOverTimeEntity>{

	/**
	 * 
	 * @description
	 *  	加班统计
	 * @return List<OaHrUserOverTimeModel>
	 * @author guyp
	 * @time 2020年4月24日 上午10:10:17
	 */
	public List<OaHrUserOverTimeModel> queryOverTimeModel();
}
