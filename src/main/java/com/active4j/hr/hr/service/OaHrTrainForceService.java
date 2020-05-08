package com.active4j.hr.hr.service;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrTrainForceModel;
import com.active4j.hr.hr.entity.OaHrTrainForceEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrTrainForceService extends IService<OaHrTrainForceEntity> {

	/**
	 * 
	 * @description
	 *  	培训实施查询
	 * @return IPage<OaHrTrainForceModel>
	 * @author guyp
	 * @time 2020年4月23日 下午3:54:56
	 */
	public IPage<OaHrTrainForceModel> selectPageForce(DataGrid dataGrid,  @Param("planName") String planName);
	
}
