package com.active4j.hr.workcal.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.workcal.dao.OaHrWorkcalRuleDao;
import com.active4j.hr.workcal.entity.OaHrWorkcalRuleEntity;
import com.active4j.hr.workcal.service.OaHrWorkcalRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrWorkcalRuleServiceImpl.java
 * @description 
		考勤管理-工作日历规则设置
 * @time  2020年4月20日 上午9:59:19
 * @author guyp
 * @version 1.0
 */
@Service("oaHrWorkcalRuleService")
@Transactional
@Slf4j
public class OaHrWorkcalRuleServiceImpl extends ServiceImpl<OaHrWorkcalRuleDao, OaHrWorkcalRuleEntity> implements OaHrWorkcalRuleService {

	/**
	 * 
	 * @description
	 *  	按输入年份，初始化考勤日历规则
	 * @return void
	 * @author guyp
	 * @time 2020年4月20日 上午10:49:10
	 */
	public void initYearRule(String year) {
		//根据年份数据，查询系统中的当前年的数据, 并将其删除
		QueryWrapper<OaHrWorkcalRuleEntity> queryWrapper = new QueryWrapper<OaHrWorkcalRuleEntity>();
		queryWrapper.eq("YEAR", year);
		this.remove(queryWrapper);
		
		//开始初始化数据
		int year1 = Integer.valueOf(year); //当前年的年份数据
		int year2 = year1 + 1; //下一年
		Calendar startCa = Calendar.getInstance();
		Calendar endCa = Calendar.getInstance();
		
		startCa.set(year1, 0, 1);
		endCa.set(year2, 0, 1);
		
		List<OaHrWorkcalRuleEntity> lstRules = new ArrayList<OaHrWorkcalRuleEntity>();
		while(startCa.before(endCa)) {
			log.info(startCa.get(Calendar.YEAR) + "年" + (startCa.get(Calendar.MONTH) + 1) + "月" + startCa.get(Calendar.DAY_OF_MONTH) + "日");
			OaHrWorkcalRuleEntity rule = new OaHrWorkcalRuleEntity();
			
			//当前日期
			Date nowDate = startCa.getTime();
			String strNowDate = DateUtils.date2Str(nowDate, DateUtils.SDF_YYYY_MM_DD);
			String strYearMonth = DateUtils.date2Str(nowDate, DateUtils.SDF_YYYY_MM_);
			rule.setCurrentDateTime(nowDate);
			rule.setCurrentStrDate(strNowDate);
			rule.setStrYearMonth(strYearMonth);
			rule.setEndTime("00:00");
			rule.setStartTime("00:00");
			if(Calendar.SATURDAY == startCa.get(Calendar.DAY_OF_WEEK)) {
				//双休
				rule.setNeedSign(false);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WEEKEND);
				rule.setWeekStr("周六");
			}else if(Calendar.SUNDAY == startCa.get(Calendar.DAY_OF_WEEK)){
				//双休
				rule.setNeedSign(false);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WEEKEND);
				rule.setWeekStr("周日");
			}else if(Calendar.MONDAY == startCa.get(Calendar.DAY_OF_WEEK)){
				//星期一
				rule.setNeedSign(true);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WORKDAY);
				rule.setWeekStr("周一");
			}else if(Calendar.TUESDAY == startCa.get(Calendar.DAY_OF_WEEK)){
				rule.setNeedSign(true);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WORKDAY);
				rule.setWeekStr("周二");
			}else if(Calendar.WEDNESDAY == startCa.get(Calendar.DAY_OF_WEEK)){
				rule.setNeedSign(true);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WORKDAY);
				rule.setWeekStr("周三");
			}else if(Calendar.THURSDAY == startCa.get(Calendar.DAY_OF_WEEK)){
				rule.setNeedSign(true);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WORKDAY);
				rule.setWeekStr("周四");
			}else if(Calendar.FRIDAY == startCa.get(Calendar.DAY_OF_WEEK)){
				rule.setNeedSign(true);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WORKDAY);
				rule.setWeekStr("周五");
			}else {
				rule.setNeedSign(true);
				rule.setType(GlobalConstant.OA_WORKCAL_TYPE_WORKDAY);
				rule.setWeekStr("其他");
			}
			rule.setYear(year);
			
			lstRules.add(rule);
			
			startCa.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		//批量保存
		this.saveBatch(lstRules);
	}

	/**
	 * 
	 * @description
	 *  	获取系统中的年份配置
	 * @return List<String>
	 * @author guyp
	 * @time 2020年4月20日 下午1:33:32
	 */
	public List<String> getYearGroups() {
		return this.baseMapper.getYearGroups();
	}

}
