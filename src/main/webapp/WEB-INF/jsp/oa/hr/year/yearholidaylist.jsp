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
						<div id="yearHolidayTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/yearholiday/datagrid" tableContentId="yearHolidayTable" searchGroupId="searchGroupId" fit="true" caption="年假" name="yearHolidayTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="userName" label="姓名" width="60" query="true"></t:dgCol>
		<t:dgCol name="departName" label="部门" width="70"></t:dgCol>
		<t:dgCol name="optType" label="操作类型" width="70" dictionary="opttype"></t:dgCol>
		<t:dgCol name="days" label="小时数" width="50"></t:dgCol>
		<t:dgCol name="memo" label="备注" width="180"></t:dgCol>
		<t:dgCol name="departId" label="部门" hidden="true" query="true" common="depart" queryId="deptId" valueId="deptId"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/yearholiday/del?id={id}"/>
		<t:dgToolBar url="oa/hr/yearholiday/edit" type="view" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/yearholiday/add" type="add" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/yearholiday/edit" type="edit" width="70%"></t:dgToolBar>
	</t:datagrid>

</body>

</html>