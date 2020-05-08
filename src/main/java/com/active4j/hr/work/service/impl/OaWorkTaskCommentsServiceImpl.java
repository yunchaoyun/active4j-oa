package com.active4j.hr.work.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkTaskCommentsDao;
import com.active4j.hr.work.entity.OaWorkTaskCommentsEntity;
import com.active4j.hr.work.service.OaWorkTaskCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @description 
		  工作中心 任务管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkTaskCommentsService")
@Transactional
public class OaWorkTaskCommentsServiceImpl extends ServiceImpl<OaWorkTaskCommentsDao, OaWorkTaskCommentsEntity> implements OaWorkTaskCommentsService {
	

}
