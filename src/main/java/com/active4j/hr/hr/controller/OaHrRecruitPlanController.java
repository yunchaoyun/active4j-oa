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
import com.active4j.hr.common.constant.SysConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.upload.entity.UploadAttachmentEntity;
import com.active4j.hr.func.upload.service.UploadAttachmentService;
import com.active4j.hr.hr.entity.OaHrRecruitCVEntity;
import com.active4j.hr.hr.entity.OaHrRecruitNeedEntity;
import com.active4j.hr.hr.entity.OaHrRecruitPlanEntity;
import com.active4j.hr.hr.entity.OaHrRecruitPlanNeedEntity;
import com.active4j.hr.hr.service.OaHrRecruitCVService;
import com.active4j.hr.hr.service.OaHrRecruitNeedService;
import com.active4j.hr.hr.service.OaHrRecruitPlanNeedService;
import com.active4j.hr.hr.service.OaHrRecruitPlanService;
import com.active4j.hr.system.entity.SysDicValueEntity;
import com.active4j.hr.system.util.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title OaHrRecruitPlanController.java
 * @description 
		招聘计划管理
 * @time  2020年4月21日 下午5:30:07
 * @author guyp
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/oa/hr/recruit/plan")
public class OaHrRecruitPlanController extends BaseController{

	@Autowired
	private OaHrRecruitPlanService oaHrRecruitPlanService;
	
	@Autowired
	private OaHrRecruitNeedService oaHrRecruitNeedService;
	
	@Autowired
	private OaHrRecruitPlanNeedService oaHrRecruitPlanNeedService;
	
	@Autowired
	private OaHrRecruitCVService oaHrRecruitCVervice;
	
	@Autowired
	private UploadAttachmentService uploadAttachmentService;
	
	private static final String prefix_page = "oa/hr/recruit/plan/";
	
