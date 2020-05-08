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
						<div id="recruitPlanTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/plan/datagrid" tableContentId="recruitPlanTable" searchGroupId="searchGroupId" fit="true" caption="招聘计划" name="recruitPlanTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="attachment" label="附件" hidden="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="计划名称" width="60" query="true"></t:dgCol>
		<t:dgCol name="canalType" label="招聘渠道" width="70" dictionary="canaltype" query="true"></t:dgCol>
		<t:dgCol name="planNum" label="需求人数" width="60"></t:dgCol>
		<t:dgCol name="startDate" label="用工时间(从)" width="60" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="endDate" label="用工时间(到)" width="60" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="money" label="预算费用(元)" width="50"></t:dgCol>
		<t:dgCol name="status" label="状态" width="20" dictionary="planstatus"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="60"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/recruit/plan/del?id={id}"/>
		<t:dgToolBar url="oa/hr/recruit/plan/addorupdate" type="view" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/plan/addorupdate" type="add" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/plan/addorupdate" type="edit" width="70%"></t:dgToolBar>
		<t:dgToolBar label="附件下载" icon="glyphicon glyphicon-resize-full" type="define" funName="doAttachment"></t:dgToolBar>
	</t:datagrid>

</body>
<script type="text/javascript">
		
		function doAttachment() {
			var rowId = $('#recruitPlanTableList').jqGrid('getGridParam','selrow');
			var rowData = $('#recruitPlanTableList').jqGrid('getRowData',rowId);
			
			if(!rowId) {
				qhAlert('请选择招聘计划后再下载！');
				return;
			}
			
			if(!rowData.attachment) {
				qhAlert('该招聘计划还未上传附件！');
				return;
			}
			
			location.href = "func/upload/download?id=" + rowData.attachment;				
		};
	
	</script>

</html>