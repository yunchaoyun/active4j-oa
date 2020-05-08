package com.active4j.hr.hr.service;

import java.util.List;

import com.active4j.hr.hr.entity.OaHrJobEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrJobService extends IService<OaHrJobEntity> {
	
	/**
	 * 
	 * @description
	 *  	获取所有顶级岗位
	 * @params
	 * @return List<OaHrJobEntity>
	 * @author guyp
	 * @time 2020年4月9日 下午3:55:37
	 */
	public List<OaHrJobEntity> getParentJobs();
	
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
	public List<OaHrJobEntity> getChildJobsByJobId(String jobId);
	
}
