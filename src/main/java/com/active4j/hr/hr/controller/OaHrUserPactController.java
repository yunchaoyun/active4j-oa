package com.active4j.hr.hr.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.entity.OaHrUserPactEntity;
import com.active4j.hr.hr.service.OaHrUserPactService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrUserController.java
 * @description 
		实力资源-人事信息管理
 * @time  2020年4月10日 下午2:30:14
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/hr/pact")
@Slf4j
public class OaHrUserPactController extends BaseController {
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	@Autowired
	private OaHrUserPactService oaHrUserPactService;
	
	@Autowired
	private UploadAttachmentService uploadAttachmentService;
	
	
	private static final String prefix_page = "oa/hr/pact/";
	
	/**
	 * 
	 * @description
	 *  	跳转到列表页
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月10日 下午2:33:22
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView(prefix_page + "pactlist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	获取表格数据
	 * @return void
	 * @author guyp
	 * @time 2020年4月10日 下午2:34:52
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrUserPactEntity oaHrUserPactEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//拼接查询条件
		QueryWrapper<OaHrUserPactEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrUserPactEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrUserPactEntity> lstResult = oaHrUserPactService.page(new Page<OaHrUserPactEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	跳转新增页面
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月15日 下午6:05:31
	 */
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "pactadd");
		
		//查询按用户分配权限使用的树形结构
		String userTreeStr = oaHrUserService.getCompanyOfOaUser(null);
		view.addObject("userTreeStr", userTreeStr);
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	编辑
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月16日 上午9:11:29
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(String id, HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "pactedit");
		
		if(StringUtils.isNotEmpty(id)) {
			//获取人员合同信息
			OaHrUserPactEntity pact = oaHrUserPactService.getById(id);
			view.addObject("pact", pact);
			//获取附件地址
			UploadAttachmentEntity attachment = uploadAttachmentService.getById(pact.getAttachment());
			if(null != attachment) {
				view.addObject("attachment", attachment.getName());
			}
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	新增保存
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月16日 下午3:11:55
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	public AjaxJson saveAdd(String[] UList, String userNo, String pactNo, String pactType, 
			String canTrainPact, String canGuDing, String attachment, String effectDate, String noEffectDate, String memo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//员工选择
			if(null == UList || UList.length <= 0) {
				j.setMsg("请选择员工!");
				j.setSuccess(false);
				return j;
			}
			
			//合同编号
			if(StringUtils.isEmpty(pactNo)) {
				j.setMsg("请填写合同编号!");
				j.setSuccess(false);
				return j;
			}
			
			//日期选择
			if(StringUtils.isEmpty(effectDate) || StringUtils.isEmpty(noEffectDate)) {
				j.setMsg("请填写合同日期！");
				j.setSuccess(false);
				return j;
			}
			
			Date effect = DateUtils.str2Date(effectDate, DateUtils.SDF_YYYY_MM_DD);
			Date noEffect = DateUtils.str2Date(noEffectDate, DateUtils.SDF_YYYY_MM_DD);
			
			List<OaHrUserPactEntity> lstPacts = new ArrayList<OaHrUserPactEntity>();
			for(String oaUserId : UList) {
				OaHrUserPactEntity pact = new OaHrUserPactEntity();
				
				//查出OaUser
				OaHrUserEntity oaUser = oaHrUserService.getById(oaUserId);
				if(null == oaUser) {
					j.setMsg("不存在该员工！");
					j.setSuccess(false);
					return j;
				}
				//合同参数赋值
				pact.setUserNo(userNo);
				pact.setCanGuDing(canGuDing);
				pact.setCanTrainPact(canTrainPact);
				pact.setMemo(memo);
				pact.setEffectDate(effect);
				pact.setName(oaUser.getRealName());
				pact.setNoEffectDate(noEffect);
				pact.setUserId(oaUser.getId());
				pact.setPactType(pactType);
				pact.setPactNo(pactNo);
				pact.setAttachment(attachment);
				
				lstPacts.add(pact);
			}
			//新增保存
			oaHrUserPactService.saveBatch(lstPacts);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存人事合同失败");
			log.error("保存人事合同报错，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	编辑保存
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月16日 下午3:15:14
	 */
	@RequestMapping("/saveEdit")
	@ResponseBody
	public AjaxJson saveEdit(String id, String userNo, String pactNo, String pactType, 
			String canTrainPact, String canGuDing, String attachment, String effectDate, String noEffectDate, String memo,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//日期选择
			if(StringUtils.isEmpty(effectDate) || StringUtils.isEmpty(noEffectDate)) {
				j.setMsg("请填写合同日期！");
				j.setSuccess(false);
				return j;
			}
			
			Date effect = DateUtils.str2Date(effectDate, DateUtils.SDF_YYYY_MM_DD);
			Date noEffect = DateUtils.str2Date(noEffectDate, DateUtils.SDF_YYYY_MM_DD);
			//获取合同
			OaHrUserPactEntity pact = oaHrUserPactService.getById(id);
			//重新赋值
			pact.setUserNo(userNo);
			pact.setCanGuDing(canGuDing);
			pact.setCanTrainPact(canTrainPact);
			pact.setMemo(memo);
			pact.setEffectDate(effect);
			pact.setNoEffectDate(noEffect);
			pact.setPactType(pactType);
			pact.setPactNo(pactNo);
			pact.setAttachment(attachment);
			//编辑保存
			oaHrUserPactService.saveOrUpdate(pact);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存人事合同失败");
			log.error("保存人事合同报错，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
}
