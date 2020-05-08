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
						<t:formvalid action="oa/work/task/saveComments">
							<input type="hidden" name="taskId" id="taskId" value="${task.id }">
                            <div class="form-group">
								<label class="col-sm-3 control-label">回复内容*：</label>
								<div class="col-sm-8">
									  <textarea id="content" name="content" class="form-control" rows="10" required=""></textarea>
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

