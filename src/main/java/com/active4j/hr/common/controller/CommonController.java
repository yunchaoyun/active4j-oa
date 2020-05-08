package com.active4j.hr.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.hr.entity.OaHrJobEntity;
import com.active4j.hr.hr.service.OaHrJobService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysFunctionEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysRoleFunctionEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysFunctionService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;


/**
 * 通用业务处理
 * 
 * 
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysFunctionService sysFunctionService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private OaHrJobService oaHrJobService;
	
	/**
	 * 系统中 跳转到操作成功页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/goSuccess")
	public ModelAndView goSuccess(HttpServletRequest req) {
		return new ModelAndView("system/common/success");
	}

	/**
	 * icon弹出框
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/selectIcon")
	public ModelAndView selectIcon(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/common/selectIcon");
		
		return view;
	}
	
	
	/**
	 * 部门选择弹出框
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/selectDepart")
	public ModelAndView selectDepart(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/common/selectdepart");
		
		//查询部门
		//先查询顶级部门
		List<SysDeptEntity> lstDeparts = sysDeptService.getParentDepts();
		
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		departContact(lstDeparts, sb);
		sb = sb.append("]");
		req.setAttribute("treeData", sb.toString());
		return view;
	}
	
	/**
	 * 岗位选择弹出框
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/selectJob")
	public ModelAndView selectJob(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/common/selectjob");
		
		//查询岗位
		//先查询顶级岗位
		List<OaHrJobEntity> lstJobs = oaHrJobService.getParentJobs();
		
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		jobContact(lstJobs, sb);
		sb = sb.append("]");
		req.setAttribute("treeData", sb.toString());
		return view;
	}
	
	
	/**
	 * 用户选择弹出框
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectUsers")
	public ModelAndView selectUsers(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/common/selectusers");
		
	
		String userTreeStr = getCompanyOfUser(null);
		view.addObject("userTreeStr", userTreeStr);
		
		return view;
	}
	
	public String getCompanyOfUser(Set<String> lstUserIds) {
		//先查询顶级部门
		List<SysDeptEntity> lstDeparts = sysDeptService.getParentDepts();
		
		//拼接字符串
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		departUserContact(lstDeparts, sb, lstUserIds);
		
		sb = sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * 递归调用，正式公司类型的组织机构下
	 * @param lstDeparts
	 * @param sb
	 */
	private void departUserContact(List<SysDeptEntity> lstDeparts, StringBuffer sb, Set<String> lstUserIds) {
		if(null != lstDeparts && lstDeparts.size() > 0) {
			for(int i = 0; i < lstDeparts.size(); i++) {
				SysDeptEntity depart = lstDeparts.get(i);
				//查询所有组织架构
				sb = sb.append("{").append("icon:").append("\"fa fa-sitemap\"").append(",").append("type:").append("\"C\"").append(",").append("text:").append("\"").append(depart.getName()).append("\",").append("id:").append("\"").append(depart.getId()).append("\"");
				List<SysDeptEntity> lstTmp = sysDeptService.getChildDeptsByDeptId(depart.getId());
				if(null != lstTmp && lstTmp.size() > 0) {
					//根据type过滤数据
					if(lstTmp.size() > 0) {
						sb = sb.append(", nodes: [");
						departUserContact(lstTmp, sb, lstUserIds);
						List<SysUserEntity> lstUsers = sysUserService.findUsersByDept(depart);
						if(null != lstUsers && lstUsers.size() > 0) {
							sb = sb.append(",");
							for(int m = 0; m < lstUsers.size(); m++) {
								SysUserEntity user = lstUsers.get(m);
								if(m == lstUsers.size() - 1) {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}
									
								}else {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}
									
								}
							}
						}
						sb.append("]");
					}else {
						List<SysUserEntity> lstUsers = sysUserService.findUsersByDept(depart);
						if(null != lstUsers && lstUsers.size() > 0) {
							sb = sb.append(", nodes:[");
							for(int m = 0; m < lstUsers.size(); m++) {
								SysUserEntity user = lstUsers.get(m);
								if(m == lstUsers.size() - 1) {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}
									
								}else {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}
									
								}
							}
							sb.append("]");
						}
					}
				}else {
					List<SysUserEntity> lstUsers = sysUserService.findUsersByDept(depart);
					if(null != lstUsers && lstUsers.size() > 0) {
						sb = sb.append(", nodes:[");
						for(int m = 0; m < lstUsers.size(); m++) {
							SysUserEntity user = lstUsers.get(m);
							if(m == lstUsers.size() - 1) {
								if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
								}else {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("}");
								}
								
							}else {
								if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
								}else {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("id:").append("\"").append(user.getId()).append("\"").append("},");
								}
								
							}
						}
						sb.append("]");
					}
					
				} 
				
				if(i == lstDeparts.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
	}
	
	/**
	 * 递归的方式 循环显示各部门及其子部门
	 * @param lstDeparts
	 * @param sb
	 */
	private void departContact(List<SysDeptEntity> lstDeparts, StringBuffer sb) {
		if(null != lstDeparts && lstDeparts.size() > 0) {
			for(int i = 0; i < lstDeparts.size(); i++) {
				SysDeptEntity depart = lstDeparts.get(i);
				//查询所有组织架构
				sb = sb.append("{").append("text:").append("\"").append(depart.getName()).append("\",").append("id:").append("\"").append(depart.getId()).append("\"");
				//查询子部门
				List<SysDeptEntity> lstChild = sysDeptService.getChildDeptsByDeptId(depart.getId());
				
				if(null != lstChild && lstChild.size() > 0) {
					//根据type过滤数据
					List<SysDeptEntity> lstTmp = new ArrayList<SysDeptEntity>();
					for(SysDeptEntity d : lstChild) {
						lstTmp.add(d);
					}
					if(lstTmp.size() > 0) {
						sb = sb.append(", nodes: [");
						departContact(lstTmp, sb);
						sb.append("]");
					}
					
				}
				
				if(i == lstDeparts.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
	}
	
	
	
	/**
	 * 获取菜单数据，树形视图
	 * @param req
	 * @return
	 */
	@RequestMapping("/getMenusTreeDate")
	@ResponseBody
	public AjaxJson getMenusTreeDate(String roleId, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		//查询角色
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		// 已有权限菜单
		List<String> loginActionlist = new ArrayList<String>();
		if (null != role) {
			List<SysRoleFunctionEntity> roleFunctionList = sysRoleService.getRoleFunctionList(role);
			//角色拥有的权限集合
			if(null != roleFunctionList && roleFunctionList.size() > 0) {
				for (SysRoleFunctionEntity roleFunction : roleFunctionList) {
					loginActionlist.add(roleFunction.getMenuId());
				}
			}
		}
		
		//先查询顶级菜单
		List<SysFunctionEntity> lstMenus = sysFunctionService.getParentFunctions();
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		//采用递归方式拼接
		menuContact(lstMenus, sb, loginActionlist);
		sb = sb.append("]");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", sb.toString());
		j.setAttributes(map);
		return j;
	}
	
	/**
	 * 递归的方式 循环显示各菜单及其子菜单
	 * @param lstDeparts
	 * @param sb
	 */
	private void menuContact(List<SysFunctionEntity> lstMenus, StringBuffer sb, List<String> loginActionlist) {
		if(null != lstMenus && lstMenus.size() > 0) {
			for(int i = 0; i < lstMenus.size(); i++) {
				SysFunctionEntity function = lstMenus.get(i);
				if(loginActionlist.contains(function.getId())) {
					//已拥有的菜单权限
					sb = sb.append("{").append("text:").append("\"").append(function.getName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(function.getId()).append("\"");
				}else {
					//未拥有的菜单权限
					sb = sb.append("{").append("text:").append("\"").append(function.getName()).append("\",").append("id:").append("\"").append(function.getId()).append("\"");
				}
				
				List<SysFunctionEntity> lstChildren = sysFunctionService.getChildFunctionsByParentId(function.getId());
				if(null != lstChildren && lstChildren.size() > 0) {
					sb = sb.append(", nodes: [");
					menuContact(lstChildren, sb, loginActionlist);
					sb.append("]");
				}
				if(i == lstMenus.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
	}
	
	/**
	 * 获取部门数据，树形视图
	 * @param req
	 * @return
	 */
	@RequestMapping("/getDepartTreeData")
	@ResponseBody
	public AjaxJson getDepartTreeData(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		//先查询顶级岗位
		List<SysDeptEntity> lstDeparts = sysDeptService.getParentDepts();
		
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		departContact(lstDeparts, sb);
		sb = sb.append("]");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", sb.toString());
		j.setAttributes(map);
		
		return j;
	}
	
	/**
	 * 获取岗位数据，树形视图
	 * @param req
	 * @return
	 */
	@RequestMapping("/getJobTreeData")
	@ResponseBody
	public AjaxJson getJobTreeData(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		//先查询顶级岗位
		List<OaHrJobEntity> lstJobs = oaHrJobService.getParentJobs();
		
		//拼接成bootstrap treeview树形结构
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		jobContact(lstJobs, sb);
		sb = sb.append("]");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", sb.toString());
		j.setAttributes(map);
		
		return j;
	}
	
	/**
	 * 递归的方式 循环显示各岗位及其子岗位
	 * @param lstJobs
	 * @param sb
	 */
	private void jobContact(List<OaHrJobEntity> lstJobs, StringBuffer sb) {
		if(null != lstJobs && lstJobs.size() > 0) {
			for(int i = 0; i < lstJobs.size(); i++) {
				OaHrJobEntity job = lstJobs.get(i);
				//查询所有岗位
				sb = sb.append("{").append("text:").append("\"").append(job.getJobName()).append("\",").append("id:").append("\"").append(job.getId()).append("\"");
				//查询子岗位
				List<OaHrJobEntity> lstChild = oaHrJobService.getChildJobsByJobId(job.getId());
				
				if(null != lstChild && lstChild.size() > 0) {
					//递归遍历
					sb = sb.append(", nodes: [");
					jobContact(lstChild, sb);
					sb.append("]");
					
				}
				
				if(i == lstJobs.size() - 1) {
					sb = sb.append("}");
				}else {
					sb = sb.append("},");
				}
			}
		}
	}
	
}
