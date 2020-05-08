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
						<div id="jqGrid_wrapper" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="sys/user/datagrid" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true" caption="用户管理" name="table_list_2" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="userName" label="用户名" width="90" query="true"></t:dgCol>
		<t:dgCol name="realName" label="真实姓名" width="90" query="true"></t:dgCol>
		<t:dgCol name="admin" label="是否管理员" dictionary="byesorno" query="true"></t:dgCol>
		<t:dgCol name="deptId" label="部门" replace="${departsReplace}" query="true" common="depart" queryId="deptId" valueId="deptId"></t:dgCol>
		<t:dgCol name="status" label="状态" replace="正常_1,禁用_0" query="true"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="290"></t:dgCol>
		<t:dgDelOpt label="删除" url="sys/user/del?id={id}" operationCode="sys:user:del"/>
		<t:dgToolBar url="sys/user/addorupdate" type="add" width="60%" operationCode="sys:user:add"></t:dgToolBar>
		<t:dgToolBar url="sys/user/addorupdate" type="edit" width="60%" operationCode="sys:user:edit"></t:dgToolBar>
		<t:dgToolBar label="重置密码" icon="fa fa-cog" url="sys/user/changepwd" type="pop" width="40%" height="50%" operationCode="sys:user:password"></t:dgToolBar>
		<t:dgToolBar label="锁定用户" icon="fa fa-lock" type="define" funName="lockUser" operationCode="sys:user:lock"></t:dgToolBar>
		<t:dgToolBar label="解锁用户" icon="fa fa-unlock" type="define" funName="unLockUser" operationCode="sys:user:unlock"></t:dgToolBar>
	</t:datagrid>
<script type="text/javascript">
		//锁定用户
		function lockUser() {
			var rowId = $('#table_list_2').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要编辑的项目');
				return;
			}
			
			qhConfirm("你确定要锁定该用户吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("sys/user/lock", {id : rowId}, function(data){
					if(data.success) {
						qhTipSuccess(data.msg);
						//操作结束，刷新表格
						reloadTable('table_list_2');
					}else {
						qhTipWarning(data.msg);
					}
				}); 
				
			}, function() {
				//否
			});
		}
		//解锁用户
		function unLockUser() {
			var rowId = $('#table_list_2').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要编辑的项目');
				return;
			}
			
			qhConfirm("你确定要解锁该用户吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("sys/user/unlock", {id : rowId}, function(data){
					if(data.success) {
						qhTipSuccess(data.msg);
						//操作结束，刷新表格
						reloadTable('table_list_2');
					}else {
						qhTipWarning(data.msg);
					}
				}); 
				
			}, function() {
				//否
			});
		}
</script>
</body>
</html>