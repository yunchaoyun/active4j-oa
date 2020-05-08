<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,treeview,icheck"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-7">
				<div class="row">
					<div class="col-sm-12" id="menuGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="menuTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-5">
				<div class="row">
					<div class="col-sm-12" id="buttonGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="buttonTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
			
			<!-- 页面隐藏字段 -->
			<input type="hidden" name="functionId" id="functionId" value="">
		</div>
	</div>

	<!-- 脚本部门 -->
	<!-- 菜单表格 -->
	<t:datagrid actionUrl="sys/menu/treeDataGrid" treeGrid="true" treeColomnName="functionName" tableContentId="menuTable" fit="true" caption="菜单管理" name="table_menu_list" pageSize="1000" height="600px" >
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="functionName" label="菜单名称" width="150"></t:dgCol>
		<t:dgCol name="functionUrl" label="菜单地址" width="250"></t:dgCol>
		<t:dgCol name="functionOrder" label="菜单排序" width="60"></t:dgCol>
		<t:dgCol name="parentId" label="上级菜单" hidden="true"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="140"></t:dgCol>
		<t:dgDelOpt label="删除" url="sys/menu/del?id={id}"/>
		<t:dgFunOpt label="设置按钮" funName="setBtnByFun(id)" icon="fa fa-check"></t:dgFunOpt>
		<t:dgToolBar url="sys/menu/addorupdate" type="add" width="60%"></t:dgToolBar>
		<t:dgToolBar url="sys/menu/addorupdate" type="edit" width="60%"></t:dgToolBar>
	</t:datagrid>
	
	<!-- 按钮表格 -->
	<t:datagrid actionUrl="sys/menu/opdategrid" tableContentId="buttonTable" fit="true" caption="按钮管理" name="table_button_list" pageSize="20" height="600px" >
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="操作名称"></t:dgCol>
		<t:dgCol name="code" label="代码"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="sys/menu/delop?id={id}"/>
		<t:dgToolBar url="sys/menu/addorupdateop" params="functionId" type="add" width="60%"></t:dgToolBar>
		<t:dgToolBar url="sys/menu/addorupdateop" type="edit" width="60%"></t:dgToolBar>
	</t:datagrid>
	
	<script type="text/javascript">
	
		//根据菜单设置按钮
		function setBtnByFun(functionId) {
			//页面隐藏字段的赋值
			$("#functionId").val(functionId);
			
			//按钮表的查询
			$("#table_button_list").jqGrid('setGridParam',{postData:{functionId : functionId}}).trigger('reloadGrid');
		}
	
	</script>
	
</body>
</html>
