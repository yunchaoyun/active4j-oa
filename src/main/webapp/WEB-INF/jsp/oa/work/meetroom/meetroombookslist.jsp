<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,laydate"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12" id="searchGroupId">
					</div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="oaWorkMeetRoomBooksTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/work/meetRoomBooks/datagrid" tableContentId="oaWorkMeetRoomBooksTable" searchGroupId="searchGroupId" fit="true" caption="会议室预定" name="woaWorkMeetRoomBooksList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="meetRoomId" label="会议室名称" width="100" query="true" queryId="meetRoomId" replace="${lstRooms }"></t:dgCol>
		<t:dgCol name="userName" label="预定人" width="80"></t:dgCol>
		<t:dgCol name="bookDate" label="预定日期" width="100" datefmt="yyyy-MM-dd" query="true" defval="${nowStrDate }" datePlugin="laydate"></t:dgCol>
		<t:dgCol name="startDate" label="开始时间" width="80" datefmt="HH:mm"></t:dgCol>
		<t:dgCol name="endDate" label="结束时间" width="80" datefmt="HH:mm"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/work/meetRoomBooks/delete?id={id}"/>
		<t:dgToolBar url="oa/work/meetRoomBooks/addorupdate" type="add" width="50%" height="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/meetRoomBooks/addorupdate" type="edit" width="50%" height="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/meetRoomBooks/addorupdate" type="view" width="50%" height="70%"></t:dgToolBar>
		<t:dgToolBar  type="refresh" ></t:dgToolBar>
	</t:datagrid>
</body>
<script type="text/javascript">
	$(function() {
		laydate({
			elem : "#bookDate",
			event : "focus",
			istime : false,
			format : 'YYYY-MM-DD'
		});
		
		$("#bookDate").val('${nowStrDate}');
	});

</script>
</html>