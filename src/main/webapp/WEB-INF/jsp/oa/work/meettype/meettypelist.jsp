<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,jqgrid"></t:base>
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
						<div id="oaWorkMeetTypeTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/work/meetType/datagrid" tableContentId="oaWorkMeetTypeTable" searchGroupId="searchGroupId" fit="true" caption="会议类型" name="oaWorkMeetTypeTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="会议类型名称" width="120"></t:dgCol>
		<t:dgCol name="memo" label="备注" width="160"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/work/meetType/delete?id={id}"/>
		<t:dgToolBar url="oa/work/meetType/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/meetType/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/meetType/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>
	</t:datagrid>
</body>

</html>