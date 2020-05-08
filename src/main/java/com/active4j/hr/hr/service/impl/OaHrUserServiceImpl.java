package com.active4j.hr.hr.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.hr.dao.OaHrUserDao;
import com.active4j.hr.hr.entity.OaHrUserEntity;
import com.active4j.hr.hr.entity.OaHrUserPactEntity;
import com.active4j.hr.hr.entity.OaHrUserPayEntity;
import com.active4j.hr.hr.entity.OaHrUserRewdPunishEntity;
import com.active4j.hr.hr.entity.OaHrUserStudyEntity;
import com.active4j.hr.hr.entity.OaHrUserWorkEntity;
import com.active4j.hr.hr.service.OaHrUserPactService;
import com.active4j.hr.hr.service.OaHrUserPayService;
import com.active4j.hr.hr.service.OaHrUserRewdPunishService;
import com.active4j.hr.hr.service.OaHrUserService;
import com.active4j.hr.hr.service.OaHrUserStudyService;
import com.active4j.hr.hr.service.OaHrUserWorkService;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrUserServiceImpl.java
 * @description 
		人力资源-人事信息
 * @time  2020年4月10日 上午10:42:36
 * @author guyp
 * @version 1.0
 */
@Service("oaHrUserService")
@Transactional
public class OaHrUserServiceImpl extends ServiceImpl<OaHrUserDao, OaHrUserEntity> implements OaHrUserService {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private OaHrUserPactService oaHrUserPactService;
	
	@Autowired
	private OaHrUserPayService oaHrUserPayService;
	
	@Autowired
	private OaHrUserRewdPunishService oaHrUserRewdPunishService;
	
	@Autowired
	private OaHrUserStudyService oaHrUserStudyService;
	
	@Autowired
	private OaHrUserWorkService oaHrUserWorkService;
	
	/**
	 * 
	 * @description
	 *  	根据部门获取人事用户
	 * @return List<OaHrUserEntity>
	 * @author guyp
	 * @time 2020年4月15日 下午5:48:59
	 */
	public List<OaHrUserEntity> findOaUsersByDept(SysDeptEntity sysDeptEntity) {
		QueryWrapper<OaHrUserEntity> queryWrapper = new QueryWrapper<OaHrUserEntity>();
		queryWrapper.eq("DEPT_ID", sysDeptEntity.getId());
		return this.list(queryWrapper);
	}
	
	/**
	 * 
	 * @description
	 *  	企业人事信息树形选择
	 * @return String
	 * @author guyp
	 * @time 2020年4月15日 下午6:00:16
	 */
	public String getCompanyOfOaUser(Set<String> lstOaUserIds) {
		//先查询顶级部门
		List<SysDeptEntity> lstDeparts = sysDeptService.getParentDepts();
		
		//拼接字符串
		StringBuffer sb = new StringBuffer();
		sb = sb.append("[");
		departOaUserContact(lstDeparts, sb, lstOaUserIds);
		
		sb = sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @description
	 *  	递归查询部门下的人事用户
	 * @return void
	 * @author guyp
	 * @time 2020年4月15日 下午6:04:28
	 */
	private void departOaUserContact(List<SysDeptEntity> lstDeparts, StringBuffer sb, Set<String> lstUserIds) {
		if(null != lstDeparts && lstDeparts.size() > 0) {
			for(int i = 0; i < lstDeparts.size(); i++) {
				SysDeptEntity depart = lstDeparts.get(i);
				//查询所有组织架构
				sb = sb.append("{").append("icon:").append("\"fa fa-sitemap\"").append(",").append("type:").append("\"C\"").append(",").append("text:").append("\"").append(depart.getName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(depart.getId()).append("\"");
				List<SysDeptEntity> lstTmp = sysDeptService.getChildDeptsByDeptId(depart.getId());
				if(null != lstTmp && lstTmp.size() > 0) {
					//根据type过滤数据
					if(lstTmp.size() > 0) {
						sb = sb.append(", nodes: [");
						departOaUserContact(lstTmp, sb, lstUserIds);
						List<OaHrUserEntity> lstUsers = findOaUsersByDept(depart);
						if(null != lstUsers && lstUsers.size() > 0) {
							sb = sb.append(",");
							for(int m = 0; m < lstUsers.size(); m++) {
								OaHrUserEntity user = lstUsers.get(m);
								if(m == lstUsers.size() - 1) {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}
									
								}else {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}
									
								}
							}
						}
						sb.append("]");
					}else {
						List<OaHrUserEntity> lstUsers = findOaUsersByDept(depart);
						if(null != lstUsers && lstUsers.size() > 0) {
							sb = sb.append(", nodes:[");
							for(int m = 0; m < lstUsers.size(); m++) {
								OaHrUserEntity user = lstUsers.get(m);
								if(m == lstUsers.size() - 1) {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
									}
									
								}else {
									if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}else {
										sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
									}
									
								}
							}
							sb.append("]");
						}
					}
				}else {
					List<OaHrUserEntity> lstUsers = findOaUsersByDept(depart);
					if(null != lstUsers && lstUsers.size() > 0) {
						sb = sb.append(", nodes:[");
						for(int m = 0; m < lstUsers.size(); m++) {
							OaHrUserEntity user = lstUsers.get(m);
							if(m == lstUsers.size() - 1) {
								if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
								}else {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("}");
								}
								
							}else {
								if(null != lstUserIds && lstUserIds.size() > 0 && lstUserIds.contains(user.getId())) {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:true").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
								}else {
									sb = sb.append("{").append("icon:").append("\"fa fa-user\"").append(",").append("type:").append("\"U\"").append(",").append("text:").append("\"").append(user.getRealName()).append("\",").append("state:{").append("checked:false").append("},").append("id:").append("\"").append(user.getId()).append("\"").append("},");
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
	 * 
	 * @description
	 *  	删除人事信息
	 * @return void
	 * @author guyp
	 * @time 2020年4月16日 下午5:54:51
	 */
	public void delLeave(String oaUserId) {
		//删除合同
		QueryWrapper<OaHrUserPactEntity> queryPact = new QueryWrapper<OaHrUserPactEntity>();
		queryPact.eq("USER_ID", oaUserId);
		oaHrUserPactService.remove(queryPact);
		
		//删除成本
		QueryWrapper<OaHrUserPayEntity> queryPay = new QueryWrapper<OaHrUserPayEntity>();
		queryPay.eq("USER_ID", oaUserId);
		oaHrUserPayService.remove(queryPay);
		
		//删除奖惩记录
		QueryWrapper<OaHrUserRewdPunishEntity> queryRewdPunish = new QueryWrapper<OaHrUserRewdPunishEntity>();
		queryRewdPunish.eq("USER_ID", oaUserId);
		oaHrUserRewdPunishService.remove(queryRewdPunish);
		
		//删除教育
		QueryWrapper<OaHrUserStudyEntity> queryStudy = new QueryWrapper<OaHrUserStudyEntity>();
		queryStudy.eq("USER_ID", oaUserId);
		oaHrUserStudyService.remove(queryStudy);
		
		//删除工作
		QueryWrapper<OaHrUserWorkEntity> queryWork = new QueryWrapper<OaHrUserWorkEntity>();
		queryWork.eq("USER_ID", oaUserId);
		oaHrUserWorkService.remove(queryWork);
		
		//删除人事信息
		this.removeById(oaUserId);
	}
}
