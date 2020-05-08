package com.active4j.hr.hr.dao;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.hr.domain.OaHrRecruitViewModel;
import com.active4j.hr.hr.entity.OaHrRecruitViewEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OaHrRecruitViewDao extends BaseMapper<OaHrRecruitViewEntity>{

	/**
	 * 
	 * @description
	 *  	面试记录查询
	 * @return IPage<OaHrRecruitViewModel>
	 * @author guyp
	 * @time 2020年4月22日 下午2:58:22
	 */
	public IPage<OaHrRecruitViewModel> selectPageView(Page<OaHrRecruitViewModel> page, @Param("cvName") String cvName);
}
