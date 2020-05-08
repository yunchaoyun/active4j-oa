package com.active4j.hr.core.shiro;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.active4j.hr.system.model.ActiveUser;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;


/**
 * shiro工具类
 * @author teli_
 *
 */
public class ShiroUtils {
	
	 /**
     * 加盐参数
     */
    public final static String hashAlgorithmName = "MD5";

    /**
     * 循环次数
     */
    public final static int hashIterations = 2;
    
    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource  密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        return new SimpleHash(hashAlgorithmName, credentials, ByteSource.Util.bytes(saltSource), hashIterations).toString();
    }
    
    /**
     * 生成随机数  盐
     * @return
     */
    public static String getRandomSalt() {
    	String index = RandomStringUtils.random(1, "23456789");
    	
    	String str = RandomStringUtils.random(Integer.valueOf(index), "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    	
    	return str;
    	
    }
	
	 /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    
    public static boolean hasLogin() {
    	return null != getSubject() && getSubject().isAuthenticated() && null != getSubject().getPrincipals();
    }
    
    /**
     * 是否具有某种角色
     * @param roleName
     * @return
     */
    public static boolean hasRole(String roleCode) {
        return getSubject() != null && StringUtils.isNotEmpty(roleCode) && getSubject().hasRole(roleCode);
    }
    
    /**
     * 是否具有权限
     * @param permission
     * @return
     */
    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null
                && permission.length() > 0
                && getSubject().isPermitted(permission);
    }
	
	/**
     * 从shiro获取session
     */
    public static Session getSession() {
        return getSubject().getSession();
    }
    
    /**
     * 从shiro session中取值
     * @param key
     * @return
     */
    public static String getSessionValue(String key) {
    	return (String)getSession().getAttribute(key);
    }
    
    /**
     * 从shiro session中赋值
     * @param key
     * @param value
     */
    public static void setSessionValue(String key, String value) {
    	getSession().setAttribute(key, value);;
    }
    
    public static void logout() {
    	getSubject().logout();
    }
    
    /**
     * 		取得当前登录用户信息
     * @return
     */
    public static ActiveUser getSessionUser() {
    	return (ActiveUser) getSubject().getPrincipals().getPrimaryPrincipal();
    }

    /**
     	* 取得当前登录用户名
     * @return
     */
    public static String getSessionUserName() {
    	ActiveUser user = getSessionUser();
    	return user.getUserName();
    }
    
    public static boolean isAdmin() {
    	ActiveUser user = getSessionUser();
    	return user.isAdmin();
    }
    
    /**
 	 * 取得当前登录用户ID
	 * @return
	 */
	public static String getSessionUserId() {
		ActiveUser user = getSessionUser();
		return user.getId();
	}
}