	/**
	 * 
	 * @description
	 *  	跳转列表页
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月21日 下午5:30:51
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(prefix_page + "planlist");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转选择需求页
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月21日 下午5:31:09
	 */
	@RequestMapping("/selectNeed")
	public ModelAndView selectNeed(HttpServletRequest request){
		ModelAndView view = new ModelAndView(prefix_page + "selectneed");
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param planEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaHrRecruitPlanEntity oaHrRecruitPlanEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<OaHrRecruitPlanEntity> queryWrapper = QueryUtils.installQueryWrapper(oaHrRecruitPlanEntity, request.getParameterMap(), dataGrid);
		//执行查询
		IPage<OaHrRecruitPlanEntity> lstResult = oaHrRecruitPlanService.page(new Page<OaHrRecruitPlanEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//结果处理，直接写到客户端
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 
	 * @description
	 *  	跳转新增编辑页
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年4月21日 下午5:37:15
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaHrRecruitPlanEntity planEntity, HttpServletRequest req) {
		
		if(StringUtils.isNotEmpty(planEntity.getId())) {
			//获取计划实体
			OaHrRecruitPlanEntity plan = oaHrRecruitPlanService.getById(planEntity.getId());
			req.setAttribute("plan", plan);
			
			//获得需求
			//先去中间表获取该计划的数据
			QueryWrapper<OaHrRecruitPlanNeedEntity> queryPlanNeed = new QueryWrapper<OaHrRecruitPlanNeedEntity>();
			queryPlanNeed.eq("PLAN_ID", plan.getId());
			List<OaHrRecruitPlanNeedEntity> lstPlanNeeds = oaHrRecruitPlanNeedService.list(queryPlanNeed);
			
			
			String needIds = "";
			String needJobs = "";
			//这里直接拼接显示
//			List<OaHrRecruitNeedEntity> lstNeeds = new ArrayList<OaHrRecruitNeedEntity>();
			if(null != lstPlanNeeds && lstPlanNeeds.size() > 0) {
				for(OaHrRecruitPlanNeedEntity planNeed : lstPlanNeeds) {
					if(null != planNeed) {
						OaHrRecruitNeedEntity need = oaHrRecruitNeedService.getById(planNeed.getNeedId());
//						lstNeeds.add(need);
						needIds += need.getId() + ",";
						needJobs += need.getNeedJob() + ",";
					}
				}
			}
			req.setAttribute("needIds", needIds);
			req.setAttribute("needJobs", needJobs);
			
			//获取附件名
			UploadAttachmentEntity attachment = uploadAttachmentService.getById(plan.getAttachment());
			if(null != attachment) {
				req.setAttribute("attachment", attachment.getName());
			}
			
		}
		
		//招聘渠道
		List<SysDicValueEntity> canalTypes = SystemUtils.getDictionaryLst(SysConstant.DIC_CANAL_TYPE);
		req.setAttribute("canalTypes", canalTypes);
		
		//计划状态
		List<SysDicValueEntity> planStatus = SystemUtils.getDictionaryLst(SysConstant.DIC_PLAN_STATUS);
		req.setAttribute("planStatus", planStatus);
		
		return new ModelAndView(prefix_page + "planadd");
	}
	
	/**
	 * 
	 * @description
	 *  	保存方法
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月22日 上午9:23:03
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(String needJobIds, OaHrRecruitPlanEntity planEntity, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		try {
			//需求选择
			if(StringUtils.isEmpty(needJobIds)) {
				j.setMsg("请选择招聘需求!");
				j.setSuccess(false);
				return j;
			}
			
			//计划名称
			if(StringUtils.isEmpty(planEntity.getName())) {
				j.setMsg("请填写计划名称!");
				j.setSuccess(false);
				return j;
			}
			
			//得到需求
			List<OaHrRecruitNeedEntity> needs = new ArrayList<OaHrRecruitNeedEntity>();
			for(String jobId : needJobIds.split(",")) {
				OaHrRecruitNeedEntity need = oaHrRecruitNeedService.getById(jobId);
				
				if(null == need) {
					continue;
				}
				needs.add(need);
			}
			
			//编辑保存
			if(StringUtils.isNotEmpty(planEntity.getId())) {
				OaHrRecruitPlanEntity t = oaHrRecruitPlanService.getById(planEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(planEntity, t);
				//保存计划实体
				oaHrRecruitPlanService.saveOrUpdate(t);
				//将公共表中的计划移除重新保存
				QueryWrapper<OaHrRecruitPlanNeedEntity> queryPlanNeed = new QueryWrapper<OaHrRecruitPlanNeedEntity>();
				queryPlanNeed.eq("PLAN_ID", planEntity.getId());
				oaHrRecruitPlanNeedService.remove(queryPlanNeed);
				//重新保存中间表
				if(null != needs && needs.size() > 0) {
					List<OaHrRecruitPlanNeedEntity> lstPns = new ArrayList<OaHrRecruitPlanNeedEntity>();
					for(OaHrRecruitNeedEntity need : needs) {
						OaHrRecruitPlanNeedEntity pn = new OaHrRecruitPlanNeedEntity();
						pn.setPlanId(planEntity.getId());
						pn.setNeedId(need.getId());
						lstPns.add(pn);
					}
					//批量保存中间表
					oaHrRecruitPlanNeedService.saveBatch(lstPns);
				}
				
			} else {
				//新增保存计划
				oaHrRecruitPlanService.save(planEntity);
				//新增保存中间表
				if(null != needs && needs.size() > 0) {
					List<OaHrRecruitPlanNeedEntity> lstPns = new ArrayList<OaHrRecruitPlanNeedEntity>();
					for(OaHrRecruitNeedEntity need : needs) {
						OaHrRecruitPlanNeedEntity pn = new OaHrRecruitPlanNeedEntity();
						pn.setPlanId(planEntity.getId());
						pn.setNeedId(need.getId());
						lstPns.add(pn);
					}
					//批量保存中间表
					oaHrRecruitPlanNeedService.saveBatch(lstPns);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg("招聘计划数据保存错误");
			j.setSuccess(false);
			log.error("招聘计划数据保存错误，错误信息：{}", e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年4月22日 上午9:27:17
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
			
			//看看简历库中有没有关联的
			QueryWrapper<OaHrRecruitCVEntity> queryCV = new QueryWrapper<OaHrRecruitCVEntity>();
			queryCV.eq("PLAN_ID", id);
			List<OaHrRecruitCVEntity> lstCVs = oaHrRecruitCVervice.list(queryCV);
			if(null != lstCVs && lstCVs.size() > 0) {
				j.setSuccess(false);
				j.setMsg("该计划存在于简历库信息中，请移除后再试");
				return j;
			}
			
			//将公共表中的计划移除
			QueryWrapper<OaHrRecruitPlanNeedEntity> queryPlanNeed = new QueryWrapper<OaHrRecruitPlanNeedEntity>();
			queryPlanNeed.eq("PLAN_ID", id);
			oaHrRecruitPlanNeedService.remove(queryPlanNeed);
			//删除计划实体
			oaHrRecruitPlanService.removeById(id);
			
		}catch(Exception e) {
			log.error("删除招聘计划数据报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除招聘计划数据错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
