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
						<div id="oaWorkScheduleTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/work/cal/datagrid" tableContentId="oaWorkScheduleTable" searchGroupId="searchGroupId" fit="true" caption="日程管理" name="oaWorkScheduleTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="title" label="日程内容" width="200" query="true"></t:dgCol>
		<t:dgCol name="userName" label="参与人" width="70" query="true"></t:dgCol>
		<t:dgCol name="startTime" label="开始时间" width="120" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgCol name="endTime" label="结束时间" width="120" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/work/cal/delete?id={id}"/>
		<t:dgToolBar url="oa/work/cal/addorupdate" type="add" width="50%" height="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/cal/update" type="edit" width="50%" height="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/cal/update" type="view" width="50%" height="80%"></t:dgToolBar>
	</t:datagrid>
</body>

</html>