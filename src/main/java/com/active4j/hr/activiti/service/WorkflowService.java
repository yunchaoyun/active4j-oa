package com.active4j.hr.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @title WorkflowService.java
 * @time  2020年4月17日 下午3:08:44
 * @author 麻木神
 * @version 1.0
*/
public interface WorkflowService {

	/**
	 * 查询流程部署列表
	 * 
	 * @param dataGrid
	 * @return
	 */
	public IPage<Deployment> findDeployList(DataGrid dataGrid);
	
	/**
	 * 根据部署ID 删除部署
	 * @param id
	 * @throws Exception
	 */
	public void deleteDeploy(String id) throws Exception;
	
	/**
	 * 部署流程
	 * 
	 * @param name
	 *            流程名称
	 * @param category
	 *            流程类别
	 * @param file
	 *            部署文件
	 */
	public void saveNewDeploy(String name, InputStream file) throws Exception;
	
	
	/**
	 * 根据流程定义的名称查询流程列表 带分页
	 * 
	 * @param name
	 * @param dataGrid
	 * @return
	 */
	public IPage<ProcessDefinition> findProcessListByName(String name, Boolean isLastestVersion, DataGrid dataGrid);
	
	/**
	 * 根据流程定义ID，显示流程图
	 * @param id
	 * @return
	 */
	public InputStream findWorkflowImage(String id);
	
	/**
	 * 根据流程定义ID 删除
	 * @param id
	 * @throws Exception
	 */
	public void deleteDefine(String id) throws Exception;
	
	/**
	 * 根据流程定义ID 删除
	 * @param id
	 * @throws Exception
	 */
	public void deleteAll(String id);
	
	/**
	 * 查询系统中所有流程定义，最新版本
	 */
	public List<ProcessDefinition> findProcessDefineList();
	

	/**
	 * 根据流程定义key，查询最新流程定义
	 * @param key
	 * @return
	 */
	public ProcessDefinition findNewestProcessDefine(String key);
	
	/**
	 * 根据流程定义的ID，启动流程，并赋值参数, 并关联业务ID
	 * @param processDefinitionKey  流程定义的key key的话能保证启动最新版本
	 * @param map  参数变量
	 * @param businessKey 业务ID
	 * @param flag 存在申请人节点
	 * @param applyName 申请人
	 */
	public void startProcessInstanceByKey(String processDefinitionKey, String businessKey, boolean flag, String applyName, Map<String, Object> map);
	
	/**
	 * 根据流程定义的ID，启动流程，并赋值参数, 并关联业务ID
	 * @param processDefinitionKey  流程定义的key key的话能保证启动最新版本
	 * @param variables  参数变量
	 * @param businessKey 业务ID
	 */
	public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables, String businessKey);
	
	/**
	 * 根据业务ID，查询当前执行任务列表
	 * @param businesskey
	 * @return
	 */
	public List<Task> findTaskListByBusinessKey(String businesskey, String userName);
	
	/**
	 * 根据业务ID，查询当前流程实例所有审批意见信息列表
	 * @param businesskey
	 * @return
	 */
	public List<Comment> findCommentsListByBusinessKey(String businesskey);
	
	/**
	 * 根据流程定义ID，高亮显示流程图
	 * @param businessKey
	 * @return
	 */
	public InputStream findImageProcess(String processInstanceId);
	
	/**
	 * 根据用户名称获取任务列表  --审批
	 * @param userName
	 * @param category
	 * @return
	 */
	public IPage<WorkflowBaseEntity> findTaskStrsByUserName(IPage<WorkflowBaseEntity> page, WorkflowBaseEntity base, String startTime, String endTime, String userName, String category);
	
	/**
	 * 根据当前用户，获取业务表ID
	 * @param userName
	 * @param category 区别审批还是办理
	 * @return
	 */
	public IPage<WorkflowBaseEntity> findFinishedTaskByUserName(IPage<WorkflowBaseEntity> page, WorkflowBaseEntity base, String startTime, String endTime, String userName, String category);
	
	
	/**
	 * 完成任务提交
	 * @param taskId   任务ID
	 * @param businessKey  业务数据ID
	 * @param comments  审批意见
	 * @param variables   流程变量
	 */
	public void saveSubmitTask(String taskId, String businessKey, String comments);
	
	/**
	 * 完成任务提交
	 * @param taskId   任务ID
	 * @param businessKey  业务数据ID
	 * @param comments  审批意见
	 * @param variables   流程变量
	 */
	public void saveSubmitTask(String taskId, String businessKey, String comments, Map<String, Object> variables);
	
	/**
	 * 完成任务打回
	 * @param taskId   任务ID
	 * @param businessKey  业务数据ID
	 * @param comments  审批意见
	 * @param variables   流程变量
	 */
	public void saveBackTask(String taskId, String businessKey, String comments, Map<String, Object> variables);
	
	/**
	 * 根据任务ID，获取任务连线
	 * @param taskId
	 * @return
	 */
	public int findTaskOutgoByTaskId(String taskId);
	
	/**
	 * 根据业务key，删除当前流程实例
	 * @param businessKey
	 */
	public void deleteProcessInstranceByBusinessKey(String businessKey, String reason);
	
	/**
	 * 根据当前用户，获取业务表ID  组任务
	 * @param userName
	 * @return
	 */
	public Object[] findGroupTaskStrsByUserName(String sessionCnUser);
	

	/**
	 * 根据当前用户，获取业务表ID  组任务
	 * @param userName
	 * @param category
	 * @return
	 */
	public Object[] findGroupTaskStrsByUserName(String sessionCnUser, String category);
	
	/**
	 * 根据当前用户，业务部主键ID  拾取任务
	 * @param userName
	 * @return
	 */
	public void saveClaimGroupTaskByBusinessKey(String businessKey, String userName);

	/**
	 * 根据当前用户，业务部主键ID  回退组任务
	 * @param userName
	 * @return
	 */
	public boolean saveBackClaimGroupTaskByBusinessKey(String businessKey, String userName);
	
	/**
	 * 
	 * @description
	 *  	分页查询 用户代办组任务
	 * @return IPage<WorkflowBaseEntity>
	 * @author 麻木神
	 * @time 2020年4月28日 上午10:45:43
	 */
	public IPage<WorkflowBaseEntity> findGroupTaskStrsByUserName(IPage<WorkflowBaseEntity> page,WorkflowBaseEntity base, String startTime, String endTime, String userName);
	   
	/**
	 *    
	 * @description
	 *  	查询用户代办组任务，拾取任务
	 * @return List<String>
	 * @author 麻木神
	 * @time 2020年4月28日 上午10:47:20
	 */
	public List<String> findGroupTaskIdByBusinessKey(String businessKey, String userName);
	
}
