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
                            	<input type="text" class="form-control" readonly="readonly" value="${log.jobNo }">
                            </div>
                        </div>
						<div class="form-group">
                        	<label class="col-sm-3 control-label">任务简称：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.shortName }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">任务分组：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.jobGroup }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">调用参数：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.invokeParams }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">日志信息：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.jobMessage }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">执行状态：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.status }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">开始执行时间：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.startTime }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">结束执行时间：</label>
                            <div class="col-sm-8">
                            	<input type="text" class="form-control" readonly="readonly" value="${log.endTime }">
                            </div>
                        </div>
                        <div class="form-group">
                        	<label class="col-sm-3 control-label">异常信息：</label>
                            <div class="col-sm-8">
                            	<textarea name="description" readonly="readonly" rows="10" class="form-control">${log.exceptionInfo }</textarea>
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

