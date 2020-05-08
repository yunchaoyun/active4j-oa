package com.active4j.hr.hr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrOverTimeDao;
import com.active4j.hr.hr.domain.OaHrUserOverTimeModel;
import com.active4j.hr.hr.entity.OaHrOverTimeEntity;
import com.active4j.hr.hr.service.OaHrOverTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrOverTimeServiceImpl.java
 * @description 
		加班维护管理
 * @time  2020年4月24日 上午9:59:28
 * @author guyp
 * @version 1.0
 */
@Service("oaHrOverTimeService")
@Transactional
public class OaHrOverTimeServiceImpl extends ServiceImpl<OaHrOverTimeDao, OaHrOverTimeEntity> implements OaHrOverTimeService {

	/**
	 * 
	 * @description
	 *  	加班统计
	 * @return List<OaHrUserOverTimeModel>
	 * @author guyp
	 * @time 2020年4月24日 上午10:10:17
	 */
	public List<OaHrUserOverTimeModel> queryOverTimeModel() {
		return this.baseMapper.queryOverTimeModel();
	}

}
