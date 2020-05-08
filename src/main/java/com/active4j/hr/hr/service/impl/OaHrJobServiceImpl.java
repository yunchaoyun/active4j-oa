package com.active4j.hr.hr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrJobDao;
import com.active4j.hr.hr.entity.OaHrJobEntity;
import com.active4j.hr.hr.service.OaHrJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrJobServiceImpl.java
 * @description 
		人力资源-岗位管理
 * @time  2020年4月9日 下午3:54:18
 * @author guyp
 * @version 1.0
 */
@Service("oaHrJobService")
@Transactional
public class OaHrJobServiceImpl extends ServiceImpl<OaHrJobDao, OaHrJobEntity> implements OaHrJobService {

	/**
	 * 
	 * @description
	 *  	获取所有顶级岗位
	 * @params
	 * @return List<OaHrJobEntity>
	 * @author guyp
	 * @time 2020年4月9日 下午3:55:37
	 */
	public List<OaHrJobEntity> getParentJobs() {
		// 先查父级菜单 注意排序
		QueryWrapper<OaHrJobEntity> queryWrapper = new QueryWrapper<OaHrJobEntity>();
		queryWrapper.isNull("PARENT_ID");
		queryWrapper.orderByAsc("LEVEL");
		List<OaHrJobEntity> lstJobs = this.list(queryWrapper);

		return lstJobs;
	}

	/**
	 * 
	 * @description
	 *  	根据岗位id查询子岗位
	 * @params
	 *      jobId 岗位id
	 * @return List<OaHrJobEntity>
	 * @author guyp
	 * @time 2020年4月9日 下午4:00:21
	 */
	public List<OaHrJobEntity> getChildJobsByJobId(String jobId) {
		QueryWrapper<OaHrJobEntity> queryWrapper = new QueryWrapper<OaHrJobEntity>();
		queryWrapper.eq("PARENT_ID", jobId);
		queryWrapper.orderByAsc("LEVEL");
		List<OaHrJobEntity> lstJobs = this.list(queryWrapper);

		return lstJobs;
	}

}
