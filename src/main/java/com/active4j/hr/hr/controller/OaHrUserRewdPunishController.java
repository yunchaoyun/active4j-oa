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
import com.active4j.hr.hr.entity.OaHrUserRewdPunishEntity;
import com.active4j.hr.hr.service.OaHrUserRewdPunishService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrUserRewdPunishController.java
 * @description 
		实力资源-奖惩记录管理
 * @time  2020年4月16日 下午4:35:23
 * @author guyp
 * @version 1.0
 */
@Controller
@RequestMapping("/oa/hr/rewdpunish")
@Slf4j
public class OaHrUserRewdPunishController extends BaseController {
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	@Autowired
	private OaHrUserRewdPunishService oaHrUserRewdPunishService;
	
	@Autowired
	private UploadAttachmentService uploadAttachmentService;
	
	
	private static final String prefix_page = "oa/hr/rewdpunish/";
	
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
		ModelAndView view = new ModelAndView(prefix_page + "rewdpunishlist");
		
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
	public void datagrid(OaHrUserRewdPunishEntity oaHrUserRewdPunishEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//拼接查询条件
		QueryWrapper<OaHrUserRewdPunishEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrUserRewdPunishEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrUserRewdPunishEntity> lstResult = oaHrUserRewdPunishService.page(new Page<OaHrUserRewdPunishEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
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
		ModelAndView view = new ModelAndView(prefix_page + "rewdpunishadd");
		
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
		ModelAndView view = new ModelAndView(prefix_page + "rewdpunishedit");
		
		if(StringUtils.isNotEmpty(id)) {
			//获取人员合同信息
			OaHrUserRewdPunishEntity rewdpunish = oaHrUserRewdPunishService.getById(id);
			view.addObject("rewdpunish", rewdpunish);
			//获取附件地址
			UploadAttachmentEntity attachment = uploadAttachmentService.getById(rewdpunish.getAttachment());
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
	public AjaxJson saveAdd(String[] UList, String rpType, String items, String rpDate, 
			String rpMoney, String rpDemo, String attachment, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//员工选择
			if(null == UList || UList.length <= 0) {
				j.setMsg("请选择员工!");
				j.setSuccess(false);
				return j;
			}
			
			//金额
			if(StringUtils.isEmpty(rpMoney)) {
				j.setMsg("请填写金额!");
				j.setSuccess(false);
				return j;
			}
			
			//日期选择
			if(StringUtils.isEmpty(rpDate)) {
				j.setMsg("请填写奖惩日期！");
				j.setSuccess(false);
				return j;
			}
			
			//日期和金钱的处理
			Date date = DateUtils.str2Date(rpDate, DateUtils.SDF_YYYY_MM_DD);
			Double money = Double.parseDouble(rpMoney);
			
			List<OaHrUserRewdPunishEntity> lstRewdPunishs = new ArrayList<OaHrUserRewdPunishEntity>();
			for(String oaUserId : UList) {
				OaHrUserRewdPunishEntity rewdPunish = new OaHrUserRewdPunishEntity();
				
				//查出OaUser
				OaHrUserEntity oaUser = oaHrUserService.getById(oaUserId);
				if(null == oaUser) {
					continue;
				}
				//奖惩记录参数赋值
				rewdPunish.setName(oaUser.getRealName());;
				rewdPunish.setItems(items);;
				rewdPunish.setRpDate(date);
				rewdPunish.setRpDemo(rpDemo);
				rewdPunish.setRpMoney(money);
				rewdPunish.setRpType(rpType);
				rewdPunish.setUserId(oaUser.getId());
				rewdPunish.setAttachment(attachment);
				
				lstRewdPunishs.add(rewdPunish);
			}
			//新增保存
			oaHrUserRewdPunishService.saveBatch(lstRewdPunishs);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存奖惩记录失败");
			log.error("保存奖惩记录报错，错误信息：{}", e.getMessage());
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
	public AjaxJson saveEdit(String id, String rpType, String items, String rpDate, 
			String rpMoney, String rpDemo, String attachment, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//奖惩日期选择
			if(StringUtils.isEmpty(rpDate)) {
				j.setMsg("请填写奖惩日期！");
				j.setSuccess(false);
				return j;
			}
			
			Date date = DateUtils.str2Date(rpDate, DateUtils.SDF_YYYY_MM_DD);
			//获取记录
			OaHrUserRewdPunishEntity rewdPunish = oaHrUserRewdPunishService.getById(id);
			//重新赋值
			rewdPunish.setItems(items);;
			rewdPunish.setRpDate(date);
			rewdPunish.setRpDemo(rpDemo);
			rewdPunish.setRpMoney(Double.parseDouble(rpMoney));
			rewdPunish.setRpType(rpType);
			rewdPunish.setAttachment(attachment);
			//编辑保存
			oaHrUserRewdPunishService.saveOrUpdate(rewdPunish);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("保存奖惩记录失败");
			log.error("保存奖惩记录报错，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除奖惩记录
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月21日 上午10:30:04
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//奖惩记录选择
			if(StringUtils.isEmpty(id)) {
				j.setMsg("请选择一条记录");
				j.setSuccess(false);
				return j;
			}
			
			//删除
			oaHrUserRewdPunishService.removeById(id);
		}catch(Exception e){
			j.setSuccess(false);
			j.setMsg("删除奖惩记录失败");
			log.error("删除奖惩记录报错，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
}
