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
						<t:formvalid action="oa/work/cal/save">
							<input type="hidden" name="id" id="id" value="${schedule.id }">
                            <div class="form-group">
								<label class="col-sm-3 control-label">日程内容*：</label>
								<div class="col-sm-8">
									<input type="text" name="title" id="title" class="form-control" required="" value="${schedule.title }">
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">参与人*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="participantIds" hiddenValue="${defaultUserId }" textValue="${defaultUserName }" textName="participants" hiddenId="participantIds" textId="participants"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">执行方式*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<c:choose>
                                			<c:when test="${empty  schedule.id}">
                                				<t:dictSelect name="category" type="select" typeGroupCode="oashedulecate" defaultVal=""></t:dictSelect>
                                			</c:when>
                                			<c:otherwise>
                                				<t:dictSelect name="category" type="select" typeGroupCode="oashedulecate" defaultVal="${schedule.category }"></t:dictSelect>
                                			</c:otherwise>
                                		</c:choose>
                                		
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label m-b">时间 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startTime" name="startTime" value="<fmt:formatDate value="${schedule.startTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endTime" name="endTime" value="<fmt:formatDate value="${schedule.endTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>"/>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">是否开启提醒：</label>
                                <div class="col-sm-8">
                                	<c:choose>
                               			<c:when test="${empty  schedule.id}">
                               				<t:dictSelect name="tip" type="radio" typeGroupCode="byesorno" defaultVal="false"></t:dictSelect>
                               			</c:when>
                               			<c:otherwise>
                               				<t:dictSelect name="tip" type="radio" typeGroupCode="byesorno" defaultVal="${schedule.tip }"></t:dictSelect>
                               			</c:otherwise>
                                	</c:choose>
                                	
                                </div>
                            </div>
                            <div class="form-group tip-control">
                                <label class="col-sm-3 control-label">提醒时间：</label>
                                <div class="col-sm-8">
                                	<input class="laydate-icon form-control layer-date" id="tipTime" name="tipTime" value="<fmt:formatDate value="${schedule.tipTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>"/>
                                </div>
                            </div>
                            <div class="form-group tip-control">
                                <label class="col-sm-3 control-label">提醒方式：</label>
                                <div class="col-sm-8">
                                	<t:tipSelect name="tmpTipType" defValue="${schedule.tipType }"/>
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
	
	laydate({
		elem : "#tipTime",
		event : "focus",
		istime : true,
		format : 'YYYY-MM-DD hh:mm'
	});
	
	initShow();
	
	$("input[name='tip']").on('ifChecked', function(event){ 
		  var value = $("input[name='tip']:checked").val();
		  if("true" == value) {
			  $(".tip-control").show();
		  }else {
			  $(".tip-control").hide();
		  }
	}); 
});

function initShow() {
   var value = $("input[name='tip']:checked").val();
   if("true" == value) {
	  $(".tip-control").show();
   }else {
	  $(".tip-control").hide();
   }
}

</script>

</html>

