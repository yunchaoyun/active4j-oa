package com.active4j.hr.hr.dao;

import java.util.List;

import com.active4j.hr.hr.domain.OaHrUserYearHolidayModel;
import com.active4j.hr.hr.entity.OaHrYearHolidayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface OaHrYearHolidayDao extends BaseMapper<OaHrYearHolidayEntity>{

	/**
	 * 
	 * @description
	 *  	年假统计
	 * @return List<OaHrUserYearHolidayModel>
	 * @author guyp
	 * @time 2020年4月24日 下午2:03:40
	 */
	public List<OaHrUserYearHolidayModel> queryYearHolidayModel();
}
