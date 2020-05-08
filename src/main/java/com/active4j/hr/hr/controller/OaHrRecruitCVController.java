package com.active4j.hr.hr.controller;

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
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.hr.entity.OaHrRecruitBackupEntity;
import com.active4j.hr.hr.entity.OaHrRecruitCVEntity;
import com.active4j.hr.hr.entity.OaHrRecruitOfferEntity;
import com.active4j.hr.hr.entity.OaHrRecruitPlanEntity;
import com.active4j.hr.hr.entity.OaHrRecruitViewEntity;
import com.active4j.hr.hr.service.OaHrRecruitBackupService;
import com.active4j.hr.hr.service.OaHrRecruitCVService;
import com.active4j.hr.hr.service.OaHrRecruitOfferService;
import com.active4j.hr.hr.service.OaHrRecruitPlanService;
import com.active4j.hr.hr.service.OaHrRecruitViewService;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaRecruitCVController.java
 * @description 
		招聘管理-简历库管理
 * @time  2020年4月22日 下午1:35:10
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/recruit/cv")
public class OaHrRecruitCVController extends BaseController{
	
	@Autowired
	private OaHrRecruitCVService oaHrRecruitCVService;
	
	@Autowired
	private OaHrRecruitBackupService oaHrRecruitBackupService;
	
	@Autowired
	private OaHrRecruitPlanService oaHrRecruitPlanService;
	
	@Autowired
	private OaHrRecruitViewService oaHrRecruitViewService;
	
	@Autowired
	private OaHrRecruitOfferService oaHrRecruitOfferService;
	
	@Autowired
	private UploadAttachmentService uploadAttachmentService;

