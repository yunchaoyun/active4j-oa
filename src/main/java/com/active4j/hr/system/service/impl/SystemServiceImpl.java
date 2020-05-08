package com.active4j.hr.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysDicService;
import com.active4j.hr.system.service.SysDicValueService;
import com.active4j.hr.system.service.SystemService;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

@Service("systemService")
@Transactional
@Slf4j
public class SystemServiceImpl implements SystemService {

	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private SysDicService sysDicService;
	
	@Autowired
	private SysDicValueService sysDicValueService;
	
	/**
	 * 初始化部门信息
	 */
	public void initDeparts() {
		log.info("系统初始化部门数据......");
		
		List<SysDeptEntity> lstDeparts = sysDeptService.list();
		if(null != lstDeparts && lstDeparts.size() > 0) {
			for(SysDeptEntity depart : lstDeparts) {
				SystemUtils.putDept(depart.getId(), depart.getName());
			}
		}
		
		
		log.info("系统初始化部门数据结束......");
	}
	
	
	/**
	 * 
	 * @description
	 *  	初始化数据字典数据
	 * @return void
	 * @author 麻木神
	 * @time 2020年1月16日 上午11:17:55
	 */
	public void initDic() {
		log.info("系统初始化数据字典....");
		List<SysDicEntity> lstDics = sysDicService.list();
		if(null != lstDics && lstDics.size() > 0) {
			for(SysDicEntity dic : lstDics) {
				QueryWrapper<SysDicValueEntity> queryWrapper = new QueryWrapper<SysDicValueEntity>();
				queryWrapper.eq("PARENT_ID", dic.getId());
				List<SysDicValueEntity> lstValues = sysDicValueService.list(queryWrapper);
				if(null != lstValues && lstValues.size() > 0) {
					List<SysDicValueEntity> lst = new ArrayList<SysDicValueEntity>();
					for(SysDicValueEntity value : lstValues) {
						lst.add(value);
					}
					SystemUtils.putDictionary(dic.getCode(), lst);
					log.info("存入数据字典值:" + dic.getName());
				}
			}
		}
		log.info("系统初始化数据字典结束...");
	}
	
}
