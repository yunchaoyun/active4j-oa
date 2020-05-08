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
	<t:datagrid actionUrl="func/timer/job/datagrid" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true" caption="定时任务管理" name="table_list_2" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="shortName" label="任务简称" width="120" query="true"></t:dgCol>
		<t:dgCol name="jobGroup" label="任务分组" width="80" dictionary="func_timer_job_group" query="true"></t:dgCol>
		<t:dgCol name="invokeParams" label="调用参数" width="200"></t:dgCol>
		<t:dgCol name="cronExpression" label="执行表达式" width="120"></t:dgCol>
		<t:dgCol name="jobStatus" label="状态" width="80" dictionary="func_timer_job_status" query="true"></t:dgCol>
		<t:dgCol name="jobExecuteStatus" label="执行状态" width="90" dictionary="func_timer_job_execute_status"></t:dgCol>
		<t:dgCol name="createDate" label="创建时间" width="160" datefmt="yyyy-MM-dd HH:mm:ss" datePlugin="laydate"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="90"></t:dgCol>
		<t:dgDelOpt label="删除" url="func/timer/job/del?id={id}"/>
		<t:dgToolBar url="func/timer/job/view" type="view" width="60%"></t:dgToolBar>
		<t:dgToolBar url="func/timer/job/addorupdate" type="add" width="60%"></t:dgToolBar>
		<t:dgToolBar url="func/timer/job/addorupdate" type="edit" width="60%"></t:dgToolBar>
		<t:dgToolBar label="启用" icon="glyphicon glyphicon-play" type="define" funName="jobStart"></t:dgToolBar>
		<t:dgToolBar label="暂停" icon="glyphicon glyphicon-pause" type="define" funName="jobPause"></t:dgToolBar>
		<t:dgToolBar label="执行一次" icon="glyphicon glyphicon-saved" type="define" funName="jobOne"></t:dgToolBar>
	</t:datagrid>
	<script type="text/javascript">
		//启用任务
		function jobStart() {
			var rowId = $('#table_list_2').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要启用的任务');
				return;
			}
			
			qhConfirm("你确定要启用该任务吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("func/timer/job/start", {id : rowId}, function(data){
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
		
		//暂停任务
		function jobPause() {
			var rowId = $('#table_list_2').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要暂停的任务');
				return;
			}
			
			qhConfirm("你确定要暂停该任务吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("func/timer/job/pause", {id : rowId}, function(data){
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
		
		//执行一次
		function jobOne() {
			var rowId = $('#table_list_2').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要执行的任务');
				return;
			}
			
			qhConfirm("你确定要执行一次该任务吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("func/timer/job/one", {id : rowId}, function(data){
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