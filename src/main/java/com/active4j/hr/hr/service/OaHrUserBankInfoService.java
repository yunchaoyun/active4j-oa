package com.active4j.hr.hr.service;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaUserBankInfoModel;
import com.active4j.hr.hr.entity.OaHrUserBankInfoEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrUserBankInfoService extends IService<OaHrUserBankInfoEntity> {
	
	/**
	 * 
	 * @description
	 *  	员工银行卡信息查询
	 * @return IPage<OaUserBankInfoModel>
	 * @author guyp
	 * @time 2020年4月20日 下午5:29:04
	 */
	public IPage<OaUserBankInfoModel> selectPageBankInfo(DataGrid dataGrid,  @Param("oaUserName") String oaUserName, @Param("departId") String departId);
}
