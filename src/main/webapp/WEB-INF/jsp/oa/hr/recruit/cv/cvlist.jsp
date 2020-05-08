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
						<div id="recruitCVTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/cv/datagrid" tableContentId="recruitCVTable" searchGroupId="searchGroupId" fit="true" caption="简历库" name="recruitCVTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="certiAttachment" label="证书附件" hidden="true"></t:dgCol>
		<t:dgCol name="cvAttachment" label="简历附件" hidden="true"></t:dgCol>
		<t:dgCol name="name" label="姓名" width="40" query="true"></t:dgCol>
		<t:dgCol name="sex" label="性别" width="40" dictionary="sex"/>
		<t:dgCol name="age" label="年龄" width="40"></t:dgCol>
		<t:dgCol name="educationBack" label="学历" width="50" dictionary="degreeinfo" query="true"></t:dgCol>
		<t:dgCol name="cvJob" label="职位" width="60" query="true"></t:dgCol>
		<t:dgCol name="telNo" label="联系电话" width="60"></t:dgCol>
		<t:dgCol name="email" label="电子邮箱" width="60"></t:dgCol>
		<t:dgCol name="status" label="状态" width="40" dictionary="cvstatus" query="true"></t:dgCol>
		<t:dgCol name="createDate" label="入库时间" width="60" datefmt="yyyy-MM-dd HH:mm:ss"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="60"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/recruit/cv/del?id={id}"/>
		<t:dgToolBar url="oa/hr/recruit/cv/addorupdate" type="view" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/cv/addorupdate" type="add" width="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/cv/addorupdate" type="edit" width="80%"></t:dgToolBar>
		<t:dgToolBar label="证书附件下载" icon="glyphicon glyphicon-arrow-down" type="define" funName="doCertiAttachment"></t:dgToolBar>
		<t:dgToolBar label="简历附件下载" icon="glyphicon glyphicon-arrow-down" type="define" funName="doCvAttachment"></t:dgToolBar>
		<t:dgToolBar label="加入后备资源库" icon="glyphicon glyphicon-share-alt" type="define" funName="doAddBackup"></t:dgToolBar>
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
			
			location.href = "func/upload/download?id=" + rowData.cvAttachment;				
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
			
			location.href = "func/upload/download?id=" + rowData.certiAttachment;				
		};
		
		//加入后备资源库
		function doAddBackup() {
			var rowId = $('#recruitCVTableList').jqGrid('getGridParam','selrow');
			
			if(!rowId) {
				qhAlert('请选择简历后再加入！');
				return;
			}
			
			qhConfirm("你确定要加入后备资源库吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("oa/hr/recruit/cv/doAddBackup", {id : rowId}, function(d){
					if(d.success) {
						qhTipSuccess(d.msg);
						//操作结束，刷新表格
						reloadTable('recruitCVTableList');
					}
				}); 
				
			}, function() {
				//否
			});		
		};
	
	</script>

</html>