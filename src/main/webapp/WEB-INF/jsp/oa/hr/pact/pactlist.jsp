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
						<div id="oaUserPactTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/pact/datagrid" tableContentId="oaUserPactTable" searchGroupId="searchGroupId" fit="true" caption="人事合同" name="oaUserPactTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="attachment" label="附件" hidden="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="姓名" width="60" query="true"></t:dgCol>
		<t:dgCol name="userNo" label="工号" width="60" query="true"></t:dgCol>
		<t:dgCol name="pactNo" label="合同编号" width="70"></t:dgCol>
		<t:dgCol name="pactType" label="合同类型" width="70" dictionary="contracttype"></t:dgCol>
		<t:dgCol name="EffectDate" label="生效期" width="100" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="noEffectDate" label="失效期" width="100" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/pact/del&id={id}"/>
		<t:dgToolBar url="oa/hr/pact/edit" type="view" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/pact/add" type="add" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/pact/edit" type="edit" width="70%"></t:dgToolBar>
		<t:dgToolBar label="附件下载" icon="glyphicon glyphicon-resize-full" type="define" funName="doAttachment"></t:dgToolBar>
	</t:datagrid>

</body>

<script type="text/javascript">
		
		function doAttachment() {
			var rowId = $('#oaUserPactTableList').jqGrid('getGridParam','selrow');
			var rowData = $('#oaUserPactTableList').jqGrid('getRowData',rowId);
			
			if(!rowId) {
				qhAlert('请选择员工后再下载！');
				return;
			}
			
			if(!rowData.attachment) {
				qhAlert('该员工还未上传附件！');
				return;
			}
			
			location.href = "func/upload/download?id=" + rowData.attachment;				
		};
	
	</script>

</html>