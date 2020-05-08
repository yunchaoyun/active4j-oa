package com.active4j.hr.work.service;

import java.util.List;

import com.active4j.hr.work.entity.OaWorkTargetEntity;
import com.active4j.hr.work.entity.OaWorkTargetExcuteEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkTargetExcuteService extends IService<OaWorkTargetExcuteEntity> {
	
	public void save(OaWorkTargetEntity target, OaWorkTargetExcuteEntity oaWorkTargetExcuteEntity, int finishData);
	
	public List<OaWorkTargetExcuteEntity> findOaWorkTargetExcute(OaWorkTargetEntity target);
}
