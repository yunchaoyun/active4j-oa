package com.active4j.hr.hr.dao;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.hr.domain.OaHrTrainForceModel;
import com.active4j.hr.hr.entity.OaHrTrainForceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OaHrTrainForceDao extends BaseMapper<OaHrTrainForceEntity>{

	/**
	 * 
	 * @description
	 *  	培训实施查询
	 * @return IPage<OaHrTrainForceModel>
	 * @author guyp
	 * @time 2020年4月23日 下午3:53:38
	 */
	public IPage<OaHrTrainForceModel> selectPageForce(Page<OaHrTrainForceModel> page, @Param("planName") String planName);
}
