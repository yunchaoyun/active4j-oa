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
					<t:formvalid>
						<div class="form-group">
                        	<label class="col-sm-3 control-label">任务编号：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.jobNo }">
                            </div>
                        </div>
						<div class="form-group">
                        	<label class="col-sm-3 control-label">任务简称：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.shortName }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">任务分组：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.jobGroup }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">调用参数：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.invokeParams }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">cron表达式：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.cronExpression }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">执行策略：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.misfirePolicy }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">并发执行：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.concurrentStatus }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">任务状态：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.jobStatus }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">执行状态：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.jobExecuteStatus }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">上一次执行时间：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.previousTime }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">下一次执行时间：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${job.nextTime }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">任务描述：</label>
                            <div class="col-sm-8">
                            	<textarea name="description" readonly="readonly" class="form-control">${job.description }</textarea>
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

