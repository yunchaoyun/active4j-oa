package com.active4j.hr.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.system.entity.SysFunctionEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.SysUserModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysUserDao extends BaseMapper<SysUserEntity>{
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取用户下属ID
	 * @return List<String>
	 * @author 麻木神
	 * @time 2020年4月3日 下午4:35:50
	 */
	public List<String> getUnderUserIds(List<String> roleIds);
	

	/**
	 * 
	 * @description
	 *  	根据用户ID 获取用户所有菜单
	 * @params
	 *      userId 用户ID
	 * @return List<SysMenuEntity>
	 * @author guyp
	 * @time 2020年1月3日 下午1:18:08
	 */
	public List<SysFunctionEntity> findMenuByUserId(@Param("userId") String userId);
	
	
	/**
	 * 
	 * @description
	 *  	根据用户ID 获取用户所有按钮
	 * @params
	 *      userId 用户ID
	 * @return List<SysMenuEntity>
	 * @author guyp
	 * @time 2020年1月3日 下午1:18:08
	 */
	public List<String> findOperationByUserId(@Param("userId") String userId);
	
	
	/**
	 * 
	 * @description
	 *  	根据用户id查询角色列表
	 * @params
	 *      userId 用户id
	 * @return List<SysRoleEntity>
	 * @author guyp
	 * @time 2020年1月16日 下午2:19:15
	 */
	public List<SysRoleEntity> findRolesByUserId(@Param("userId") String userId);
	
	/**
	 * 
	 * @description
	 *  	根据用户id查询用户个人信息
	 * @params
	 * @return SysUserModel
	 * @author guyp
	 * @time 2020年2月8日 下午12:38:09
	 */
	public SysUserModel findInfoByUserId(@Param("userId") String userId);
}
