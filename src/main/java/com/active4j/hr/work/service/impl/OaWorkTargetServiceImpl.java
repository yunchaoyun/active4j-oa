package com.active4j.hr.work.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.work.dao.OaWorkTargetDao;
import com.active4j.hr.work.entity.OaWorkTargetEntity;
import com.active4j.hr.work.service.OaWorkTargetService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @description 
		  工作中心 目标管理
 * @time  2020年4月3日 下午2:24:38
 * @author 麻木神
 * @version 1.0
 */
@Service("oaWorkTargetService")
@Transactional
public class OaWorkTargetServiceImpl extends ServiceImpl<OaWorkTargetDao, OaWorkTargetEntity> implements OaWorkTargetService {
	

	public List<OaWorkTargetEntity> findOaWorkTarget(String userId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper){
		queryWrapper.eq("USER_ID", userId);
		queryWrapper.eq("TYPE", type);
		queryWrapper.orderByDesc("CREATE_DATE");
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetCreate(String userId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper){
		queryWrapper.eq("CREATE_USER_ID", userId);
		queryWrapper.eq("TYPE", type);
		queryWrapper.orderByDesc("CREATE_DATE");
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetPart(String userId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper) {
		queryWrapper.like("PARTICIPANTIDS", userId);
		queryWrapper.eq("TYPE", type);
		queryWrapper.orderByDesc("CREATE_DATE");
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetDepart(String departId, String type, QueryWrapper<OaWorkTargetEntity> queryWrapper) {
		if(StringUtils.isNotEmpty(departId)) {
			queryWrapper.eq("DEPART_ID", departId);
			queryWrapper.eq("TYPE", type);
			queryWrapper.orderByDesc("CREATE_DATE");
			return this.list(queryWrapper);
		}else {
			return new ArrayList<OaWorkTargetEntity>();
		}
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetComp(String type, QueryWrapper<OaWorkTargetEntity> queryWrapper){
		queryWrapper.eq("TYPE", type);
		queryWrapper.orderByDesc("CREATE_DATE");
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetChart(String type, String id, String flag) {
		QueryWrapper<OaWorkTargetEntity> queryWrapper = new QueryWrapper<OaWorkTargetEntity>();
		queryWrapper.eq("TYPE", type);
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		if(StringUtils.equals("0", flag)) {
			queryWrapper.eq("USER_ID", id);
		}else if(StringUtils.equals("1", flag)) {
			queryWrapper.eq("CREATE_USER_ID", id);
		}else if(StringUtils.equals("2", flag)) {
			queryWrapper.like("PARTICIPANTIDS", id);
		}else if(StringUtils.equals("3", flag)) {
			queryWrapper.eq("DEPART_ID", id);
		}else if(StringUtils.equals("4", flag)) {
			
		}
		queryWrapper.isNull("PARENT_TARGET_ID");
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findChildrenTarget(OaWorkTargetEntity oaWorkTargetEntity){
		QueryWrapper<OaWorkTargetEntity> queryWrapper = new QueryWrapper<OaWorkTargetEntity>();
		queryWrapper.eq("PARENT_TARGET_ID", oaWorkTargetEntity.getId());
		
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetStatus(String type, String userId){
		QueryWrapper<OaWorkTargetEntity> queryWrapper = new QueryWrapper<OaWorkTargetEntity>();
		queryWrapper.eq("USER_ID", userId);
		queryWrapper.eq("TYPE", type);
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetStatusDepart(String type, String departId){
		QueryWrapper<OaWorkTargetEntity> queryWrapper = new QueryWrapper<OaWorkTargetEntity>();
		queryWrapper.eq("DEPART_ID", departId);
		queryWrapper.eq("TYPE", type);
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		return this.list(queryWrapper);
	}
	
	public List<OaWorkTargetEntity> findOaWorkTargetStatus(String type){
		QueryWrapper<OaWorkTargetEntity> queryWrapper = new QueryWrapper<OaWorkTargetEntity>();
		queryWrapper.eq("TYPE", type);
		queryWrapper.in("STATUS", new Object[] {"0", "1"});
		return this.list(queryWrapper);
	}
	
	public void saveOrUpdate(OaWorkTargetEntity oaWorkTargetEntity, String status){
		this.saveOrUpdate(oaWorkTargetEntity);
		
		saveStatus(oaWorkTargetEntity, status);
	}

	private void saveStatus(OaWorkTargetEntity oaWorkTargetEntity, String status) {
		//子目标也要改变状态
		List<OaWorkTargetEntity> lstChildren = findChildrenTarget(oaWorkTargetEntity);
		if(null != lstChildren && lstChildren.size() > 0) {
			for(OaWorkTargetEntity target : lstChildren) {
				target.setStatus(status);
				this.saveOrUpdate(target);
				
				saveStatus(target, status);
			}
		}
	}
}
