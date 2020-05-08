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
						<div id="workflowCategoryTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="wf/flow/category/datagrid" tableContentId="workflowCategoryTable" searchGroupId="searchGroupId" fit="true" caption="流程类别" name="workflowCategoryList" pageSize="20" sortName="sort" sortOrder="asc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="类别名称" width="90" query="true"></t:dgCol>
		<t:dgCol name="sort" label="排序" width="60"></t:dgCol>
		<t:dgCol name="memo" label="备注" width="160"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="wf/flow/category/delete?id={id}"/>
		<t:dgToolBar url="wf/flow/category/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
		<t:dgToolBar url="wf/flow/category/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
		<t:dgToolBar url="wf/flow/category/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>
	</t:datagrid>
</body>

</html>