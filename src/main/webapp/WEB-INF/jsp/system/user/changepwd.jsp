<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="sys/user/savepwd">
							<input type="hidden" name="id" id="id" value="${userId}">
	                            <div class="form-group">
	                                <label class="col-sm-3 control-label">密码：</label>
	                                <div class="col-sm-8">
	                                    <input id="password" name="password" minlength="2" maxlength="20" type="password" class="form-control" required="">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-3 control-label">重复密码：</label>
	                                <div class="col-sm-8">
	                                    <input id="repassword" name="repassword" minlength="2" maxlength="20" type="password" class="form-control" required="" equalTo='#password' >
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

