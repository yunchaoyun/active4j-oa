package com.active4j.hr.common.constant;


/**
 * 全局常量类
 * @author teli_
 *
 */
public class GlobalConstant {
	
	/**
	 * 验证码存放Key
	 */
	public static final String SESSION_KEY_OF_RAND_CODE = "randCode"; // todo 要统一常量

	/**
	 * 默认创建人
	 */
	public static final String Default_Create_Name = "system";
	
	/**
	 * 错误提示 联系管理员
	 */
	public static String ERROR_MSG = "操作失败，请联系管理员";
	
	/**
	 * 用户
	 */
	public static final String SESSION_USER = "user";
	
	/**
	 * 没有权限进行此操作
	 */
	public static final String Err_Msg_No_Auth = "没有权限进行此操作";
	public static final String Err_Msg_All = "系统错误，请联系管理员";
	
	/**
	 * 文件上传 保存方式 常量
	 */
	public static final String FILE_UPLOADER_SAVE_FILE = "0"; // 保存文件系统
	public static final String FILE_UPLOADER_SAVE_DB = "1"; // 保存数据库
	
	/**
	 * 文件上传 类型 常量
	 */
	public static final String FILE_UPLOADER_TYPE_IMAGE = "0"; // 图片
	public static final String FILE_UPLOADER_TYPE_OTHER = "1"; // 其他
	
	/**
	 * 文件保存入库配置地址
	 */
	public static final String CONFIG_FILE_SAVE_DB_URL = "func/upload/viewImage?n=";
	 
	
	/**
	 * 系统消息 读取状态
	 */
	public static String SYS_MSG_NO_READ = "0"; //未读
	public static String SYS_MSG_READED = "1"; //已读
	
	/**
	 * 系统消息 发送人
	 */
	public static String SYS_MSG_SENDER = "系统消息";
	
	 /**
	  * 员工离职状态
	  */
	 public static String OAUSER_JOB_STATUS_LEAVE = "0"; // 离职
	 public static String OAUSER_JOB_STATUS_IN = "1"; // 在职
	 public static String OAUSER_JOB_STATUS_DEL = "2"; // 删除
	
	 /**
	  * 日报状态
	  */
	 public static String OAWORK_DAILY_STATUS_DRAFT = "0"; //草稿
	 public static String OAWORK_DAILY_STATUS_SUBMIT = "1"; //已提交
	 
	 /***
	  * OA 工作中心
	  */
	 public static final String OA_WORK_MEET_ROOM_STATUS_NORMAL = "1"; //正常
	 public static final String OA_WORK_MEET_ROOM_STATUS_STOP = "0"; //停用
	 
	 /**
	  * OA 工作中心   日程状态
	  */
	 public static final String OA_WORK_SHEDULE_STATUS_NO_A = "0"; //未安排
	 public static final String OA_WORK_SHEDULE_STATUS_A = "1"; //已安排
	 public static final String OA_WORK_SHEDULE_STATUS_FINISH = "2"; //已完成
	 public static final String OA_WORK_SHEDULE_STATUS_NO_FINISH = "3"; //未完成
	 
	 /**
	  * OA 工作中心   日程类型
	  */
	 public static final String OA_WORK_SHEDULE_TYPE_MEET = "0"; //会议
	 public static final String OA_WORK_SHEDULE_TYPE_TASK = "1"; //任务
	 public static final String OA_WORK_SHEDULE_TYPE_DEFINE = "2"; //自定义
	 
	 /***
	  * OA 工作中心  计划状态
	  */
	 public static final String OA_WORK_PLAN_STATUS_NEW = "0"; //新建
	 public static final String OA_WORK_PLAN_STATUS_DOING = "1"; //进行中
	 public static final String OA_WORK_PLAN_STATUS_FINISH = "2"; //完成
	 public static final String OA_WORK_PLAN_STATUS_NO = "3"; //未完成
	 public static final String OA_WORK_PLAN_STATUS_GIVEUP = "4"; //放弃
	 
	 /***
	  * OA 工作中心  任务状态
	  */
	 public static final String OA_WORK_TASK_STATUS_NEW = "0"; //新建
	 public static final String OA_WORK_TASK_STATUS_DOING = "1"; //进行中
	 public static final String OA_WORK_TASK_STATUS_FINISH = "2"; //完成
	 public static final String OA_WORK_TASK_STATUS_NO = "3"; //取消
	 public static final String OA_WORK_TASK_STATUS_GIVEUP = "4"; //放弃
	 public static final String OA_WORK_TASK_STATUS_EX = "5"; //超期
	 
	 /***
	  * OA 工作中心  目标状态
	  */
	 public static final String OA_WORK_TARGET_STATUS_NEW = "0"; //新建
	 public static final String OA_WORK_TARGET_STATUS_DOING = "1"; //进行中
	 public static final String OA_WORK_TARGET_STATUS_FINISH = "2"; //完成
	 public static final String OA_WORK_TARGET_STATUS_NO = "3"; //未完成
	 public static final String OA_WORK_TARGET_STATUS_GIVEUP = "4"; //放弃
	 
	 /***
	  * OA 工作中心  计划类型
	  */
	 public static final String OA_WORK_PLAN_TYPE_COMPANY = "0"; //公司计划
	 public static final String OA_WORK_PLAN_TYPE_DEPART = "1"; //部门计划
	 public static final String OA_WORK_PLAN_TYPE_PERSON = "2"; //个人计划
	 
	 /***
	  * OA 工作中心  目标类别
	  */
	 public static final String OA_WORK_TARGET_CATEGORY_YEAR = "0"; //年度目标
	 public static final String OA_WORK_TARGET_CATEGORY_SESSION = "1"; //季度目标
	 public static final String OA_WORK_TARGET_CATEGORY_MONTH = "2"; //月目标
	 public static final String OA_WORK_TARGET_CATEGORY_WEEK = "3"; //周目标
	 public static final String OA_WORK_TARGET_CATEGORY_DAY = "4"; //每日目标
	 
	 /**
	  * OA系统中 考勤规则设置
	  */
	 public static final String OA_WORKCAL_TYPE_WORKDAY = "0"; //工作日
	 public static final String OA_WORKCAL_TYPE_HOLIDAY = "1"; //法定假日
	 public static final String OA_WORKCAL_TYPE_SPECIAL = "2"; //公司假期
	 public static final String OA_WORKCAL_TYPE_WEEKEND = "3"; //双休如
	 public static final String OA_WORKCAL_TYPE_OFF = "4"; //调休
	 
	 /**
	  * 操作类型
	  */
	 public static final String OPT_TYPE_ADD = "0"; //增加
	 public static final String OPT_TYPE_SUB = "1"; //减少
	 
	 /**
	  * 员工加班类型
	  */
	 public static final String OA_OVER_TIME_TYPE_WORKDAY = "0"; //平时加班
	 public static final String OA_OVER_TIME_TYPE_WEEKENDAY = "1"; //双休日加班
	 public static final String OA_OVER_TIME_TYPE_HOLIDAY = "2"; //节假日加班
	 public static final String OA_OVER_TIME_TYPE_OFF = "3"; //调休使用
}
