package com.active4j.hr.workcal.dao;

import java.util.List;

import com.active4j.hr.workcal.entity.OaHrWorkcalRuleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface OaHrWorkcalRuleDao extends BaseMapper<OaHrWorkcalRuleEntity>{
	
	/**
	 * 
	 * @description
	 *  	获取系统中的年份配置
	 * @return List<String>
	 * @author guyp
	 * @time 2020年4月20日 下午1:30:54
	 */
	public List<String> getYearGroups();
}
