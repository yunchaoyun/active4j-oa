package com.active4j.hr.work.service;

import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.entity.OaWorkTaskExcuteEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaWorkTaskExcuteService extends IService<OaWorkTaskExcuteEntity> {
	
	public void save(OaWorkTaskExcuteEntity oaWorkTaskExcuteEntity, OaWorkTaskEntity oaWorkTaskEntity);

}
