<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="func/timer/job/save">
							<input type="hidden" name="id" id="id" value="${job.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">任务简称*：</label>
                                <div class="col-sm-8">
                                    <input id="shortName" name="shortName" type="text" class="form-control" required="" value="${job.shortName }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务分组*：</label>
                                <div class="col-sm-8">
                                   <select class="form-control m-b" name="jobGroup" id="jobGroup">
                                   		<c:forEach items="${jobGroup }" var="t">
                                   			<option value="${t.value }" <c:if test="${job.jobGroup == t.value}">selected</c:if>>${t.label }</option>
                                   		</c:forEach>
                                   </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">调用参数*：</label>
                                <div class="col-sm-8">
                                    <input id="invokeParams" name="invokeParams" maxlength="500" type="text" class="form-control" required="" value="${job.invokeParams }">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label"></label>
                            	<div class="col-sm-8">
		                            <div class="alert alert-warning">
		                            	Bean调用示例：timerTask.taskParams('active4j')<br>
										Class类调用示例：com.active4j.hr.func.timer.task.TimerTask.taskParams('active4j')<br>
										参数说明：支持字符串，布尔类型，长整型，浮点型，整型
									</div>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">cron表达式*：</label>
                                <div class="col-sm-8">
                                    <input id="cronExpression" name="cronExpression" maxlength="200" type="text" class="form-control" required="" value="${job.cronExpression }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">执行策略*：</label>
                                <div class="col-sm-8">
                                	<c:choose>
                                		<c:when test="${empty job.misfirePolicy}">
                                			<t:dictSelect name="misfirePolicy" type="radio" typeGroupCode="func_timer_job_misfire_policy" defaultVal="1"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="misfirePolicy" type="radio" typeGroupCode="func_timer_job_misfire_policy" defaultVal="${job.misfirePolicy }"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">并发执行*：</label>
                                <div class="col-sm-8">
                                	<c:choose>
                                		<c:when test="${empty job.concurrentStatus}">
                                			<t:dictSelect name="concurrentStatus" type="radio" typeGroupCode="func_timer_job_concurrent_status" defaultVal="0"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="concurrentStatus" type="radio" typeGroupCode="func_timer_job_concurrent_status" defaultVal="${job.concurrentStatus }"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">状态*：</label>
                                <div class="col-sm-8">
                                	<c:choose>
                                		<c:when test="${job.jobStatus == '3'}">
                                			<t:dictSelect name="jobStatus" type="radio" typeGroupCode="func_timer_job_status" defaultVal="3"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="jobStatus" type="radio" typeGroupCode="func_timer_job_status" defaultVal="0"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务描述：</label>
                                <div class="col-sm-8">
                                    <textarea name="description" maxlength="250" class="form-control">${job.description }</textarea>
                                </div>
                            </div>
                            
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>