	private static final String prefix_page = "oa/hr/recruit/cv/";
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "cvlist");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param cvEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrRecruitCVEntity oaHrRecruitCVEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaHrRecruitCVEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrRecruitCVEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrRecruitCVEntity> lstResult = oaHrRecruitCVService.page(new Page<OaHrRecruitCVEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 新建编辑
	 * @param cvEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrRecruitCVEntity cvEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(cvEntity.getId())) {
			//获取简历库实体
			OaHrRecruitCVEntity cv = oaHrRecruitCVService.getById(cvEntity.getId());
			req.setAttribute("cv", cv);
			//获取计划实体
			OaHrRecruitPlanEntity plan = oaHrRecruitPlanService.getById(cv.getPlanId());
			req.setAttribute("plan", plan);
			//获取简历附件
			UploadAttachmentEntity cvAttachment = uploadAttachmentService.getById(cv.getCvAttachment());
			if(null != cvAttachment) {
				req.setAttribute("cvAttachment", cvAttachment.getName());
			}
			//获取证书附件
			UploadAttachmentEntity certiAttachment = uploadAttachmentService.getById(cv.getCertiAttachment());
			if(null != certiAttachment) {
				req.setAttribute("certiAttachment", certiAttachment.getName());
			}
		}
		
		//性别
		List<SysDicValueEntity> sexs = SystemUtils.getDictionaryLst(SysConstant.DIC_SEX_TYPE);
		req.setAttribute("sexs", sexs);
		
		//招聘渠道
		List<SysDicValueEntity> canaltypes = SystemUtils.getDictionaryLst(SysConstant.DIC_CANAL_TYPE);
		req.setAttribute("canaltypes", canaltypes);
		
		//工作年限
		List<SysDicValueEntity> jobLengths = SystemUtils.getDictionaryLst(SysConstant.DIC_JOB_LENGTH);
		req.setAttribute("jobLengths", jobLengths);
		
		//到岗时间
		List<SysDicValueEntity> reportTimes = SystemUtils.getDictionaryLst(SysConstant.DIC_REPORT_TIME);
		req.setAttribute("reportTimes", reportTimes);
		
		//期望工作性质
		List<SysDicValueEntity> jobnaturetypes = SystemUtils.getDictionaryLst(SysConstant.DIC_JOBNATURE_TYPE);
		req.setAttribute("jobnaturetypes", jobnaturetypes);
		
		//血型
		List<SysDicValueEntity> bloodtypes = SystemUtils.getDictionaryLst(SysConstant.DIC_BLOOD_TYPE);
		req.setAttribute("bloodtypes", bloodtypes);
		
		//学历
		List<SysDicValueEntity> degreeinfos = SystemUtils.getDictionaryLst(SysConstant.DIC_DEGREE_INFO);
		req.setAttribute("degreeinfos", degreeinfos);
		
		//简历状态
		List<SysDicValueEntity> cvstatuss = SystemUtils.getDictionaryLst(SysConstant.DIC_CV_STATUS);
		req.setAttribute("cvstatuss", cvstatuss);
		
		//计划选择
		List<OaHrRecruitPlanEntity> plans = oaHrRecruitPlanService.list();
		req.setAttribute("plans", plans);
				
		return new ModelAndView(prefix_page + "cvadd");
	}
	
	/**
	 * 保存
	 * @param planId
	 * @param cvEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String planId, OaHrRecruitCVEntity cvEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//姓名
			if(StringUtils.isEmpty(cvEntity.getName())) {
				j.setMsg("请填写姓名！");
				j.setSuccess(false);
				return j;
			}
			
			//联系电话
			if(StringUtils.isEmpty(cvEntity.getTelNo())) {
				j.setMsg("请填写联系电话！");
				j.setSuccess(false);
				return j;
			}
			
			//职位
			if(StringUtils.isEmpty(cvEntity.getCvJob())) {
				j.setMsg("请填写应聘职位！");
				j.setSuccess(false);
				return j;
			}
			
			//应聘计划选择
			if(StringUtils.isEmpty(planId)) {
				j.setMsg("请选择应聘计划！");
				j.setSuccess(false);
				return j;
			}
			
			OaHrRecruitPlanEntity planEntity = oaHrRecruitPlanService.getById(planId);
			if(null == planEntity) {
				j.setMsg("不存在该计划，请重新选择！");
				j.setSuccess(false);
				return j;
			}
			//计划id赋值
			cvEntity.setPlanId(planId);
			//编辑保存
			if(StringUtils.isNotEmpty(cvEntity.getId())) {
				OaHrRecruitCVEntity t = oaHrRecruitCVService.getById(cvEntity);
				MyBeanUtils.copyBeanNotNull2Bean(cvEntity, t);
				oaHrRecruitCVService.saveOrUpdate(t);
			} else {
				//新增保存
				oaHrRecruitCVService.save(cvEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("保存简历库数据错误");
			j.setSuccess(false);
			log.error("保存简历库数据错误，错误原因：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 加入后备资源库
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/doAddBackup")
	@ResponseBody
	public AjaxJson doAddBackup(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			if(StringUtils.isEmpty(id)) {
				j.setMsg("您还未选择简历！");
				j.setSuccess(false);
				return j;
			}
			//获取简历实体
			OaHrRecruitCVEntity cvEntity = oaHrRecruitCVService.getById(id);
			
			OaHrRecruitBackupEntity backupEntity = new OaHrRecruitBackupEntity();
			//后备库实体赋值
			backupEntity.setAddTime(new Date());
			backupEntity.setAge(cvEntity.getAge());
			backupEntity.setBirthDate(cvEntity.getBirthDate());
			backupEntity.setBloodType(cvEntity.getBloodType());
			backupEntity.setCanalType(cvEntity.getCanalType());
			backupEntity.setCertiAttachment(cvEntity.getCertiAttachment());
			backupEntity.setWeixin(cvEntity.getWeixin());
			backupEntity.setCvAttachment(cvEntity.getCvAttachment());
			backupEntity.setCvJob(cvEntity.getCvJob());
			backupEntity.setDegree(cvEntity.getDegree());
			backupEntity.setEducationBack(cvEntity.getEducationBack());
			backupEntity.setEmail(cvEntity.getEmail());
			backupEntity.setExperience(cvEntity.getExperience());
			backupEntity.setFrnLanguage1(cvEntity.getFrnLanguage1());
			backupEntity.setFrnLevel1(cvEntity.getFrnLevel1());
			backupEntity.setFrnLanguage2(cvEntity.getFrnLanguage2());
			backupEntity.setFrnLevel2(cvEntity.getFrnLevel2());
			backupEntity.setGradSchool(cvEntity.getGradSchool());
			backupEntity.setHopeCity(cvEntity.getHopeCity());
			backupEntity.setHopeWage(cvEntity.getHopeWage());
			backupEntity.setJobLength(cvEntity.getJobLength());
			backupEntity.setJobNature(cvEntity.getJobNature());
			backupEntity.setJobSkill(cvEntity.getJobSkill());
			backupEntity.setLiveCity(cvEntity.getLiveCity());
			backupEntity.setName(cvEntity.getName());
			backupEntity.setNation(cvEntity.getNation());
			backupEntity.setReportTime(cvEntity.getReportTime());
			backupEntity.setSex(cvEntity.getSex());
			backupEntity.setSpeciality(cvEntity.getSpeciality());
			backupEntity.setStatus(cvEntity.getStatus());
			backupEntity.setStudyMajor(cvEntity.getStudyMajor());
			backupEntity.setTelNo(cvEntity.getTelNo());
			//保存
			oaHrRecruitBackupService.save(backupEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("加入后备资源库错误");
			j.setSuccess(false);
			log.error("加入后备资源库错误，错误信息：{}", e.getMessage());
		}
		
		return j;
		
	}
	
	/**
	 * 
	 * @description
	 *  	删除
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月22日 下午2:05:18
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择一条记录");
				return j;
			}
			
			//看看面试记录有没有相关记录
			QueryWrapper<OaHrRecruitViewEntity> queryView = new QueryWrapper<OaHrRecruitViewEntity>();
			queryView.eq("CV_ID", id);
			List<OaHrRecruitViewEntity> lstViews = oaHrRecruitViewService.list(queryView);
			if(null != lstViews && lstViews.size() > 0) {
				j.setSuccess(false);
				j.setMsg("该简历存在于面试记录中，请移除后再试");
				return j;
			}
			
			//看看offer记录有没有相关记录
			QueryWrapper<OaHrRecruitOfferEntity> queryOffer = new QueryWrapper<OaHrRecruitOfferEntity>();
			queryOffer.eq("CV_ID", id);
			List<OaHrRecruitOfferEntity> lstOffers = oaHrRecruitOfferService.list(queryOffer);
			if(null != lstOffers && lstOffers.size() > 0) {
				j.setSuccess(false);
				j.setMsg("该简历存在于offer记录中，请移除后再试");
				return j;
			}
			
			//删除
			oaHrRecruitCVService.removeById(id);
			
		}catch(Exception e) {
			log.error("删除简历库数据报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除简历库数据错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
