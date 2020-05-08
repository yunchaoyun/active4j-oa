<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate,ionrange"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/work/task/saveExcute" beforeSubmit="setEditValue();">
							<input type="hidden" name="taskId" id="taskId" value="${task.id }">
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">执行时间 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startTime" name="startTime">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endTime" name="endTime">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">添加进度：</label>
								<div class="col-sm-8">
									<div id="ionrange"></div>
									<input type="hidden" id="progress" name="progress">
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">说明：</label>
								<div class="col-sm-8">
									  <textarea id="description" name="description" class="form-control" rows="8"></textarea>
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
	
	$("#ionrange").ionRangeSlider({min:0,max:100,step:1,postfix:" ",prettify:!1,hasGrid:!0, from: ${task.progress}});
});

function setEditValue() {

	var value =$('#ionrange').attr('value');
	$("#progress").val(value);
	
	return true;
}

</script>

</html>

