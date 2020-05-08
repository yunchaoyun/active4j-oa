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
						<div id="trainPlanTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/train/plan/datagrid" tableContentId="trainPlanTable" searchGroupId="searchGroupId" fit="true" caption="培训计划" name="trainPlanTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="计划名称" width="40" query="true"></t:dgCol>
		<t:dgCol name="cycle" label="计划周期" width="20" dictionary="plancycle" query="true"></t:dgCol>
		<t:dgCol name="startDate" label="开始日期" width="40" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="endDate" label="结束日期" width="40" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="target" label="培训目标" width="60"></t:dgCol>
		<t:dgCol name="memo" label="培训说明" width="60"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="30" />
		<t:dgDelOpt label="删除" url="oa/hr/train/plan/del?id={id}"/> 
		<t:dgToolBar url="oa/hr/train/plan/addorupdate" type="view" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/plan/addorupdate" type="add" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/plan/addorupdate" type="edit" width="70%"></t:dgToolBar>
	</t:datagrid>

</body>

</html>