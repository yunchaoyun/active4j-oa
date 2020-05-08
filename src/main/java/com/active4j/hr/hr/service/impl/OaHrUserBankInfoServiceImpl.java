package com.active4j.hr.hr.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.dao.OaHrUserBankInfoDao;
import com.active4j.hr.hr.domain.OaUserBankInfoModel;
import com.active4j.hr.hr.entity.OaHrUserBankInfoEntity;
import com.active4j.hr.hr.service.OaHrUserBankInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserBankInfoServiceImpl.java
 * @description 
		薪资管理-员工银行卡信息
 * @time  2020年4月20日 下午4:58:33
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserBankInfoService")
@Transactional
public class OaHrUserBankInfoServiceImpl extends ServiceImpl<OaHrUserBankInfoDao, OaHrUserBankInfoEntity> implements OaHrUserBankInfoService {

	/**
	 * 
	 * @description
	 *  	员工银行卡信息查询
	 * @return IPage<OaUserBankInfoModel>
	 * @author guyp
	 * @time 2020年4月20日 下午5:29:04
	 */
	public IPage<OaUserBankInfoModel> selectPageBankInfo(DataGrid dataGrid, String oaUserName, String departId) {
		Page<OaUserBankInfoModel> page = new Page<OaUserBankInfoModel>();
		page.setSize(dataGrid.getRows());
		page.setCurrent(dataGrid.getPage());
		return this.baseMapper.selectPageBankInfo(page, oaUserName, departId);
	}

}
