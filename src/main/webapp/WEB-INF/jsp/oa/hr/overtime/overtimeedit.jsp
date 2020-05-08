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
						<t:formvalid action="oa/hr/overtime/saveEdit">
							<input type="hidden" id="id" name="id" value="${time.id }">
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">类型：</label>
								<div class="col-sm-4 m-b">
									<t:dictSelect name="type" type="select" typeGroupCode="oaovertimetype" defaultVal="${time.type }"></t:dictSelect>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">上班时间：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startTime" name="startTime" required="" value='<fmt:formatDate value="${time.startTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">下班时间：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endTime" name="endTime" required="" value='<fmt:formatDate value="${time.endTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">合计小时数：</label>
								<div class="col-sm-4 m-b">
									<input class="form-control" type="number" id="hours" name="hours" required="" value="${time.hours }">
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
<script type="text/javascript">
	$(function() {

		$(function() {
			laydate({
				elem : "#startTime",
				event : "focus",
				istime : true,
				format : 'YYYY-MM-DD hh:mm'
			});
			laydate({
				elem : "#endTime",
				event : "focus",
				istime : true,
				format : 'YYYY-MM-DD hh:mm'
			});
		});

	});
</script>
</html>

