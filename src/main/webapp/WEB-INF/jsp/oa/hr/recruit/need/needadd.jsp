<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate"></t:base>
<script type="text/javascript">
$(function() {
	laydate({elem:"#preDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
});

</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/recruit/need/save">
							<input type="hidden" name="id" id="id" value="${need.id }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">需求部门*：</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                     	<t:choose url="common/selectDepart" hiddenName="departId" hiddenValue="${need.departId }" textValue="${need.departName }" textName="departLabel" hiddenId="departId" textId="departLabel"></t:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">需求职位*：</label>
                                <div class="col-sm-8">
                                    <input id="needJob" name="needJob" type="text" class="form-control" required="" value="${need.needJob }">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">需求人数：</label>
                                <div class="col-sm-8">
                                    <input id="needNum" name="needNum" type="number" class="form-control" required="" value="${need.needNum }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">预计用工时间：</label>
                                <div class="col-sm-8">
                                    <input id="preDate" name="preDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${need.preDate }" type="date" pattern="yyyy-MM-dd"/>'>
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

