package com.active4j.hr.hr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaUserStudyModel;
import com.active4j.hr.hr.domain.OaUserWorkModel;
import com.active4j.hr.hr.entity.OaHrJobEntity;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.entity.OaHrUserPayEntity;
import com.active4j.hr.hr.entity.OaHrUserStudyEntity;
import com.active4j.hr.hr.entity.OaHrUserWorkEntity;
import com.active4j.hr.hr.service.OaHrJobService;
import com.active4j.hr.hr.service.OaHrUserPayService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.active4j.hr.hr.service.OaHrUserStudyService;
import com.active4j.hr.hr.service.OaHrUserWorkService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.util.SystemUtils;
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
@RequestMapping("/oa/hr/user")
@Slf4j
public class OaHrUserController extends BaseController {
	
	@Autowired
	private OaHrUserService oaHrUserService;
	
	@Autowired
	private OaHrUserPayService oaHrUserPayService;
	
	@Autowired
	private OaHrUserStudyService oaHrUserStudyService;
	
	@Autowired
	private OaHrUserWorkService oaHrUserWorkService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private OaHrJobService oaHrJobService;
	
	private static final String prefix_page = "oa/hr/record/";
	
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
		ModelAndView view = new ModelAndView(prefix_page + "oauserlist");
		
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
	public void datagrid(OaHrUserEntity oaHrUserEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//查询在职人员
		oaHrUserEntity.setWorkStatus(GlobalConstant.OAUSER_JOB_STATUS_IN);
		//拼接查询条件
		QueryWrapper<OaHrUserEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrUserEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrUserEntity> lstResult = oaHrUserService.page(new Page<OaHrUserEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	新增编辑
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月10日 下午5:08:34
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrUserEntity oaHrUserEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView(prefix_page + "oauser");
		
		if(StringUtils.isNotEmpty(oaHrUserEntity.getId())) {
			//编辑
			oaHrUserEntity = oaHrUserService.getById(oaHrUserEntity.getId());
			view.addObject("oaUser", oaHrUserEntity);
			
			SysDeptEntity parentDept = sysDeptService.getById(oaHrUserEntity.getDeptId());
			if(null != parentDept) {
				view.addObject("parentDepartName", parentDept.getName());
			}
			
			OaHrJobEntity parentJob = oaHrJobService.getById(oaHrUserEntity.getJobId());
			if(null != parentJob) {
				view.addObject("parentJobName", parentJob.getJobName());
			}
			
			//成本信息查询
			QueryWrapper<OaHrUserPayEntity> payQuery = new QueryWrapper<OaHrUserPayEntity>();
			payQuery.eq("USER_ID", oaHrUserEntity.getId());
			OaHrUserPayEntity userPay = oaHrUserPayService.getOne(payQuery);
			view.addObject("oaUserPay", userPay);
			
			//教育经历查询
			QueryWrapper<OaHrUserStudyEntity> studyQuery = new QueryWrapper<OaHrUserStudyEntity>();
			studyQuery.eq("USER_ID", oaHrUserEntity.getId());
			List<OaHrUserStudyEntity> lstStudys = oaHrUserStudyService.list(studyQuery);
			if(null != lstStudys && lstStudys.size() > 0) {
				view.addObject("userStudys", lstStudys);
			}
			
			//工作经历查询
			QueryWrapper<OaHrUserWorkEntity> workQuery = new QueryWrapper<OaHrUserWorkEntity>();
			workQuery.eq("USER_ID", oaHrUserEntity.getId());
			List<OaHrUserWorkEntity> lstWorks = oaHrUserWorkService.list(workQuery);
			if(null != lstWorks && lstWorks.size() > 0) {
				view.addObject("userWorks", lstWorks);
			}
			
		}
		
		//数据字典
		//性别
		List<SysDicValueEntity> sexs = SystemUtils.getDictionaryLst(SysConstant.DIC_SEX_TYPE);
		view.addObject("sexs", sexs);
		
		//婚姻状况
		List<SysDicValueEntity> canMarriages = SystemUtils.getDictionaryLst(SysConstant.DIC_MARRIAGE_STATUS);
		view.addObject("canMarriages", canMarriages);
		
		//户口类型
		List<SysDicValueEntity> accountTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_RESIDENCE_TYPE);
		view.addObject("accountTypes", accountTypes);	
		
		//政治面貌
		List<SysDicValueEntity> politicals = SystemUtils.getDictionaryLst(SysConstant.DIC_POLITICAL_TYPE);
		view.addObject("politicals", politicals);
		
		//最高学历
		List<SysDicValueEntity> highestEducations = SystemUtils.getDictionaryLst(SysConstant.DIC_DEGREE_INFO);
		view.addObject("highestEducations", highestEducations);
		
		//培养方式
		List<SysDicValueEntity> developTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_DEVELOP_TYPE);
		view.addObject("developTypes", developTypes);
		
