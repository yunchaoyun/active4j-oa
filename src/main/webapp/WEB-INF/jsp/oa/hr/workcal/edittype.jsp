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
					<div class="ibox-content">
						<t:formvalid action="oa/hr/workcal/saveEditType">
							<div class="form-group">
								<label class="col-sm-2 control-label m-b">调整日期 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="editDate_begin" name="editDate_begin">
								</div>
								<label class="col-sm-2 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="editDate_end" name="editDate_end">
								</div>
								<label class="col-sm-2 control-label m-b">类型：</label>
								<div class="col-sm-4 m-b">
									<t:dictSelect name="type" type="select" typeGroupCode="oaworkcaltype" defaultVal="0"></t:dictSelect>
								</div>
								<label class="col-sm-2 control-label m-b">是否需要打卡：</label>
								<div class="col-sm-4 m-b">
									<t:dictSelect name="needSign" type="radio" typeGroupCode="byesorno" defaultVal="true"></t:dictSelect>
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
		$(function(){
			laydate({elem:"#editDate_begin",event:"focus",istime: true, format: 'YYYY-MM-DD'});
			laydate({elem:"#editDate_end",event:"focus",istime: true, format: 'YYYY-MM-DD'});
			
		});
</script>

</html>

