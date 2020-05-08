package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrUserRewdPunishDao;
import com.active4j.hr.hr.entity.OaHrUserRewdPunishEntity;
import com.active4j.hr.hr.service.OaHrUserRewdPunishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserRewdPunishServiceImpl.java
 * @description 
		人力资源-奖惩记录
 * @time  2020年4月16日 下午4:34:36
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserRewdPunishService")
@Transactional
public class OaHrUserRewdPunishServiceImpl extends ServiceImpl<OaHrUserRewdPunishDao, OaHrUserRewdPunishEntity> implements OaHrUserRewdPunishService {


}
