package com.active4j.hr.hr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaUserBankInfoModel;
import com.active4j.hr.hr.entity.OaHrUserBankInfoEntity;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.service.OaHrUserBankInfoService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrUserBankInfoController.java
 * @description 
		员工银行卡信息管理
 * @time  2020年4月20日 下午4:43:06
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/bankinfo")
public class OaHrUserBankInfoController extends BaseController{

	@Autowired
	private OaHrUserBankInfoService oaHrUserBankInfoService;
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	private static final String prefix_page = "oa/hr/bank/";
	
	/**
	 * 跳转到银行卡界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "bankinfolist");
		
		return view;
	}
	
	
	
	/**
	 * 查询数据
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaUserBankInfoModel oaUserBankInfoModel, String oaUserName, String deptId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		IPage<OaUserBankInfoModel> lstResult = oaHrUserBankInfoService.selectPageBankInfo(dataGrid, oaUserName, deptId);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "bankinfoadd");
		
		//查询按用户分配权限使用的树形结构
		String userTreeStr = oaHrUserService.getCompanyOfOaUser(null);
		view.addObject("userTreeStr", userTreeStr);
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转编辑页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月21日 上午10:22:00
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(String id, HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "bankinfoedit");
		
		if(StringUtils.isNotEmpty(id)) {
			//获取人员合同信息
			OaHrUserBankInfoEntity bank = oaHrUserBankInfoService.getById(id);
			view.addObject("bank", bank);
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	新增保存银行卡信息
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月21日 上午10:05:37
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public AjaxJson saveAdd(String[] UList, String bank, String cardNo, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			if(null == UList || UList.length <= 0) {
				j.setMsg("请选择用户!");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(bank)) {
				j.setSuccess(false);
				j.setMsg("请填写开户行信息");
				return j;
			}
			if(StringUtils.isEmpty(cardNo)) {
				j.setSuccess(false);
				j.setMsg("请填写银行卡号信息");
				return j;
			}
			
			List<OaHrUserBankInfoEntity> lstBanks = new ArrayList<OaHrUserBankInfoEntity>();
			for(String id : UList) {
				OaHrUserBankInfoEntity bankInfo = new OaHrUserBankInfoEntity();
				//获取人力信息
				OaHrUserEntity oaUser = oaHrUserService.getById(id);
				
				if(null == oaUser) {
					continue;
				}
				
				//查询是否已经存在银行卡
				QueryWrapper<OaHrUserBankInfoEntity> queryWrapper = new QueryWrapper<OaHrUserBankInfoEntity>();
				queryWrapper.eq("USER_ID", bankInfo.getUserId());
				OaHrUserBankInfoEntity tmp = oaHrUserBankInfoService.getOne(queryWrapper);
				if(null != tmp) {
					j.setMsg("已存在该员工的银行卡，不能重复添加");
					j.setSuccess(false);
					return j;
				}
				
				bankInfo.setUserId(id);
				bankInfo.setBank(bank);
				bankInfo.setCardNo(cardNo);
				bankInfo.setMemo(memo);
				
				lstBanks.add(bankInfo);
			}
			//保存
			oaHrUserBankInfoService.saveBatch(lstBanks);
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存银行卡数据失败");
			log.error("保存银行卡数据失败，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	编辑保存
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月21日 下午4:13:00
	 */
	@RequestMapping("/saveEdit")
	@ResponseBody
	public AjaxJson saveEdit(String id, String bank, String cardNo, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isEmpty(id)) {
				j.setMsg("请选择一个员工");
				j.setSuccess(false);
				return j;
			}
			
			if(StringUtils.isEmpty(bank)) {
				j.setSuccess(false);
				j.setMsg("请填写开户行信息");
				return j;
			}
			if(StringUtils.isEmpty(cardNo)) {
				j.setSuccess(false);
				j.setMsg("请填写银行卡号信息");
				return j;
			}
			OaHrUserBankInfoEntity bankInfo = oaHrUserBankInfoService.getById(id);
			bankInfo.setBank(bank);
			bankInfo.setCardNo(cardNo);
			bankInfo.setMemo(memo);
			//编辑保存
			oaHrUserBankInfoService.saveOrUpdate(bankInfo);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存银行卡数据失败");
			log.error("保存银行卡数据失败，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除银行卡信息
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月21日 上午10:30:47
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//银行卡信息选择
			if(StringUtils.isEmpty(id)) {
				j.setMsg("请选择一条记录");
				j.setSuccess(false);
				return j;
			}
			
			//删除
			oaHrUserBankInfoService.removeById(id);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除银行卡记录失败");
			log.error("删除银行卡记录报错，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
}
