package com.active4j.hr.activiti.util;


/**
 * @title WorkflowConstant.java
 * @description 
		  工作流常量
 * @time  2020年4月23日 下午8:46:03
 * @author 麻木神
 * @version 1.0
*/
public class WorkflowConstant {
	
	//任务节点类型
	public static final String Task_Category_approval = "0"; //审批
	public static final String Task_Category_handle = "1"; //处理
	public static final String Task_Category_admin = "4"; //审批人未知 转给管理员指定

	//管理员
	public static final String Str_Admin = "admin";
	
	//部门经理称呼
	public static final String Str_Dept_Manager = "经理";
	
	//任务节点类型
	public static final String STR_TASK_NAME_APPROVE = "审批";
	public static final String STR_TASK_NAME_HANDLE = "办理";
}
