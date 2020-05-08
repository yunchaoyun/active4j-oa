package com.active4j.hr.hr.controller;

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
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrRecruitOfferModel;
import com.active4j.hr.hr.entity.OaHrRecruitCVEntity;
import com.active4j.hr.hr.entity.OaHrRecruitOfferEntity;
import com.active4j.hr.hr.service.OaHrRecruitCVService;
import com.active4j.hr.hr.service.OaHrRecruitOfferService;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrRecruitOfferController.java
 * @description 
		招聘管理-offer记录
 * @time  2020年4月22日 下午4:38:06
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/recruit/offer")
public class OaHrRecruitOfferController extends BaseController{
	
	@Autowired
	private OaHrRecruitOfferService oaHrRecruitOfferService;
	
	@Autowired
	private OaHrRecruitCVService oaHrRecruitCVService;

	private static final String prefix_page = "oa/hr/recruit/offer/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "offerlist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转选择简历页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月22日 下午2:45:14
	 */
	@RequestMapping("/selectCv")
	public ModelAndView selectNeed(HttpServletRequest request){
		ModelAndView view = new ModelAndView("oa/hr/recruit/view/selectCv");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param offerEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrRecruitOfferModel oaHrRecruitOfferModel, String cvName, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		IPage<OaHrRecruitOfferModel> lstResult = oaHrRecruitOfferService.selectPageOffer(dataGrid, cvName);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param offerEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrRecruitOfferEntity offerEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(offerEntity.getId())) {
			//获取offer记录实体
			OaHrRecruitOfferEntity offer = oaHrRecruitOfferService.getById(offerEntity.getId());
			req.setAttribute("offer", offer);
			//获取简历实体
			OaHrRecruitCVEntity cv = oaHrRecruitCVService.getById(offer.getCvId());
			req.setAttribute("cv", cv);
			
		}
		
		//offer状态
		List<SysDicValueEntity> offerStatuss = SystemUtils.getDictionaryLst(SysConstant.DIC_OFFER_TYPE);
		req.setAttribute("offerStatuss", offerStatuss);
				
		return new ModelAndView(prefix_page + "offeradd");
	}
	
	/**
	 * 保存
	 * @param cvId
	 * @param offerEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String cvId, OaHrRecruitOfferEntity offerEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//简历选择
			if(StringUtils.isEmpty(cvId)) {
				j.setMsg("请选择简历！");
				j.setSuccess(false);
				return j;
			}
			
			OaHrRecruitCVEntity cvEntity = oaHrRecruitCVService.getById(cvId);
			if(null == cvEntity) {
				j.setMsg("不存在该简历，请重新选择！");
				j.setSuccess(false);
				return j;
			}
			//简历id赋值
			offerEntity.setCvId(cvId);
			
			if(StringUtils.isNotEmpty(offerEntity.getId())) {
				OaHrRecruitOfferEntity t = oaHrRecruitOfferService.getById(offerEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(offerEntity, t);
				//编辑保存
				oaHrRecruitOfferService.saveOrUpdate(t);					
				
			} else {
				//新增保存
				oaHrRecruitOfferService.save(offerEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存offer记录数据错误");
			j.setSuccess(false);
			log.error("保存offer记录数据错误，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月22日 下午4:45:38
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try{
			if(StringUtils.isNotEmpty(id)) {
				//删除
				oaHrRecruitOfferService.removeById(id);
			}
			
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除offer记录数据错误");
			log.error("删除offer记录数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	
}
