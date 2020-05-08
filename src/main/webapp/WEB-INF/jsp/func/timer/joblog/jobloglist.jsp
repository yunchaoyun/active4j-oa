<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,jqgrid,datetimePicker,laydate"></t:base>
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
	<t:datagrid actionUrl="func/timer/joblog/datagrid" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true" caption="定时任务日志管理" name="table_list_2" pageSize="20" sortName="startTime" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="shortName" label="任务简称" width="120" query="true"></t:dgCol>
		<t:dgCol name="jobGroup" label="任务分组" width="80" dictionary="func_timer_job_group" query="true"></t:dgCol>
		<t:dgCol name="invokeParams" label="调用参数" width="200"></t:dgCol>
		<t:dgCol name="jobMessage" label="日志信息" width="200"></t:dgCol>
		<t:dgCol name="status" label="状态" width="80" dictionary="func_timer_job_log_status" query="true"></t:dgCol>
		<t:dgCol name="startTime" label="执行时间" width="160" datefmt="yyyy-MM-dd HH:mm:ss" datePlugin="laydate" query="true" queryModel="group"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="90"></t:dgCol>
		<t:dgDelOpt label="删除" url="func/timer/joblog/del?id={id}"/>
		<t:dgToolBar url="func/timer/joblog/view" type="view" width="60%"></t:dgToolBar>
		<t:dgToolBar label="清空" icon="glyphicon glyphicon-floppy-remove" type="define" funName="empty"></t:dgToolBar>
	</t:datagrid>
	<script type="text/javascript">
		$(function(){
			laydate({elem:"#startTime_begin",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
			laydate({elem:"#startTime_end",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
		});
		
		//清空定时任务
		function empty() {
			qhConfirm("你确定要清空定时任务日志吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("func/timer/joblog/empty", function(data){
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