<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,prettyfile"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12" id="searchGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="workflowDefineTableId" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="wf/flow/deploy/datagridDef" tableContentId="workflowDefineTableId" fit="true" caption="流程定义列表" searchGroupId="searchGroupId" name="workflowDeployList" pageSize="20">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="id" label="定义ID"  width="140"></t:dgCol>
		<t:dgCol name="name" label="流程名称"  width="120" query="true"></t:dgCol>
		<t:dgCol name="key" label="流程key"  width="80"></t:dgCol>
		<t:dgCol name="version" label="流程版本"  width="40"></t:dgCol>
		<t:dgCol name="deploymentId" label="部署ID"  width="140"></t:dgCol>
		<t:dgCol name="isLastest" label="是否显示最新版本" dictionary="byesorno" hidden="true" query="true" labelCol="3" defval="true"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="wf/flow/deploy/deleteDef?id={id}"/>
		<t:dgToolBar label="查看流程图" icon="fa fa-binoculars" url="wf/flow/deploy/viewImage" type="read" width="80%" height="80%"></t:dgToolBar>
		<t:dgToolBar label="彻底删除流程" icon="fa fa-remove" type="define" funName="deleteAll"></t:dgToolBar>
		<t:dgToolBar type="refresh" ></t:dgToolBar>
	</t:datagrid>

</body>
<script type="text/javascript">
	function deleteAll() {
		var rowId = $('#workflowDeployList').jqGrid('getGridParam','selrow');
		if(!rowId) {
			qhAlert('请选择要删除的项目');
			return;
		}
		
		qhConfirm("你确定要删除该流程实例吗?这将会删除所有有关数据！", function(index) {
			// 关闭询问
			parent.layer.close(index);

			// 执行操作
			$.post("wf/flow/deploy/deleteAll", {id:rowId}, function(data) {
				var o = $.parseJSON(data);
				if (o.success) {
					// 成功提示
					qhTipSuccess(o.msg);
					// 刷新表格
					reloadTable('workflowDeployList');
				} else {
					// 失败提示
					qhTipWarning(o.msg);
				}
			});
		}, function() {

		});
		
		
	}


</script>


</html>