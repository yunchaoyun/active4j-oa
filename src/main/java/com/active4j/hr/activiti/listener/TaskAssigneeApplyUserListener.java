package com.active4j.hr.activiti.listener;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;

import com.active4j.hr.activiti.util.WorkflowConstant;
import com.active4j.hr.core.beanutil.ApplicationContextUtil;

/**
 * @title TaskAssigneeApplyUserListener.java
 * @description 申请人
 * @time 2020年4月23日 下午8:39:25
 * @author 麻木神
 * @version 1.0
 */
public class TaskAssigneeApplyUserListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4872950173178370389L;

	
	/**
	 * delegateTask.setAssignee(userName); 这种方式设置审批人，无法复制历史任务审批人字段
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		TaskService taskService = ApplicationContextUtil.getContext().getBean(TaskService.class);

		String applyName = (String) delegateTask.getVariable("applyName");

		// 如果申请人为空，默认提交给管理员
		if (StringUtils.isEmpty(applyName)) {
			taskService.setAssignee(delegateTask.getId(), WorkflowConstant.Str_Admin);
			return;
		}
		taskService.setAssignee(delegateTask.getId(), applyName);
	}

}
