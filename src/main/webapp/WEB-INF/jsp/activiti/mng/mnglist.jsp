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
					<div class="ibox-content" style="height: 100% ">
						<div id="workflowMngTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="wf/flow/mng/datagrid" tableContentId="workflowMngTable" searchGroupId="searchGroupId" fit="true" caption="流程管理" name="workflowMngList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="workflowNo" label="流程编号" width="60" query="true"></t:dgCol>
		<t:dgCol name="name" label="流程名称" width="90" query="true"></t:dgCol>
		<t:dgCol name="categoryId" label="流程类别" replace="${categoryReplace}" query="true" queryId="categoryId" width="70"></t:dgCol>
		<t:dgCol name="status" label="流程状态" width="60" query="true" dictionary="workflowstatus" display="status"></t:dgCol>
		<t:dgCol name="type" label="权限类型" width="60" replace="所有用户_0,指定用户_1"></t:dgCol>
		<t:dgCol name="formId" label="表单名称" replace="${formReplace}" width="90"></t:dgCol>
		<t:dgCol name="processDefineId" label="流程定义ID" width="90"></t:dgCol>
		<t:dgCol name="processKey" label="流程key" width="100"></t:dgCol>
		<t:dgCol name="deployId" label="部署ID" width="120"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="wf/flow/mng/del?id={id}"/>
		<t:dgToolBar url="wf/flow/mng/addorupdate" type="addNo" width="90%"></t:dgToolBar>
		<t:dgToolBar url="wf/flow/mng/addorupdate" type="editNo" width="90%"></t:dgToolBar>
		<t:dgToolBar type="refresh" ></t:dgToolBar>
		<t:dgToolBar label="停用" icon="fa fa-lock" type="define" funName="lockFlow"></t:dgToolBar>
		<t:dgToolBar label="启用" icon="fa fa-unlock" type="define" funName="unLockFlow"></t:dgToolBar>
		<t:dgToolBar label="启用最新" icon="fa fa-unlock" type="define" funName="startNew"></t:dgToolBar>
		<t:dgToolBar label="预览" icon="fa fa-binoculars" url="wf/flow/mng/viewImage" type="read" width="80%" height="80%"></t:dgToolBar>
	</t:datagrid>
</body>

	<script type="text/javascript">
	//停用流程
	function lockFlow() {
		var rowId = $('#workflowMngList').jqGrid('getGridParam','selrow');
		if(!rowId) {
			qhAlert('请选择要编辑的项目');
			return;
		}
		
		var rowData = $('#workflowMngList').jqGrid('getRowData',rowId);
		
		if('过期' == rowData.status) {
			qhAlert('过期流程无法停用');
			return;
		}
		
		qhConfirm("你确定要停用该流程吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("wf/flow/mng/lock", {id : rowId}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					//操作结束，刷新表格
					reloadTable('workflowMngList');
				}else {
					qhTipWarning(d.msg);
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//启用流程
	function unLockFlow() {
		var rowId = $('#workflowMngList').jqGrid('getGridParam','selrow');
		if(!rowId) {
			qhAlert('请选择要编辑的项目');
			return;
		}
		
		var rowData = $('#workflowMngList').jqGrid('getRowData',rowId);
		
		if('过期' == rowData.status) {
			qhAlert('过期流程无法启用');
			return;
		}
		
		qhConfirm("你确定要启用该流程吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("wf/flow/mng/unLock", {id : rowId}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					//操作结束，刷新表格
					reloadTable('workflowMngList');
				}else {
					qhTipWarning(d.msg);
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//启用最新流程
	function startNew() {
		var rowId = $('#workflowMngList').jqGrid('getGridParam','selrow');
		if(!rowId) {
			qhAlert('请选择要编辑的项目');
			return;
		}
		qhConfirm("你确定要刷新该流程，使用最新版本吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			//是
			$.post("wf/flow/mng/startNew", {id : rowId}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					//操作结束，刷新表格
					reloadTable('workflowMngList');
				}else {
					qhTipWarning(d.msg);
				}
			}); 
			
		}, function() {
			//否
		});
	}
	</script>

</html>