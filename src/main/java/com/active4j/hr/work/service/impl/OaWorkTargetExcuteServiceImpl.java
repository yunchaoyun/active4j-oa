package com.active4j.hr.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkTargetExcuteDao;
import com.active4j.hr.work.entity.OaWorkTargetEntity;
import com.active4j.hr.work.entity.OaWorkTargetExcuteEntity;
import com.active4j.hr.work.service.OaWorkTargetExcuteService;
import com.active4j.hr.work.service.OaWorkTargetService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @description 
		  工作中心 目标管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkTargetExcuteService")
@Transactional
public class OaWorkTargetExcuteServiceImpl extends ServiceImpl<OaWorkTargetExcuteDao, OaWorkTargetExcuteEntity> implements OaWorkTargetExcuteService {
	
	@Autowired
	private OaWorkTargetService oaWorkTargetService;
	
	public void save(OaWorkTargetEntity target, OaWorkTargetExcuteEntity oaWorkTargetExcuteEntity, int finishData){
		oaWorkTargetExcuteEntity.setOaWorkTargetId(target.getId());
		this.save(oaWorkTargetExcuteEntity);
		
		target.setFinishRecord(target.getFinishRecord() + finishData);
		oaWorkTargetService.saveOrUpdate(target);
		
		String parentTargetId = target.getParentTargetId();
		while(StringUtils.isNotEmpty(parentTargetId)) {
			OaWorkTargetEntity parent = oaWorkTargetService.getById(parentTargetId);
			parent.setFinishRecord(parent.getFinishRecord() + finishData);
			oaWorkTargetService.saveOrUpdate(parent);
			
			parentTargetId = parent.getParentTargetId();
		}
		
	}
	
	public List<OaWorkTargetExcuteEntity> findOaWorkTargetExcute(OaWorkTargetEntity target){
		QueryWrapper<OaWorkTargetExcuteEntity> queryWrapper = new QueryWrapper<OaWorkTargetExcuteEntity>();
		queryWrapper.eq("OA_WORK_TARGET_ID", target.getId());
		return this.list(queryWrapper);
	}
	
}
