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
					<div class="ibox-content" style="height: 100% ">
						<div id="oaWorkMeetTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/work/meet/datagrid" tableContentId="oaWorkMeetTable" searchGroupId="searchGroupId" fit="true" caption="会议管理" name="oaWorkMeetTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="type" label="会议类型" width="110" query="true" replace="${lstTypes }"></t:dgCol>
		<t:dgCol name="name" label="会议名称" width="120" query="true"></t:dgCol>
		<t:dgCol name="callUserName" label="会议召集人" width="100"></t:dgCol>
		<t:dgCol name="meetRoomId" label="会议地点" width="100" replace="${lstRooms }"></t:dgCol>
		<t:dgCol name="meetingTime" label="会议时间" width="120" query="true" queryModel="group" datePlugin="laydate" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgCol name="toTime" label="结束时间" width="120" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/work/meet/delete?id={id}"/>
		<t:dgToolBar url="oa/work/meet/add" type="add" width="60%" height="90%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/meet/update" type="edit" width="60%" height="90%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/meet/update" type="view" width="60%" height="90%"></t:dgToolBar>
		<t:dgToolBar label="创建会议纪要" icon="fa fa-bookmark-o" url="oa/work/meet/createSummary" type="pop" width="60%" height="90%"></t:dgToolBar>
	</t:datagrid>
</body>
<script type="text/javascript">
		$(function(){
			laydate({elem:"#meetingTime_begin",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm'});
			laydate({elem:"#meetingTime_end",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm'});
		});
	</script>
</html>