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
						<t:formvalid action="oa/work/meet/save">
							<div class="form-group">
                                <label class="col-sm-3 control-label">会议类型*：</label>
                                <div class="col-sm-8">
                                    <select name="type" class="form-control" required="">
	                                	<c:forEach items="${lstTypes }" var="c">
											<option value="${c.id }">${c.name }</option>
										</c:forEach>
									</select>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">会议名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="30" type="text" class="form-control" required="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">召集人*：</label>
                                <div class="col-sm-8">
                                	<p class="form-control-static">${callUserName }</p>
                                	<input type="hidden" name="callUserName" id="callUserName" value="${callUserName }">
                                	<input type="hidden" name="callUserId" id="callUserId" value="${callUserId }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">参与人*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="participantIds" hiddenValue="" textValue="" textName="participants" hiddenId="participantIds" textId="participants"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否生成日程：</label>
                                <div class="col-sm-8">
                                	<t:dictSelect name="partIn" type="radio" typeGroupCode="byesorno" defaultVal="true"></t:dictSelect>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label m-b">会议时间 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="meetingTime" name="meetingTime" required="" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="toTime" name="toTime" required="" >
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">是否开启提醒：</label>
                                <div class="col-sm-8">
                                	<t:dictSelect name="tip" type="radio" typeGroupCode="byesorno" defaultVal="true"></t:dictSelect>
                                </div>
                            </div>
                            <div class="form-group tip-control">
                                <label class="col-sm-3 control-label">提醒时间：</label>
                                <div class="col-sm-8">
                                	<input class="laydate-icon form-control layer-date" id="tipTime" name="tipTime">
                                </div>
                            </div>
                            <div class="form-group tip-control">
                                <label class="col-sm-3 control-label">提醒方式：</label>
                                <div class="col-sm-8">
                                	<t:tipSelect name="tmpTipType"/>
                                </div>
                            </div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">会议地点：</label>
								<div class="col-sm-4 m-b">
                                	<select name="meetRoomId" class="form-control" required="">
	                                	<c:forEach items="${lstRooms }" var="c">
											<option value="${c.id }">${c.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">会议内容：</label>
                                <div class="col-sm-8">
                                    <textarea id="content" name="content" class="form-control" rows="6"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" rows="3"></textarea>
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
		elem : "#meetingTime",
		event : "focus",
		istime : true,
		format : 'YYYY-MM-DD hh:mm'
	});
	
	laydate({
		elem : "#toTime",
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
	
	$("input[name='tip']").on('ifChecked', function(event){ 
		  var value = $("input[name='tip']:checked").val();
		  if("true" == value) {
			  $(".tip-control").show();
		  }else {
			  $(".tip-control").hide();
		  }
	}); 
	
});


</script>
</html>

