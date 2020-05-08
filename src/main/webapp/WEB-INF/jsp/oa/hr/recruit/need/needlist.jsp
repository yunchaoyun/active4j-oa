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
						<div id="recruitNeedTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/need/datagrid" tableContentId="recruitNeedTable" multiSelect="true" searchGroupId="searchGroupId" fit="true" caption="招聘需求" name="recruitNeedTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="needJob" label="需求职位" width="60" query="true"></t:dgCol>
		<t:dgCol name="departName" label="需求部门" width="70"></t:dgCol>
		<t:dgCol name="needNum" label="需求人数" width="60"></t:dgCol>
		<t:dgCol name="preDate" label="预计用工时间" width="100" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="putDate" label="提出时间" width="100" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/recruit/need/del?id={id}"/>
		<t:dgToolBar url="oa/hr/recruit/need/addorupdate" type="view" width="50%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/need/addorupdate" type="add" width="50%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/need/addorupdate" type="edit" width="50%"></t:dgToolBar>
	</t:datagrid>

</body>

</html>