<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate,icheck"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>人事请假审批流程</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="commonForm" action="flow/biz/hrholiday/save" method="post">
							<input type="hidden" name="workflowId" id="workflowId" value="${workflowId }">
							<input type="hidden" name="optType" id="optType">
							<input type="hidden" name="id" id="id" value="${base.id }">
							<%@include file="/WEB-INF/jsp/flow/hrholiday/form.jsp" %>
							<div class="form-group" style="margin-top: 30px;">
								<div class="col-sm-4 col-sm-offset-3">
									<button class="btn btn-primary" type="button" onclick="doBtnSaveDraftAction();">保存草稿</button>
									<button class="btn btn-primary" type="button" onclick="doBtnSaveApplyAction();">发起申请</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

	//表单验证
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
	
	//时间控件初始化
	$(function() {
		laydate({
			elem : "#startDay",
			event : "focus",
			istime : false,
			format : 'YYYY-MM-DD'
		});
		laydate({
			elem : "#endDay",
			event : "focus",
			istime : false,
			format : 'YYYY-MM-DD'
		});
	});
	
	//保存草稿
	function doBtnSaveDraftAction() {
		$("#optType").val("0");
		$("#commonForm").submit();
	}
	
	//保存申请
	function doBtnSaveApplyAction() {
		$("#optType").val("1");
		$("#commonForm").submit();
	}
</script>
</html>

