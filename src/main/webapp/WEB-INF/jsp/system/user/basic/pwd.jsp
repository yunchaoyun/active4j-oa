<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>

<script type="text/javascript">
$(function() {
	$("#personInfoForm").validate({
		submitHandler : function(form) {
			$(form).ajaxSubmit({
				success : function(data) {
					if (data.success) {
						qhTipSuccess('密码修改成功');
					} else {
						qhTipWarning(data.msg);
					}
				},
				error : function(data) {
					qhTipError('系统错误，请联系系统管理员');
				}
			});
		}
	});
});
function submitAction() {
	$("#personInfoForm").submit();
}



</script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInRight">
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>修改密码</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="personInfoForm" action="sys/user/alterpwd" method="post">
							<div class="form-group">
								<label class="col-sm-3 control-label">旧密码：</label>
								<div class="col-sm-8">
									<input id="oldPassword" name="oldPassword"  type="password" class="form-control" required="" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">新密码：</label>
								<div class="col-sm-8">
									<input id="newPassword" name="newPassword" type="password" class="form-control" required="" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">确认新密码：</label>
								<div class="col-sm-8">
									<input id="reNewPassword" name="reNewPassword" type="password" class="form-control" required="" value="" equalTo='#newPassword'>
								</div>
							</div>
						</form>
						<div class="row">
							<div class="col-sm-11 text-right">
								<p>
									<button class="btn btn-info" onclick="submitAction();" type="submit">
										<i class="fa fa-check"></i>&nbsp;&nbsp;确定
									</button>
								</p>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>