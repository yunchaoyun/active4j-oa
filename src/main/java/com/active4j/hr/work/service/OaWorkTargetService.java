package com.active4j.hr.work.service;

import java.util.List;

import com.active4j.hr.work.entity.OaWorkTargetEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkTargetService extends IService<OaWorkTargetEntity> {
	
	public List<OaWorkTargetEntity> findOaWorkTarget(String userId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper);
	
	public List<OaWorkTargetEntity> findOaWorkTargetCreate(String userId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper);
	
	public List<OaWorkTargetEntity> findOaWorkTargetPart(String userId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper);
	
	public List<OaWorkTargetEntity> findOaWorkTargetDepart(String departId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper);
	
	public List<OaWorkTargetEntity> findOaWorkTargetComp(String type, QueryWrapper<OaWorkTargetEntity> queryWrapper);
	
	public List<OaWorkTargetEntity> findChildrenTarget(OaWorkTargetEntity oaWorkTargetEntity);
	
	public List<OaWorkTargetEntity> findOaWorkTargetStatus(String type, String userId);
	
	public List<OaWorkTargetEntity> findOaWorkTargetStatusDepart(String type, String departId);
	
	public List<OaWorkTargetEntity> findOaWorkTargetStatus(String type);
	
	public void saveOrUpdate(OaWorkTargetEntity oaWorkTargetEntity, String status);
	
	public List<OaWorkTargetEntity> findOaWorkTargetChart(String type, String id, String flag);
}
