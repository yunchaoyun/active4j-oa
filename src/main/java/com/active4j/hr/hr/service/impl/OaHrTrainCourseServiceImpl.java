package com.active4j.hr.hr.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.dao.OaHrTrainCourseDao;
import com.active4j.hr.hr.domain.OaHrTrainCourseModel;
import com.active4j.hr.hr.entity.OaHrTrainCourseEntity;
import com.active4j.hr.hr.service.OaHrTrainCourseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 
 * @title OaHrTrainCourseServiceImpl.java
 * @description 
		课程管理
 * @time  2020年4月23日 上午10:17:15
 * @author guyp
 * @version 1.0
 */
@Service("oaHrTrainCourseService")
@Transactional
public class OaHrTrainCourseServiceImpl extends ServiceImpl<OaHrTrainCourseDao, OaHrTrainCourseEntity> implements OaHrTrainCourseService {

	/**
	 * 
	 * @description
	 *  	培训课程查询
	 * @return IPage<OaHrTrainCourseModel>
	 * @author guyp
	 * @time 2020年4月23日 下午3:31:25
	 */
	public IPage<OaHrTrainCourseModel> selectPageCourse(DataGrid dataGrid, @Param("cateId") String cateId) {
		Page<OaHrTrainCourseModel> page = new Page<OaHrTrainCourseModel>();
		page.setSize(dataGrid.getRows());
		page.setCurrent(dataGrid.getPage());
		return this.baseMapper.selectPageCourse(page, cateId);
	}

}
