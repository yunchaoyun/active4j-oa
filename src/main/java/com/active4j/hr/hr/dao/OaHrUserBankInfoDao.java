package com.active4j.hr.hr.dao;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.hr.domain.OaUserBankInfoModel;
import com.active4j.hr.hr.entity.OaHrUserBankInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OaHrUserBankInfoDao extends BaseMapper<OaHrUserBankInfoEntity>{

	/**
	 * 
	 * @description
	 *  	员工银行卡信息查询
	 * @return IPage<OaUserBankInfoModel>
	 * @author guyp
	 * @time 2020年4月20日 下午5:29:04
	 */
	public IPage<OaUserBankInfoModel> selectPageBankInfo(Page<OaUserBankInfoModel> page, @Param("oaUserName") String oaUserName, @Param("departId") String departId);
}
