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
					<div class="ibox-content">
						<div id="oaUserlist" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/user/datagrid" tableContentId="oaUserlist" searchGroupId="searchGroupId" fit="true" caption="人事信息" name="oa_user_list" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="realName" label="真实姓名" width="90" query="true" queryId="realName"></t:dgCol>
		<t:dgCol name="sex" label="性别" dictionary="sex" width="80"></t:dgCol>
		<t:dgCol name="phoneNo" label="手机号" query="true" queryId="phoneNo" width="100"></t:dgCol>
		<t:dgCol name="officePhone" label="办公电话" width="100"></t:dgCol>
		<t:dgCol name="email" label="邮箱" width="200"></t:dgCol>
		<t:dgCol name="idCard" label="身份证号" width="200"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="90"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/user/del?id={id}"/>
		<t:dgToolBar url="oa/hr/user/addorupdate" type="view" width="60%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/user/addorupdate" type="add" width="60%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/user/addorupdate" type="edit" width="60%"></t:dgToolBar>
		<t:dgToolBar label="设为离职" icon="glyphicon glyphicon-resize-full" type="define" funName="leaveJob"></t:dgToolBar>
	</t:datagrid>
	
	<script type="text/javascript">
	
		$(function() {
			$.getScript("static/index/js/contabs.min.js");
		});
		
		//设为离职
		function leaveJob() {
			var rowId = $('#oa_user_list').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要离职的员工！');
				return;
			}
			
			qhConfirm("你确定要离职该员工吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("oa/hr/user/doLeaveJob", {id : rowId}, function(d){
					if(d.success) {
						qhTipSuccess(d.msg);
						//操作结束，刷新表格
						reloadTable('oa_user_list');
					}
				}); 
				
			}, function() {
				//否
			});
		}
	
	</script>

</body>