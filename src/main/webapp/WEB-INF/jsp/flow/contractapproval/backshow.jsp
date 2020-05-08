<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate,icheck"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		
		<%@include file="/WEB-INF/jsp/flow/include/info.jsp" %>
		
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>行政用章审批流程</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="commonForm" action="${action }" method="post">
							<input type="hidden" name="id" id="id" value="${base.id }">
							<input type="hidden" name="taskId" id="taskId" value="${taskId }">
							<div class="form-group">
								<label class="col-sm-3 control-label">申请编号：</label>
								<div class="col-sm-5">
									<input id="projectNo" name="projectNo" minlength="2" type="text" class="form-control" required="" value="${base.projectNo }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称：</label>
								<div class="col-sm-5">
									<input id="name" name="name" minlength="2" type="text" class="form-control" required="" value="${base.name }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">合同名称：</label>
								<div class="col-sm-5">
									<input id="contractName" name="contractName" minlength="1" type="text" class="form-control" required="" value="${biz.contractName }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金钱：</label>
								<div class="col-sm-5">
									<input id="money" name="money" type="number" class="form-control" required="" value="${biz.money }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">紧急程度：</label>
								<div class="col-sm-5">
									<c:choose>
                                		<c:when test="${empty base.level}">
                                			<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="0"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="${base.level}"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
								</div>
							</div>
								<div class="form-group" style="margin-top: 30px;">
								<div class="col-sm-4 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="doBtnSaveApplyAction();">重新提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="/WEB-INF/jsp/flow/include/apphis.jsp" %>
		
	</div>
</body>

<script type="text/javascript">
	//时间控件初始化
	$(function() {
		$("#commonForm").validate({
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function(o) {
						if (o.success) {
							qhTipSuccess('保存成功');
							location.href='common/goSuccess';
						} else {
							qhTipWarning(o.msg);
						}
					},
					error : function(data) {
						qhTipError('系统错误，请联系系统管理员');
					}
				});
			}
		});
		
	});
	
	//重新提交
	function doBtnSaveApplyAction() {
		$("#commonForm").submit();
	}
</script>
</html>

