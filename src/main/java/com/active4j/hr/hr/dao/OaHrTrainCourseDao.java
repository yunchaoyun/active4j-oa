package com.active4j.hr.hr.dao;

import org.apache.ibatis.annotations.Param;

import com.active4j.hr.hr.domain.OaHrTrainCourseModel;
import com.active4j.hr.hr.entity.OaHrTrainCourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OaHrTrainCourseDao extends BaseMapper<OaHrTrainCourseEntity>{

	/**
	 * 
	 * @description
	 *  	培训课程查询
	 * @return IPage<OaHrTrainCourseModel>
	 * @author guyp
	 * @time 2020年4月23日 下午3:33:41
	 */
	public IPage<OaHrTrainCourseModel> selectPageCourse(Page<OaHrTrainCourseModel> page, @Param("cateId") String cateId);
}
