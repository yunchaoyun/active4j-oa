package com.active4j.hr.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SysUserController.java
 * @description 
		  系统管理  用户管理
 * @time  2020年1月23日 下午11:42:03
 * @author 麻木神
 * @version 1.0
*/
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController extends BaseController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysRoleService sysRoleService;

	
	/**
	 * 菜单列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView("system/user/userlist");
		
		// 给部门查询条件中的下拉框准备数据
		List<SysDeptEntity> lstDeparts = sysDeptService.list();
		view.addObject("departsReplace", ListUtils.listToReplaceStr(lstDeparts, "name", "id"));
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	表格数据显示
	 * @return void
	 * @author 麻木神
	 * @time 2020年1月25日 下午9:46:12
	 */
	@RequestMapping("/datagrid")
	public void datagrid(SysUserEntity sysUserEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//拼接查询条件
		QueryWrapper<SysUserEntity> queryWrapper = QueryUtils.installQueryWrapper(sysUserEntity, request.getParameterMap(), dataGrid);
		
		//执行查询
		IPage<SysUserEntity> lstResult = sysUserService.page(new Page<SysUserEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
		
		//输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
		
	}
	
	/**
	 * 
	 * @description
	 *  	新增或编辑跳转
	 * @return ModelAndView
	 * @author 麻木神
	 * @time 2020年1月28日 下午3:48:35
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(SysUserEntity sysUserEntity, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/user/user");
		
		if(StringUtils.isEmpty(sysUserEntity.getId())) {
			//新增
			sysUserEntity = new SysUserEntity();
			view.addObject("user", sysUserEntity);
			
		
		}else {
			//编辑
			sysUserEntity = sysUserService.getById(sysUserEntity.getId());
			view.addObject("user", sysUserEntity);
			//用户所在部门
			SysDeptEntity dept =  sysDeptService.getById(sysUserEntity.getDeptId());
			view.addObject("deptName", dept.getName());
			
			//所属角色
			String roleId = "";
			String roleName = "";
			List<SysRoleEntity> roles = sysUserService.getUserRoleByUserId(sysUserEntity.getId());
			if(roles.size() > 0) {
				for (SysRoleEntity r : roles) {
					roleId += r.getId() + ",";
					roleName += r.getRoleName() + ",";
				}
			}
			view.addObject("roleId", roleId);
			view.addObject("roleName", roleName);
		}
		
		//角色的处理
		List<SysRoleEntity> lstRoles = sysRoleService.list();
		view.addObject("lstRoles", lstRoles);
		
		return view;
	}
	
	
	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveUser")
	@ResponseBody
	@Log(type = LogType.save, name = "保存用户信息", memo = "新增或编辑保存了用户信息")
	public AjaxJson saveUser(HttpServletRequest req, SysUserEntity sysUserEntity) {
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isEmpty(sysUserEntity.getUserName())) {
				j.setSuccess(false);
				j.setMsg("用户名不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(sysUserEntity.getDeptId())) {
				j.setSuccess(false);
				j.setMsg("所属部门为空");
				return j;
			}
			
			if(!StringUtils.equals(sysUserEntity.getPassword(), req.getParameter("repassword"))) {
				j.setSuccess(false);
				j.setMsg("两次输入的密码不一致");
				return j;
			}
			
			//角色
			String[] roleIds = req.getParameterValues("roleid");
			
			if(StringUtils.isEmpty(sysUserEntity.getId())) {
				//新增保存
				SysUserEntity tmpUser = sysUserService.getUserByUseName(sysUserEntity.getUserName());
				if(null != tmpUser) {
					j.setSuccess(false);
					j.setMsg("该用户名已经存在");
					return j;
				}
				
				//状态
				sysUserEntity.setStatus("1");
				
				//初始化密码
				sysUserEntity.setSalt(ShiroUtils.getRandomSalt());
				sysUserEntity.setPassword(ShiroUtils.md5(sysUserEntity.getPassword(), sysUserEntity.getSalt()));
				
				sysUserService.saveUser(sysUserEntity, roleIds);
				
			}else {
				//编辑保存
				SysUserEntity tmp = sysUserService.getById(sysUserEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(sysUserEntity, tmp);
				sysUserService.saveOrUpdateUser(tmp, roleIds);
			}
			
			
		}catch(Exception e) {
			log.error("保存用户信息报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存用户错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转到修改密码页面
	 * @return ModelAndView
	 * @author 麻木神
	 * @time 2020年1月29日 下午1:13:10
	 */
	@RequestMapping("/changepwd")
	public ModelAndView changepwd(SysUserEntity user, HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/user/changepwd");
		view.addObject("userId", user.getId());
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	保存密码
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月4日 上午10:06:14
	 */
	@RequestMapping("/savepwd")
	@ResponseBody
	@Log(type = LogType.save, name = "保存用户密码", memo = "编辑保存了用户密码")
	public AjaxJson savenewpwdforuser(String id, String password, String repassword, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("缺少必要参数");
				return j;
			}
			
			if(StringUtils.isEmpty(password) || StringUtils.isEmpty(repassword)) {
				j.setSuccess(false);
				j.setMsg("密码不能为空");
				return j;
			}
			
			if(!StringUtils.equals(password, repassword)) {
				j.setSuccess(false);
				j.setMsg("两次输入的密码不相等");
				return j;
			}
			
			SysUserEntity user = sysUserService.getById(id);
			user.setSalt(ShiroUtils.getRandomSalt());
			user.setPassword(ShiroUtils.md5(password, user.getSalt()));
			
			sysUserService.saveOrUpdate(user);
			
		}catch(Exception e) {
			log.error("修改用户密码报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改用户密码错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	锁定用户
	 * @return AjaxJson
	 * @author 麻木神
	 * @time 2020年1月29日 下午1:58:16
	 */
	@RequestMapping("/lock")
	@ResponseBody
	@RequiresPermissions("sys:user:test")
	@Log(type = LogType.update, name = "锁定用户", memo = "更新锁定了用户")
	public AjaxJson lock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			SysUserEntity user = sysUserService.getById(id);
			if(StringUtils.equals(user.getUserName(), "admin")) {
				j.setSuccess(false);
				j.setMsg("管理员不能被锁定");
				return j;
			}
			user.setStatus("0");
			sysUserService.saveOrUpdate(user);
		}catch(Exception e) {
			log.error("锁定用户报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("锁定用户错误");
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	解锁用户
	 * @return AjaxJson
	 * @author 麻木神
	 * @time 2020年1月29日 下午1:58:16
	 */
	@RequestMapping("/unlock")
	@ResponseBody
	@Log(type = LogType.update, name = "解锁用户", memo = "更新解锁了用户")
	public AjaxJson unlock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			SysUserEntity user = sysUserService.getById(id);
			
			user.setStatus("1");
			sysUserService.saveOrUpdate(user);
		}catch(Exception e) {
			log.error("解锁用户报错，错误信息:" + e.getMessage());
			j.setSuccess(false);
			j.setMsg("解锁用户错误");
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转到个人资料页面
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年2月8日 上午11:53:07
	 */
	@RequestMapping("/goinfo")
	public ModelAndView goInfo(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/user/basic/info");
		//获取当前用户id
		String userId = ShiroUtils.getSessionUserId();
		//获取当前用户个人资料
		SysUserModel user = sysUserService.getInfoByUserId(userId);
		view.addObject("user", user);
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	用户保存个人资料
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月8日 下午2:33:28
	 */
	@RequestMapping("/saveinfo")
	@ResponseBody
	@Log(type = LogType.save, name = "保存个人资料", memo = "编辑保存了个人资料信息")
	public AjaxJson saveInfo(HttpServletRequest req, SysUserEntity sysUserEntity) {
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isEmpty(sysUserEntity.getId())) {
				j.setSuccess(false);
				j.setMsg("用户ID不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(sysUserEntity.getRealName())) {
				j.setSuccess(false);
				j.setMsg("真实姓名不能为空");
				return j;
			}
			
			//编辑保存
			SysUserEntity tmp = sysUserService.getById(sysUserEntity.getId());
			MyBeanUtils.copyBeanNotNull2Bean(sysUserEntity, tmp);
			sysUserService.saveOrUpdate(tmp);
			//参数返回前端
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("headImgUrl", tmp.getHeadImgUrl());
			map.put("realName", tmp.getRealName());
			
			j.setAttributes(map);
			
			log.info("用户：" + tmp.getUserName() + "修改了个人资料");
		}catch(Exception e) {
			log.error("保存个人资料报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存个人资料错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	跳转修改密码页面
	 * @params
	 * @return ModelAndView
	 * @author guyp
	 * @time 2020年2月8日 下午3:41:16
	 */
	@RequestMapping("/gopwd")
	public ModelAndView goPwd(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("system/user/basic/pwd");
		
		return view;
	}
	
	/**
	 * 
	 * @description
	 *  	用户修改密码
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月8日 下午3:54:22
	 */
	@RequestMapping("/alterpwd")
	@ResponseBody
	@Log(type = LogType.save, name = "用户修改密码", memo = "用户修改了密码")
	public AjaxJson alterPwd(String oldPassword, String newPassword, String reNewPassword, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isEmpty(oldPassword)) {
				j.setSuccess(false);
				j.setMsg("旧密码不能为空");
				return j;
			}
			
			if(StringUtils.isEmpty(newPassword)) {
				j.setSuccess(false);
				j.setMsg("新密码不能为空");
				return j;
			}
			
			
			if(StringUtils.isEmpty(reNewPassword)) {
				j.setSuccess(false);
				j.setMsg("确认新密码不能为空");
				return j;
			}
			
			if(!StringUtils.equals(newPassword, reNewPassword)) {
				j.setSuccess(false);
				j.setMsg("两次输入的密码不相同");
				return j;
			}
			
			String userId = ShiroUtils.getSessionUserId();
			SysUserEntity user = sysUserService.getById(userId);
			
			if(null == user) {
				j.setSuccess(false);
				j.setMsg("用户不存在，请联系管理员");
				return j;
			}
			//校验旧密码
			String tmpPwd = ShiroUtils.md5(oldPassword, user.getSalt());
			if(!StringUtils.equals(tmpPwd, user.getPassword())) {
				j.setSuccess(false);
				j.setMsg("旧密码输入错误");
				return j;
			}
			//密码赋值
			user.setSalt(ShiroUtils.getRandomSalt());
			user.setPassword(ShiroUtils.md5(newPassword, user.getSalt()));
			//保存
			sysUserService.saveOrUpdate(user);
			
			log.info("用户：" + user.getUserName() + "修改了个人密码");
		}catch(Exception e) {
			log.error("用户修改密码报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("修改密码错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 
	 * @description
	 *  	删除账户
	 * @params
	 * @return AjaxJson
	 * @author guyp
	 * @time 2020年2月8日 下午4:25:02
	 */
	@RequestMapping("/del")
	@ResponseBody
	@Log(type = LogType.del, name = "删除用户", memo = "删除了用户信息")
	public AjaxJson del(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			
			if(StringUtils.isEmpty(id)) {
				j.setSuccess(false);
				j.setMsg("请选择需要删除的用户");
				return j;
			}
			
			ActiveUser user = ShiroUtils.getSessionUser();
			if(StringUtils.equals(user.getId(), id)) {
				j.setSuccess(false);
				j.setMsg("不允许删除自身账户");
				return j;
			}
			
			//删除用户
			sysUserService.delete(id);
			
			log.info("用户：" + user.getUserName() + "删除了id为：" + id + "的用户");
		}catch(Exception e) {
			log.error("删除用户报错，错误信息：{}", e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除用户错误");
			e.printStackTrace();
		}
		
		return j;
	}
	
}
