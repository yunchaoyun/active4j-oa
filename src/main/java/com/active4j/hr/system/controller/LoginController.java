package com.active4j.hr.system.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.annotation.Log;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.model.LogType;
import com.active4j.hr.core.shiro.ShiroUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @title LoginController.java
 * @time  2020年1月15日 下午2:06:06
 * @author 麻木神
 * @version 1.0
*/
@Controller
@Slf4j
public class LoginController {
	
	
	/**
	 * 
	 * @description
	 *  	跳转到项目首页
	 * @return String
	 * @author 麻木神
	 * @time 2020年1月15日 下午10:25:13
	 */
	@RequestMapping(value = "/console", method = RequestMethod.GET)
	public String console(Model model) {
		return "main/console";
	}
	
	/**
	 * 
	 * @description
	 *  	跳转到项目首页
	 * @return String
	 * @author 麻木神
	 * @time 2020年1月15日 下午10:25:13
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		return "main/main";
	}
	
	/**
	 * 
	 * @description
	 *  	跳转到项目首页
	 * @return String
	 * @author 麻木神
	 * @time 2020年1月15日 下午10:25:13
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index2(Model model) {
		return "main/main";
	}
	
	/**
	 * 
	 * @description
	 *  	跳转到403
	 * @return String
	 * @author 麻木神
	 * @time 2020年1月15日 下午10:25:13
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String auth(Model model) {
		return "common/403";
	}
	

	/**
	 * 
	 * @description
	 *  	跳转登录页面
	 * @return String
	 * @author 麻木神
	 * @time 2020年1月15日 下午2:09:06
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "login/login";
	}
	
	/**
	 * 
	 * @description
	 *  	登出
	 * @params
	 * @return String
	 * @author guyp
	 * @time 2020年2月4日 上午9:09:37
	 */
	@RequestMapping("/logout")
	@Log(type = LogType.logout, name = "用户登出", memo = "用户已登出")
	public String logout() {
		ShiroUtils.logout();
		return "login/login";
	}
	
	/**
	 * 登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	@Log(type = LogType.login, name = "用户登录", memo = "用户成功登录")
	public AjaxJson loginAction(String userName, String password, String randCode) {
		AjaxJson j = new AjaxJson();
		
		try {
			// 后端校验
			if (StringUtils.isEmpty(userName)) {
				j.setSuccess(false);
				j.setMsg("用户名不能为空");
				return j;
			}

			if (StringUtils.isEmpty(password)) {
				j.setSuccess(false);
				j.setMsg("密码不能为空");
				return j;
			}

			if (StringUtils.isEmpty(randCode)) {
				j.setSuccess(false);
				j.setMsg("验证码不能为空");
				return j;
			}

			// 验证码的校验
			if (!StringUtils.equalsIgnoreCase(randCode, ShiroUtils.getSessionValue(GlobalConstant.SESSION_KEY_OF_RAND_CODE))) {
				j.setSuccess(false);
				j.setMsg("验证码填写错误");
				return j;
			}

			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			ShiroUtils.getSubject().login(token);

		} catch (IncorrectCredentialsException e) {
			j.setSuccess(false);
			j.setMsg("用户名或密码填写错误");
		} catch (UnknownAccountException e) {
			j.setSuccess(false);
			j.setMsg("用户名或密码填写错误");
		} catch (LockedAccountException e) {
			j.setSuccess(false);
			j.setMsg("当前账户已锁定，请联系管理员");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("系统错误，请联系管理员");
			log.error("系统错误，错误信息:{}", e);
		}

		return j;
		
	}
}
