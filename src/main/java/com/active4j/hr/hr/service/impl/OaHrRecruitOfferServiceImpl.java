package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.dao.OaHrRecruitOfferDao;
import com.active4j.hr.hr.domain.OaHrRecruitOfferModel;
import com.active4j.hr.hr.entity.OaHrRecruitOfferEntity;
import com.active4j.hr.hr.service.OaHrRecruitOfferService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrRecruitOfferServiceImpl.java
 * @description 
		招聘管理-offer记录
 * @time  2020年4月22日 下午4:31:07
 * @author guyp
 * @version 1.0
 */
@Service("oaHrRecruitOfferService")
@Transactional
public class OaHrRecruitOfferServiceImpl extends ServiceImpl<OaHrRecruitOfferDao, OaHrRecruitOfferEntity> implements OaHrRecruitOfferService {

	/**
	 * offer记录查询
	 */
	public IPage<OaHrRecruitOfferModel> selectPageOffer(DataGrid dataGrid, String cvName) {
		Page<OaHrRecruitOfferModel> page = new Page<OaHrRecruitOfferModel>();
		page.setSize(dataGrid.getRows());
		page.setCurrent(dataGrid.getPage());
		return this.baseMapper.selectPageOffer(page, cvName);
	}

}
