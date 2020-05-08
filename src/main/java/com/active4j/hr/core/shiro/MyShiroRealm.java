package com.active4j.hr.core.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysUserService;

public class MyShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		ActiveUser user = (ActiveUser) principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(user.getPermissions());
		info.addRoles(user.getRoles());
		return info;
	}

	/**
	 * 认证信息 登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// 根据用户名查询用户
		SysUserService userService = ApplicationContextUtil.getContext().getBean(SysUserService.class);
		
		// 用户名
		String userName = (String) authcToken.getPrincipal();
		
		// 查询用户
		SysUserEntity sysUser = userService.getUserByUseName(userName);
		if (null == sysUser) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		if (StringUtils.equals(sysUser.getStatus(), "0")) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		
		ActiveUser user = userService.getActiveUserByUser(sysUser);
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), getName());

		ShiroUtils.getSession().setAttribute(GlobalConstant.SESSION_USER, user);
		
		return info;

	}

}
