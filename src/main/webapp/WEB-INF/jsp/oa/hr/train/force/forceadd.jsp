<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate"></t:base>
<script type="text/javascript">
$(function() {
	laydate({elem:"#startDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
	laydate({elem:"#endDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
});

</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/train/force/save">
							<input type="hidden" name="id" id="id" value="${force.id }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择计划*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="oa/hr/train/force/selectPlan" hiddenName="planId" hiddenValue="${plan.id }" textValue="${plan.name }" textName="planName" width="80%" height="80%" hiddenId="planId" textId="planName"></t:choose>
                                   	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">开始时间：</label>
                                <div class="col-sm-8">
                                    <input id="startDate" name="startDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${force.startDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">结束时间：</label>
                                <div class="col-sm-8">
                                    <input id="endDate" name="endDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${force.endDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">培训地点：</label>
                                <div class="col-sm-8">
                                    <input id="place" name="place" type="text" class="form-control" value="${force.place }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">参加人员：</label>
                                <div class="col-sm-8">
                                    <input id="canPerson" name="canPerson" type="text" class="form-control" value="${force.canPerson }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">负责人：</label>
                                <div class="col-sm-8">
                                    <input id="leadPerson" name="leadPerson" type="text" class="form-control" value="${force.leadPerson }">
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

