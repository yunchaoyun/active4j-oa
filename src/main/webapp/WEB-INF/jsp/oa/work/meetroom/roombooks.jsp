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
						<t:formvalid action="oa/work/meetRoomBooks/save">
							<div class="form-group">
                                <label class="col-sm-2 control-label">会议室名称*：</label>
                                <div class="col-sm-8">
                                	<p class="form-control-static">${meetRoomName }</p>
                                	<input type="hidden" name="meetRoomId" id="meetRoomId" value="${meetRoomId }">
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-2 control-label m-b">会议日期：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="bookDate" name="bookDate" required="" value='<fmt:formatDate value="${bookDate }" type="date" pattern="yyyy-MM-dd"/>'>
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-2 control-label m-b">开始时间：</label>
								<div class="col-sm-4 m-b">
									<div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" class="form-control" name="startDate" value='<fmt:formatDate value="${meet.startDate }" type="time" pattern="HH:mm"/>'>
			                            <span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>
			                        </div>
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-2 control-label m-b">结束时间：</label>
								<div class="col-sm-4 m-b">
									<div class="input-group clockpicker" data-autoclose="true">
									 	<input type="text" class="form-control" name="endDate" value='<fmt:formatDate value="${meet.endDate }" type="time" pattern="HH:mm"/>'>
			                            <span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>
			                        </div>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" >${meet.memo}</textarea>
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
			elem : "#bookDate",
			event : "focus",
			istime : false,
			format : 'YYYY-MM-DD'
		});
		
		
		$('.clockpicker').clockpicker();
		
	});
	
	
	

</script>
</html>

