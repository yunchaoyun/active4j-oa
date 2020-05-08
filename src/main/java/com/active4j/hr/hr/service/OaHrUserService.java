package com.active4j.hr.hr.service;

import java.util.List;
import java.util.Set;

import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrUserService extends IService<OaHrUserEntity> {
	
	/**
	 * 
	 * @description
	 *  	根据部门获取人事用户
	 * @return List<OaHrUserEntity>
	 * @author guyp
	 * @time 2020年4月15日 下午5:48:59
	 */
	public List<OaHrUserEntity> findOaUsersByDept(SysDeptEntity sysDeptEntity);
	
	/**
	 * 
	 * @description
	 *  	企业人事信息树形选择
	 * @return String
	 * @author guyp
	 * @time 2020年4月15日 下午6:00:16
	 */
	public String getCompanyOfOaUser(Set<String> lstOaUserIds);
	
	/**
	 * 
	 * @description
	 *  	删除人事信息
	 * @return void
	 * @author guyp
	 * @time 2020年4月16日 下午5:54:51
	 */
	public void delLeave(String oaUserId);
}