		//证书类型
		List<SysDicValueEntity> certificateTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_CERTIFI_TYPE);
		view.addObject("certificateTypes", certificateTypes);
		
		//合同类型
		List<SysDicValueEntity> contractTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_CONTRACT_TYPE);
		view.addObject("contractTypes", contractTypes);
		
		//成本类型
		List<SysDicValueEntity> payTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_PAY_TYPE);
		view.addObject("payTypes", payTypes);
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	保存方法
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月14日 上午9:19:51
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaHrUserEntity oaUser, String oaUserId, OaHrUserPayEntity oaUserPay, String oaUserPayId, 
			OaUserStudyModel oaUserStudy, OaUserWorkModel oaUserWork, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//手动设
			oaUser.setId(oaUserId);
			//状态默认为在职
			oaUser.setWorkStatus(GlobalConstant.OAUSER_JOB_STATUS_IN);
			
			//用户名验证
			if(StringUtils.isEmpty(oaUser.getRealName())) {
				j.setSuccess(false);
				j.setMsg("真实姓名不能为空");
				return j;
			}
			
			//所属部门验证
			if(StringUtils.isEmpty(oaUser.getDeptId())) {
				j.setSuccess(false);
				j.setMsg("所属部门不能为空");
				return j;
			}
			
			//岗位验证
			if(StringUtils.isEmpty(oaUser.getJobId())) {
				j.setSuccess(false);
				j.setMsg("岗位不能为空");
				return j;
			}
			
			//用户信息
			if(StringUtils.isNotEmpty(oaUserId)) {
				OaHrUserEntity tmpUser = oaHrUserService.getById(oaUserId);
				MyBeanUtils.copyBeanNotNull2Bean(oaUser, tmpUser);
				//保存人事信息
				oaHrUserService.saveOrUpdate(tmpUser);
				
				//保存成本信息
				QueryWrapper<OaHrUserPayEntity> payQuery = new QueryWrapper<OaHrUserPayEntity>();
				payQuery.eq("USER_ID", oaUserId);
				OaHrUserPayEntity tmpPay = oaHrUserPayService.getOne(payQuery);
				MyBeanUtils.copyBeanNotNull2Bean(oaUserPay, tmpPay);
				oaHrUserPayService.saveOrUpdate(tmpPay);
				
				QueryWrapper<OaHrUserStudyEntity> studyQuery = new QueryWrapper<OaHrUserStudyEntity>();
				studyQuery.eq("USER_ID", oaUserId);
				oaHrUserStudyService.remove(studyQuery);
				//教育经历list不为空则遍历保存
				if(null != oaUserStudy && null != oaUserStudy.getStudys() && oaUserStudy.getStudys().size() > 1) {
					for(OaHrUserStudyEntity study : oaUserStudy.getStudys()) {
						//如果输入的3个有一个为空都不保存
						if(null == study || StringUtils.isEmpty(study.getBeforeDate()) || StringUtils.isEmpty(study.getEndDate()) || StringUtils.isEmpty(study.getSchoolName())) {
							//不保存
						}else {
							//保存
							study.setUserId(oaUserId);
							oaHrUserStudyService.save(study);
						}
					}
				}
				
				//移除已存在的工作经历
				QueryWrapper<OaHrUserWorkEntity> workQuery = new QueryWrapper<OaHrUserWorkEntity>();
				workQuery.eq("USER_ID", oaUserId);
				oaHrUserWorkService.remove(workQuery);
				//工作经历list不为空则遍历保存
				if(null != oaUserWork && null != oaUserWork.getWorks() && oaUserWork.getWorks().size() > 1) {
					for(OaHrUserWorkEntity work : oaUserWork.getWorks()) {
						//公司，开始时间，结束时间有一个为空都不保存
						if(null == work || StringUtils.isEmpty(work.getBeforeWorkDate()) || StringUtils.isEmpty(work.getEndWorkDate()) || StringUtils.isEmpty(work.getCompanyName())) {
							//不保存
						}else {
							//保存
							work.setUserId(oaUserId);
							oaHrUserWorkService.save(work);
						}
					}
				}
				
			}else {
				//新增保存
				//人事信息保存
				oaHrUserService.save(oaUser);
				oaUserPay.setUserId(oaUser.getId());
				//成本信息保存
				oaHrUserPayService.save(oaUserPay);
				//教育经历list不为空则遍历保存,前端传过来的list下标0为空的，所以这里要求size>1
				if(null != oaUserStudy && null != oaUserStudy.getStudys() && oaUserStudy.getStudys().size() > 1) {
					for(OaHrUserStudyEntity study : oaUserStudy.getStudys()) {
						//如果输入的3个有一个为空都不保存
						if(null == study || StringUtils.isEmpty(study.getBeforeDate()) || StringUtils.isEmpty(study.getEndDate()) || StringUtils.isEmpty(study.getSchoolName())) {
							//不保存
						}else {
							//保存
							study.setUserId(oaUserId);
							oaHrUserStudyService.save(study);
						}
					}
				}
				
				//工作经历list不为空则遍历保存
				if(null != oaUserWork && null != oaUserWork.getWorks() && oaUserWork.getWorks().size() > 1) {
					for(OaHrUserWorkEntity work : oaUserWork.getWorks()) {
						//公司，开始时间，结束时间有一个为空都不保存
						if(null == work || StringUtils.isEmpty(work.getBeforeWorkDate()) || StringUtils.isEmpty(work.getEndWorkDate()) || StringUtils.isEmpty(work.getCompanyName())) {
							//不保存
						}else {
							//保存
							work.setUserId(oaUserId);
							oaHrUserWorkService.save(work);
						}
					}
				}
				
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("保存人事信息异常");
			log.error("人事信息保存报错，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	离职员工
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月15日 上午10:12:30
	 */
	@RequestMapping("/doLeaveJob")
	@ResponseBody
	public AjaxJson doLeaveJob(@RequestParam String id, HttpServletRequest req) {
		
		AjaxJson j = new AjaxJson();
		
		OaHrUserEntity oaUser = oaHrUserService.getById(id);
		
		if(null == oaUser) {
			j.setMsg("不存在该员工，请重新选择！");
			j.setSuccess(false);
			return j;
		}
		
		//工作状态设为离职
		oaUser.setWorkStatus(GlobalConstant.OAUSER_JOB_STATUS_LEAVE);
		//更新保存
		oaHrUserService.saveOrUpdate(oaUser);
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除员工
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月15日 上午10:22:03
	 */
	@RequestMapping("/del")
	@ResponseBody
	public AjaxJson del(@RequestParam String id, HttpServletRequest req) {
		
		AjaxJson j = new AjaxJson();
		
		OaHrUserEntity oaUser = oaHrUserService.getById(id);
		
		if(null == oaUser) {
			j.setMsg("不存在该员工，请重新选择！");
			j.setSuccess(false);
			return j;
		}
		
		//工作状态设为删除
		oaUser.setWorkStatus(GlobalConstant.OAUSER_JOB_STATUS_DEL);
		//更新保存
		oaHrUserService.saveOrUpdate(oaUser);
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转离职列表页
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月16日 下午5:00:44
	 */
	@RequestMapping("/leavelist")
	public ModelAndView leaveList() {
		ModelAndView view = new ModelAndView(prefix_page + "oauserleavelist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	获取离职人员的表格数据
	 * @return void
	 * @author guyp
	 * @time 2020年4月16日 下午4:59:44
	 */
	@RequestMapping("/leaveDatagrid")
	public void leaveDatagrid(OaHrUserEntity oaHrUserEntity, DataGrid dataGrid, HttpServletRequest request, HttpServletResponse response) {
		//查询在职人员
		oaHrUserEntity.setWorkStatus(GlobalConstant.OAUSER_JOB_STATUS_LEAVE);
		//拼接查询条件
		QueryWrapper<OaHrUserEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrUserEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrUserEntity> lstResult = oaHrUserService.page(new Page<OaHrUserEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	查看离职人员信息
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月16日 下午5:05:30
	 */
	@RequestMapping("/leaveview")
	public ModelAndView leaveView(OaHrUserEntity oaHrUserEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView(prefix_page + "oauserleave");
		
		if(StringUtils.isNotEmpty(oaHrUserEntity.getId())) {
			//编辑
			oaHrUserEntity = oaHrUserService.getById(oaHrUserEntity.getId());
			view.addObject("oaUser", oaHrUserEntity);
			
			//部门信息
			SysDeptEntity parentDept = sysDeptService.getById(oaHrUserEntity.getDeptId());
			if(null != parentDept) {
				view.addObject("parentDepartName", parentDept.getName());
			}
			
			//岗位信息
			OaHrJobEntity parentJob = oaHrJobService.getById(oaHrUserEntity.getJobId());
			if(null != parentJob) {
				view.addObject("parentJobName", parentJob.getJobName());
			}
			
			//成本信息查询
			QueryWrapper<OaHrUserPayEntity> payQuery = new QueryWrapper<OaHrUserPayEntity>();
			payQuery.eq("USER_ID", oaHrUserEntity.getId());
			OaHrUserPayEntity userPay = oaHrUserPayService.getOne(payQuery);
			view.addObject("oaUserPay", userPay);
			
			//教育经历查询
			QueryWrapper<OaHrUserStudyEntity> studyQuery = new QueryWrapper<OaHrUserStudyEntity>();
			studyQuery.eq("USER_ID", oaHrUserEntity.getId());
			List<OaHrUserStudyEntity> lstStudys = oaHrUserStudyService.list(studyQuery);
			if(null != lstStudys && lstStudys.size() > 0) {
				view.addObject("userStudys", lstStudys);
			}
			
			//工作经历查询
			QueryWrapper<OaHrUserWorkEntity> workQuery = new QueryWrapper<OaHrUserWorkEntity>();
			workQuery.eq("USER_ID", oaHrUserEntity.getId());
			List<OaHrUserWorkEntity> lstWorks = oaHrUserWorkService.list(workQuery);
			if(null != lstWorks && lstWorks.size() > 0) {
				view.addObject("userWorks", lstWorks);
			}
			
			//数据字典
			//性别
			List<SysDicValueEntity> sexs = SystemUtils.getDictionaryLst(SysConstant.DIC_SEX_TYPE);
			for(SysDicValueEntity dic : sexs) {
				if(StringUtils.equals(oaHrUserEntity.getSex(), dic.getValue())) {
					view.addObject("sex", dic.getLabel());
				}
			}
			
			//婚姻状况
			List<SysDicValueEntity> canMarriages = SystemUtils.getDictionaryLst(SysConstant.DIC_MARRIAGE_STATUS);
			for(SysDicValueEntity dic : canMarriages) {
				if(StringUtils.equals(oaHrUserEntity.getCanMarriage(), dic.getValue())) {
					view.addObject("canMarriage", dic.getLabel());
				}
			}
			
			//户口类型
			List<SysDicValueEntity> accountTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_RESIDENCE_TYPE);
			for(SysDicValueEntity dic : accountTypes) {
				if(StringUtils.equals(oaHrUserEntity.getAccountType(), dic.getValue())) {
					view.addObject("accountType", dic.getLabel());
				}
			}
			
			//政治面貌
			List<SysDicValueEntity> politicals = SystemUtils.getDictionaryLst(SysConstant.DIC_POLITICAL_TYPE);
			for(SysDicValueEntity dic : politicals) {
				if(StringUtils.equals(oaHrUserEntity.getPolitical(), dic.getValue())) {
					view.addObject("political", dic.getLabel());
				}
			}
			
			//最高学历
			List<SysDicValueEntity> highestEducations = SystemUtils.getDictionaryLst(SysConstant.DIC_DEGREE_INFO);
			for(SysDicValueEntity dic : highestEducations) {
				if(StringUtils.equals(oaHrUserEntity.getHighestEducation(), dic.getValue())) {
					view.addObject("highestEducation", dic.getLabel());
				}
			}
			
			//培养方式
			List<SysDicValueEntity> developTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_DEVELOP_TYPE);
			for(SysDicValueEntity dic : developTypes) {
				if(StringUtils.equals(oaHrUserEntity.getDevelopType(), dic.getValue())) {
					view.addObject("developType", dic.getLabel());
				}
			}
			
			//证书类型
			List<SysDicValueEntity> certificateTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_CERTIFI_TYPE);
			for(SysDicValueEntity dic : certificateTypes) {
				if(StringUtils.equals(oaHrUserEntity.getCertificateType(), dic.getValue())) {
					view.addObject("certificateType", dic.getLabel());
				}
			}
			
			//合同类型
			List<SysDicValueEntity> contractTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_CONTRACT_TYPE);
			for(SysDicValueEntity dic : contractTypes) {
				if(StringUtils.equals(oaHrUserEntity.getContractType(), dic.getValue())) {
					view.addObject("contractType", dic.getLabel());
				}
			}
			
			//成本类型
			List<SysDicValueEntity> payTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_PAY_TYPE);
			for(SysDicValueEntity dic : payTypes) {
				if(StringUtils.equals(userPay.getPayType(), dic.getValue())) {
					view.addObject("payType", dic.getLabel());
				}
			}
			
		}
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	删除离职人事信息
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月16日 下午6:02:23
	 */
	@RequestMapping("/leavedel")
	@ResponseBody
	public AjaxJson leaveDel(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			//id校验
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择一条记录");
				return j;
			}
			//删除离职人事信息
			oaHrUserService.delLeave(id);
		}catch(Exception e) {
			log.error("删除离职人员报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除离职人员异常");
			e.printStackTrace();
		}
		
		return j;
	}
}
