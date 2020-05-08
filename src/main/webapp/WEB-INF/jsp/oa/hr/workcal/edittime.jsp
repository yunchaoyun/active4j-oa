<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate,clock"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/workcal/saveEditTime">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label m-b">调整日期 从：</label>
	                                <div class="col-sm-4 m-b">
	                                    <input class="laydate-icon form-control layer-date" id="editDate_begin" name="editDate_begin">
	                                </div>
	                                <label class="col-sm-2 control-label m-b">到：</label>
	                                <div class="col-sm-4 m-b">
	                                    <input class="laydate-icon form-control layer-date" id="editDate_end" name="editDate_end">
	                                </div>
	                                <label class="col-sm-2 control-label m-b">上班时间：</label>
	                                <div class="col-sm-4 m-b">
	                                   <div class="input-group clockpicker" data-autoclose="true">
				                            <input type="text" class="form-control" name="startTime">
				                            <span class="input-group-addon">
				                                   <span class="fa fa-clock-o"></span>
				                            </span>
				                        </div>
	                                </div>
	                                <label class="col-sm-2 control-label m-b">下班班时间：</label>
	                                <div class="col-sm-4 m-b">
	                                   <div class="input-group clockpicker" data-autoclose="true">
				                            <input type="text" class="form-control" name="endTime">
				                            <span class="input-group-addon">
				                                   <span class="fa fa-clock-o"></span>
				                            </span>
				                        </div>
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
			
			$('.clockpicker').clockpicker();
		});
</script>
</html>

