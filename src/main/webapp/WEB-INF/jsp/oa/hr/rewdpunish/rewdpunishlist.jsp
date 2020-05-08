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
						<div id=oaUserRewdPunishTable class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/rewdpunish/datagrid" tableContentId="oaUserRewdPunishTable" searchGroupId="searchGroupId" fit="true" caption="奖惩记录" name="oaUserRewdPunishTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="attachment" label="编号" hidden="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="姓名" width="60" query="true"></t:dgCol>
		<t:dgCol name="rpType" label="奖惩区分" width="70" dictionary="rewdpunishtype" query="true"></t:dgCol>
		<t:dgCol name="items" label="奖惩名目" width="70"></t:dgCol>
		<t:dgCol name="rpMoney" label="奖惩金额" width="70"></t:dgCol>
		<t:dgCol name="rpDemo" label="奖惩原因" width="100"></t:dgCol>
		<t:dgCol name="rpDate" label="奖惩日期" width="70" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/rewdpunish/del?id={id}"/>
		<t:dgToolBar url="oa/hr/rewdpunish/edit" type="view" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/rewdpunish/add" type="add" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/rewdpunish/edit" type="edit" width="70%"></t:dgToolBar>
		<t:dgToolBar label="附件下载" icon="glyphicon glyphicon-resize-full" type="define" funName="doAttachment"></t:dgToolBar>
	</t:datagrid>

</body>

<script type="text/javascript">
		
		function doAttachment() {
			var rowId = $('#oaUserRewdPunishTableList').jqGrid('getGridParam','selrow');
			var rowData = $('#oaUserRewdPunishTableList').jqGrid('getRowData',rowId);
			
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