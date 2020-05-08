<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid"></t:base>
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
						<div id="trainForceTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/train/force/datagrid" tableContentId="trainForceTable" searchGroupId="searchGroupId" fit="true" caption="面试记录" name="trainForceTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="planName" label="培训名称" width="60" query="true" queryId="planName"></t:dgCol>
		<t:dgCol name="startDate" label="开始日期" width="20" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="endDate" label="结束日期" width="20" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="place" label="培训地点" width="30"></t:dgCol>
		<t:dgCol name="canPerson" label="参与人员" width="40"></t:dgCol>
		<t:dgCol name="leadPerson" label="负责人" width="20"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="20"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/train/force/del?id={id}"/>
		<t:dgToolBar url="oa/hr/train/force/addorupdate" type="view" width="60%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/force/addorupdate" type="add" width="60%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/force/addorupdate" type="edit" width="60%"></t:dgToolBar>
	</t:datagrid>
</body>

</html>