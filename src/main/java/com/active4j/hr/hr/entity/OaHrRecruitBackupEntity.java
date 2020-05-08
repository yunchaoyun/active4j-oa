package com.active4j.hr.hr.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @title OaHrRecruitBackupEntity.java
 * @description 
		后备资源库实体
 * @time  2020年4月22日 下午5:04:27
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_recruit_backup")
@Getter
@Setter
public class OaHrRecruitBackupEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8508664741018539466L;

	/** 
	 * 姓名
	 */
	@TableField("NAME")
	@QueryField(queryColumn="NAME", condition=QueryCondition.eq)
	private String name;
	
	/**
	 * 性别
	 */
	@TableField("SEX")
	private String sex;
	
	/**
	 * 联系电话
	 */
	@TableField("TEL_NO")
	private String telNo;
	
	/**
	 * 电子邮件
	 */
	@TableField("EMAIL")
	private String email;
	
	/** 
	 * 应聘职位
	 */
	@TableField("CV_JOB")
	@QueryField(queryColumn="CV_JOB", condition=QueryCondition.eq)
	private String cvJob;
	
	/** 
	 * 招聘渠道(数据字典)
	 */
	@TableField("CANAL_TYPE")
	private String canalType;
	
	/** 
	 * 工作年限(数据字典)
	 */
	@TableField("JOB_LENGTH")
	private String jobLength;
	
	/** 
	 * 期望工作城市
	 */
	@TableField("HOPE_CITY")
	private String hopeCity;
	
	/** 
	 * 现居住城市
	 */
	@TableField("LIVE_CITY")
	private String liveCity;
	
	/** 
	 * 期望薪水(税前)
	 */
	@TableField("HOPE_WAGE")
	private BigDecimal hopeWage;
	
	/** 
	 * 到岗时间(数据字典)
	 */
	@TableField("REPORT_TIME")
	private String reportTime;
	
	/** 
	 * 期望工作性质
	 */
	@TableField("JOB_NATURE")
	private String jobNature;
	
	/** 
	 * 出生日期
	 */
	@TableField("BIRTH_DATE")
	private Date birthDate;
	
	/** 
	 * 年龄
	 */
	@TableField("AGE")
	private String age;
	
	/** 
	 * 微信
	 */
	@TableField("WEIXIN")
	private String weixin;
	
	/** 
	 * 国籍
	 */
	@TableField("NATION")
	private String nation;
	
	/** 
	 * 血型(数据字典)
	 */
	@TableField("BLOOD_TYPE")
	private String bloodType;
	
	/** 
	 * 毕业学校
	 */
	@TableField("GRAD_SCHOOL")
	private String gradSchool;
	
	/** 
	 * 所学专业
	 */
	@TableField("STUDY_MAJOR")
	private String studyMajor;
	
	/** 
	 * 学历(数据字典)
	 */
	@TableField("EDUCATION_BACK")
	@QueryField(queryColumn="EDUCATION_BACK", condition=QueryCondition.eq)
	private String educationBack;
	
	/** 
	 * 学位
	 */
	@TableField("DEGREE")
	private String degree;
	
	/** 
	 * 外国语种1
	 */
	@TableField("FRN_LANGUAGE_1")
	private String frnLanguage1;
	
	/** 
	 * 外语水平1
	 */
	@TableField("FRN_LEVEL_1")
	private String frnLevel1;
	
	/** 
	 * 外国语种2
	 */
	@TableField("FRN_LANGUAGE_2")
	private String frnLanguage2;
	
	/** 
	 * 外语水平2
	 */
	@TableField("FRN_LEVEL_2")
	private String frnLevel2;
	
	/** 
	 * 特长
	 */
	@TableField("SPECIALITY")
	private String speciality;
	
	/** 
	 * 职业技能
	 */
	@TableField("JOB_SKILL")
	private String jobSkill;
	
	/** 
	 * 工作经验
	 */
	@TableField("EXPERIENCE")
	private String experience;
	
	/** 
	 * 证书附件
	 */
	@TableField("CERTI_ATTACHMENT")
	private String certiAttachment;
	
	/** 
	 * 简历附件
	 */
	@TableField("CV_ATTACHMENT")
	private String cvAttachment;
	
	/**
	 * 简历状态(0：未处理， 1：面试中 ， 2：被淘汰， 3：被录用， 4：已发offer, 5:已入职)
	 */
	@TableField("STATUS")
	@QueryField(queryColumn="STATUS", condition=QueryCondition.eq)
	private String status;
	
	/** 
	 * 加入后备资源库时间
	 */
	@TableField("ADD_TIME")
	private Date addTime;
	
}

