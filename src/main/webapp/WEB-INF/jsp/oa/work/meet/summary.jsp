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
						<t:formvalid action="oa/work/meet/saveSummary">
							<input type="hidden" id="meetId" name="oaMeetId" value="${oaMeetId }">
							<input type="hidden" id="id" name="id" value="${summary.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">会议议题：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control" required="" value="${summary.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">参与人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="participantIds" hiddenValue="${summary.participantIds }" textValue="${summary.participants }" textName="participants" hiddenId="participantIds" textId="participants"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">缺席人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="noParticipantIds" hiddenValue="${summary.noParticipantIds }" textValue="${summary.noParticipants }" textName="noParticipants" hiddenId="noParticipantIds" textId="noParticipants"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label m-b">会议时间 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startTime" name="startTime" required="" value='<fmt:formatDate value="${summary.startTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endTime" name="endTime" required="" value='<fmt:formatDate value="${summary.endTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">会议地点：</label>
								<div class="col-sm-4 m-b">
                                	<select name="meetRoomId" class="form-control" required="">
	                                	<c:forEach items="${lstRooms }" var="c">
											<option value="${c.id }" <c:if test="${summary.meetRoomId == c.id }">selected='selected'</c:if>>${c.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">会议纪要内容：</label>
                                <div class="col-sm-8">
                                    <textarea id="content" name="content" class="form-control" rows="10">${summary.content }</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" rows="4">${summary.memo }</textarea>
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
	
});


</script>
</html>

