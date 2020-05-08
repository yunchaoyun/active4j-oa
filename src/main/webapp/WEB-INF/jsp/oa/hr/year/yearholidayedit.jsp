<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview,laydate"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/yearholiday/saveEdit">
							<input type="hidden" id="id" name="id" value="${time.id }">
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">年份：</label>
								<div class="col-sm-4 m-b">
									<input class="form-control" type="digits" id="year" name="year" required="" value="${time.year }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">合计小时数：</label>
								<div class="col-sm-4 m-b">
									<input class="form-control" type="number" id="days" name="days" required="" value="${time.days }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">备注：</label>
								<div class="col-sm-6">
									<textarea rows="6" id="memo" name="memo" class="form-control">${time.memo }</textarea>
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

