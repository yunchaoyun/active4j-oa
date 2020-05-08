package com.active4j.hr.workcal.service;

import java.util.List;

import com.active4j.hr.workcal.entity.OaHrWorkcalRuleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrWorkcalRuleService extends IService<OaHrWorkcalRuleEntity> {
	
	/**
	 * 
	 * @description
	 *  	按输入年份，初始化考勤日历规则
	 * @return void
	 * @author guyp
	 * @time 2020年4月20日 上午10:49:10
	 */
	public void initYearRule(String year);
	
	/**
	 * 
	 * @description
	 *  	获取系统中的年份配置
	 * @return List<String>
	 * @author guyp
	 * @time 2020年4月20日 下午1:33:32
	 */
	public List<String> getYearGroups();
}
