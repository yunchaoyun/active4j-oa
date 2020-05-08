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
						<div id="formEntityTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="wf/form/form/datagrid" tableContentId="formEntityTable" searchGroupId="searchGroupId" fit="true" caption="表单列表" name="formEntityList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="表单名称" width="90" query="true"></t:dgCol>
		<t:dgCol name="code" label="表单标识" width="90"></t:dgCol>
		<t:dgCol name="type" label="表单类型" width="90" dictionary="sysform"></t:dgCol>
		<t:dgCol name="path" label="表单路径" width="130"></t:dgCol>
		<t:dgCol name="categoryId" label="表单类别" replace="${categoryReplace}" query="true" queryId="categoryId" width="90"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="wf/form/form/delete?id={id}"/>
		<t:dgToolBar url="wf/form/form/addorupdate" type="add" width="90%"></t:dgToolBar>
		<t:dgToolBar url="wf/form/form/addorupdate" type="edit" width="90%"></t:dgToolBar>
		<t:dgToolBar url="wf/form/form/addorupdate" type="view" width="90%"></t:dgToolBar>
		<t:dgToolBar type="refresh" ></t:dgToolBar>
	</t:datagrid>
</body>
</html>