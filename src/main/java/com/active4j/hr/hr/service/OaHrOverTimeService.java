package com.active4j.hr.hr.service;

import java.util.List;

import com.active4j.hr.hr.domain.OaHrUserOverTimeModel;
import com.active4j.hr.hr.entity.OaHrOverTimeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrOverTimeService extends IService<OaHrOverTimeEntity> {
	
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
