package com.active4j.hr.hr.dao;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.hr.domain.OaHrRecruitOfferModel;
import com.active4j.hr.hr.entity.OaHrRecruitOfferEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OaHrRecruitOfferDao extends BaseMapper<OaHrRecruitOfferEntity>{

	/**
	 * 
	 * @description
	 *  	offer记录查询
	 * @return IPage<OaHrRecruitOfferModel>
	 * @author guyp
	 * @time 2020年4月22日 下午4:32:07
	 */
	public IPage<OaHrRecruitOfferModel> selectPageOffer(Page<OaHrRecruitOfferModel> page, @Param("cvName") String cvName);
}
