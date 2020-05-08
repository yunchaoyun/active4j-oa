package com.active4j.hr.activiti.service.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.active4j.hr.activiti.dao.WorkflowDao;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.entity.WorkflowMngEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowMngService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.activiti.util.ActivitiUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.web.tag.PagerUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @title WorkflowServiceImpl.java
 * @description 工作流 核心
 * @time 2020年4月17日 下午3:08:58
 * @author 麻木神
 * @version 1.0
 */
@Service("workflowService")
@Transactional
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private WorkflowMngService workflowMngService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private WorkflowBaseService workflowBaseService;

	/** 流程图生成器 */
	@Autowired
	private ProcessDiagramGenerator processDiagramGenerator;
	
	@Autowired
	private WorkflowDao workflowDao;

	/**
	 * 查询流程部署列表
	 * 
	 * @param dataGrid
	 * @return
	 */
	public IPage<Deployment> findDeployList(DataGrid dataGrid) {
		Long allCounts = repositoryService.createDeploymentQuery()// 创建部署对象查询
				.count();

		int counts = allCounts.intValue();

		int pageSize = dataGrid.getRows();// 每页显示数

		List<Deployment> list = repositoryService.createDeploymentQuery()// 创建部署对象查询
				.orderByDeploymenTime().desc()// 根据部署时间 降序
				// .list(); //全部查询
				.listPage(PagerUtil.getFirstResult(dataGrid.getPage(), pageSize), pageSize); // 分页查询

		Page<Deployment> lstResult = new Page<Deployment>(dataGrid.getPage(), pageSize, counts);
		lstResult.setRecords(list);

		return lstResult;
	}

	/**
	 * 根据部署ID 删除部署
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteDeploy(String id) throws Exception {
		repositoryService.deleteDeployment(id);
	}

	/**
	 * 部署流程
	 * 
	 * @param name     流程名称
	 * @param category 流程类别
	 * @param file     部署文件流
	 * @throws FileNotFoundException
	 */
	public void saveNewDeploy(String name, InputStream file) throws Exception {
		ZipInputStream zipInputStream = new ZipInputStream(file);
		Deployment deployment = repositoryService.createDeployment()// 创建部署对象
				.name(name)// 添加部署名称
				.addZipInputStream(zipInputStream)// 部署文件
				.deploy();// 完成部署
		/**
		 * 如果新增部署，则查询流程管理表，如果key相同，则设置为过期
		 */
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

		QueryWrapper<WorkflowMngEntity> queryWrapper = new QueryWrapper<WorkflowMngEntity>();
		queryWrapper.eq("PROCESS_KEY", pd.getKey());
		List<WorkflowMngEntity> lstResult = workflowMngService.list(queryWrapper);
		if (null != lstResult && lstResult.size() > 0) {
			for (WorkflowMngEntity mng : lstResult) {
				mng.setStatus("2");
				workflowMngService.saveOrUpdate(mng);
			}
		}
	}

	/**
	 * 根据流程定义的名称查询流程列表 带分页
	 * 
	 * @param name             流程名称
	 * @param isLastestVersion 是否获取最新版本
	 * @param dataGrid         分页
	 * @return
	 */
	public IPage<ProcessDefinition> findProcessListByName(String name, Boolean isLastestVersion, DataGrid dataGrid) {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		if (StringUtils.isNotEmpty(name)) {
			query.processDefinitionNameLike(name);
		}
		if (null != isLastestVersion && isLastestVersion) {
			query.latestVersion();
		}
		// 分页需要总数
		Long allCounts = query.count();

		int counts = allCounts.intValue();

		int pageSize = dataGrid.getRows();// 每页显示数

		// 执行查询
		List<ProcessDefinition> lst = query.orderByProcessDefinitionVersion().desc().listPage(PagerUtil.getFirstResult(dataGrid.getPage(), pageSize), pageSize); // 分页查询

		IPage<ProcessDefinition> lstResult = new Page<ProcessDefinition>(dataGrid.getPage(), pageSize, counts);
		lstResult.setRecords(lst);

		return lstResult;
	}

	/**
	 * 根据流程定义ID，显示流程图
	 * 
	 * @param id
	 * @return
	 */
	public InputStream findWorkflowImage(String id) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
		return repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
	}

	/**
	 * 根据部署ID 删除部署
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteDefine(String id) throws Exception {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

		repositoryService.deleteDeployment(pd.getDeploymentId());

		QueryWrapper<WorkflowMngEntity> queryWrapper = new QueryWrapper<WorkflowMngEntity>();
		queryWrapper.eq("PROCESS_DEFINE_ID", id);
		List<WorkflowMngEntity> lstResult = workflowMngService.list(queryWrapper);
		if (null != lstResult && lstResult.size() > 0) {
			for (WorkflowMngEntity mng : lstResult) {
				mng.setStatus("2");
				workflowMngService.saveOrUpdate(mng);
			}
		}

	}

	/**
	 * 根据流程定义ID 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteAll(String id) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

		repositoryService.deleteDeployment(pd.getDeploymentId(), true);

		QueryWrapper<WorkflowMngEntity> queryWrapper = new QueryWrapper<WorkflowMngEntity>();
		queryWrapper.eq("PROCESS_DEFINE_ID", id);
		List<WorkflowMngEntity> lstResult = workflowMngService.list(queryWrapper);
		if (null != lstResult && lstResult.size() > 0) {
			for (WorkflowMngEntity mng : lstResult) {
				mng.setStatus("2");
				workflowMngService.saveOrUpdate(mng);
			}
		}
	}

	/**
	 * 查询系统中所有流程定义，最新版本
	 */
	public List<ProcessDefinition> findProcessDefineList() {
		List<ProcessDefinition> lst = repositoryService.createProcessDefinitionQuery().latestVersion().orderByDeploymentId().desc().list();

		return lst;
	}

	/**
	 * 根据流程定义key，查询最新流程定义
	 * 
	 * @param key
	 * @return
	 */
	public ProcessDefinition findNewestProcessDefine(String key) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();

		return pd;
	}

	/**
	 * 根据流程定义的ID，启动流程，并赋值参数, 并关联业务ID
	 * 
	 * @param processDefinitionKey 流程定义的key key的话能保证启动最新版本
	 * @param map                  参数变量
	 * @param businessKey          业务ID
	 * @param flag                 存在申请人节点
	 * @param applyName            申请人
	 */
	public void startProcessInstanceByKey(String processDefinitionKey, String businessKey, boolean flag, String applyName, Map<String, Object> map) {
		if (flag) {
			// 存在申请人申请任务节点，在任务启动后，自动跳过
			startProcessInstanceByKey(processDefinitionKey, map, businessKey);

			// 处理节点
			Task task = taskService.createTaskQuery()//
					.taskAssignee(applyName)// 指定个人任务查询
					.processInstanceBusinessKey(businessKey).singleResult();

			taskService.complete(task.getId());

		} else {
			startProcessInstanceByKey(processDefinitionKey, map, businessKey);
		}
	}

	/**
	 * 根据流程定义的ID，启动流程，并赋值参数, 并关联业务ID
	 * 
	 * @param processDefinitionKey 流程定义的key key的话能保证启动最新版本
	 * @param variables            参数变量
	 * @param businessKey          业务ID
	 */
	@Override
	public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables, String businessKey) {
		variables.put("applyName", ShiroUtils.getSessionUserName());
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}

	/**
	 * 根据业务ID，查询当前执行任务列表
	 * 
	 * @param businesskey
	 * @return
	 */
	public List<Task> findTaskListByBusinessKey(String businesskey, String userName) {
		List<Task> lstTasks = new ArrayList<Task>();

		// 查询流程实例
		List<ProcessInstance> lstPis = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businesskey).list();
		// 获取流程实例的ID，这里确切的说只有一条数据，保险起见，弄个Set存放一下
		Set<String> st = new HashSet<String>();
		for (ProcessInstance pi : lstPis) {
			String piId = pi.getProcessInstanceId();
			st.add(piId);
		}

		for (String piId : st) {
			List<Task> lst = taskService.createTaskQuery().processInstanceId(piId).taskAssignee(userName).list();
			lstTasks.addAll(lst);
		}

		return lstTasks;
	}
	
	/**
	 * 根据当前用户，获取业务表ID
	 * @param userName
	 * @param category 区别审批还是办理
	 * @return
	 */
	public IPage<WorkflowBaseEntity> findFinishedTaskByUserName(IPage<WorkflowBaseEntity> page, WorkflowBaseEntity base, String startTime, String endTime, String userName, String category){
		/*List<HistoricTaskInstance> lstHisTasks = historyService.createHistoricTaskInstanceQuery()
				.taskAssignee(userName) //任务办理人
				.taskCategory(category) // 区别办理还是审批
				.finished()  //已经完成
				.orderByTaskCreateTime().desc()//排序
				.list();
		
		//根据历史任务，查询历史流程实例
		if(null != lstHisTasks && lstHisTasks.size() > 0) {
			Set<String> st = new HashSet<String>();
			for(HistoricTaskInstance task : lstHisTasks) {
				HistoricProcessInstance hisPi = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				//获取业务key
				if(null != hisPi && StringUtils.isNotEmpty(hisPi.getBusinessKey())) {
					st.add(hisPi.getBusinessKey());
				}
			}
			return st.toArray();
		}*/
		
		//直接改为使用sql直接查询
		
		return workflowDao.findFinishedTaskByUserName(page, userName, base.getCategoryId(), base.getProjectNo(), base.getName(), base.getApplyName(), startTime, endTime);
	}

	/**
	 * 根据业务ID，查询当前流程实例所有审批意见信息列表
	 * 
	 * @param businesskey
	 * @return
	 */
	public List<Comment> findCommentsListByBusinessKey(String businesskey) {
		List<Comment> lstComments = new ArrayList<Comment>();
		// 查询流程实例
		List<HistoricProcessInstance> lstPis = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businesskey).list();

		// 获取流程实例的ID，这里确切的说只有一条数据，保险起见，弄个Set存放一下
		Set<String> st = new HashSet<String>();
		for (HistoricProcessInstance pi : lstPis) {
			String piId = pi.getId();
			st.add(piId);
		}

		for (String piId : st) {
			List<Comment> lst = taskService.getProcessInstanceComments(piId);
			if (null != lst) {
				lstComments.addAll(lst);
			}
		}

		return lstComments;
	}

	/**
	 * 根据流程定义ID，高亮显示流程图
	 * 
	 * @param businessKey
	 * @return
	 */
	public InputStream findImageProcess(String businessKey) {
		// 根据业务ID，查询流程实例对象
		/*
		 * 获取流程实例
		 */
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
		if (processInstance == null) {
			return null;
		}

		// 根据流程对象获取流程对象模型
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		

		/*
		 * 查看已执行的节点集合 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
		 */
		// 构造历史流程查询
		HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
		// 查询历史节点
		List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();

		if (historicActivityInstanceList == null || historicActivityInstanceList.size() == 0) {
			return outputImg(bpmnModel, null, null);
		}

		// 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
		List<String> executedActivityIdList = historicActivityInstanceList.stream().map(item -> item.getActivityId()).collect(Collectors.toList());

		/*
		 * 获取流程走过的线
		 */
		// 获取流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, processDefinition, historicActivityInstanceList);

		return outputImg(bpmnModel, flowIds, executedActivityIdList);
	}

	/**
	 * <p>
	 * 输出图像
	 * </p>
	 * 
	 * @param response               响应实体
	 * @param bpmnModel              图像对象
	 * @param flowIds                已执行的线集合
	 * @param executedActivityIdList void 已执行的节点ID集合
	 * @author FRH
	 * @time 2018年12月10日上午11:23:01
	 * @version 1.0
	 */
	private InputStream outputImg(BpmnModel bpmnModel, List<String> flowIds, List<String> executedActivityIdList) {
		InputStream imageStream = null;
		try {
			imageStream = processDiagramGenerator.generateDiagram(bpmnModel, executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", true, "png");
		} catch (Exception e) {
			log.error("显示流程图跟踪报错, 错误信息:{}", e);
		}

		return imageStream;
	}

	/**
	 * 根据用户名称获取任务列表 --审批
	 * 
	 * @param userName
	 * @param category
	 * @return
	 */
	public IPage<WorkflowBaseEntity> findTaskStrsByUserName(IPage<WorkflowBaseEntity> page, WorkflowBaseEntity base, String startTime, String endTime, String userName, String category) {
		// 查询任务列表
		/*List<Task> lst = taskService.createTaskQuery().taskAssignee(userName) .taskCategory(category) .list();
		if (null != lst && lst.size() > 0) {
			Set<String> lstKeys = new HashSet<String>();
			for (Task task : lst) {
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				lstKeys.add(pi.getBusinessKey());
			}

			return (Object[]) lstKeys.toArray();
		}*/
		
		//方法优化，直接改用sql查出结果
		return workflowDao.findTaskStrsByUserName(page, userName, base.getCategoryId(), base.getProjectNo(), base.getName(), base.getApplyName(), startTime, endTime);
	}

	/**
	 * 完成任务提交
	 * 
	 * @param taskId      任务ID
	 * @param businessKey 业务数据ID 只能用于BaseActivitiEntity
	 * @param comments    审批意见
	 */
	public void saveSubmitTask(String taskId, String businessKey, String comments) {
		// 使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();

		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();

		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用： String userId =
		 * Authentication.getAuthenticatedUserId(); CommentEntity comment = new
		 * CommentEntity(); comment.setUserId(userId);
		 * 所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
		 * 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 */
		Authentication.setAuthenticatedUserId(ShiroUtils.getSessionUserName());
		taskService.addComment(taskId, processInstanceId, comments);
		// 使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId);

		/**
		 * 在完成任务之后，判断流程是否结束 如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		WorkflowBaseEntity workflowBaseEntity = workflowBaseService.getById(businessKey);
		if (null != workflowBaseEntity) {

			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)// 使用流程实例ID查询
					.singleResult();
			// 流程结束了
			if (pi == null) {
				// 更新请假单表的状态从2变成3（审核中-->审核完成）
				workflowBaseEntity.setStatus("3");
			} else {
				workflowBaseEntity.setStatus("2");
			}
			workflowBaseService.saveOrUpdate(workflowBaseEntity);
			log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
		}

	}

	/**
	 * 完成任务提交
	 * 
	 * @param taskId      任务ID
	 * @param businessKey 业务数据ID 只能用于BaseActivitiEntity
	 * @param comments    审批意见
	 * @param variables   流程变量
	 */
	public void saveSubmitTask(String taskId, String businessKey, String comments, Map<String, Object> variables) {
		// 使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();

		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();

		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用： String userId =
		 * Authentication.getAuthenticatedUserId(); CommentEntity comment = new
		 * CommentEntity(); comment.setUserId(userId);
		 * 所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
		 * 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 */
		Authentication.setAuthenticatedUserId(ShiroUtils.getSessionUserName());
		taskService.addComment(taskId, processInstanceId, comments);

		// 使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables, true);

		/**
		 * 在完成任务之后，判断流程是否结束 如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		WorkflowBaseEntity workflowBaseEntity = workflowBaseService.getById(businessKey);
		if (null != workflowBaseEntity) {

			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)// 使用流程实例ID查询
					.singleResult();
			// 流程结束了
			if (pi == null) {
				// 更新请假单表的状态从2变成3（审核中-->审核完成）
				workflowBaseEntity.setStatus("3");
			} else {
				workflowBaseEntity.setStatus("2");
			}
			workflowBaseService.saveOrUpdate(workflowBaseEntity);
			log.info("流程:" + workflowBaseEntity.getName() + "完成审批，审批任务ID:" + taskId + "， 审批状态:" + workflowBaseEntity.getStatus());
		}
	}
	
	/**
	 * 完成任务打回
	 * @param taskId   任务ID
	 * @param businessKey  业务数据ID
	 * @param comments  审批意见
	 * @param variables   流程变量
	 */
	public void saveBackTask(String taskId, String businessKey, String comments, Map<String, Object> variables){
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用：
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
			 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 * */
		Authentication.setAuthenticatedUserId(ShiroUtils.getSessionUserName());
		taskService.addComment(taskId, processInstanceId, comments);
		
		//使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables, true);
		
		/**
		 * 在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		WorkflowBaseEntity baseActivitiEntity = workflowBaseService.getById(businessKey);
		baseActivitiEntity.setStatus("5");
		workflowBaseService.saveOrUpdate(baseActivitiEntity);
		log.info("流程:" + baseActivitiEntity.getName() + "打回审批，审批任务ID:" + taskId + "， 审批状态:" + baseActivitiEntity.getStatus());
	}

	/**
	 * 根据任务ID，获取任务连线 只是审批通过或者驳回两种情况
	 * 
	 * @param taskId
	 * @return
	 */
	public int findTaskOutgoByTaskId(String taskId) {
		int i = 1;
		// 获取任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 根据流程对象获取流程对象模型
		BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
		
		List<org.activiti.bpmn.model.Process> lstProcess = bpmnModel.getProcesses();
		if(null != lstProcess && lstProcess.size() > 0) {
			for(org.activiti.bpmn.model.Process process : lstProcess) {
				//获取所有普通任务节点
		        List<UserTask> UserTaskList = process.findFlowElementsOfType(UserTask.class);
		        for(UserTask userTask:UserTaskList){
		        	if(StringUtils.equals(task.getTaskDefinitionKey(), userTask.getId())) {
		        		List<SequenceFlow> pvmTransitions = userTask.getOutgoingFlows();
		        		for (SequenceFlow pvmTransition : pvmTransitions) {
		        			String nextTarget = pvmTransition.getTargetRef();
		    				FlowElement flowElement = process.getFlowElement(nextTarget);
		    				if(flowElement instanceof ExclusiveGateway) {
		    					ExclusiveGateway gateway = (ExclusiveGateway)flowElement;
		    					List<SequenceFlow> flows = gateway.getOutgoingFlows();
		    					if(null != flows && flows.size() > 0) {
		    						i = 2;
		    					}
		    				}
		        		}
		        	}
		        }
			}
		}
		
		return i;
	}

	/**
	 * 根据业务key，删除当前流程实例
	 * @param businessKey
	 */
	public void deleteProcessInstranceByBusinessKey(String businessKey, String reason){
		//根据业务ID，查询流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
		if(null != pi) {
			runtimeService.deleteProcessInstance(pi.getId(), reason);
		}
	}
	
	/**
	 * 根据当前用户，获取业务表ID  组任务  activiti7强引用了springsecurity导致taskCandidateUser查询报错
	 * @param userName
	 * @return
	 */
	public Object[] findGroupTaskStrsByUserName(String userName){
		//查询组任务列表
		/*List<Task> lst = taskService.createTaskQuery().taskCandidateUser(userName).list();
		if(null != lst && lst.size() > 0) {
			Set<String> lstKeys = new HashSet<String>();
			for(Task task : lst) {
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				lstKeys.add(pi.getBusinessKey());
			}
			
			return (Object[])lstKeys.toArray();
		}*/
		
		return null;
	}
	
	
	/**
	 * 根据当前用户，获取业务表ID  组任务
	 * 			activiti7强引用了springsecurity导致taskCandidateUser查询报错
	 * @param userName
	 * @param category
	 * @return
	 */
	public Object[] findGroupTaskStrsByUserName(String userName, String category){
		//查询组任务列表
		/*List<Task> lst = taskService.createTaskQuery().taskCandidateUser(userName).taskCategory(category).list();
		if(null != lst && lst.size() > 0) {
			Set<String> lstKeys = new HashSet<String>();
			for(Task task : lst) {
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				lstKeys.add(pi.getBusinessKey());
			}
			
			return (Object[])lstKeys.toArray();
		}*/
		
		return null;
	}
	
	/**
	 * 根据当前用户，业务部主键ID  拾取任务
	 * @param userName
	 * @return
	 */
	public void saveClaimGroupTaskByBusinessKey(String businessKey, String userName){
		/*//查询流程实例
		List<ProcessInstance> lstPis = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
		//获取流程实例的ID，这里确切的说只有一条数据，保险起见，弄个Set存放一下
		Set<String> st = new HashSet<String>();
		for(ProcessInstance pi : lstPis) {
			String piId = pi.getProcessInstanceId();
			st.add(piId);
		}
		
		for(String piId : st) {
			List<Task> lst = taskService.createTaskQuery().processInstanceId(piId).taskCandidateUser(userName).list();
			for(Task task : lst) {
				taskService.claim(task.getId(), userName);
			}
		}*/
		
		List<String> lstTaskIds = workflowDao.findGroupTaskIdByBusinessKey(businessKey, userName);
		
		for(String taskId : lstTaskIds) {
			taskService.claim(taskId, userName);
		}
		
	}
	
	/**
	 * 
	 * @description
	 *  	分页查询 用户代办组任务
	 * @return IPage<WorkflowBaseEntity>
	 * @author 麻木神
	 * @time 2020年4月28日 上午10:45:43
	 */
	public IPage<WorkflowBaseEntity> findGroupTaskStrsByUserName(IPage<WorkflowBaseEntity> page, WorkflowBaseEntity base, String startTime, String endTime, String userName){
		return workflowDao.findGroupTaskStrsByUserName(page, userName, base.getCategoryId(), base.getProjectNo(), base.getName(), base.getApplyName(), startTime, endTime);
	}
	   
	/**
	 *    
	 * @description
	 *  	查询用户代办组任务，拾取任务
	 * @return List<String>
	 * @author 麻木神
	 * @time 2020年4月28日 上午10:47:20
	 */
	public List<String> findGroupTaskIdByBusinessKey(String businessKey, String userName){
		return workflowDao.findGroupTaskIdByBusinessKey(businessKey, userName);
	}
	
	
	/**
	 * 根据当前用户，业务部主键ID  回退组任务
	 * @param userName
	 * @return
	 */
	public boolean saveBackClaimGroupTaskByBusinessKey(String businessKey, String userName){
		//查询流程实例
		List<ProcessInstance> lstPis = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
		//获取流程实例的ID，这里确切的说只有一条数据，保险起见，弄个Set存放一下
		Set<String> st = new HashSet<String>();
		for(ProcessInstance pi : lstPis) {
			String piId = pi.getProcessInstanceId();
			st.add(piId);
		}
		
		boolean isCandiate = false;
		for(String piId : st) {
			List<Task> lst = taskService.createTaskQuery().processInstanceId(piId).taskAssignee(userName).list();
			for(Task task : lst) {
				List<IdentityLink> lstLinks = taskService.getIdentityLinksForTask(task.getId());
				for(IdentityLink link : lstLinks) {
					if(StringUtils.equals(link.getType(), IdentityLinkType.CANDIDATE)) {
						isCandiate = true;
						if(isCandiate) {
							break;
						}
					}
				}
			}
			if(isCandiate) {
				for(Task task : lst) {
					taskService.setAssignee(task.getId(), null);
				}
			}
			
		}
		return isCandiate;
	}
}
