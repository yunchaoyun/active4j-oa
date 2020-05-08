<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,fullcalender2"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5 id="roomNameTitle">会议室预定信息(${roomName })</h5>
					</div>
					<div class="ibox-content">
						<p>
							<c:forEach items="${lstRooms }" var="r">
								<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="goRoom('${r.id }');">&nbsp;${r.name }&nbsp;</button>
							</c:forEach>
							<input type="hidden" id="roomId" name="roomId" value="${roomId }">
						</p>
						<div id="calendar"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			var tmp1 = ${lstBooks};
			$('#calendar').fullCalendar({
				defaultView : 'agendaWeek',
				header : {
					left : 'prev,next today',
					center : 'title'
				},
				allDaySlot : false,
				dayClick: function (date, allDay, jsEvent, view){
					var currentDate = new Date(date).QHformat('yyyy-MM-dd');
					var roomId = $("#roomId").val();
					var url = 'oa/work/meetRoom/bookview?id=' + roomId + '&currentDate=' + currentDate;
					parent.layer.open({
						type : 2,
						title : '预定会议室',
						shadeClose : true,
						shade : 0.8,
						area : [ '50%', '70%' ],
						content : url, 
						btn : [ '确定', '取消' ],
						yes : function(index, layero) {
							//确定按钮回调
							//表单提交
							parent.frames['layui-layer-iframe' + index].submitL();
							initData();
						},
						btn2 : function(index, layero) {
							//取消按钮回调
							
						},
						end: function() {
							goRoom(roomId);
						}
					});
				},
				events : tmp1
			});
		});
		
		function goRoom(id) {
			
			$("#roomId").val(id);
			
			$.post("oa/work/meetRoom/getRoomView",{id:id}, function(o){
				if(o.success) {
					$("#calendar").fullCalendar("removeEvents");
					
					var roomName = o.attributes.roomName;
					$("#roomNameTitle").empty();
					$("#roomNameTitle").append("会议室预定信息("+roomName+")");
					
					var lstBooks = o.attributes.lstBooks;
					for(var i = 0; i < lstBooks.length; i++) {
						var book = lstBooks[i];
						$("#calendar").fullCalendar('renderEvent', book, true);
					}
					
				}else {
					qhTipWarning(o.msg);
				}
			});
			
			
		}
	</script>

</body>

</html>