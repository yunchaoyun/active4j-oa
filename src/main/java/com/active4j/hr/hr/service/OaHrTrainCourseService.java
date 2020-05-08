package com.active4j.hr.hr.service;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.hr.domain.OaHrTrainCourseModel;
import com.active4j.hr.hr.entity.OaHrTrainCourseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OaHrTrainCourseService extends IService<OaHrTrainCourseEntity> {
	
	/**
	 * 
	 * @description
	 *  	培训课程查询
	 * @return IPage<OaHrTrainCourseModel>
	 * @author guyp
	 * @time 2020年4月23日 下午3:31:25
	 */
	public IPage<OaHrTrainCourseModel> selectPageCourse(DataGrid dataGrid, @Param("cateId") String cateId);
}
