package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.dao.OaHrRecruitViewDao;
import com.active4j.hr.hr.domain.OaHrRecruitViewModel;
import com.active4j.hr.hr.entity.OaHrRecruitViewEntity;
import com.active4j.hr.hr.service.OaHrRecruitViewService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitViewServiceImpl.java
 * @description 
		招聘管理-面试记录
 * @time  2020年4月22日 下午2:41:49
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitViewService")
@Transactional
public class OaHrRecruitViewServiceImpl extends ServiceImpl<OaHrRecruitViewDao, OaHrRecruitViewEntity> implements OaHrRecruitViewService {

	/**
	 * 
	 * @description
	 *  	面试记录查询
	 * @return IPage<OaHrRecruitViewModel>
	 * @author guyp
	 * @time 2020年4月22日 下午2:58:22
	 */
	public IPage<OaHrRecruitViewModel> selectPageView(DataGrid dataGrid, String cvName) {
		Page<OaHrRecruitViewModel> page = new Page<OaHrRecruitViewModel>();
		page.setSize(dataGrid.getRows());
		page.setCurrent(dataGrid.getPage());
		return this.baseMapper.selectPageView(page, cvName);
	}

}
