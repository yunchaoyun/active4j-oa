package com.active4j.hr.hr.service;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrRecruitOfferModel;
import com.active4j.hr.hr.entity.OaHrRecruitOfferEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrRecruitOfferService extends IService<OaHrRecruitOfferEntity> {
	
	/**
	 * 
	 * @description
	 *  	offer记录管理
	 * @return IPage<OaHrRecruitOfferModel>
	 * @author guyp
	 * @time 2020年4月22日 下午4:28:25
	 */
	public IPage<OaHrRecruitOfferModel> selectPageOffer(DataGrid dataGrid,  @Param("cvName") String cvName);
}
