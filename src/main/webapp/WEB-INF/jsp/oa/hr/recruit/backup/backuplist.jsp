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
						<div id="recruitBackupTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/backup/datagrid" tableContentId="recruitBackupTable" searchGroupId="searchGroupId" fit="true" caption="后备资源库" name="recruitBackupTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="姓名" width="20" query="true"></t:dgCol>
		<t:dgCol name="sex" label="性别" width="20" dictionary="sex"/>
		<t:dgCol name="age" label="年龄" width="20"></t:dgCol>
		<t:dgCol name="educationBack" label="学历" width="30" dictionary="degreeinfo" query="true"></t:dgCol>
		<t:dgCol name="cvJob" label="职位" width="40" query="true"></t:dgCol>
		<t:dgCol name="telNo" label="联系电话" width="60"></t:dgCol>
		<t:dgCol name="email" label="电子邮箱" width="60"></t:dgCol>
		<t:dgCol name="status" label="状态" width="20" dictionary="cvstatus" query="true"></t:dgCol>
		<t:dgCol name="addTime" label="入库时间" width="60" datefmt="yyyy-MM-dd HH:mm:ss"></t:dgCol>
	</t:datagrid>
</body>

<script type="text/javascript">
		
		//简历下载
		function doCvAttachment() {
			var rowId = $('#recruitCVTableList').jqGrid('getGridParam','selrow');
			var rowData = $('#recruitCVTableList').jqGrid('getRowData',rowId);
			
			if(!rowId) {
				qhAlert('请选择简历后再下载！');
				return;
			}
			
			if(!rowData.cvAttachment) {
				qhAlert('该简历还未上传简历附件！');
				return;
			}
			
			location.href = "fileUploaderController.do?download&id=" + rowData.cvAttachment;				
		};
		
		//证书下载
		function doCertiAttachment() {
			var rowId = $('#recruitCVTableList').jqGrid('getGridParam','selrow');
			var rowData = $('#recruitCVTableList').jqGrid('getRowData',rowId);
			
			if(!rowId) {
				qhAlert('请选择简历后再下载！');
				return;
			}
			
			if(!rowData.certiAttachment) {
				qhAlert('该简历还未上传证书附件！');
				return;
			}
			
			location.href = "fileUploaderController.do?download&id=" + rowData.certiAttachment;				
		};
	
	</script>

</html>