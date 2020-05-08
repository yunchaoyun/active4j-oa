package com.active4j.hr.hr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrYearHolidayDao;
import com.active4j.hr.hr.domain.OaHrUserYearHolidayModel;
import com.active4j.hr.hr.entity.OaHrYearHolidayEntity;
import com.active4j.hr.hr.service.OaHrYearHolidayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 年假维护管理
 * @title OaHrYearHolidayServiceImpl.java
 * @description 
		TODO
 * @time  2020年4月24日 下午2:06:09
 * @author guyp
 * @version 1.0
 */
@Service("oaHrYearHolidayService")
@Transactional
public class OaHrYearHolidayServiceImpl extends ServiceImpl<OaHrYearHolidayDao, OaHrYearHolidayEntity> implements OaHrYearHolidayService {

	/**
	 * 
	 * @description
	 *  	年假统计
	 * @return List<OaHrUserYearHolidayModel>
	 * @author guyp
	 * @time 2020年4月24日 下午2:03:40
	 */
	public List<OaHrUserYearHolidayModel> queryYearHolidayModel() {
		return this.baseMapper.queryYearHolidayModel();
	}


}
