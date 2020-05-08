package com.active4j.hr.hr.entity;

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
 * @title OaHrUserEntity.java
 * @description 
		人力资源-人事信息实体
 * @time  2020年4月10日 下午1:18:04
 * @author guyp
 * @version 1.0
 */
@TableName("oa_hr_user")
@Getter
@Setter
public class OaHrUserEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7297141312228534560L;

	/**
	 * 真实姓名
	 */
	@TableField("REAL_NAME")
	@QueryField(queryColumn="REAL_NAME", condition=QueryCondition.eq)
	private String realName;
	
	/**
	 * 手机号
	 */
	@TableField("PHONE_NO")
	@QueryField(queryColumn="PHONE_NO", condition=QueryCondition.eq)
	private String phoneNo;
	
	/**
	 * 办公电话
	 */
	@TableField("OFFICE_PHONE")
	private String officePhone;
	
	/**
	 * 邮箱
	 */
	@TableField("EMAIL")
	private String email;
	
	/**
	 * 上级姓名
	 */
	@TableField("CHIEF_NAME")
	private String chiefName;
	
	/**
	 * 所属部门
	 */
	@TableField("DEPT_ID")
 	private String deptId;
	
	/**
	 * 所属岗位
	 */
	@TableField("JOB_ID")
 	private String jobId;
	
	/**
	 * 英文名
	 */
	@TableField("ENGLISH_NAME")
 	private String englishName;
	
	/**
	 * 微信号
	 */
	@TableField("WEIXIN")
 	private String weixin;
	
	/**
	 * QQ
	 */
	@TableField("QQ")
 	private String qq;
	
	/**
	 * 个人照片
	 */
	@TableField("SELF_IMAGE")
 	private String selfImage;
	
	/**
	 * 身份证号
	 */
	@TableField("ID_CARD")
 	private String idCard;
	
	/**
	 * 身份证照片
	 */
	@TableField("ID_CARD_IMAGE")
 	private String idCardImage;
	
	/**
	 * 出生日期
	 */
	@TableField("BIRTH_DATE")
 	private Date birthDate;
	
	/**
	 * 性别
	 */
	@TableField("SEX")
 	private String sex;
	
	/**
	 * 国家
	 */
	@TableField("COUNTRY")
 	private String country;
	
	/**
	 * 民族
	 */
	@TableField("NATION")
 	private String nation;
	
	/**
	 * 血型
	 */
	@TableField("BLOOD_TYPE")
 	private String bloodType;
	
	/**
	 * 婚姻状况
	 */
	@TableField("CAN_MARRIAGE")
 	private String canMarriage;
	
	/**
	 * 户口类型
	 */
	@TableField("ACCOUNT_TYPE")
 	private String accountType;
	
	/**
	 * 政治面貌
	 */
	@TableField("POLITICAL")
	private String political;
	
	/**
	 * 毕业时间
	 */
	@TableField("GRADUATE_DATE")
	private Date graduateDate;
	
	/**
	 * 户口所在地
	 */
	@TableField("RESIDENCE")
	private String residence;
	
	/**
	 * 最高学历
	 */
	@TableField("HIGNEST_EDUCATION")
	private String highestEducation;
	
	/**
	 * 毕业学校
	 */
	@TableField("GRADUATE_SCHOOL")
	private String graduateSchool;
	
	/**
	 * 所学专业
	 */
	@TableField("MAJOR")
	private String major;
	
	/**
	 * 培养方式
	 */
	@TableField("DEVELOP_TYPE")
	private String developType;
	
	/**
	 * 第一外语
	 */
	@TableField("FST_FOREIGN_LANG")
	private String fstForeignLang;
	
	/**
	 * 第二外语
	 */
	@TableField("SEC_FOREIGN_LANG")
	private String secForeignLang;
	
	/**
	 * 第三外语
	 */
	@TableField("THD_FOREIGN_LANG")
	private String thdForeignLang;
	
	/**
	 * 其他语种
	 */
	@TableField("OTHER_LANG")
	private String otherLang;
	
	/**
	 * 职业证书
	 */
	@TableField("CERTIFICATE")
	private String certificate;
	
	/**
	 * 职业证书类型
	 */
	@TableField("CERTIFICATE_TYPE")
	private String certificateType;
	
	/**
	 * 职业证书图片
	 */
	@TableField("CERTIFICATE_IMAGE")
	private String certificateImage;
	
	/**
	 * 首次工作时间
	 */
	@TableField("FST_WORK_DATE")
	private Date fstWorkDate;
	
	/**
	 * 工龄
	 */
	@TableField("WORK_AGE")
	private String workAge;
	
	/**
	 * 社保断档
	 */
	@TableField("SOCAIL_SHORT")
	private String socailShort;
	
	/**
	 * 上家雇主
	 */
	@TableField("LAST_EMPLOY")
	private String lastEmploy;
	
	/**
	 * 合同类型
	 */
	@TableField("CONTRACT_TYPE")
	private String contractType;
	
	/**
	 * 合同期限
	 */
	@TableField("CONTRACT_TERM")
	private String contractTerm;
	
	/**
	 * 合同编号
	 */
	@TableField("CONTRACT_NO")
	private String contractNo;
	
	/**
	 * 合同起始日期
	 */
	@TableField("CONTRACT_START_DATE")
	private String contractStartDate;
	
	/**
	 * 合同结束日期
	 */
	@TableField("CONTRACT_END_DATE")
	private String contractEndDate;
	
	/**
	 * 工作状态（是否离职）
	 */
	@TableField("WORK_STATUS")
	@QueryField(queryColumn="WORK_STATUS", condition=QueryCondition.eq)
	private String workStatus;
}

