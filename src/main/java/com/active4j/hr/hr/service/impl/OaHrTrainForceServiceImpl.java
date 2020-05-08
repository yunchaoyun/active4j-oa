package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.dao.OaHrTrainForceDao;
import com.active4j.hr.hr.domain.OaHrTrainForceModel;
import com.active4j.hr.hr.entity.OaHrTrainForceEntity;
import com.active4j.hr.hr.service.OaHrTrainForceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrTrainForceServiceImpl.java
 * @description 
		培训实施管理
 * @time  2020年4月23日 上午10:18:16
 * @author guyp
 * @version 1.0
 */
@Service("oaHrTrainForceService")
@Transactional
public class OaHrTrainForceServiceImpl extends ServiceImpl<OaHrTrainForceDao, OaHrTrainForceEntity> implements OaHrTrainForceService {

	/**
	 * 
	 * @description
	 *  	培训实施查询
	 * @return IPage<OaHrTrainForceModel>
	 * @author guyp
	 * @time 2020年4月23日 下午3:54:56
	 */
	public IPage<OaHrTrainForceModel> selectPageForce(DataGrid dataGrid, String planName) {
		Page<OaHrTrainForceModel> page = new Page<OaHrTrainForceModel>();
		page.setSize(dataGrid.getRows());
		page.setCurrent(dataGrid.getPage());
		return this.baseMapper.selectPageForce(page, planName);
	}

}
