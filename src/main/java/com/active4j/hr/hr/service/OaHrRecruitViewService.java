package com.active4j.hr.hr.service;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrRecruitViewModel;
import com.active4j.hr.hr.entity.OaHrRecruitViewEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrRecruitViewService extends IService<OaHrRecruitViewEntity> {
	
	/**
	 * 
	 * @description
	 *  	面试记录查询
	 * @return IPage<OaHrRecruitViewModel>
	 * @author guyp
	 * @time 2020年4月22日 下午2:58:22
	 */
	public IPage<OaHrRecruitViewModel> selectPageView(DataGrid dataGrid,  @Param("cvName") String cvName);
}
